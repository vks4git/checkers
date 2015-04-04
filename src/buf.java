import com.sun.deploy.util.BufferUtil;
import ru.ifmo.morozov.Main;
import ru.ifmo.morozov.classes.*;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import javax.xml.soap.Text;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        field.setTurn(player1);
        System.out.println(field.isFree(6, 5));
        System.out.println(validator.isLegal(field, 4, 1, 5, 2));
        System.out.println(validator.canMove(1, 2));

        String root = System.getProperty("user.dir") + "/src/";
        Texture tex = new Texture(root + "tex/texture.black");
        Buffer buffer = tex.getImage();


        byte x = -1;
        System.out.println(~x);

        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(6);
        list.add(7);

        list.remove(0);
        System.out.println(list.get(0));
        list.remove(0);
        System.out.println(list.get(0));
        list.remove(0);
        System.out.println(list.get(0));

        System.out.println();
    }
}
