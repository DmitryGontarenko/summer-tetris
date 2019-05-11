import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class GameTetris extends JPanel {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris"); // Создаем окно приложения
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Создание системных кнопок (Свернуть, развернуть, закрыть)
        frame.setResizable(false); // Запрещаем масштабирования окна
        frame.setSize(Model.WINDOW_WIDTH, Model.WINDOW_HEIGHT); // Устанавливаем размеры окна
        frame.setVisible(true); // Отображаем созданное окно

        Model game = new Model();
        Initial init = new Initial(game);
        init.init();
        frame.add(game);

        // Обработчик нажатий клавиш
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: // Стрелка вверх
                        game.rotate(+1);
                        break;
                    case KeyEvent.VK_LEFT: // Стрелка влево
                        game.move(-1);
                        break;
                    case KeyEvent.VK_RIGHT: // Стрелка вправо
                        game.move(+1);
                        break;
                    case KeyEvent.VK_DOWN: // Стрелка вниз
                        game.dropDown();
                        break;
                }
            }
            public void keyReleased(KeyEvent e) {}
        });

        while (game.stateGame) { // Цикл выполняется, пока игра не закончена
            try {
                if (Model.score > 1000) { // Если кол-во набранных очков больше заданного числа, то
                    Model.level = 2; // Присваиваем новый уровень игры
                    Model.speed = 400; // Присваиваем новую скорость игры
                }
                if (Model.score > 3000) {
                    Model.level = 3;
                    Model.speed = 300;
                }
                if (Model.score > 5000) {
                    Model.level = 4;
                    Model.speed = 200;
                }
                if (Model.score > 10000) {
                    Model.level = 5;
                    Model.speed = 100;
                }

                Thread.sleep(Model.speed); // Время задержки анимации движения фигуры
                game.dropDown(); // Выполняется метод, отвечающий за движением фигуры
            }
            catch (InterruptedException e) { // Выпоняется при прирывании потока другим потоком
                System.out.println(e);
            }
        }
    }
}
