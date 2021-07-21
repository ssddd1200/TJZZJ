package com.yjs.compont;

import javax.swing.*;
import java.awt.*;

/**
 * 圆角按钮
 */
public class RoundRectButton extends JButton {

    public RoundRectButton(String s){
        super(s);
        setMargin(new Insets(0,0,0,0));
        setBorder(new BorderRound());
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (getModel().isArmed()){
            graphics.setColor(Color.GREEN);
        } else {
            graphics.setColor(getBackground());
        }
        graphics.fillRoundRect(0,0,getSize().width - 1, getSize().height - 1, 10, 10);
        super.paintComponent(graphics);
    }
}
