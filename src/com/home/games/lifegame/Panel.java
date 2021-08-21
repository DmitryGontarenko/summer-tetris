package com.home.games.lifegame;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.awt.Color.*;

public class Panel extends JPanel {
    protected static final int WINDOW_WIDTH = 1195; // Ширина окна (используется в статическом методе)
    protected static final int WINDOW_HEIGHT = 653; // Высота окна (используется в статическом методе)
    protected final int BLOCK_HORIZONTAL = 70; // Количество блоков (ячеек) по горизонтали
    protected final int BLOCK_VERTICAL = 37; // Количество блоков (ячеек) по вертикали
    protected final int BLOCK_WIDTH = 15; // Ширина блока (ячейки)
    protected final int BLOCK_HEIGHT = 15; // Высота блока (ячейки)
    protected final int BLOCK_COORDINATES = 17; // Размер

    protected static final Color COLOR_BORDER = GRAY;
    protected static final Color COLOR_BACKGROUND = BLACK;
    protected static final Color COLOR_FIGURE = CYAN;

    protected Color[][] web = new Color[BLOCK_HORIZONTAL][BLOCK_VERTICAL];

    private boolean isCellDrawn = false;

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        for (int i = 0; i < BLOCK_HORIZONTAL; i++) {
            for (int j = 0; j < BLOCK_VERTICAL; j++) {
                graphics.setColor(web[i][j]);
                graphics.fillRect(BLOCK_COORDINATES * i, BLOCK_COORDINATES * j, BLOCK_WIDTH, BLOCK_HEIGHT);
            }
        }

        if (!isCellDrawn) {
            drawCells(graphics);
            isCellDrawn = true;
        }
    }

    private void drawCells(Graphics graphics) {
        graphics.setColor(COLOR_FIGURE); // цвет клетки
        randomFill(graphics);
    }

    private void randomFill(Graphics graphics) {
        final Random random = new Random();

        for (int i = 0; i < ((BLOCK_HORIZONTAL-2) * (BLOCK_VERTICAL-2)); i++) {
            if (Math.random() < 0.3) {
                int x_random = random.nextInt(BLOCK_HORIZONTAL - 2); // где 2 - это границы
                int y_random = random.nextInt(BLOCK_VERTICAL - 2);

                web[x_random + 1][y_random + 1] = COLOR_FIGURE;
                graphics.fillRect((x_random + 1) * BLOCK_COORDINATES, (y_random + 1) * BLOCK_COORDINATES, BLOCK_WIDTH, BLOCK_HEIGHT);
            }
        }
    }

    public void runCalc() {
        Pair<Color[][], Color[][]> arrayByColor = getArrayByColor();

        for (int y = 0; y < BLOCK_VERTICAL-1; y++) {
            for (int x = 0; x < BLOCK_HORIZONTAL - 1; x++) {
                if (arrayByColor.getKey()[x][y] == web[x][y]) {
                    web[x][y] = COLOR_FIGURE;
                }
                if (arrayByColor.getValue()[x][y] == web[x][y])
                    web[x][y] = COLOR_BACKGROUND;
            }
        }

        repaint();
    }

    private Pair<Color[][], Color[][]> getArrayByColor() {
        Color[][] arrayBlackToWhite = new Color[BLOCK_HORIZONTAL][BLOCK_VERTICAL];
        Color[][] arrayWhiteToBlack = new Color[BLOCK_HORIZONTAL][BLOCK_VERTICAL];

        for (int y = 0; y < BLOCK_VERTICAL-1; y++) {
            for (int x = 0; x < BLOCK_HORIZONTAL-1; x++) {
                int count_of_white = 0;

                if (x > 0 && y > 0) {
                    if (web[x][y+1] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                    if (web[x+1][y+1] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                    if (web[x+1][y] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                    if (web[x+1][y-1] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                    if (web[x][y-1] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                    if (web[x-1][y-1] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                    if (web[x-1][y] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                    if (web[x-1][y+1] == COLOR_FIGURE) {
                        count_of_white++;
                    }
                }

                // Правила: B2/S23
                if (web[x][y] == COLOR_BACKGROUND && count_of_white == 3) {
                    arrayBlackToWhite[x][y] = web[x][y];

                }
                if (web[x][y] == COLOR_FIGURE && (count_of_white < 2 || count_of_white > 3)) {
                    arrayWhiteToBlack[x][y] = web[x][y];
                }

            }
        }

        return new Pair<>(arrayBlackToWhite, arrayWhiteToBlack);
    }
}
