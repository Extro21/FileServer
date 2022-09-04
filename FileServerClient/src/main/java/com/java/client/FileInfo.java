package com.java.client;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class FileInfo {
    public enum FileType {
        FILE("F"), DIRECTORY("D");

        @Getter
        private String name;

        FileType(String name) {
            this.name = name;
        }
    }

    @Getter
    private String filename;
    @Getter
    private FileType type;
    @Getter
    private Long size;
    @Getter
    private LocalDateTime lastModified;


    public FileInfo(Path path){
        try {
            this.filename = path.getFileName().toString();
            this.size = Files.size(path);
            this.type = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
            if(this.type == FileType.DIRECTORY){
                this.size = 1L;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
