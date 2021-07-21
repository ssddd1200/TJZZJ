package com.yjs.compont;

import javax.swing.*;
import java.awt.*;

/**
 * 背景色渐变 （上下渐变）
 */
public class GradientPanel extends JPanel {

    private Color startC;
    private Color endC;

    public GradientPanel(){
        startC = new Color(0,206,209);
        endC = new Color(0,233,234);
    }

    public GradientPanel(Color c1, Color c2){
        startC = c1;
        endC = c2;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (!isOpaque()){
            return;
        }
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) graphics;
        GradientPaint gradientPaint = new GradientPaint(width, 0, startC, width, height, endC, false);

        g2.setPaint(gradientPaint);
        g2.fillRect(0,0, width, height);
    }
}
