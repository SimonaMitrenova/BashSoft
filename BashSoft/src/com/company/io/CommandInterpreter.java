package com.company.io;

import com.company.commands.*;
import com.company.judge.contracts.ContentComparer;
import com.company.io.contracts.DirectoryManager;
import com.company.commands.contracts.Executable;
import com.company.io.contracts.Interpreter;
import com.company.exceptions.InvalidInputException;
import com.company.network.DownloadManager;
import com.company.network.contracts.AsynchDownloader;
import com.company.repository.StudentRepository;
import com.company.repository.contracts.Database;

public class CommandInterpreter implements Interpreter{

    private ContentComparer tester;
    private Database studentRepository;
    private AsynchDownloader downloadManager;
    private DirectoryManager ioManager;

    public CommandInterpreter(ContentComparer tester,
                              Database studentRepository,
                              AsynchDownloader downloadManager,
                              DirectoryManager ioManager) {
        this.tester = tester;
        this.studentRepository = studentRepository;
        this.downloadManager = downloadManager;
        this.ioManager = ioManager;
    }

    public void interpretCommand(String input){
        String[] data = input.split("\\s+");
        String commandName = data[0].toLowerCase();
        try{
            Executable command = parseCommand(input, data, commandName);
            command.execute();
        } catch (Throwable ex){
            OutputWriter.displayException(ex.getMessage());
        }
    }

    private Executable parseCommand(String input, String[] data, String command) {
        switch (command){
            case "open":
                return new OpenFileCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "mkdir":
                return new MakeDirectoryCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "ls":
                return new TraverseFoldersCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "cmp":
                return new CompareFilesCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "cdrel":
                return new ChangeRelativePathCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "cdabs":
                return new ChangeAbsolutePathCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "readdb":
                return new ReadDatabaseFromFileCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "dropdb":
                return new DropDatabaseCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "help":
                return new GetHelpCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "show":
                return new ShowWantedCourseCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "filter":
                return new PrintFilteredStudentsCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "order":
                return new PrintOrderedStudentsCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "download":
                return new DownloadFileCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "downloadasynch":
                return new DownloadAsynchCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            case "display":
                return new DisplayCommand(input, data, studentRepository, tester, ioManager, downloadManager);

            default:
                throw new InvalidInputException(input);
        }
    }
}
