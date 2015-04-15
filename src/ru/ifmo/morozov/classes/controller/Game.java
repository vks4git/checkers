package ru.ifmo.morozov.classes.controller;

import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.classes.model.Pointer;
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
        Coordinates move;
        if (players[0].getColour() == Colour.White) {
            i = 0;
        } else {
            i = 1;
        }

        State state;

        do {
            if (rules.canMove(field, players[i % 2])) {
                pointer.setTurn(players[i % 2].getColour());

                if (players[i % 2].keyboardEntry()) {
                    pointer.usePointer();
                }
                do {
                    do {
                        move = players[i % 2].move(field, pointer);
                        state = rules.isLegal(field, move, players[i % 2]);
                    } while (state == State.Illegal);

                    field.move(move.x1, move.y1, move.x2, move.y2);
                    update();

                    if (Math.abs(move.x1 - move.x2) > 1) {
                        int dirX = (move.x2 - move.x1) / Math.abs(move.x2 - move.x1);
                        int dirY = (move.y2 - move.y1) / Math.abs(move.y2 - move.y1);
                        int x = move.x1;
                        int y = move.y1;
                        for (int j = 0; j <= Math.abs(move.x2 - move.x1); j++) {
                            field.free(x, y);
                            x += dirX;
                            y += dirY;
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
