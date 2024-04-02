package org.example.validator;

import org.example.model.Student;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class StudentValidator implements Validator<Student> {

    @Override
    public void validate(Student item) throws ValidationException {
        validateId(item.getId());
        validateFirstName(item.getFirstName());
        validateLastName(item.getLastName());
        validateFaculty(item.getFaculty());
        validateYearOfBirth(item.getYearOfBirth());
        validateYearOfAdmission(item.getYearOfAdmission());
        validateIdnp(item.getIdnp());
    }

    void validateId(Long id) throws ValidationException {
        // Additional ID validation logic can be added here if needed
    }

    void validateFirstName(String firstName) throws ValidationException {
        if (firstName == null || firstName.isEmpty()) {
            throw new ValidationException("First name cannot be null or empty");
        }
    }

    void validateLastName(String lastName) throws ValidationException {
        if (lastName == null || lastName.isEmpty()) {
            throw new ValidationException("Last name cannot be null or empty");
        }
    }

    void validateFaculty(String faculty) throws ValidationException {
        if (!isValidFaculty(faculty)) {
            throw new ValidationException("Invalid faculty: " + faculty);
        }
    }

    boolean isValidFaculty(String faculty) {
        // Only allow specific faculties
        String[] allowedFaculties = {"Mathematics", "Informatics", "Physics", "Chemistry"};
        for (String allowedFaculty : allowedFaculties) {
            if (allowedFaculty.equalsIgnoreCase(faculty)) {
                return true;
            }
        }
        return false;
    }

    void validateYearOfBirth(int yearOfBirth) throws ValidationException {
        if (yearOfBirth < 1995 || yearOfBirth > 2004) {
            throw new ValidationException("Invalid year of birth: " + yearOfBirth);
        }
    }

    void validateYearOfAdmission(int yearOfAdmission) throws ValidationException {
        if (yearOfAdmission < 2014 || yearOfAdmission > 2023) {
            throw new ValidationException("Invalid year of admission: " + yearOfAdmission);
        }
    }

    void validateIdnp(String idnp) throws ValidationException {
        if (idnp == null || idnp.length() != 13 || !idnp.matches("\\d+")) {
            throw new ValidationException("Invalid IDNP: " + idnp);
        }
    }
}
