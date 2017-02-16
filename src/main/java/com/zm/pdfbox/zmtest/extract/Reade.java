package com.zm.pdfbox.zmtest.extract;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Reade {
	public static void main(String[] args){

        File pdfFile = new File("src/main/resources/pdfbox/image.pdf");
        PDDocument document = null;
        try
        {
            // 方式一：
            /**
            InputStream input = null;
            input = new FileInputStream( pdfFile );
            //加载 pdf 文档
            PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
            parser.parse();
            document = parser.getPDDocument();
            **/

            // 方式二：
            document=PDDocument.load(pdfFile);

            // 获取页码
            int pages = document.getNumberOfPages();

            // 读文本内容
            PDFTextStripper stripper=new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            String content = stripper.getText(document);
            System.out.println(content);  
            System.out.println("=============================================================");  
            BufferedReader bre = null;
            String str = "";
            int state = 0;
            bre = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            while ((str = bre.readLine())!= null) {
            	  if(str.startsWith("International")){
                      state = 1;
                      System.out.println(str);
                      continue;
                  }

            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
}
