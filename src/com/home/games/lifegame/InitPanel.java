package com.home.games.lifegame;

public class InitPanel {
    private Panel panel;

    public InitPanel(Panel panel) {
        this.panel = panel;
    }

    public void init() {
        for (int i = 0; i < panel.BLOCK_HORIZONTAL; i++) {
            for (int j = 0; j < panel.BLOCK_VERTICAL; j++) {
                 if (i == 0 || i == panel.BLOCK_HORIZONTAL-1 || j == 0 || j == panel.BLOCK_VERTICAL-1) {
                     panel.web[i][j] = Panel.COLOR_BORDER;
                 } else {
                     panel.web[i][j] = Panel.COLOR_BACKGROUND;
                 }
            }
        }
    }
}
