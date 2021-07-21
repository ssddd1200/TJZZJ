package com.yjs.compont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerPanel extends JPanel {

    private Timer time;
    private JLabel timeLabel;

    public TimerPanel(){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        timeLabel = new JLabel();
        timeLabel.setSize(dimension);
        timeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        timeLabel.setForeground(Color.white);

        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                timeLabel.setText(new SimpleDateFormat("yyyy年MM月dd日 EEEE HH:mm:ss").format(new Date()));
            }
        });

        time.start();
        add(timeLabel);
        setOpaque(false);
        setVisible(true);
    }
}
