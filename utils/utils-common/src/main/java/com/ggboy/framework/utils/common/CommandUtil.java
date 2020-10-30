package com.ggboy.framework.utils.common;

import java.io.IOException;

public class CommandUtil {
    public static void main(String[] args) throws IOException {
        Process process = new ProcessBuilder("sh").redirectErrorStream(true).start();
        process.getOutputStream().write("echo `date`".getBytes());
        process.getOutputStream().close();
    }
}