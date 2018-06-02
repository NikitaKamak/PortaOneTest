package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadSecondFileRunner implements Runnable {

    private List<String> userName;

    public ReadSecondFileRunner(List<String> userName) {
        this.userName = userName;
    }

    @Override
    public void run() {
        List<String> lines = new ArrayList<>();
        //  Путь к файлу. В данном случае для простоты файл находиться в папке Java проекта рядом с исходниками.
        Path path = Paths.get("file2");
        Pattern pattern = Pattern.compile("user: (\\w*)");

        try (Stream<String> lineStream = Files.newBufferedReader(path).lines()) {
            lines = lineStream.collect(Collectors.toList());
        } catch (IOException ignored) {}

        for (String oneLine: lines) {
            Matcher matcher = pattern.matcher(oneLine);
            while (matcher.find()) {
                userName.add(matcher.group().substring(6));
            }
        }
    }
}
