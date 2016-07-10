package com.company.commands;

import com.company.judge.contracts.ContentComparer;
import com.company.io.contracts.DirectoryManager;
import com.company.commands.contracts.Executable;
import com.company.network.DownloadManager;
import com.company.network.contracts.AsynchDownloader;
import com.company.repository.StudentRepository;
import com.company.repository.contracts.Database;

public class DownloadFileCommand extends Command implements Executable {
    public DownloadFileCommand(String input,
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
        if (data.length != 2) {
            throw new IllegalArgumentException(this.getInput());
        }
        String fileUrl = data[1];
        this.getDownloadManager().download(fileUrl);
    }
}
