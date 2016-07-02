package com.company.commands;

import com.company.exceptions.InvalidInputException;
import com.company.io.IOManager;
import com.company.judge.Tester;
import com.company.network.DownloadManager;
import com.company.repository.StudentRepository;

public class ShowWantedCourseCommand extends Command {
    public ShowWantedCourseCommand(String input,
                            String[] data,
                            StudentRepository studentRepository,
                            Tester tester,
                            IOManager ioManager,
                            DownloadManager downloadManager) {
        super(input, data, studentRepository, tester, ioManager, downloadManager);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 2 && data.length != 3){
            throw new InvalidInputException(this.getInput());
        }

        if (data.length == 2){
            String courseName = data[1];
            this.getStudentRepository().getStudentsByCourse(courseName);
        } else {
            String courseName = data[1];
            String student = data[2];
            this.getStudentRepository().getStudentMarksInCourse(courseName, student);
        }
    }
}
