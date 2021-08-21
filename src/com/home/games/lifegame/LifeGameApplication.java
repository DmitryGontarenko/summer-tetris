package com.home.games.lifegame;

import javax.swing.*;

public class LifeGameApplication extends JPanel {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Life Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(Panel.WINDOW_WIDTH, Panel.WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // для центрирования окна
        frame.setVisible(true);

        Panel panel = new Panel();
        InitPanel init = new InitPanel(panel);
        Button button = new Button(panel);
        init.init();
        frame.add(button.start_button);
        frame.add(panel);


        while (true) {
            panel.runCalc();
            Thread.sleep(200);
        }
    }
}
