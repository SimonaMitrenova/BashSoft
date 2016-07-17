package com.company.commands;

import com.company.collections.contracts.SimpleOrderedBag;
import com.company.commands.contracts.Executable;
import com.company.exceptions.InvalidInputException;
import com.company.io.OutputWriter;
import com.company.io.contracts.DirectoryManager;
import com.company.judge.contracts.ContentComparer;
import com.company.models.contracts.Course;
import com.company.models.contracts.Student;
import com.company.network.contracts.AsynchDownloader;
import com.company.repository.contracts.Database;

import java.util.Comparator;

public class DisplayCommand extends Command implements Executable{
    public DisplayCommand(String input,
                   String[] data,
                   Database studentRepository,
                   ContentComparer tester,
                   DirectoryManager ioManager,
                   AsynchDownloader downloadManager) {
        super(input, data, studentRepository, tester, ioManager, downloadManager);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 3){
            throw new InvalidInputException(this.getInput());
        }

        String entryToDisplay = data[1];
        String sortType = data[2];

        if (entryToDisplay.equalsIgnoreCase("students")){
            Comparator<Student> studentComparator = this.createStudentComparator(sortType);
            SimpleOrderedBag<Student> sortedStudents = this.getStudentRepository().getAllStudentsSorted(studentComparator);
            OutputWriter.writeMessageOnNewLine(sortedStudents.joinWith(System.lineSeparator()));

        } else if (entryToDisplay.equalsIgnoreCase("courses")){

            Comparator<Course> courseComparator = this.createCourseComparator(sortType);
            SimpleOrderedBag<Course> sortedCourses = this.getStudentRepository().getAllCoursesSorted(courseComparator);
            OutputWriter.writeMessageOnNewLine(sortedCourses.joinWith(System.lineSeparator()));

        } else {
            throw new InvalidInputException(this.getInput());
        }
    }

    private Comparator<Course> createCourseComparator(String sortType) {
        switch (sortType){
            case "ascending":
                return (o1, o2) -> o1.compareTo(o2);

            case "descending":
                return (o1, o2) -> o2.compareTo(o1);

            default:
                throw new InvalidInputException(this.getInput());
        }
    }


    private Comparator<Student> createStudentComparator(String sortType) {
        switch (sortType){
            case "ascending":
                return Comparable::compareTo;

            case "descending":
                return (o1, o2) -> o2.compareTo(o1);

            default:
                throw new InvalidInputException(this.getInput());
        }
    }
}
