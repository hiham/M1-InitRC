package main.java.com.exemple.View;

import main.java.com.exemple.Controller.CustomMouseListener;
import main.java.com.exemple.Tools.CloseCrossFinder;
import main.java.com.exemple.Tools.Cross;
import main.java.com.exemple.Tools.Type;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageParser;
import org.apache.commons.imaging.formats.tiff.TiffImagingParameters;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class CutView {

    private JLabel imageLabel;
    private MenuView menuView;
    private ArrayList<Cross> crossArrayList = new ArrayList<> ();
    private ArrayList<Cross> drawnCrosses = new ArrayList<> ();
    private BufferedImage image;
    private int imageWidth,imageHeight,ogWidth;
    private boolean isLoaded = false;
    private  int year = 0;
    private double zoomFactor = 100;
    private CustomMouseListener customMouseListener;

    public CutView(MenuView m)
    {
        menuView = m;
        customMouseListener = new CustomMouseListener (m);
        imageLabel = new JLabel (){
            @Override
            protected void paintComponent (Graphics g) {
                super.paintComponent (g);
                Graphics2D graphics = (Graphics2D) g;
                if(isLoaded) {
                    graphics.setStroke(new BasicStroke(5));
                    for(Cross content : crossArrayList){
                        if(content.getType ().equals (Type.LIMITE))
                        {
                            graphics.setColor (Color.BLUE);
                        }
                        else
                        {
                            graphics.setColor (Color.BLACK);
                        }
                        graphics.draw(content.getHorizontalLine ());
                        graphics.draw(content.getVerticaline ());
                    }
                }
                graphics.dispose();
            }
        };
    }

    public void loadImage(String path) throws IOException, ImageReadException {
        imageLabel.addMouseListener (customMouseListener);
        image = ImageIO.read(new File (path));
        ogWidth = image.getWidth ();
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        if(path.contains ("tif"))
        {
            image = loadImageWithApacheImaging(path);
        }
        imageLabel.setIcon(new ImageIcon(image));
        isLoaded = true;
    }

    private static BufferedImage loadImageWithApacheImaging(String filePath) throws IOException, ImageReadException {
        File file = new File(filePath);
        ImageMetadata metadata = Imaging.getMetadata(file);
        if (metadata != null) {
            TiffImageParser tiffImageParser = new TiffImageParser ();
            BufferedImage image = tiffImageParser.getBufferedImage (file,new TiffImagingParameters ());
            return image;
        } else {
            throw new IOException("Unsupported image format or invalid TIFF file.");
        }
    }

    public void drawPoint (int x, int y, int index, Type type)
    {
        if(isLoaded) {
            Cross cross = new Cross (new Line2D.Float(x-10,y,x+10,y),new Line2D.Float(x,y-10,x,y+10));
            cross.setType (type);
            if(hasSister (cross))
            {
                menuAlert ("Une moelle par coupe.");
            }
            else {
                if(index != -1)
                {
                    crossArrayList.add (index,cross);
                }else
                {
                    crossArrayList.add (cross);
                }
                crossArrayList.sort (Comparator.comparing (p -> p.getCenter ().getX ()));
                imageLabel.repaint ();
            }
        }
        else
        {
            menuAlert ("Image not loaded");
        }
    }


    public void replaceCross(Cross cross, int x, int y)
    {
        if(isLoaded) {
            int index = crossArrayList.indexOf (cross);
            if (index != -1) {
                crossArrayList.remove (index);
                crossArrayList.add (index, new Cross (new Line2D.Float(x-10,y,x+10,y),new Line2D.Float(x,y-10,x,y+10)));
                crossArrayList.sort (Comparator.comparing (p -> p.getCenter ().getX ()));
                imageLabel.repaint();
            }
        }
    }

    public int clearPoint(int x,int y)
    {
        if(isLoaded){
            int index = crossArrayList.indexOf (CloseCrossFinder.returnClosestCross(crossArrayList,x,y));
            crossArrayList.remove (CloseCrossFinder.returnClosestCross(crossArrayList,x,y));
            crossArrayList.sort (Comparator.comparing (p -> p.getCenter ().getX ()));
            imageLabel.repaint ();
            return index;
        }
        else
        {
            menuAlert ("Image not loaded");
            return -1;
        }
    }

    private void menuAlert(String msg) {
        JOptionPane.showMessageDialog(menuView,
                msg,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public boolean isLoaded () {
        return isLoaded;
    }

    public  JLabel getImageLabel () {
        return imageLabel;
    }

    public ArrayList<Cross> getCrossArrayList () {
        return crossArrayList;
    }

    public void setYear (int year) {
        this.year = year;
    }

    public int getYear () {
        return year;
    }

    private boolean hasSister(Cross addedCross) {
        for (Cross cross : crossArrayList) {
            if (cross.getType().equals (addedCross.getType ()) && addedCross.getType ().equals (Type.MOELLE)) {
                return true;
            }
        }
        return false;
    }

    public void zoomIn()
    {
        try {
            if(image != null && zoomFactor <= 100)
            {
                zoomFactor += 5;
                updateImage();
                zoom ();
            }
            else{
                menuAlert ("Zoom impossible");
            }

        } catch (ArrayIndexOutOfBoundsException e)
        {
            menuAlert ("Zoom impossible");
        }
    }

    public void zoomOut() {
        if (image != null && zoomFactor >= 5) {
            zoomFactor -= 5;
            updateImage();
            zoom ();
        }else{
            menuAlert ("Zoom impossible");
        }

    }

    private void zoom() {
        if(isLoaded) {
            ArrayList<Cross> crosses = new ArrayList<> ();
            for (Cross point : crossArrayList) {
                System.out.println (point);
                int newX = (int) (point.getOriginalCenter ().getX () * zoomFactor / 100);
                int newY = (int) (point.getOriginalCenter ().getY () * zoomFactor / 100);
                crosses.add (new Cross (new Line2D.Float ((float)(newX - (10* zoomFactor / 100)), newY, (float) (newX + (10* zoomFactor / 100)), newY), new Line2D.Float (newX, (float)( newY - (10* zoomFactor / 100)), newX, (float)( newY + (10* zoomFactor / 100))), point.getOriginalCenter ()));
                System.out.println (crosses.get (0));
                crosses.sort (Comparator.comparing (p -> p.getCenter ().getX ()));
            }
            crossArrayList.clear ();
            crossArrayList.addAll (crosses);
            imageLabel.repaint ();
        }
    }


    private void updateImage() {
        try {
            System.out.println (zoomFactor);
            int W = (int) (imageWidth * zoomFactor/100);
            int H = (int) (imageHeight * zoomFactor/100);
            System.out.println (imageWidth + "||" + imageHeight + "||" + ogWidth);
            imageLabel.setIcon(new ImageIcon(image.getScaledInstance(W, H, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
