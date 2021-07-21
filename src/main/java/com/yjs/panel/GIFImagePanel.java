package com.yjs.panel;

import javax.swing.*;
import java.awt.*;

/**
 * gif文件图片加载
 */
public class GIFImagePanel extends JPanel {

    Image image;

    public GIFImagePanel(){
        image = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/gif/ceshigif.gif"));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (image != null){
            graphics.drawImage(image, 0, 0, this);
        }
    }
}
