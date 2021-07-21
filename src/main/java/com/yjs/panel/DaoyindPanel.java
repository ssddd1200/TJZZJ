package com.yjs.panel;

import com.alibaba.fastjson.JSONObject;
import com.yjs.UI.ScrollBarUI;
import com.yjs.compont.MyTextField;
import com.yjs.compont.SplashScreen;
import com.yjs.server.PrinterServer;
import com.yjs.server.RequestServer;
import com.yjs.utils.INIUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Vector;

public class DaoyindPanel extends JPanel {

    // 计数器
    static int count = INIUtils.getNumberValue("back", "time");
    private JLabel worrLabel;
    private Timer timer;
    private MyTextField text;
    private JButton searchBtn;
    private JButton backBtn;
    private JButton printBtn;

    private JScrollPane scroll;
    private JTable table;

    // table数据集
    private AbstractTableModel atm;
    private Vector<java.util.List<String>> rowData;
    private static String[] columnNames = {"姓名", "性别", "科室","检查项目"};

    public DaoyindPanel(JFrame f){

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        RequestServer server = new RequestServer();

        worrLabel = new JLabel();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (count == 0){
                    timer.stop();
                    count = INIUtils.getNumberValue("back", "time");
                    ((ContentFrame) f).changeContentPanel("main");
                }
                worrLabel.setText("本页面将在"+count+ "s后将自动返回");
                count--;
            }
        });
        worrLabel.setForeground(Color.RED);
        text = new MyTextField();
        backBtn = new JButton("后退");
        printBtn = new JButton("打印");
        searchBtn = new JButton();
        rowData = new Vector<>();
        atm = new AbstractTableModel() {
            // 取行数
            @Override
            public int getRowCount() {
                return rowData.size();
            }
            // 取列数
            @Override
            public int getColumnCount() {
                return columnNames.length;
            }
            // 取值
            @Override
            public Object getValueAt(int row, int column) {
                if (!rowData.isEmpty()){
                    return ((Vector<String>) rowData.elementAt(row)).elementAt(column);
                }else
                    return null;
            }
            // 取列名
            @Override
            public String getColumnName(int i) {
                return columnNames[i];
            }
        };

        table = new JTable(atm);
        scroll = new JScrollPane();

        Font btnFont = new Font("宋体", Font.BOLD, 20);

        text.setPlaceholder("请输入体检编号或者身份证");
        text.setFont(new Font("宋体", Font.PLAIN, 16));
        text.setAlignmentY(JTextField.CENTER);
        text.setBorder(BorderFactory.createLineBorder(new Color(199,199,199)));

        searchBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/search.png")));
        searchBtn.setBorder(BorderFactory.createLineBorder(new Color(6, 118, 88)));
        searchBtn.setBackground(new Color(46,196,174));
        searchBtn.setFocusable(false);
        searchBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                // 清理表数据
                rowData.removeAllElements();
                table.updateUI();

                if (!server.path2.isEmpty()){
                    JSONObject json = server.getTijianxm(text.getText());
                    if (json.getString("appcode").equals("0")){
                        java.util.List<LinkedHashMap> list = json.getObject("resultlist", java.util.List.class);
                        for (int i = 0;i< list.size(); i++){
                            Vector<String> list2 = new Vector<>();
                            list2.add(list.get(i).get("xingming").toString());
                            list2.add(list.get(i).get("xingbie").toString());
                            list2.add(list.get(i).get("zhixingksmc").toString());
                            list2.add(list.get(i).get("xiangmumc").toString());

                            rowData.add(list2);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "未找到信息", "消息",JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "参数配置出错", "错误",JOptionPane.WARNING_MESSAGE);
                }

//                atm.fireTableStructureChanged();
                table.updateUI();
                setTableUI();
            }
        });

        //表格标题
        JTableHeader header = table.getTableHeader();
        // 表头大小
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setFont(new Font("宋体", Font.BOLD, 16));
        header.setBackground(new Color(31,196, 190));
        // 列不可拖动
        header.setReorderingAllowed(false);
        // 列不可移动
        header.setResizingAllowed(false);

        // 表格行高
        table.setRowHeight(40);
        table.setBackground(new Color(255,255,255));
        table.setPreferredSize(new Dimension(600, 360));
        table.setLocation(0, 40);
        table.setFont(new Font("宋体", Font.BOLD, 16));

        setTableUI();

        scroll.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scroll.setViewportView(table);
        scroll.getVerticalScrollBar().setUI(new ScrollBarUI());

        printBtn.setFont(btnFont);
        printBtn.setBorder(BorderFactory.createLineBorder(new Color(6, 118, 88)));
        printBtn.setBackground(new Color(46,196,174));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFocusable(false);
        printBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!server.path4.isEmpty()){
                    try {
                        InputStream is = server.printDaoyind(text.getText());
//                        File file = new File("read.pdf");
//                        byte[] bytes = new byte[is.available()];
//                        is.read(bytes);
//                        OutputStream downFile = new FileOutputStream(file);
//                        BufferedOutputStream bos = new BufferedOutputStream(downFile);
//                        bos.write(bytes);
                        new SplashScreen("导引单打印中...",() ->{

                            new PrinterServer().printPDF(is);
                        }, () -> {
                            if (timer.isRunning()){
                                timer.stop();
                            }
                            count = INIUtils.getNumberValue("back", "time");
                            ((ContentFrame) f).changeContentPanel("main");
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "参数配置出错", "错误",JOptionPane.WARNING_MESSAGE);
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
                if (timer.isRunning()){
                    timer.stop();
                }
                count = INIUtils.getNumberValue("back", "time");
                ((ContentFrame) f).changeContentPanel("main");
            }
        });

        setLayout(null);

        text.setBounds((dimension.width-600) / 2, 40, 500, 40);
        searchBtn.setBounds((dimension.width+400) / 2, 40, 100, 40);
        scroll.setBounds((dimension.width-600) / 2, 90, 600, 405);
        printBtn.setBounds((dimension.width-400) / 2, 520, 150,50);
        backBtn.setBounds((dimension.width+100) / 2, 520, 150,50);
        worrLabel.setBounds((dimension.width+140) / 2, 500, 160,15);

        add(text);
        add(searchBtn);
        add(scroll);
        add(worrLabel);
        add(printBtn);
        add(backBtn);

        setBounds(0, 0, dimension.width, dimension.height);
        setOpaque(false);
        setVisible(true);
    }

    public void setFocusAndClear(){
        text.grabFocus();
        text.setText("");
        rowData.removeAllElements();
        table.updateUI();
        setTableUI();
    }

    public void startTimer(){
        timer.start();
    }

    public void setTableUI(){
        //表格间隔色
        DefaultTableCellRenderer ter = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                if (row % 2 == 0){
                    setBackground(Color.decode("#FEEAEA"));
                }else if (row % 2 == 1){
                    setBackground(Color.WHITE);
                }
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createLineBorder(Color.WHITE));
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        for (int i  = 0; i< table.getColumnCount();i++){
            table.getColumn(table.getColumnName(i)).setCellRenderer(ter);
        }

        for (int i = 0; i<columnNames.length;i++){
            setTableHeaderColor(i, new Color(31,196, 190));
        }
    }

    public void setTableHeaderColor(int columnIndex, Color c) {
        TableColumn column = table.getTableHeader().getColumnModel().getColumn(columnIndex);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {

                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("宋体", Font.BOLD, 16));
                ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                        .setHorizontalAlignment(DefaultTableCellRenderer.CENTER);// 表头内容居中

                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        cellRenderer.setBackground(c);
        column.setHeaderRenderer(cellRenderer);
    }
}
