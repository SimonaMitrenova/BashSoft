package com.company.commands;

import com.company.io.IOManager;
import com.company.judge.Tester;
import com.company.network.DownloadManager;
import com.company.repository.StudentRepository;

public class DownloadFileCommand extends Command {
    public DownloadFileCommand(String input,
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
        if (data.length != 2){
            throw new IllegalArgumentException(this.getInput());
        }
        String fileUrl = data[1];
        this.getDownloadManager().download(fileUrl);
    }
}
