package com.home.games.lifegame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JPanel implements ActionListener {
    public JButton start_button;
    private final Panel panel;

    public Button(Panel panel) {
        this.panel = panel;
        start_button = new JButton("Run");
        start_button.setBounds(Panel.WINDOW_WIDTH - 150, 200, 100, 50);
        start_button.setActionCommand("Run");
        start_button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Run".equals(e.getActionCommand())) {
                    }
    }
}
