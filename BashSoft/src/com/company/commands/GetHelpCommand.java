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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetHelpCommand extends Command implements Executable {
    public GetHelpCommand(String input,
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

        this.displayHelp();
    }

    private void displayHelp() {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources\\getHelp.txt"))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                OutputWriter.writeMessageOnNewLine(line);
            }
            OutputWriter.writeEmptyLine();

        } catch (IOException e) {
            OutputWriter.displayException(e.getMessage());
        }
    }
}
