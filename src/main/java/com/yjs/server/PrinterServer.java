package com.yjs.server;

import com.yjs.utils.INIUtils;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.awt.print.*;
import java.io.*;

@Service
public class PrinterServer {

    Logger logger = LoggerFactory.getLogger(PrinterServer.class);

    public void printPDF(InputStream is){
        String printerName = INIUtils.getValue("printer", "name");
        PDDocument document = null;
        try {
            document = PDDocument.load(is);
            PrinterJob printerJob = PrinterJob.getPrinterJob();

            PrintService printService = findPrintService(printerName);
            if(printService==null){
                logger.info("打印失败，未找到名称为"+printerName+"的打印机，请检查。");
                return;
            }else{
                printerJob.setPrintService(printService);
            }
            //设置纸张以及缩放
            PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.SCALE_TO_FIT);
            // 设置多页打印
            Book book = new Book();
            PageFormat pageFormat = new PageFormat();
            pageFormat.setOrientation(PageFormat.PORTRAIT);

            // 设置纸张
            pageFormat.setPaper(getPaper());
            book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
            // 设置页面横纵向
            printerJob.setPageable(book);
            // 份数
            printerJob.setCopies(1);
            PrintRequestAttributeSet parms = new HashPrintRequestAttributeSet();
            // 单双面，默认单面
            parms.add(Sides.ONE_SIDED);

            printerJob.print(parms);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("PDF文件打印出错！请检查参数");
        } catch (PrinterException e) {
            e.printStackTrace();
            logger.info("PDF文件打印出错！请检查参数");
        } finally {
            if (document != null){
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据打印机名称获取打印机服务
     * @param printerName
     * @return
     */
    private PrintService findPrintService(String printerName){
        PrintService printService = null;
        if (printerName != null && !printerName.isEmpty()){
            // 获取本台电脑上所有的打印机设备
            PrintService[] printServices = PrinterJob.lookupPrintServices();
            if (printServices == null || printServices.length == 0){
            }else{
                for (int i = 0; i< printServices.length;i++){
                    System.out.println(printServices[i].getName());
                    if (printServices[i].getName().contains(printerName)){
                        printService = printServices[i];
                        break;
                    }
                }
            }
        }else{
        }
        return printService;
    }

    private static Paper getPaper(){
        Paper paper = new Paper();
        // 默认设置页面为A4纸
        int width = 595;
        int height = 842;
        // 设置边距
        int marginLeft = 20;
        int marginRight = 20;
        int marginTop = 20;
        int marginBottom = 20;

        paper.setSize(width, height);
        paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight), height - (marginTop + marginBottom));
        return paper;
    }
}
