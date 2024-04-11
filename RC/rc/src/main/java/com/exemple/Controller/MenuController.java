package main.java.com.exemple.Controller;

import main.java.com.exemple.Tools.*;
import main.java.com.exemple.View.GraphView;
import main.java.com.exemple.View.MenuView;
import org.apache.commons.imaging.ImageReadException;
import org.jfree.ui.RefineryUtilities;

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

    private String nameOfImage;

    private ArrayList<Point> points = new ArrayList<> ();

    private boolean isPointLoaded = false;


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
                    nameOfImage =  j.getSelectedFile().getName ();
                    menuView.loadImage(j.getSelectedFile().getAbsolutePath(),nameOfImage);
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
            if(isPointLoaded)
            {
                if(menuView.getCrossArrayList().size () > 0)
                {
                    for (Cross c:menuView.getCrossArrayList()) {
                        if(!points.contains (c.getCenter ()))
                        points.add (c.getCenter ());
                    }
                }

                int result = menuView.showSaveOption("Matrix", "DataText", "Quit");
                if(result == 0)
                {
                    DistanceMatrixWriter.writeDistanceMatrixToFile(points,"distance_matrix_"+nameOfImage+".txt");
                }else if(result == 1)
                {
                    DistanceCalculator.writeDistancesToFile(points,"distance_"+nameOfImage+".txt");
                }
            }
            else{
                menuView.menuAlert ("Points not loaded");
            }
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
        else if(source == MenuView.getYear ())
        {
            if(menuView.isLoaded ())
            {
                String yearInput = JOptionPane.showInputDialog(null, "Entrer une annee:");
                menuView.setCutYear(yearInput);
            }
            else{
                menuView.menuAlert ("Pas d'image");
            }

        }
        else if(source == MenuView.getCloseEdit())
        {
            menuView.hideMenu();
        }
        else if(source == MenuView.getDraw())
        {
            isPointLoaded = true;
            menuView.setErasing(false);
            menuView.setMoving (false);
            menuView.setDrawing (true);
        }
        else if(source == MenuView.getErase ())
        {
            isPointLoaded = true;
            menuView.setDrawing (false);
            menuView.setMoving (false);
            menuView.setErasing(true);
        }
        else if(source == MenuView.getMove ())
        {
            isPointLoaded = true;
            menuView.setMoving (true);
            menuView.setDrawing (false);
            menuView.setErasing (false);
        }
        else if(source == MenuView.getText())
        {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION)
            {
                try {
                    points = extractPointsFromPosFile(j.getSelectedFile().getAbsolutePath());
                    isPointLoaded = true;
                    for (Point point : points) {
                        if(menuView.isLoaded ())
                        {
                            menuView.drawPoint (point.x,point.y,-1, Type.LIMITE);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(source == MenuView.getLaunchMenu ())
        {
            int result = menuView.showSaveOption ("Apprentissage","Sans Apprentissage","Quitter");
            if(result == 0)
            {
                int res = menuView.showSaveOption ("Inference","Entrainement","Quitter");
                if(res == 0)
                {
                    new Thread(() -> {
                        try {
                            KitC.runPythonScriptInference ("C:\\Users\\Utilisateur\\Documents\\InitR\\APP\\M1-InitRC\\approche_avec_apprentissage\\inference\\inference.py");
                        } catch (IOException e) {
                            e.printStackTrace ();
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }
                    }).start();
                }else if (res == 1)
                {
                    String input1 = JOptionPane.showInputDialog(null, "nommodele le nom que vous souhaité donner au modèle, une chaine de caractères:");
                    String input2 = JOptionPane.showInputDialog(null, "wloss un entier (recommandé 3):");
                    String input3 = JOptionPane.showInputDialog(null, "batch_size un entier (dépend de la mémoire de votre gpu):");
                    String input4 = JOptionPane.showInputDialog(null, "imgSize:");
                    String input5 = JOptionPane.showInputDialog(null, "nbE un entier (recommandé 40):");
                    new Thread(() -> {
                        try {
                            KitC.runPythonScriptLearning (input1,input2,input3,input4,input5);
                        } catch (IOException e) {
                            e.printStackTrace ();
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }
                    }).start();
                }
            }else if(result == 1)
            {
                KitC.cCompiler ();
            }
        }
        else if(source == MenuView.getDebugMenu ())
        {
            if(menuView.getCrossArrayList().size () > 0)
            {
                for (Cross c:menuView.getCrossArrayList()) {
                    if(!points.contains (c.getCenter ()))
                        points.add (c.getCenter ());
                }
            }

            SwingUtilities.invokeLater(() -> {
                GraphView chart = new GraphView(menuView.getCutViews (),
                        "Chart " ,
                        "Graphique d'acroissement");
                chart.pack( );
                RefineryUtilities.centerFrameOnScreen( chart );
                chart.setVisible( true );
                chart.setLocationRelativeTo (null);
            });
        }
        else if(source == MenuView.getAddTab ())
        {
            menuView.addTab ();
        }else if(source == MenuView.getRemoveTab ()) {
            menuView.removeTab();
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