import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Model extends JPanel {

    // Info block
    protected static int score = 0; // игровой счет
    protected static int level = 1; // игровой уровень
    protected static int speed = 500; // скорость игры
    
    protected static final int WINDOW_WIDTH = 600; // Ширина окна (используется в статическом методе)
    protected static final int WINDOW_HEIGHT = 635; // Высота окна (используется в статическом методе)
    protected final int BLOCK_HORIZONTAL = 12; // Количество блоков (ячеек) по горизонтали
    protected final int BLOCK_VERTICAL = 23; // Количество блоков (ячеек) по вертикали
    protected final int BLOCK_WIDTH = 25; // Ширина блока (ячейки)
    protected final int BLOCK_HEIGHT = 25; // Высота блока (ячейки)
    protected final int BLOCK_COORDINATES = 26; // Размер

    // region private final Point[][][] FIGURE
    private final Point[][][] FIGURE = { // Массив координат точек фигур под разным углом
            // I
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
            },
            // J
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
            },
            // L
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
            },
            // O
            {
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
            },
            // S
            {
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
            },
            // T
            {
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
            },
            // Z
            {
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
            }
    };

    //endregion .//

    private final Color[] FIGURE_COLOR = { // Цвет фигур в том же порядке, что и фигуры в массиве координат
            Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
    };

    protected Color[][] well = new Color[12][24]; // Массив для отображения цветов шахты

    // перечень полей для вывода отрисовки в предпросмотр
    private Point piecePrewiew; // хранит в себе координаты фигуры
    private int prewievPiece; // хранит в себе следующую фигуру
    private ArrayList<Integer> nextPrewiev = new ArrayList<Integer>(); // масиив - хранит в себе список всех фигур
    private int check = 0; // выполняет проверку повторения

    // перечень полей для отрисовки фигуры
    private Point pieceOrigin; // хранит в себе координаты фигуры
    private int currentPiece; // хранит в себе следующую фигуру
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>(); // масиив - хранит в себе список всех фигур
    private int rotation; // принимает угол поворота

    private boolean empty; // Проверка заполнения строк
    private boolean Pause = false; // Пауза при удалении заполненых строк
    protected boolean stateGame = true; // Проверка на конец игры

    // Заполнение цветом и вывод текста
    @Override
    public void paintComponent(Graphics g)
    {
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        for (int i = 0; i < BLOCK_HORIZONTAL; i++) {
            for (int j = 0; j < BLOCK_VERTICAL; j++) {
                g.setColor(well[i][j]);
                g.fillRect(BLOCK_COORDINATES * i, BLOCK_COORDINATES * j, BLOCK_WIDTH, BLOCK_HEIGHT);
            }
        }

        g.setColor(Color.WHITE);
        // Блок информации
        g.drawString("CURRENT SCORE: " + score, 400, 50);
        g.drawString("LEVEL: " + level, 400, 100);
        g.drawString("SPEED: " + speed, 400, 150);
        g.drawString("NEXT FIGURE: " , 400, 200);

        if (stateGame == false) {
            g.drawString("GAME OVER", 400, 350);
        }

        drawPiece(g); // Прорисовка фигуры
    }

    // Создание фигуры для предпросмотра
    public void newPiecePrewiev(){

        piecePrewiew = new Point (16, 9);

        if (nextPrewiev.isEmpty()){
            Collections.addAll(nextPrewiev, 0, 1, 2, 3, 4, 5, 6); // , 1, 2, 3, 4, 5, 6
            Collections.shuffle(nextPrewiev);
        }
        prewievPiece = nextPrewiev.get(0);
        nextPrewiev.remove(0);
    }

    // Создание фигуры
    public void newPiece(){
            pieceOrigin = new Point(5, 2);
            rotation = 0;

            if (nextPieces.isEmpty()) {
                Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6); // , 1, 2, 3, 4, 5, 6
                Collections.shuffle(nextPieces);
            }

            if (check == 0) {
                currentPiece = nextPieces.get(0);
                check += 1;
            } else {
                currentPiece = prewievPiece;
            }

            nextPieces.remove(0);
    }

    // Прорисовка фигуры
    public void drawPiece(Graphics g) {
        // Падающая фиугра
        g.setColor(FIGURE_COLOR[currentPiece]); // Цвет фигуры
        for (Point p : FIGURE[currentPiece][rotation]) {
            g.fillRect((p.x + pieceOrigin.x) * BLOCK_COORDINATES, // Заполнение цветом
                    (p.y + pieceOrigin.y) * BLOCK_COORDINATES,
                    BLOCK_WIDTH, BLOCK_HEIGHT); // Ширина и высота одного блока
        }

        // Показывает следующую фигуру
        g.setColor(FIGURE_COLOR[prewievPiece]); // Цвет фигуры
        for (Point p : FIGURE[prewievPiece][0]) {
            g.fillRect((p.x + piecePrewiew.x) * BLOCK_COORDINATES, // Заполнение цветом
                    (p.y + piecePrewiew.y) * BLOCK_COORDINATES,
                    BLOCK_WIDTH, BLOCK_HEIGHT); // Ширина и высота одного блока
        }
    }

    // Проводит проверку на столкновение
    public boolean collision(int x, int y, int rotation){
        for (Point p : FIGURE[currentPiece][rotation]) {
            if (well[p.x + x][p.y + y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    }

    // Движение фигуры
    public void dropDown(){
        if (!collision(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
            if (Pause){
                pieceOrigin.y += 0; // Движение фигуры
            }
            else {
                pieceOrigin.y += 1; // Движение фигуры
            }

        }
        else {
            gameOver();
            fixToWell();
        }
        repaint();
    }

    // Проводит проверку на конец игры
    public boolean gameOver(){
        if(pieceOrigin.y == 2 || pieceOrigin.y == 3) {
            return stateGame = false;
        }
        else {
            return true;
        }
    }

    // Фиксирует фигуру при столкновении и если игра не окончена, вызывает новую фигуру
    public void fixToWell() {
        for (Point p : FIGURE[currentPiece][rotation]) {
            well[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = FIGURE_COLOR[currentPiece];
        }
        if (stateGame){ // Проверка на конец игры
            clearRows();
            newPiece();
            newPiecePrewiev();
        }
        else {}

    }

    // Двигает фигуру (влево-вправо)
    public void move(int i) {
        if (!collision(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
            pieceOrigin.x += i;
        }
        repaint();
    }

    // Поворачивает фигуру
    public void rotate(int i) {
        int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (!collision(pieceOrigin.x, pieceOrigin.y, newRotation)) {
            rotation = newRotation;
        }
        repaint();
    }

    // Мигание строк
    public void flash(int row){ // передает номер строки на удаление/ окраску
        new Thread() { // Создания потока
            @Override // Перегрузка базового метода run
            public void run() { // Метод run выполняется при запуске потока
                for (int i = 1; i < 11; i++) {
                    well[i][row] = Color.WHITE;
                }

                try {Thread.sleep(100);}
                catch (InterruptedException e){}


                for (int i = 1; i < 11; i++) {
                    well[i][row] = Color.RED;
                }

                try {Thread.sleep(500);}
                catch (InterruptedException e){}

                for (int i = 1; i < 11; i++) {
                    well[i][row] = Color.GREEN;
                }

                try {Thread.sleep(600);}
                catch (InterruptedException e){}

                for (int j = 21; j > 0; j--) {
                    empty = false;
                    for (int i = 1; i < 11; i++){
                        if (well[i][row] == Color.BLACK) {
                            empty = true;
                            break;
                        }
                    }
                    if (!empty) {
                        deleteRow(row);
                    }
                }
                Pause = false;
            }

        }.start();
    }

    // Замещение строки
    public void deleteRow(int row)  {
        for (int j = row - 1; j > 0; j--) {
                for (int i = 1; i < 11; i++) {
                    well[i][j + 1] = well[i][j];
                }
            }
    }

    // Проверяет пробелы в строке и если их нет - удаляет завершенные строки и присваивает очки
    public void clearRows(){
        int numClears = 0;
        for (int j = 21; j > 0; j--) {
            empty = false;
            for (int i = 1; i < 11; i++){
                // Если в строке ячейка черного цвета, возвращает empty = true.
                if (well[i][j] == Color.BLACK) {
                    empty = true;
                    break;
                }
            }

            if (!empty) {
                Pause = true;
                flash(j);
                numClears += 1;
            }
        }

        // Присваивает очки в соответствии с количеством одновременно очищенных строк.
        switch (numClears) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 700;
                break;
            case 4:
                score += 1500;
                break;
        }

    }

}