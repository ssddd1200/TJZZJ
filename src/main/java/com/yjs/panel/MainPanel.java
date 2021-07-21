package com.yjs.panel;

import com.yjs.compont.RoundRectButton;
import com.yjs.compont.SplashScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {

    private JLabel label;

    private JButton button1;
    private JButton button2;
    private JButton button3;

    public MainPanel(JFrame f){

        label = new JLabel("你好!欢迎来我院进行体检检查，请按下面按钮进行业务体检!谢谢配合!", JLabel.CENTER);

        button1 = new JButton("来检确认");
        button2 = new JButton("导引单打印");
        button3 = new JButton("报告打印");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();

        Font btnFont = new Font("宋体", Font.BOLD, 20);

        label.setFont(new Font("宋体", Font.PLAIN, 16));

        button1.setFont(btnFont);
        button1.setBorder(BorderFactory.createLineBorder(new Color(6, 118, 88)));
        button1.setBackground(new Color(46,196,174));
        button1.setForeground(Color.WHITE);

        //去除按钮获得焦点时，文字周围的虚线
        button1.setFocusable(false);
        button1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                new SplashScreen("", () -> {
                    ((ContentFrame) f).changeContentPanel("laijian");
                });
            }
        });

        button2.setFont(btnFont);
        button2.setBorder(BorderFactory.createLineBorder(new Color(6, 118, 88)));
        button2.setBackground(new Color(46,196,174));
        button2.setForeground(Color.WHITE);
        button2.setFocusable(false);
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                new SplashScreen("", () -> {
                    ((ContentFrame) f).changeContentPanel("daoyind");
                });
            }
        });

        button3.setFont(btnFont);
        button3.setBorder(BorderFactory.createLineBorder(new Color(6, 118, 88)));
        button3.setBackground(new Color(46,196,174));
        button3.setForeground(Color.WHITE);
        button3.setFocusable(false);
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                new SplashScreen("", () -> {
                    ((ContentFrame) f).changeContentPanel("dayin");
                });
            }
        });

        // 布局
        setLayout(null);
        label.setBounds((dimension.width-600) / 2, (int) (dimension.height * 0.1), 600, 30);
        button1.setBounds((dimension.width-200) / 2, (int) (dimension.height * 0.2), 200, 50);
        button2.setBounds((dimension.width-200) / 2, (int) (dimension.height * 0.4), 200, 50);
        button3.setBounds((dimension.width-200) / 2, (int) (dimension.height * 0.6), 200, 50);

        add(label);
        add(button1);
        add(button2);
        add(button3);
        setOpaque(false);
        setBounds(0, 0, dimension.width, dimension.height);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setUndecorated(true);
        setVisible(true);
    }


}
