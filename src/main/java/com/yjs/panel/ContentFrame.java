package com.yjs.panel;

import com.yjs.compont.BackgroundPanel;
import com.yjs.compont.GradientPanel;
import com.yjs.compont.TimerPanel;

import javax.swing.*;
import java.awt.*;

// 主界面，用于Panel切换
public class ContentFrame extends JFrame {

    private GradientPanel topPanel;
    private JLabel imageLabel;
    private TimerPanel date;

    private BackgroundPanel mainPanel;
    private MainPanel panel1;
    private LaijianPanel panel2;
    private DaoyindPanel panel3;
    private BaogaoPanel panel4;

    public ContentFrame(){

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();

        // 设置Swing风格样式
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel = new BackgroundPanel();
        panel1 = new MainPanel(this);
        panel2 = new LaijianPanel(this);
        panel3 = new DaoyindPanel(this);
        panel4 = new BaogaoPanel(this);

        topPanel = new GradientPanel();
        topPanel.setLayout(null);
        Icon image = new ImageIcon(this.getClass().getResource("/images/yiyuan.png"));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(20,0, 440, 110);
        date = new TimerPanel();
        date.setBounds(dimension.width - 400, 40, 350, 110);
        topPanel.add(imageLabel);
        topPanel.add(date);

        mainPanel.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        //卡片布局，方便切换面板
        mainPanel.setLayout(new CardLayout());
        panel1.setBounds(0, 0,dimension.width, dimension.height - 110);
        mainPanel.add(panel1, "main");
        mainPanel.add(panel2, "laijian");
        mainPanel.add(panel3, "daoyind");
        mainPanel.add(panel4, "dayin");

        setLayout(null);

        topPanel.setBounds(0, 0, dimension.width, 110);
        add(topPanel);
        mainPanel.setBounds(0, 110, dimension.width, dimension.height - 110);
        add(mainPanel);

        setBounds(0, 0, dimension.width, dimension.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
    }

    // 切换面板
    public void changeContentPanel(String cardName){
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, cardName);
        if ("laijian".equals(cardName)){
            panel2.setFocusAndClear();
            panel2.startTimer();
        }else if ("daoyind".equals(cardName)){
            panel3.setFocusAndClear();
            panel3.startTimer();
        }else if ("dayin".equals(cardName)){
            panel4.setFocusAndClear();
        }
    }
}
