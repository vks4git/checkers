package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.MoveSequence;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Listener;
import ru.ifmo.morozov.interfaces.Notifier;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

/**
 * Created by vks on 2/27/15.
 */
public class Game implements Notifier {
    private Player[] players;
    private Field field;
    private Pointer pointer;
    private Validator rules;
    private Listener listener;

    public Game(Player player1, Player player2) {
        players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        field = new Field(players[0].getColour(), players[1].getColour());
        pointer = new Pointer(field.getMatrix(), 0, 0);
        rules = new Rules();
    }

    public Player start() {
        int i;
        MoveSequence sequence;
        if (players[0].getColour() == Colour.White) {
            i = 0;
        } else {
            i = 1;
        }

        State state = State.Illegal;
        Colour colour;
        do {
            colour = players[i % 2].getColour();
            if (rules.canMove(field.getMatrix(), colour)) {
                pointer.setTurn(colour);

                if (players[i % 2].keyboardEntry()) {
                    pointer.usePointer();
                }

                do {
                    sequence = players[i % 2].move(field.getMatrix(), pointer);
                    for (Coordinates move : sequence) {
                        state = rules.verify(field.getMatrix(), move, colour);
                        if (state != State.Illegal) {
                            field.move(move);
                            update();
                        }
                    }
                } while (state != State.Legal);

            } else {
                return players[(i + 1) % 2];
            }
            i++;
            pointer.doNotUsePointer();

        } while (true);

    }

    public void addListener(Listener listener) {
        this.listener = listener;
    }

    public void update() {
        listener.update();
    }

    public final Field getField() {
        return field;
    }

    public Pointer getPointer() {
        return pointer;
    }

}
