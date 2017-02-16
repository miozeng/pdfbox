package com.zm.pdfbox.zmtest.extract;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

/**
 * This is an example on how to extract text from a specific area on the PDF document.
 *
 * @author Ben Litchfield
 */
public final class ExtractTextByArea
{
    private ExtractTextByArea()
    {
        //utility class and should not be constructed.
    }


    /**
     * This will print the documents text in a certain area.
     *
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error parsing the document.
     */
    public static void main( String[] args ) throws IOException
    {
        if( args.length != 1 )
        {
            usage();
        }
        else
        {
            PDDocument document = null;
            try
            {
                document = PDDocument.load( new File(args[0]) );
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition( true );
                Rectangle rect = new Rectangle( 10, 280, 275, 60 );
                stripper.addRegion( "class1", rect );
                PDPage firstPage = document.getPage(0);
                stripper.extractRegions( firstPage );
                System.out.println( "Text in the area:" + rect );
                System.out.println( stripper.getTextForRegion( "class1" ) );
            }
            finally
            {
                if( document != null )
                {
                    document.close();
                }
            }
        }
    }

    /**
     * This will print the usage for this document.
     */
    private static void usage()
    {
        System.err.println( "Usage: java " + ExtractTextByArea.class.getName() + " <input-pdf>" );
    }

}