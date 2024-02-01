package main.java.com.exemple.View;

import main.java.com.exemple.Controller.MenuController;
import main.java.com.exemple.Tools.ImageManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Classe représentant la vue du Menu
 */
public class MenuView extends JFrame {

    private static JMenuBar menuBar;

    private static BufferedImage image;

    private static JLabel imageLabel = new JLabel();

    private static JMenu fileMenu = new JMenu("Fichier");
    private static JMenu editMenu = new JMenu("Edition");
    private static JMenu launchMenu = new JMenu();
    private static JMenu stopMenu = new JMenu();
    private static JMenu debugMenu = new JMenu();

    private  static JMenuItem openItem,recentItem,saveItem,saveAsItem,closeItem;

    private MenuController menuController;


    /**
     * Constructeur de MenuView en fonction de la taille et du niveau
     *
     * @param width
     * @param height
     * @param lvl
     */
    public MenuView(int width, int height, int lvl) {

        setIconImage(new ImageIcon(ImageManager.getInstance().getImage("MenuIcon")).getImage());
        menuController = new MenuController(this);
        addToPane(getContentPane());
        this.setJMenuBar(menuBar);
        pack();
        setSize(width, height);
    }

    public void startMenu() {
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("InitRC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();
    }

    /**
     * Procédure privée qui ajoute les boutons au panneau
     */
    private void addToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(Box.createVerticalStrut(150));

        pane.add(Box.createHorizontalStrut(250));


        menuBar = new JMenuBar();

        openItem = new JMenuItem("Ouvrir");
        recentItem = new JMenuItem("Ouvrir recent");
        saveAsItem = new JMenuItem("Enregistrer-sous");
        saveItem = new JMenuItem("Enregistrer");
        closeItem = new JMenuItem("Fermer");

        launchMenu.setIcon( new ImageIcon(ImageManager.getInstance().getImage("MenuPlay")));
        stopMenu.setIcon( new ImageIcon(ImageManager.getInstance().getImage("MenuStop")));
        debugMenu.setIcon( new ImageIcon(ImageManager.getInstance().getImage("MenuDebug")));

        addMenu(fileMenu,menuBar);
        addMenu(editMenu,menuBar);
        addMenu(launchMenu,menuBar);
        addMenu(stopMenu,menuBar);
        addMenu(debugMenu,menuBar);

        addMenuItems(fileMenu,openItem,recentItem,saveItem,saveAsItem,closeItem);
        add(imageLabel,BorderLayout.CENTER);
    }

    public void close()
    {
        System.exit(0);
    }

    private void addMenu(JMenu m, JMenuBar menuBar) {
        menuBar.add(m);
    }

    private void addMenuItems(JMenu menu , JMenuItem... items) {
        for (JMenuItem item:items
             ) {
            item.addActionListener(this.menuController);
            menu.add(item);
        }
    }

    public void menuAlert(String msg)
    {
        JOptionPane.showMessageDialog(this,
                msg,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public void loadImage(String path)
    {
        try {
            image = ImageIO.read(new File(path));
            System.out.println(path);
            imageLabel.setIcon(new ImageIcon(image.getScaledInstance(500, 500, Image.SCALE_DEFAULT)));
        } catch (IOException ex) {
            // handle exception...
        }
    }
    public static JMenuItem getOpenItem() {
        return openItem;
    }

    public static JMenuItem getRecentItem() {
        return recentItem;
    }

    public static JMenuItem getSaveItem() {
        return saveItem;
    }

    public static JMenuItem getSaveAsItem() {
        return saveAsItem;
    }

    public static JMenuItem getCloseItem() {
        return closeItem;
    }


}
