package com.exportApp.model.fileModel;

public class FileModel {
    public String name;
    public String type;
    public String path;

    public FileModel() {
    }

    public FileModel(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.path = path;
    }
}
