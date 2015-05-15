package ru.ifmo.morozov.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vks on 14/05/15.
 */
public class MoveSequence implements Iterable<Coordinates>{
    private int size;
    private List<Coordinates> sequence;

    public MoveSequence() {
        sequence = new ArrayList<>();
    }

    public void add(Coordinates move) {
        sequence.add(move);
        size++;
    }

    public void concat(MoveSequence source) {
        for (Coordinates move : source) {
            sequence.add(move);
        }
    }

    @Override
    public Iterator<Coordinates> iterator() {
        return sequence.iterator();
    }
}
