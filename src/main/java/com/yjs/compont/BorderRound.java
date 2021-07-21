package com.yjs.compont;

import javax.swing.border.Border;
import java.awt.*;

public class BorderRound implements Border {

    private Color color;

    public BorderRound(){
        this.color = Color.gray;
    }

    public BorderRound(Color c){
        this.color = c;
    }

    @Override
    public void paintBorder(Component component, Graphics graphics, int i, int i1, int i2, int i3) {
        graphics.setColor(this.color);
        graphics.drawRoundRect(0, 0, component.getWidth() - 1, component.getHeight() - 1, 20, 20);
    }

    @Override
    public Insets getBorderInsets(Component component) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
