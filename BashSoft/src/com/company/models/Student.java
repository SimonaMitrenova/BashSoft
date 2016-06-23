package com.company.models;

import com.company.io.OutputWriter;
import com.company.staticData.ExceptionMessages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Student {
    private String userName;
    private HashMap<String, Course> enrolledCourses;
    private HashMap<String, Double> marksByCourseName;

    public Student(String userName) {
        this.userName = userName;
        this.enrolledCourses = new LinkedHashMap<>();
        this.marksByCourseName = new LinkedHashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public HashMap<String, Double> getMarksByCourseName() {
        return marksByCourseName;
    }

    public void enrollInCourse(Course course){
        if (this.enrolledCourses.containsKey(course.getName())){
            OutputWriter.displayException(
                    String.format(ExceptionMessages.STUDENT_ALREADY_ENROLLED_IN_GIVEN_COURSE, this.userName, course.getName()));
            return;
        }

        this.enrolledCourses.put(course.getName(), course);
    }

    public void setMarksInCourse(String courseName, int ... scores){
        if (!this.enrolledCourses.containsKey(courseName)){
            OutputWriter.displayException(ExceptionMessages.NOT_ENROLLED_IN_COURSE);
            return;
        }
        if (scores.length > Course.NUMBER_OF_TASKS_ON_EXAM){
            OutputWriter.displayException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
            return;
        }

        double mark = this.calculateMark(scores);
        this.marksByCourseName.put(courseName, mark);
    }

    private double calculateMark(int[] scores) {
        double percentageOfSolvedExam = Arrays.stream(scores).sum() /
                (double) (Course.NUMBER_OF_TASKS_ON_EXAM * Course.MAX_SCORE_ON_EXAM_TASK);

        double mark = percentageOfSolvedExam * 4 + 2;
        return mark;
    }
}
