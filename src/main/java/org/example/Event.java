package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
@Getter
@Setter
public class Event {
    private int lsId;
    private int eventNumber;
    private short[] ch1;
    private short[] ch2;

    public Event(int eventId, short[] ch1, short[] ch2) {
        this.lsId = eventId;
        this.ch1 = ch1;
        this.ch2 = ch2;
    }

    @Override
    public String toString() {
        return "Event{" +
                "lsId=" + lsId + ", " +
                "\tch1 size=" + ch1.length + "\n " +
                Arrays.toString(ch1);
    }

    public String ch1ToText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (short num : ch1) {
            stringBuilder.append(num).append("\t");
        }
        return "Event ls-id=" + lsId + "\tch1 size=" + ch1.length +
                "\n " + stringBuilder.append("\n");
    }

    public String ch2ToText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (short num : ch2) {
            stringBuilder.append(num).append("\t");
        }
        return "Event ls-id=" + lsId + "\tch2 size=" + ch2.length +
                "\n " + stringBuilder.append("\n");
    }
}
