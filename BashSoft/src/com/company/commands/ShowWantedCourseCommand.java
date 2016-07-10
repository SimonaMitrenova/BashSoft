package com.company.commands;

import com.company.judge.contracts.ContentComparer;
import com.company.io.contracts.DirectoryManager;
import com.company.commands.contracts.Executable;
import com.company.exceptions.InvalidInputException;
import com.company.network.DownloadManager;
import com.company.network.contracts.AsynchDownloader;
import com.company.repository.StudentRepository;
import com.company.repository.contracts.Database;

public class ShowWantedCourseCommand extends Command implements Executable {
    public ShowWantedCourseCommand(String input,
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
        if (data.length != 2 && data.length != 3) {
            throw new InvalidInputException(this.getInput());
        }

        if (data.length == 2) {
            String courseName = data[1];
            this.getStudentRepository().getStudentsByCourse(courseName);
        } else {
            String courseName = data[1];
            String student = data[2];
            this.getStudentRepository().getStudentMarksInCourse(courseName, student);
        }
    }
}
