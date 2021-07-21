package com.yjs.panel;

import com.yjs.compont.MyTextField;
import com.yjs.compont.SplashScreen;
import com.yjs.server.PrinterServer;
import com.yjs.server.RequestServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class BaogaoPanel extends JPanel {

    private MyTextField text;
    private JButton backBtn;
    private JButton sureBtn;

    public BaogaoPanel(JFrame f){

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        text = new MyTextField();
        backBtn = new JButton("后退");
        sureBtn = new JButton("打印");

        Font btnFont = new Font("宋体", Font.BOLD, 20);

        text.setPlaceholder("请输入体检编号");
        text.setFont(new Font("宋体", Font.PLAIN, 16));
        text.setAlignmentY(JTextField.CENTER);
        text.setBorder(BorderFactory.createLineBorder(new Color(199,199,199)));

        sureBtn.setFont(btnFont);
        sureBtn.setBorder(BorderFactory.createLineBorder(new Color(6, 118, 88)));
        sureBtn.setBackground(new Color(46,196,174));
        sureBtn.setForeground(Color.WHITE);
        sureBtn.setFocusable(false);
        sureBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                RequestServer server = new RequestServer();
                if (!server.path1.isEmpty()){
                    try {
                        InputStream is = server.getWebPDF(text.getText());
                        new SplashScreen("体检报告打印中...",() ->{
                            new PrinterServer().printPDF(is);
                        }, () -> {
                            ((ContentFrame) f).changeContentPanel("main");
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        backBtn.setFont(btnFont);
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(6, 118, 88)));
        backBtn.setBackground(new Color(46,196,174));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusable(false);
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                ((ContentFrame) f).changeContentPanel("main");
            }
        });

        setLayout(null);

        text.setBounds((dimension.width-600) / 2, (int) (dimension.height * 0.3), 600, 40);
        sureBtn.setBounds((dimension.width-400) / 2, (int) (dimension.height * 0.4), 150,50);
        backBtn.setBounds((dimension.width+100) / 2, (int) (dimension.height * 0.4), 150,50);

        add(text);
        add(backBtn);
        add(sureBtn);

        setBounds(0, 0, dimension.width, dimension.height);
        setOpaque(false);
        setVisible(true);
    }

    public void setFocusAndClear(){
        text.grabFocus();
        text.setText("");
    }
}
