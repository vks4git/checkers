import ru.ifmo.morozov.classes.*;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

/**
 * Created by vks on 3/5/15.
 */


//Класс для дебаггинга и тестирования, в финальную версию не войдёт
public class buf {
    public static void main(String[] args) {
        Player player1 = new HumanPlayer(Colour.White, "Дарт Херохито", 1);
        Player player2 = new AIPlayer(Colour.Black, "Злобный компьютерный разум", -1);
        Game game = new Game(player1, player2);
        Field field = game.getField();
        field.setTurn(player1);
        Validator validator = new Rules();
        System.out.println(field.isFree(6, 5));
        System.out.println(validator.isLegal(field, 4, 1, 5, 2));
        System.out.println(validator.canMove(1, 2));
    }
}
