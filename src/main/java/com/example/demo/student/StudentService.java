package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Service Layer
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        checkIfEmailIsTaken(student.getEmail());
        // Instantiates a new student object
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        getExistingStudentById(studentId);
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email, Integer favNum) {
        Student student = getExistingStudentById(studentId);
        if(name != null && name.length() > 0 && !Objects.equals((student.getName()), name)) {
            // allows duplicate names
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals((student.getEmail()), email)) {
            // does not allow duplicate emails
            checkIfEmailIsTaken(email);
            student.setEmail(email);
        }

        if(favNum != null && !Objects.equals((student.getFavNum()), favNum)) {
            student.setFavNum(favNum);
        }
    }

    /**
     * Checks if student exists by Id
     * @param studentId
     * @return student object if existing, else throws exception
     */
    private Student getExistingStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist"));
        return student;
    }

    /**
     * Checks if email is taken by another student
     * @param email
     */
    private void checkIfEmailIsTaken(String email) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
    }

}
