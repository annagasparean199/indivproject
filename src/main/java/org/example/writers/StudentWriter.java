package org.example.writers;

import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentWriter implements ItemWriter<Student> {

    @Autowired
    private StudentRepository financialDataRepository;

    @Override
    public void write(Chunk<? extends Student> list) throws Exception {
        financialDataRepository.saveAll(list);
    }
}