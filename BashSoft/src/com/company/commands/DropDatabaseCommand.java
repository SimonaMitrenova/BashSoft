package com.company.commands;

import com.company.exceptions.InvalidInputException;
import com.company.io.IOManager;
import com.company.io.OutputWriter;
import com.company.judge.Tester;
import com.company.network.DownloadManager;
import com.company.repository.StudentRepository;

public class DropDatabaseCommand extends Command {
    public DropDatabaseCommand(String input,
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
        if (data.length != 1){
            throw new InvalidInputException(this.getInput());
        }

        this.getStudentRepository().unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");
    }
}
