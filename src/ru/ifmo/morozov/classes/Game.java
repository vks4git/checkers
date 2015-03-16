package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

/**
 * Created by vks on 2/27/15.
 */
public class Game {
    private Player[] players;
    private Field field;

    public Game(Player player1, Player player2) {
        players = new Player[2];
        players[0] = player1;
        players[1] = player2;

        Validator validator = new Rules();
        field = new Field(players[0].getColour(), players[1].getColour(), validator);
    }

    public Player start() {
        int i;
        if (players[0].getColour() == Colour.White) {
            i = 0;
        } else {
            i = 1;
        }

        do {
            field.setTurn(players[i % 2]);

            if (field.canMove(players[i % 2])) {
                if (players[i].keyboardEntry()) {
                    field.setKeybdEntry();
                }
                    do {
                        players[i % 2].move(field);
                    } while (!field.isFinished());
                field.unsetKeybdEntry();
            } else {
                return players[(i + 1) % 2];
            }
            i++;
        } while (true);
    }

    public Field getField() {
        return field;
    }
}
