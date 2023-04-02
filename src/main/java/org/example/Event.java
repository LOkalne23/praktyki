package org.example;

import java.util.Arrays;

public class Event {
    private int lsId;
    private int count;
    private int eventNumber;
    private short[] ch1;


    public Event(int eventId) {
        this.lsId = eventId;
        ch1 = new short[10_240];
    }

    public Event(int eventId, short[] ch1) {
        this.lsId = eventId;
        this.ch1 = ch1;
    }

    public Event(int lsId, int count, short[] ch1) {
        this.lsId = lsId;
        this.count = count;
        this.ch1 = ch1;
    }

    public int getLsId() {
        return lsId;
    }

    public void setLsId(int lsId) {
        this.lsId = lsId;
    }

    public int getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
    }

    public short[] getCh1() {
        return ch1;
    }

    public void setCh1(short[] ch1) {
        this.ch1 = ch1;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Event{" +
                "lsId=" + lsId + ", " +
                "\tch1 size=" + ch1.length + "\n " +
                Arrays.toString(ch1);
    }

    public String toText() {
        return "Event nr: " + count +  ", lsId=" + lsId + "\tch1 size=" + ch1.length +
                "\n " +
                chanelToText();
    }

    private String chanelToText() {
        StringBuilder stringBuilder = new StringBuilder();
        for(short num : ch1){
            stringBuilder.append(num).append("\t");
        }
        return stringBuilder.append("\n").toString();
    }
}
