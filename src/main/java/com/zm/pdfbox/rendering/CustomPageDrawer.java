package com.zm.pdfbox.rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.PageDrawer;
import org.apache.pdfbox.rendering.PageDrawerParameters;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;

/**
 * Example showing custom rendering by subclassing PageDrawer.
 * 
 * <p>If you want to do custom graphics processing rather than Graphics2D rendering, then you should
 * subclass {@link PDFGraphicsStreamEngine} instead. Subclassing PageDrawer is only suitable for
 * cases where the goal is to render onto a Graphics2D surface.
 *
 * @author John Hewson
 */
public class CustomPageDrawer
{
    public static void main(String[] args) throws IOException
    {
//        File file = new File("src/main/resources/pdfbox/",
//                             "image.pdf");
//        
//        PDDocument doc = PDDocument.load(file);
//        PDFRenderer renderer = new PDFRenderer(doc);
////        PDFRenderer renderer = new MyPDFRenderer(doc);
//        BufferedImage image = renderer.renderImage(0);
//        ImageIO.write(image, "PNG", new File("target/custom-render.png"));
//        doc.close();
    	CustomPageDrawer d =  new CustomPageDrawer();
    	d.createImage("image.pdf", "image034.png");
//    	d.createImage("HK00047 (04.16) Benefits Claims statistic (bilingual).pdf", "image047.png");
//    	d.createImage("HK00050 (07.16) Why do we all need protection (EN).pdf", "image050.png");
//    	d.createImage("HK00055 (07.16) Investment Flipchart (EN).pdf", "image055.png");
//    	d.createImage("HK00063 (07.16) Business Protection loan protection (EN).pdf", "image063.png");
//    	d.createImage("HK00083 (07.16) Pacific new business checklist (EN).pdf", "image083.png");
//    	d.createImage("HK00125 (08.16) Pacific Investment Choice Brochure (EN).pdf", "image125.png");
//    	d.createImage("HK00125CH (08.16) Pacific Investment Choice Brochure (CH).pdf", "image125ch.png");
//    	d.createImage("HK00132 (07.16) Pacific Product Brochure (EN).pdf", "image132.png");
    }
    
    public void createImage(String fileName, String imgName)throws IOException{
    	 File file = new File("src/main/resources/pdfbox/",
    			 fileName);

		PDDocument doc = PDDocument.load(file);
		PDFRenderer renderer = new PDFRenderer(doc);
		//PDFRenderer renderer = new MyPDFRenderer(doc);
		BufferedImage image = renderer.renderImage(0);
		ImageIO.write(image, "PNG", new File("target/"+imgName));
		doc.close();
    }

    /**
     * Example PDFRenderer subclass, uses MyPageDrawer for custom rendering.
     */
    private static class MyPDFRenderer extends PDFRenderer
    {
        MyPDFRenderer(PDDocument document)
        {
            super(document);
        }

        @Override
        protected PageDrawer createPageDrawer(PageDrawerParameters parameters) throws IOException
        {
            return new MyPageDrawer(parameters);
        }
    }

    /**
     * Example PageDrawer subclass with custom rendering.
     */
    private static class MyPageDrawer extends PageDrawer
    {
        MyPageDrawer(PageDrawerParameters parameters) throws IOException
        {
            super(parameters);
        }

        /**
         * Color replacement.
         */
        @Override
        protected Paint getPaint(PDColor color) throws IOException
        {
            // if this is the non-stroking color
            if (getGraphicsState().getNonStrokingColor() == color)
            {
                // find red, ignoring alpha channel
                if (color.toRGB() == (Color.RED.getRGB() & 0x00FFFFFF))
                {
                    // replace it with blue
                    return Color.BLUE;
                }
            }
            return super.getPaint(color);
        }

        /**
         * Glyph bounding boxes.
         */
        @Override
        protected void showGlyph(Matrix textRenderingMatrix, PDFont font, int code, String unicode,
                                 Vector displacement) throws IOException
        {
            // draw glyph
            super.showGlyph(textRenderingMatrix, font, code, unicode, displacement);
            
            // bbox in EM -> user units
            Shape bbox = new Rectangle2D.Float(0, 0, font.getWidth(code) / 1000, 1);
            AffineTransform at = textRenderingMatrix.createAffineTransform();
            bbox = at.createTransformedShape(bbox);
            
            // save
            Graphics2D graphics = getGraphics();
            Color color = graphics.getColor();
            Stroke stroke = graphics.getStroke();
            Shape clip = graphics.getClip();

            // draw
            graphics.setClip(graphics.getDeviceConfiguration().getBounds());
            graphics.setColor(Color.RED);
            graphics.setStroke(new BasicStroke(.5f));
            graphics.draw(bbox);

            // restore
            graphics.setStroke(stroke);
            graphics.setColor(color);
            graphics.setClip(clip);
        }

        /**
         * Filled path bounding boxes.
         */
        @Override
        public void fillPath(int windingRule) throws IOException
        {
            // bbox in user units
            Shape bbox = getLinePath().getBounds2D();
            
            // draw path (note that getLinePath() is now reset)
            super.fillPath(windingRule);
            
            // save
            Graphics2D graphics = getGraphics();
            Color color = graphics.getColor();
            Stroke stroke = graphics.getStroke();
            Shape clip = graphics.getClip();

            // draw
            graphics.setClip(graphics.getDeviceConfiguration().getBounds());
            graphics.setColor(Color.GREEN);
            graphics.setStroke(new BasicStroke(.5f));
            graphics.draw(bbox);

            // restore
            graphics.setStroke(stroke);
            graphics.setColor(color);
            graphics.setClip(clip);
        }

        /**
         * Custom annotation rendering.
         */
        @Override
        public void showAnnotation(PDAnnotation annotation) throws IOException
        {
            // save
            saveGraphicsState();
            
            // 35% alpha
            getGraphicsState().setNonStrokeAlphaConstants(0.35);
            super.showAnnotation(annotation);
            
            // restore
            restoreGraphicsState();
        }
    }
}
