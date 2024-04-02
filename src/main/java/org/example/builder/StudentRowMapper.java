package org.example.builder;

import org.example.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setFirstname(rs.getString("firstname"));
        student.setLastname(rs.getString("lastname"));
        student.setFaculty(rs.getString("faculty"));
        student.setYearOfBirth(rs.getInt("year_of_birth"));
        student.setYearOfAdmission(rs.getInt("year_of_admission"));
        student.setIdnp(rs.getString("idnp"));
        return student;
    }
}
