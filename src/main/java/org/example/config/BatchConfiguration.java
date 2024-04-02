package org.example.config;

import jakarta.validation.constraints.NotNull;
import org.example.listener.CustomSkipListener;
import org.example.model.Student;
import org.example.readers.StudentDataReader;
import org.example.validator.StudentValidator;
import org.example.writers.StudentWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.example.utils.UtilVariables.*;

@Configuration
public class BatchConfiguration {

    @Bean
    private static StudentDataReader getFinancialIndustryDataReader() {
        return new StudentDataReader();
    }

    @Bean
    public static FlatFileItemReader<Student> studentFlatFileItemReader(StudentDataReader studentDataReader) {
        FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(CSV_FILE_PATH));
        reader.setLinesToSkip(1);
        reader.setLineMapper(studentDataReader.getLineMapper());
        return reader;
    }

    @Bean
    public static ItemProcessor<Student, Student>  studentValidatorProcessor() {
        return new ItemProcessor<>() {
            private final StudentValidator validator = new StudentValidator();

            @Override
            public Student process(Student item) {
                validator.validate(item);
                return item;
            }
        };
    }

    @Bean
    public static FlatFileItemWriter<Student> failedStudentRecordsWriter() {
        FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(FAILED_RECORDS_PATH_STUDENT));
        writer.setHeaderCallback(writer1 -> writer1.write(CSV_FIELD_NAMES));
        LineAggregator<Student> lineAggregator = createStudentLineAggregator();
        writer.setLineAggregator(lineAggregator);

        return writer;
    }

    static LineAggregator<Student> createStudentLineAggregator() {
        DelimitedLineAggregator<Student> lineAggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<Student> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(STUDENT_DB_FIELDS);
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    @Bean
    public ItemWriter<Student> studentWriter() {
        return new StudentWriter();
    }

    @Bean
    public Step studentStep(PlatformTransactionManager transactionManager,
                            JobRepository jobRepository, FlatFileItemReader<Student> studentReader) {
        CustomSkipListener skipListener = new CustomSkipListener(failedStudentRecordsWriter());

        return new StepBuilder("studentStep", jobRepository)
                .<Student, Student>chunk(100, transactionManager)
                .reader(studentReader)
                .processor(studentValidatorProcessor())
                .faultTolerant()
                .skipPolicy((Throwable t, long skipCount) -> t instanceof ValidationException)
                .skip(ValidationException.class)
                .listener(skipListener)
                .allowStartIfComplete(true)
                .writer(studentWriter())
                .build();
    }

    @Bean
    public Step moveFileStepStudent(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("moveFileStepStudent", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    File sourceFile = new File(RESOURCES_FOLDER_PATH + CSV_FILE_PATH);
                    File destinationFolder = new File(RESOURCES_FOLDER_PATH + FOLDER_PATH_AFTER_EXECUTION);
                    Files.move(sourceFile.toPath(), destinationFolder.toPath().resolve(sourceFile.getName()),
                            StandardCopyOption.REPLACE_EXISTING);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job importStudentsJob(JobRepository jobRepository, Step studentStep, Step moveFileStepStudent) {
        return new JobBuilder("importStudentJob", jobRepository)
                .start(studentStep)
                .next(moveFileStepStudent)
                .build();
    }
}
