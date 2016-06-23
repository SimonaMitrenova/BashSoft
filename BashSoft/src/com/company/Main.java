package com.company;

import com.company.io.CommandInterpreter;
import com.company.io.IOManager;
import com.company.io.InputReader;
import com.company.io.OutputWriter;
import com.company.judge.Tester;
import com.company.network.DownloadManager;
import com.company.repository.RepositoryFilter;
import com.company.repository.RepositorySorter;
import com.company.repository.StudentRepository;

public class Main {

    public static void main(String[] args) {

        Tester tester = new Tester();
        DownloadManager downloadManager = new DownloadManager();
        IOManager ioManager = new IOManager();
        RepositorySorter sorter = new RepositorySorter();
        RepositoryFilter filter = new RepositoryFilter();
        StudentRepository studentRepository = new StudentRepository(filter, sorter);
        CommandInterpreter commandInterpreter = new CommandInterpreter(tester, studentRepository, downloadManager, ioManager);
        InputReader inputReader = new InputReader(commandInterpreter);

        try {
            inputReader.readCommands();
        } catch (Exception e) {
            OutputWriter.displayException(e.getMessage());
        }
    }
}
