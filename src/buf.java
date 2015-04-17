import ru.ifmo.morozov.classes.*;
import ru.ifmo.morozov.classes.controller.Game;
import ru.ifmo.morozov.classes.controller.Rules;
import ru.ifmo.morozov.classes.model.AIPlayer;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.classes.model.HumanPlayer;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vks on 3/5/15.
 */


//Класс для дебаггинга и тестирования, в финальную версию не войдёт
public class buf {
    public static void main(String[] args) {
        Validator validator = new Rules();
        Player player1 = new HumanPlayer(Colour.White, "Дарт Херохито", 1);
        Player player2 = new AIPlayer(Colour.Black, "Злобный компьютерный разум", -1, validator);
        Game game = new Game(player1, player2);
        Field field = game.getField();

        Coordinates move = new Coordinates();
        move.x1 = 2;
        move.y1 = 2;
        move.x2 = 3;
        move.y2 = 3;

        System.out.println(validator.isLegal(field, move, player1));

        move.x1 = 6;
        move.y1 = 6;
        move.x2 = -1;
        move.y2 = -1;
        System.out.println(validator.isLegal(field, move, player2));
    }
}
