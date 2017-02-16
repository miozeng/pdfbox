# pdfbox
pdfbox demo
PDFBox是Java实现的PDF文档协作类库，提供PDF文档的创建、处理以及文档内容提取功能，也包含了一些命令行实用工具。<br/>
##主要特性包括：<br/>
###1.Extract Text
从pdf文档中提取文本。<br/>
###2.Split & Merge
将一个单独的pdf文件拆分成多个文件，或者合并多个文件为一个文件
###3.Fill Forms
从PDF表单中提取数据或者填充表单。
###4.Preflight
验证PDF是否违反 PDF/A-1b标准
###5.Print
运用标准的java打印api打印pdf
###6.Save as Image
保存pdf为一张图片，比如png jpeg
###7.Create PDFs
利用嵌入的字体图片创建pdf文件
###8.Signing
数字签名


##Maven依赖：
<dependency>
  <groupId>org.apache.pdfbox</groupId>
  <artifactId>pdfbox</artifactId>
  <version>2.0.0</version>
</dependency>

##注
Due to the change of the java color management module towards “LittleCMS”, users can experience slow performance in color operations. Solution: disable LittleCMS in favour of the old KCMS (Kodak Color Management System):<br/>
start with -Dsun.java2d.cmm=sun.java2d.cmm.kcms.KcmsServiceProvideror call<br/>
System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");<br/>
Sources:<br/>
http://www.subshell.com/en/subshell/blog/Wrong-Colors-in-Images-with-Java8-100.html<br/>
https://bugs.openjdk.java.net/browse/JDK-8041125<br/>
