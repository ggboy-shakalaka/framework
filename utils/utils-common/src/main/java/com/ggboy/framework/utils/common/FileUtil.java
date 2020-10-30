package com.ggboy.framework.utils.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {
    public static List<String> readLines(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<String> list = new LinkedList<>();

            String line;
            while ((line = reader.readLine()) != null)
                list.add(line);
            return list;
        }
    }

    public static void writeLines(String path, String line) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.append('\n');
        fileWriter.append(line);
        fileWriter.flush();
        fileWriter.close();
    }
}