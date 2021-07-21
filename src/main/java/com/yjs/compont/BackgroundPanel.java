package com.yjs.compont;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image image;

    public BackgroundPanel(){
        image = new ImageIcon(this.getClass().getResource("/images/background.png")).getImage();
    }

    public BackgroundPanel(Image i){
        image = i;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
