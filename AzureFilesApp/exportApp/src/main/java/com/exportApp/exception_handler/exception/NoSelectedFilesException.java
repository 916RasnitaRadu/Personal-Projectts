package com.exportApp.exception_handler.exception;

public class NoSelectedFilesException extends Exception {
    public NoSelectedFilesException() {
        super("No files were selected for export.");
    }
}
