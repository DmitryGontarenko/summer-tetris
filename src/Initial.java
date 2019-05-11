import javax.swing.*;
import java.awt.*;

public class Initial {

    Model game = null;

    // Конструктор
    public Initial(Model _game) {
        game = _game;
    }

    // Создает границы и вызывает фигуры
    public void init(){
        for (int i = 0; i < game.BLOCK_HORIZONTAL; i++) {
            for (int j = 0; j < game.BLOCK_VERTICAL; j++) {
                if (i == 0 || i == game.BLOCK_HORIZONTAL - 1 || j == 0 || j == game.BLOCK_VERTICAL - 1 ) { // Установка серой рамки по всей области, i по горизонтали, j по вертикале
                    game.well[i][j] = Color.GRAY;
                }
                else {
                    game.well[i][j] = Color.BLACK;
                }
            }
        }
        game.newPiecePrewiev();
        game.newPiece();
    }

}
