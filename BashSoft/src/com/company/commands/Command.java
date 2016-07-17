package com.company.commands;

import com.company.judge.contracts.ContentComparer;
import com.company.io.contracts.DirectoryManager;
import com.company.commands.contracts.Executable;
import com.company.exceptions.InvalidInputException;
import com.company.models.contracts.Course;
import com.company.models.contracts.Student;
import com.company.network.DownloadManager;
import com.company.network.contracts.AsynchDownloader;
import com.company.repository.StudentRepository;
import com.company.repository.contracts.Database;

import java.util.Comparator;

public abstract class Command implements Executable{
    private String input;
    private String[] data;
    private Database studentRepository;
    private ContentComparer tester;
    private DirectoryManager ioManager;
    private AsynchDownloader downloadManager;

    public Command(String input,
            String[] data,
            Database studentRepository,
            ContentComparer tester,
            DirectoryManager ioManager,
            AsynchDownloader downloadManager) {
        this.setInput(input);
        this.setData(data);
        this.studentRepository = studentRepository;
        this.tester = tester;
        this.ioManager = ioManager;
        this.downloadManager = downloadManager;
    }

    Database getStudentRepository() {
        return studentRepository;
    }

    ContentComparer getTester() {
        return tester;
    }

    DirectoryManager getIoManager() {
        return ioManager;
    }

    AsynchDownloader getDownloadManager() {
        return downloadManager;
    }

    String getInput() {
        return input;
    }

    private void setInput(String input) {
        if (input == null || input.trim().length() == 0) {
            throw new InvalidInputException(input);
        }
        this.input = input;
    }

    String[] getData() {
        return data;
    }

    private void setData(String[] data) {
        if (data == null || data.length < 1) {
            throw new InvalidInputException(this.getInput());
        }
        this.data = data;
    }

    public abstract void execute() throws Exception;
}
