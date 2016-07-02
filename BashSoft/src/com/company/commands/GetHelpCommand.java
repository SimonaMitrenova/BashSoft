package com.company.commands;

import com.company.exceptions.InvalidInputException;
import com.company.io.IOManager;
import com.company.io.OutputWriter;
import com.company.judge.Tester;
import com.company.network.DownloadManager;
import com.company.repository.StudentRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetHelpCommand extends Command {
    public GetHelpCommand(String input,
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
        if (data.length != 1) {
            throw new InvalidInputException(this.getInput());
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
}
