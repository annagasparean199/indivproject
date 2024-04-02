package org.example.readers;

import org.example.model.Student;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import static org.example.utils.UtilVariables.STUDENT_DB_FIELDS;

@Component
public class StudentDataReader {

    public LineMapper<Student> getLineMapper() {
        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(getTokenizer());
        lineMapper.setFieldSetMapper(fieldSet -> {
            Student student = new Student();
            student.setId(Long.parseLong(fieldSet.readString("id")));
            student.setFirstName(fieldSet.readString("firstname"));
            student.setLastName(fieldSet.readString("lastname"));
            student.setFaculty(fieldSet.readString("faculty"));
            student.setYearOfBirth(Integer.parseInt(fieldSet.readString("year_of_birth")));
            student.setYearOfAdmission(Integer.parseInt(fieldSet.readString("year_of_admission")));
            student.setIdnp(fieldSet.readString("idnp"));
            return student;
        });
        return lineMapper;
    }

    private DelimitedLineTokenizer getTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(STUDENT_DB_FIELDS);
        return tokenizer;
    }
}
