package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 140; i++) {
            findEventsAndWriteToFileByLsId(i);
        }
    }

    private static void findEventsAndWriteToFileByLsId(final int lsId) throws IOException {
        final Scanner scanner = new Scanner(new FileReader("D:\\Downloads\\proj\\rdm_2019_07_02.root.txt"));
        System.gc();
        final List<Event> events = new LinkedList<>();
        while (scanner.hasNext()) {
            final String line = scanner.nextLine();
            if (line.matches("^LS id: " + lsId)) {
                int id = Short.parseShort(line.split(" ")[2]);
                final short[] chanel1 = readChanelResult(ChanelEnum.CH1, scanner);
                final short[] chanel2 = readChanelResult(ChanelEnum.CH2, scanner);
                events.add(new Event(id, chanel1, chanel2));
                if (events.size() % 10 == 0) {
                    System.out.println("Event list size = " + events.size());
                }
            }
            if (events.size() == 100) {
                break;
            }
        }
        System.out.println("Reading events done for ls-id #" + lsId);
        writeEventToFile(events, lsId, ChanelEnum.CH1);
        writeEventToFile(events, lsId, ChanelEnum.CH2);
    }

    private static short[] readChanelResult(final ChanelEnum chanel, final Scanner scanner) {
        while (true) {
            final String line = scanner.nextLine();
            if (line.matches(chanel.regex)) {
                return readOneChanel(scanner);
            }
        }
    }

    private static short[] readOneChanel(final Scanner scanner) {
        short[] chanelRead = new short[10_240];
        for (int i = 0; i < 10_240; i++) {
            final short aShort = Short.parseShort(scanner.next());
            chanelRead[i] = aShort;
        }
        return chanelRead;
    }

    //    /data/Station#id/Ch#id/output.txt
    public static void writeEventToFile(final List<Event> events, final int lsId, ChanelEnum chanel) throws IOException {
        final String directoryPath = "data/ls-" + lsId + chanel.directory;
        final StringBuilder stringBuilder = new StringBuilder();
        final File directory = new File(directoryPath);
        if (!directory.exists()) {
            Files.createDirectories(Paths.get(directoryPath));
        }
        switch (chanel) {
            case CH1:
                events.forEach(event -> stringBuilder.append(event.ch1ToText()));
                break;
            case CH2:
                events.forEach(event -> stringBuilder.append(event.ch2ToText()));
                break;
        }
        final BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath + "/output.txt"));
        writer.write(stringBuilder.toString());
        writer.close();
    }

    @AllArgsConstructor
    @Getter
    private enum ChanelEnum {
        CH1(1, "^Ch 1 :$", "/ch1"),
        CH2(2, "^Ch 2 :$", "/ch2");
        private final int chanel;
        private final String regex;
        private final String directory;
    }
}