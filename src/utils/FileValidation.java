package utils;

import java.io.File;

public class FileValidation {

    public void validatesIfExists(File file) throws Exception {
        if(!file.exists()) {
            throw new Exception("File does not exist.");
        }
    }

    public void validatesIfEmpty(File file) throws Exception {
        if (file.length() == 0) {
            throw new Exception("File has no content.");
        }
    }
}