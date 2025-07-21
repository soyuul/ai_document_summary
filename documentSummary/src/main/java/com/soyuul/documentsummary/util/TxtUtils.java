package com.soyuul.documentsummary.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TxtUtils {

    public static String extractTextFromTxt(Path path) throws IOException{
        return Files.readString(path);
    }
}
