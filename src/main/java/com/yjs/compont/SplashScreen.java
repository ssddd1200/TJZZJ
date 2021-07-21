package com.yjs.compont;

import com.yjs.func.SplashFun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SplashScreen extends JWindow {
    static JProgressBar progressBar = new JProgressBar();
    static int count = 1, TIMER_PAUSE = 25;
    private int PROGBAR_MAX = 100;
    static Timer progressBarTimer;
    private SplashFun startFun;
    private SplashFun endFun;
    private String text;

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            progressBar.setValue(count);
            if (PROGBAR_MAX == count) {
               endLoading();
                progressBarTimer.stop();
                SplashScreen.this.setVisible(false);
                SplashScreen.this.dispose();
                count = 0;
            }
            count++;
        }
    };

    public void setPROGBAR_MAX(int PROGBAR_MAX) {
        this.PROGBAR_MAX = PROGBAR_MAX;
    }

    public SplashScreen(String t, SplashFun endFunc) {
        if (t.isEmpty()){
            text = "加载中...";
        }else{
            text = t;
        }
        endFun = endFunc;
        init();
    }

    public SplashScreen(String t, SplashFun startFunc, SplashFun endFunc) {
        if (t.isEmpty()){
            text = "加载中...";
        }else{
            text = t;
        }
        startFun = startFunc;
        endFun = endFunc;
        init();
    }

    private void init(){
        Container container = getContentPane();

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();

        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
        panel.setLayout(new BorderLayout());
        container.setBackground(new Color(153, 153, 153));
        container.setPreferredSize(dimension);
        container.setLayout(null);
        panel.setBounds((dimension.width-400)/2, (dimension.height-80)/2, 400, 80);
        container.add(panel);

        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(label, BorderLayout.CENTER);

        progressBar.setMaximum(PROGBAR_MAX);
        panel.add(progressBar, BorderLayout.SOUTH);
        pack();
        setOpacity(0.85f);
        setVisible(true);
        startProgressBar();
    }

    private void startProgressBar() {
        startLoading();
        progressBarTimer = new Timer(TIMER_PAUSE, al);
        progressBarTimer.start();
    }

    private void startLoading(){
        if (startFun != null){
            startFun.apply();
        }
    }

    private void endLoading() {
        if (endFun != null){
            endFun.apply();
        }
    }
}