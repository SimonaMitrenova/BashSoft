package com.company.repository.contracts;

public interface Requester {
    void getStudentMarksInCourse(String course, String student);
    void getStudentsByCourse(String course);
}
