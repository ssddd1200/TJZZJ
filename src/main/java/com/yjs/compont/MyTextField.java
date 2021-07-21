package com.yjs.compont;

import javax.swing.*;
import java.awt.*;

/**
 * Placeholder功能的TextField
 */
public class MyTextField extends JTextField {

    private String placeholder;

    public MyTextField(){
        super();
    }

    public MyTextField(final int pColumns){
        super(pColumns);
    }

    public MyTextField(final String pText){
        super(pText);
    }

    public MyTextField(final String pText, final int pColumns){
        super(pText, pColumns);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0){
            return;
        }
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getDisabledTextColor());
//        g2.drawString(placeholder, getInsets().left, graphics.getFontMetrics().getMaxAscent()+getInsets().top);
        g2.drawString(placeholder, getInsets().left, getHeight()/2+getInsets().top+2);
    }
}
