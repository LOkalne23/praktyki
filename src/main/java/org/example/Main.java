package org.example;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String ls = "1";
        try {
            int value = Integer.parseInt(args[0]);
            if (value > 150 || value < 1) {
                throw new RuntimeException("Nieprawidłowy numer stacji, musi zawierac się w zakresie 1-140");
            }
            ls = String.valueOf(value);
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Nie podano parametru jako numeru stacji, domyśly numer 1");
        }catch (RuntimeException e){
            System.err.println(e.getMessage());
        }
        final List<Event> events = readFile(ls);
        writeToFile(events, ls);
    }

    private static List<Event> readFile(final String lsId) throws FileNotFoundException {
        final Scanner scanner = new Scanner(new FileReader("D:\\Downloads\\proj\\rdm_2019_07_02.root.txt"));
        final List<Event> events = new LinkedList<>();
        int counter = 1;
        while (scanner.hasNext()) {
            final String line = scanner.nextLine();
            if (line.matches("^LS id: " + lsId)) {
                int id = Short.parseShort(line.split(" ")[2]);
                final short[] shorts = readChanelResult(scanner);
                events.add(new Event(id, counter++, shorts));
                if (events.size() % 10 == 0) {
                    System.out.println("Event list size = " + events.size());
                }
            }
        }
        System.out.println("Reading events done");
        return events;
    }

    private static short[] readChanelResult(final Scanner scanner) {
        while (true) {
            final String line = scanner.nextLine();
            if (line.matches("^Ch 1 :$")) {
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

    public static void writeToFile(final List<Event> events, final String lsId) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        events.forEach(event -> stringBuilder.append(event.toText()));
        BufferedWriter writer = new BufferedWriter(new FileWriter("output-lsId-" + lsId + ".txt"));
        writer.write(stringBuilder.toString());
        writer.close();
    }
}