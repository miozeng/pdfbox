package com.zm.pdfbox.zmtest.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class PrintPdf {
	public static void main(String[] args) {
		// System.out.println("Value:"+test());
		// 打印pdf的一个方法，首先安装下PDFCreator软件
		try {
//			printFile("E:\\myworkspace\\pdfbox\\src\\\main\\resources\\pdfbox");
			printFileName("E:\\myworkspace\\pdfbox\\src\\main\\resources\\pdfbox\\FillFormField.pdf");
		} catch (Exception e) {
			System.out.println("打印文件异常：" + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * BoX方法打印，按路径
	 * @param pathFile
	 * @throws Exception
	 */
	   public static void printBOXFiles(String pathFile) throws Exception{
		   String filename = "src/main/resources/pdfbox/image.pdf";
	        PDDocument document = PDDocument.load(new File(filename));
		   PrinterJob job = PrinterJob.getPrinterJob();
		   job.setPageable(new PDFPageable(document));
		   if (job.printDialog()) {
		       job.print();
		   }
	    		    	
	    }

	   /**
	    * 系统打印，按文件名
	    * @param FileName
	    * @throws Exception
	    */
		public static void printFileName(String FileName) throws Exception {
		
		File pdfFile = new File(FileName);
		// 构建打印请求属性集
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(new JobName(FileName.substring(FileName.lastIndexOf("\\")+1,FileName.length() ), null));
		pras.add(MediaSizeName.ISO_A4);

		// 设置打印格式，因为未确定文件类型，这里选择AUTOSENSE
//		DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;//pdf打印
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;//自动
//		DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;//ps打印
		// 查找所有的可用打印服务
		HashAttributeSet has = new HashAttributeSet();
//		has.add(new PrinterName("Officejet J5500 series", null)); // 添加打印机名称
		PrintService[] printService = PrintServiceLookup
				.lookupPrintServices(flavor, has);
//		if (printService[0] != null) {
			   if (printService.length > 0) {
		       DocPrintJob pj = printService[1].createPrintJob();// 创建打印任务
		       try {
		           FileInputStream fis = new FileInputStream(pdfFile);// 构造待打印的文件流
		           DocAttributeSet das=new HashDocAttributeSet();	
//		           Doc doc = new SimpleDoc(fis, flavor, null);// 建立打印文件格式
		           Doc doc = new SimpleDoc(fis, flavor, das);// 建立打印文件格式
		           pj.print(doc, pras);// 进行文件的打印
		        } catch (FileNotFoundException fe) {
		        } catch (PrintException e) {
		        }
		   }


	}
/**
 * 系统打印，按路径
 * @param path
 * @throws Exception
 */
	public static void printFile(String path) throws Exception {
		File file = new File(path);
		File[] fies = file.listFiles();
		
		for (int x = 0; x < fies.length; x++) {
			if (fies[x].isDirectory()){
				
				printFile(fies[x].getPath());
			}
			else{
				
				String fileExt = fies[x].getName().substring(
						fies[x].getName().indexOf(".") + 1, fies[x].getName().length());
				if ("pdf".equalsIgnoreCase(fileExt)) {
					System.out.println("打印file " + fies[x].getName());
					String filepath = path + File.separator + fies[x].getName();
					File pdfFile = new File(filepath);
					// 构建打印请求属性集
					PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
					pras.add(new JobName(fies[x].getName(), null));
					
					HashAttributeSet has = new HashAttributeSet();
//					has.add(new PrinterName("Officejet J5500 series", null)); // 添加打印机名称

					// 设置打印格式，因为未确定文件类型，这里选择AUTOSENSE
					DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
					
					// 查找所有的可用打印服务
					PrintService printService[] = PrintServiceLookup
							.lookupPrintServices(flavor, has);

					if (printService[1] != null) {
						//获得打印服务的文档打印作业
						DocPrintJob job = printService[1].createPrintJob(); // 创建打印任务
						
						DocAttributeSet das=new HashDocAttributeSet();				
						
						InputStream fis = new FileInputStream(pdfFile); // 构造待打印的文件流
						Doc doc = new SimpleDoc(fis, flavor, das); // 建立打印文件格式
						job.print(doc, pras); // 进行文件的打印
					}
				}		
			}
		}
	
	}
}
