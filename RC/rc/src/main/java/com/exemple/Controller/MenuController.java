package main.java.com.exemple.Controller;

import main.java.com.exemple.View.MenuView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                menuView.loadImage(j.getSelectedFile().getAbsolutePath());
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
            menuView.drawPoint();
        }
    }
}