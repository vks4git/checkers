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
    private Validator validator;

    public Game(Player player1, Player player2) {
        players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        players[0].setDirection(1);
        players[1].setDirection(-1);

        field = new Field(players[0].getColour(), players[1].getColour());
        validator = new Rules(field);
    }

    public Player start() {
        int i;
        if (players[0].getColour() == Colour.White) {
            i = 0;
            field.setTurn(players[0]);
        } else {
            i = 1;
            field.setTurn(players[1]);
        }
        do {
            if (players[i % 2].canMove(field, validator)) {
                field.setTurn(players[i % 2]);
                players[i % 2].move(field, validator);
            } else {
                players[(i + 1) % 2].setStatus(true);
            }
            i++;
        } while (players[i % 2].isVictorious() || !players[(i + 1) % 2].isVictorious());

        return players[0].isVictorious() ? players[0] : players[1];
    }

    public Field getField() {
        return field;
    }
}
