package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        List<String> userNameFromFirstFile = new ArrayList<>();
        List<String> userNameFromSecondFile = new ArrayList<>();

        ReadFirstFileRunner readFirstFileRunner = new ReadFirstFileRunner(userNameFromFirstFile);
        ReadSecondFileRunner readSecondFileRunner = new ReadSecondFileRunner(userNameFromSecondFile);

        //  Эти потоки считывают информацию из файлов.
        Thread readFirstFileThread = new Thread(readFirstFileRunner);
        Thread readSecondFileThread = new Thread(readSecondFileRunner);

        readFirstFileThread.start();
        readSecondFileThread.start();

        try {
            readFirstFileThread.join();
            readSecondFileThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /*
            В условие к заданию не было сказано, что нужно делать с дубликатами имени пользователей,
            поэтому я принял решение удалить их (как оказалось позже это не повлияло на конечный результат
            так как имена, которые входят в ответ существуют только в одном экземпляре).
            Если удаление все-таки неприемлемо, то просто закомментируйте строку ниже.
         */
        userNameFromFirstFile = userNameFromFirstFile.stream().distinct().collect(Collectors.toList());

        /*
            Удаление дубликатов из списка имен второго файла ускорит выполнение и не повлияет на результат.
         */
        userNameFromSecondFile = userNameFromSecondFile.stream().distinct().collect(Collectors.toList());

        long countName = 0;

        for(String nameFirstFile: userNameFromFirstFile) {
            if (!userNameFromSecondFile.contains(nameFirstFile)) {
                countName++;
                System.out.println(nameFirstFile);
            }
        }
        System.out.println(countName);

        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("программа выполнялась " + (float) timeSpent / 1000 + " секунд");
    }
}