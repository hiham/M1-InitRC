package main.java.com.exemple.View;

import main.java.com.exemple.Controller.MenuController;
import main.java.com.exemple.Tools.ImageManager;
import main.java.com.exemple.Tools.RGBImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

    private static JButton launchMenu = new JButton();
    private static JButton stopMenu = new JButton();
    private static JButton debugMenu = new JButton();
    private static JButton zoomInMenu = new JButton();
    private static JButton zoomOutMenu = new JButton();

    private static JMenuItem openItem, recentItem, saveItem, saveAsItem, closeItem, openEdit, closeEdit;

    private static JToolBar toolBar = new JToolBar();

    private static JButton draw,erase,text,color,select;

    private static JScrollPane pane;

    private static JPanel p;

    private MenuController menuController;

    private int imageWidth;
    private int imageHeight;

    private boolean isLoaded = false;

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
        pane = new JScrollPane(imageLabel);
        pane.setLayout(new ScrollPaneLayout());
        pane.setPreferredSize(this.getPreferredSize());
        addToPane(pane);
        this.add(pane);
        this.setJMenuBar(menuBar);
        pack();
        setSize(width, height);
        startMenu();
        creatToolBar();
    }

    public void startMenu() {
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        menuBar = new JMenuBar();

        openItem = new JMenuItem("Ouvrir");
        recentItem = new JMenuItem("Ouvrir recent");
        saveAsItem = new JMenuItem("Enregistrer-sous");
        saveItem = new JMenuItem("Enregistrer");
        closeItem = new JMenuItem("Fermer");

        openEdit = new JMenuItem("Editer");
        closeEdit = new JMenuItem("Fermer");

        launchMenu.setIcon(new ImageIcon(ImageManager.getInstance().getImage("MenuPlay")));
        stopMenu.setIcon(new ImageIcon(ImageManager.getInstance().getImage("MenuStop")));
        debugMenu.setIcon(new ImageIcon(ImageManager.getInstance().getImage("MenuDebug")));

        zoomInMenu.setIcon(new ImageIcon(ImageManager.getInstance().getImage("ZoomIn")));
        zoomOutMenu.setIcon(new ImageIcon(ImageManager.getInstance().getImage("ZoomOut")));

        addMenu(fileMenu, menuBar);
        addMenu(editMenu, menuBar);
        addMenuB(launchMenu, menuBar);
        addMenuB(stopMenu, menuBar);
        addMenuB(debugMenu, menuBar);
        addMenuB(zoomInMenu, menuBar);
        addMenuB(zoomOutMenu, menuBar);

        addMenuItems(fileMenu, openItem, recentItem, saveItem, saveAsItem, closeItem);
        addMenuItems(editMenu, openEdit, closeEdit);
    }

    public void close() {
        System.exit(0);
    }

    private void addMenu(JMenu m, JMenuBar menuBar) {
        menuBar.add(m);
    }

    private void addMenuB(JButton b, JMenuBar menuBar) {
        b.addActionListener(this.menuController);
        menuBar.add(b);
    }

    private void addMenuItems(JMenu menu, JMenuItem... items) {
        for (JMenuItem item : items
        ) {
            item.addActionListener(this.menuController);
            menu.add(item);
        }
    }

    public void menuAlert(String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public void drawPoint()
    {
        if(isLoaded) {
            p = new JPanel () {
                Point pointStart = null;
                Point pointEnd = null;

                {
                    System.out.println ("yes");
                    addMouseListener (new MouseAdapter () {
                        public void mousePressed (MouseEvent e) {
                            pointStart = e.getPoint ();
                            System.out.println (pointStart);
                        }

                        public void mouseReleased (MouseEvent e) {
                            pointStart = null;
                        }
                    });
                    addMouseMotionListener (new MouseMotionAdapter () {
                        public void mouseMoved (MouseEvent e) {
                            pointEnd = e.getPoint ();
                        }

                        public void mouseDragged (MouseEvent e) {
                            pointEnd = e.getPoint ();
                            repaint ();
                        }
                    });
                }

                public void paint (Graphics g) {
                    super.paint (g);
                    if (pointStart != null) {
                        g.setColor (Color.RED);
                        g.drawLine (pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
                    }
                }
            };
            pane.getViewport ().add (p, null);
        }
        else
        {
            menuAlert ("Image not loaded");
        }
    }
    public void loadImage(String path) {
        try {
            RGBImageLoader rgbImageLoader = new RGBImageLoader();
            image = ImageIO.read(new File(path));
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            image = rgbImageLoader.loadImage(new File(path), imageWidth, imageHeight, 100);
            System.out.println(path);
            imageLabel.setIcon(new ImageIcon(image));
            isLoaded = true;
        } catch (IOException ex) {
            // handle exception...
        }
    }

    public void zoomOut() {
        if (image != null) {
            imageWidth = (int) (imageWidth * 0.9);
            imageHeight = (int) (imageHeight * 0.9);
            imageLabel.setIcon(new ImageIcon(image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_FAST)));
        }
    }

    private void initButtons(JToolBar jToolBar,JButton...buttons)
    {
        for (JButton b:buttons) {
            b.addActionListener(this.menuController);
            jToolBar.add(b);
        }
    }

    private void creatToolBar()
    {
        draw = new JButton("Dessiner");
        erase=new JButton("Effacer");
        text=new JButton("Ecrire");
        color=new JButton("Coleur");
        select=new JButton("Select");
        initButtons(toolBar,draw,erase,text,color,select);

    }
    public void showEditMenu()
    {
        toolBar.setVisible(true);
        add(toolBar,BorderLayout.PAGE_START);
    }

    public void hideMenu()
    {
        toolBar.setVisible(false);
    }
    public void zoomIn()
    {
        if(image != null)
        {
            imageWidth = (int) (imageWidth * 0.1) + imageWidth;
            imageHeight =(int) (imageHeight * 0.1) + imageHeight ;
            imageLabel.setIcon(new ImageIcon(image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_SMOOTH)));
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

    public static JButton getZoomOutMenu() {
        return zoomOutMenu;
    }

    public static JButton getZoomInMenu() {
        return zoomInMenu;
    }

    public static JMenuItem getOpenEdit() {
        return openEdit;
    }

    public static JMenuItem getCloseEdit() {
        return closeEdit;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public static JButton getDraw() {
        return draw;
    }
}
