package main.java.com.exemple.Controller;

import main.java.com.exemple.View.MenuView;
import org.apache.commons.imaging.ImageReadException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe permettant de controller le menu et de gérer les événements
 */
public class MenuController implements ActionListener {
    /**
     * La vue du Menu
     */
    private MenuView menuView;

    /**
     * Constructeur de MenuController avec la vue du Menu et le numero de niveau
     * @param menuView la vue du menu
     */
    public MenuController(MenuView menuView) {
        this.menuView = menuView;
    }

    /**
     * Procédure venant de ActionListener, permet de capter les événements du menu
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == menuView.getOpenItem())
        {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION)
            {
                try {
                    menuView.loadImage(j.getSelectedFile().getAbsolutePath());
                } catch (IOException | ImageReadException e) {
                    e.printStackTrace ();
                }
            }
            else
                menuView.menuAlert("Operation annulee");
        }
        else if(source == MenuView.getRecentItem())
        {
        }
        else if(source == MenuView.getSaveItem())
        {
        }
        else if(source == MenuView.getSaveAsItem())
        {
        }
        else if(source == MenuView.getCloseItem())
        {
            menuView.close();
        }else if(source == MenuView.getZoomOutMenu())
        {
            menuView.zoomOut();
        }
        else if(source == MenuView.getZoomInMenu())
        {
            menuView.zoomIn();
        }
        else if(source == MenuView.getOpenEdit())
        {
            menuView.showEditMenu();
        }
        else if(source == MenuView.getCloseEdit())
        {
            menuView.hideMenu();
        }
        else if(source == MenuView.getDraw())
        {
            menuView.setErasing(false);
            menuView.setDrawing (true);
        }
        else if(source == MenuView.getErase ())
        {
            menuView.setDrawing (false);
            menuView.setErasing(true);
        }
        else if(source == MenuView.getText ())
        {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION)
            {
                try {
                    ArrayList<Point> points = extractPointsFromPosFile(j.getSelectedFile().getAbsolutePath());
                    for (Point point : points) {
                        if(menuView.isLoaded ())
                        {
                            menuView.drawPoint (point.x,point.y);
                        }
                        System.out.println("X: " + point.x + ", Y: " + point.y);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static ArrayList<Point> extractPointsFromPosFile(String filePath) throws IOException {
        ArrayList<Point> points = new ArrayList<> ();
        String regexPattern = "([-+]?\\d*\\.\\d+|\\d+),([-+]?\\d*\\.\\d+|\\d+)";
        Pattern pattern = Pattern.compile(regexPattern);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 0; i < 10; i++) {
                reader.readLine();
            }

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    double x = Double.parseDouble(matcher.group(1));
                    double y = Double.parseDouble(matcher.group(2));
                    points.add(new Point((int)(x*(2400/25.4)), (int) (y*((2400/25.4)))));
                }
            }
        }

        return points;
    }
}