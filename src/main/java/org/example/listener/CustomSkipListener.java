package org.example.listener;

import org.example.model.Student;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomSkipListener implements SkipListener<Student, Student> {

    private final FlatFileItemWriter<Student> failedRecordsWriter;

    public CustomSkipListener(FlatFileItemWriter<Student> failedRecordsWriter) {
        this.failedRecordsWriter = failedRecordsWriter;
    }

    @Override
    public void onSkipInRead(Throwable throwable) {
        System.out.println("Skipping invalid record during Read");
    }

    @Override
    public void onSkipInWrite(Student item, Throwable throwable) {
        System.out.println("Skipping invalid record during Write: " + item);
    }

    @Override
    public void onSkipInProcess(Student item, Throwable throwable) {
        try {
            failedRecordsWriter.open(new ExecutionContext());
            failedRecordsWriter.write(new Chunk<>(Collections.singletonList(item)));
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
    }
}