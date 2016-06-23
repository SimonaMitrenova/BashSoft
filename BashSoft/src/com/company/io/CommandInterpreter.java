package com.company.io;

import com.company.judge.Tester;
import com.company.network.DownloadManager;
import com.company.repository.StudentRepository;
import com.company.staticData.ExceptionMessages;
import com.company.staticData.SessionData;

import java.awt.*;
import java.io.*;

public class CommandInterpreter {

    private Tester tester;
    private StudentRepository studentRepository;
    private DownloadManager downloadManager;
    private IOManager ioManager;

    public CommandInterpreter(Tester tester, StudentRepository studentRepository, DownloadManager downloadManager, IOManager ioManager) {
        this.tester = tester;
        this.studentRepository = studentRepository;
        this.downloadManager = downloadManager;
        this.ioManager = ioManager;
    }

    public void interpretCommand(String input){
        String[] data = input.split("\\s+");
        String command = data[0].toLowerCase();
        switch (command){
            case "open":
                this.tryOpenFile(input, data);
                break;

            case "mkdir":
                this.tryCreateDirectory(input, data);
                break;

            case "ls":
                this.tryTraverseFolders(input, data);
                break;

            case "cmp":
                this.tryCompareFiles(input, data);
                break;

            case "cdrel":
                this.tryChangeRelativePath(input, data);
                break;

            case "cdabs":
                this.tryChangeAbsolutePath(input, data);
                break;

            case "readdb":
                this.tryReadDatabaseFromFile(input, data);
                break;

            case "dropdb":
                this.tryDropDatabase(input, data);
                break;

            case "help":
                this.tryGetHelp(input, data);
                break;

            case "show":
                this.tryShowWantedCourse(input, data);
                break;

            case "filter":
                this.tryPrintFilteredStudents(input, data);
                break;

            case "order":
                this.tryPrintOrderedStudents(input, data);
                break;

            case "download":
                this.tryDownloadFile(input, data);
                break;

            case "downloadasynch":
                this.tryDownloadFileOnNewThread(input, data);
                break;

            default:
                this.displayInvalidCommandMessage(input);
                break;
        }
    }

    private void tryDropDatabase(String input, String[] data) {
        if (data.length != 1){
            this.displayInvalidCommandMessage(input);
            return;
        }

        this.studentRepository.unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");
    }

    private void tryShowWantedCourse(String input, String[] data) {
        if (data.length != 2 && data.length != 3){
            this.displayInvalidCommandMessage(input);
            return;
        }

        if (data.length == 2){
            String courseName = data[1];
            this.studentRepository.getStudentsByCourse(courseName);
        } else {
            String courseName = data[1];
            String student = data[2];
            this.studentRepository.getStudentMarksInCourse(courseName, student);
        }
    }   

    private void displayInvalidCommandMessage(String input) {
        String output = String.format("The command %s is invalid", input);
        OutputWriter.writeMessageOnNewLine(output);
    }

    private void tryGetHelp(String input, String[] data) {
        if (data.length != 1) {
            this.displayInvalidCommandMessage(input);
            return;
        }

        this.displayHelp();
    }

    private void displayHelp() {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources\\getHelp.txt"))){
            while (true){
                String line = reader.readLine();
                if (line == null){
                    break;
                }
                OutputWriter.writeMessageOnNewLine(line);
            }
            OutputWriter.writeEmptyLine();

        }  catch (IOException e) {
            OutputWriter.displayException(e.getMessage());
        }
    }

    private void tryDownloadFileOnNewThread(String input, String[] data) {
        if (data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }
        String fileUrl = data[1];
        this.downloadManager.downloadOnNewThread(fileUrl);
    }

    private void tryDownloadFile(String input, String[] data) {
        if (data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }
        String fileUrl = data[1];
        this.downloadManager.download(fileUrl);
    }

    private void tryPrintOrderedStudents(String input, String[] data) {
        if (data.length != 5) {
            this.displayInvalidCommandMessage(input);
            return;
        }

        String courseName = data[1];
        String orderType = data[2].toLowerCase();
        String takeCommand = data[3].toLowerCase();
        String takeQuantity = data[4].toLowerCase();

        this.tryParseParametersForOrder(takeCommand, takeQuantity, courseName, orderType);
    }
    private void tryParseParametersForOrder(
            String takeCommand, String takeQuantity,
            String courseName, String orderType) {
        if (!takeCommand.equals("take")) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
            return;
        }

        if (takeQuantity.equals("all")) {
            this.studentRepository.orderAndTake(courseName, orderType);
            return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.studentRepository.orderAndTake(courseName, orderType, studentsToTake);
        } catch (NumberFormatException nfe) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_QUANTITY_PARAMETER);
        }
    }

    private void tryPrintFilteredStudents(String input, String[] data) {
        if (data.length != 5) {
            this.displayInvalidCommandMessage(input);
            return;
        }

        String course = data[1];
        String filter = data[2].toLowerCase();
        String takeCommand = data[3].toLowerCase();
        String takeQuantity = data[4].toLowerCase();

        this.tryParseParametersForFilter(takeCommand, takeQuantity, course, filter);
    }

    private void tryParseParametersForFilter(
            String takeCommand, String takeQuantity,
            String courseName, String filter) {
        if (!takeCommand.equals("take")) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
            return;
        }

        if (takeQuantity.equals("all")) {
            this.studentRepository.filterAndTake(courseName, filter);
            return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.studentRepository.filterAndTake(courseName, filter, studentsToTake);
        } catch (NumberFormatException nfe) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_QUANTITY_PARAMETER);
        }
    }

    private void tryReadDatabaseFromFile(String input, String[] data) {
        if (data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }

        String fileName = data[1];
        this.studentRepository.loadData(fileName);
    }

    private void tryChangeAbsolutePath(String input, String[] data) {
        if (data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }

        String absolutePath = data[1];
        this.ioManager.changeCurrentDirAbsolutePath(absolutePath);
    }

    private void tryChangeRelativePath(String input, String[] data) {
        if (data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }

        String relativePath = data[1];
        this.ioManager.changeCurrentDirRelativePath(relativePath);
    }

    private void tryCompareFiles(String input, String[] data) {
        if (data.length != 3){
            this.displayInvalidCommandMessage(input);
            return;
        }

        String firstPath = data[1];
        String secondPath = data[2];
        this.tester.compareContent(firstPath, secondPath);
    }

    private void tryTraverseFolders(String input, String[] data) {
        if (data.length != 1 && data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }

        if (data.length == 1){
            this.ioManager.traverseDirectory(0);
        } else {
            int depth = Integer.parseInt(data[1]);
            this.ioManager.traverseDirectory(depth);
        }
    }

    private void tryCreateDirectory(String input, String[] data) {
        if (data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }

        String folderName = data[1];
        this.ioManager.createDirectoryInCurrentFolder(folderName);
    }

    private void tryOpenFile(String input, String[] data) {
        if (data.length != 2){
            this.displayInvalidCommandMessage(input);
            return;
        }

        String fileName = data[1];
        String filePath = SessionData.currentPath + "\\" + fileName;
        File file = new File(filePath);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            OutputWriter.displayException(e.getMessage());
        }
    }
}
