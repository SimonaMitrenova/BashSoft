package com.company.models;

import com.company.io.OutputWriter;
import com.company.staticData.ExceptionMessages;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Course {
    public static final int NUMBER_OF_TASKS_ON_EXAM = 5;
    public static final int MAX_SCORE_ON_EXAM_TASK = 100;

    private String name;
    private HashMap<String, Student> studentsByName;

    public Course(String name) {
        this.name = name;
        this.studentsByName = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Student> getStudentsByName() {
        return studentsByName;
    }

    public void enrollStudent(Student student){
        if (this.studentsByName.containsKey(student.getUserName())){
            OutputWriter.displayException(
                    String.format(ExceptionMessages.STUDENT_ALREADY_ENROLLED_IN_GIVEN_COURSE, student.getUserName(), this.name));
            return;
        }

        this.studentsByName.put(student.getUserName(), student);
    }
}
