package com.company.commands;

import com.company.judge.contracts.ContentComparer;
import com.company.io.contracts.DirectoryManager;
import com.company.commands.contracts.Executable;
import com.company.exceptions.InvalidInputException;
import com.company.io.OutputWriter;
import com.company.network.DownloadManager;
import com.company.network.contracts.AsynchDownloader;
import com.company.repository.StudentRepository;
import com.company.repository.contracts.Database;

public class DropDatabaseCommand extends Command implements Executable {
    public DropDatabaseCommand(String input,
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
        if (data.length != 1) {
            throw new InvalidInputException(this.getInput());
        }

        this.getStudentRepository().unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");
    }
}
