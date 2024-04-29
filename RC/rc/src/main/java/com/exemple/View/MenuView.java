package main.java.com.exemple.View;

import main.java.com.exemple.Controller.MenuController;
import main.java.com.exemple.Tools.Cross;
import main.java.com.exemple.Tools.ImageManager;
import org.apache.commons.imaging.ImageReadException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Classe représentant la vue du Menu
 */
public class MenuView extends JFrame {

    private static JMenuBar menuBar;

    private static JMenu fileMenu = new JMenu("Fichier");
    private static JMenu editMenu = new JMenu("Edition");

    private static JButton launchMenu = new JButton();
    private static JButton stopMenu = new JButton();
    private static JButton debugMenu = new JButton();
    private static JButton zoomInMenu = new JButton();
    private static JButton zoomOutMenu = new JButton();

    private static JMenuItem openItem, recentItem, saveItem, saveAsItem, closeItem, openEdit, closeEdit,year;

    private static JToolBar toolBar = new JToolBar();

    private static JButton draw,erase,text,move,addTab,removeTab;

    private static JTabbedPane tabbedPane;

    private static JScrollPane pane;

    private JPanel controlPanel = new JPanel();

    private MenuController menuController;

    private ArrayList<Cross> crossArrayList = new ArrayList<> ();

    private ArrayList<CutView> cutViews = new ArrayList<> ();

    private JRadioButton moelle = new JRadioButton ();
    private JRadioButton limite = new JRadioButton ();

    private ButtonGroup typePoint = new ButtonGroup ();

    private boolean isDrawing = false;
    private boolean isErasing = false;
    private boolean isMoving = false;

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
        tabbedPane = new JTabbedPane ();
        cutViews.add (new CutView (this));
        pane = new JScrollPane(cutViews.get (0).getImageLabel ());
        pane.setLayout(new ScrollPaneLayout());
        pane.setPreferredSize(this.getPreferredSize());
        addToPane();
        tabbedPane.add("Tab " + tabbedPane.getTabCount (),pane);
        this.add (tabbedPane,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        pack();
        setSize(width, height);
        startMenu();
        creatToolBar();
        createButton();

    }

    public void createButton() {
        addTab = new JButton ("Add Tab");
        removeTab = new JButton ("Remove Tab");
        addTab.addActionListener (menuController);
        removeTab.addActionListener (menuController);
        controlPanel.add (addTab);
        controlPanel.add (removeTab);
        this.add (controlPanel,BorderLayout.SOUTH);

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
    private void addToPane() {
        menuBar = new JMenuBar();

        openItem = new JMenuItem("Ouvrir");
        recentItem = new JMenuItem("Ouvrir recent");
        saveAsItem = new JMenuItem("Enregistrer-sous");
        saveItem = new JMenuItem("Enregistrer");
        closeItem = new JMenuItem("Fermer");

        openEdit = new JMenuItem("Editer");
        closeEdit = new JMenuItem("Fermer");
        year = new JMenuItem ("Saisir année");

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
        addMenuItems(editMenu, openEdit,year, closeEdit);
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

    public void drawPoint(int x, int y, int index, main.java.com.exemple.Tools.Type type)
    {
        cutViews.get (tabbedPane.getSelectedIndex ()).drawPoint (x,y,index,type);
    }


    public void replaceCross(Cross cross, int x, int y)
    {
        cutViews.get (tabbedPane.getSelectedIndex ()).replaceCross (cross,x,y);
    }

    public int clearPoint(int x,int y)
    {
       return cutViews.get (tabbedPane.getSelectedIndex ()).clearPoint (x,y);
    }

    public void setCutYear(String s)
    {
        if (s == null) {
            menuAlert ("Operaiton annulee");
        }
        int year;
        try {
            year = Integer.parseInt(s);
            cutViews.get (tabbedPane.getSelectedIndex ()).setYear (year);
        } catch (NumberFormatException e) {
            menuAlert("Annee invalide");
        }
    }

    public void loadImage(String path,String iName) throws IOException, ImageReadException {
            new Thread (()-> {
            try {
                Thread.sleep (1000);
                int tabIndex = tabbedPane.getSelectedIndex();
                cutViews.get (tabIndex).loadImage(path);
                tabbedPane.setTitleAt (tabIndex,iName);
            } catch (InterruptedException | IOException | ImageReadException e)
            {
                e.printStackTrace ();
            }
        }).start ();
    }

    public void addTab(){
        cutViews.add (new CutView (this));
        tabbedPane.addTab ("Tab " + tabbedPane.getTabCount (),new JScrollPane (cutViews.get (cutViews.size ()-1).getImageLabel()));
    }

    public void removeTab(){
        if(cutViews.size () > 1)
        {
         int tabIndex = tabbedPane.getSelectedIndex();
         tabbedPane.remove(tabIndex);
         cutViews.remove (tabIndex);
        }
        else{
            menuAlert ("Erreur dans la supression des onglets");
        }

    }


    private void initButtons(JToolBar jToolBar,JButton...buttons)
    {
        for (JButton b:buttons) {
            b.addActionListener(this.menuController);
            jToolBar.add(b);
        }
        typePoint.add (moelle);
        typePoint.add (limite);
        jToolBar.add (moelle);
        jToolBar.add (limite);

    }

    private void creatToolBar()
    {
        draw = new JButton("Dessiner");
        erase=new JButton("Effacer");
        text=new JButton("Ecrire");
        move = new JButton ("Deplacer");
        moelle.setText ("Pour la moelle");
        limite.setText ("Pour les limites");
        initButtons(toolBar,draw,erase,text,move);

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

    public void zoomIn() {
        int tabIndex = tabbedPane.getSelectedIndex();
        cutViews.get (tabIndex).zoomIn ();

    }

    public void zoomOut() {
        int tabIndex = tabbedPane.getSelectedIndex();
        cutViews.get (tabIndex).zoomOut ();
    }

    public int showSaveOption(String opt1,String opt2,String opt3)
    {
        Object[] options1 = { opt1,opt2,opt3 };
        int result = JOptionPane.showOptionDialog(this, null, "Choisir le type de traitement",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        return result;
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

    public static JMenuItem getYear () {
        return year;
    }

    public static JMenuItem getCloseEdit() {
        return closeEdit;
    }

    public boolean isLoaded() {
        return cutViews.get (tabbedPane.getSelectedIndex ()).isLoaded ();
    }

    public void setDrawing (boolean drawing) {
        isDrawing = drawing;
    }

    public void setErasing (boolean erasing) {
        isErasing = erasing;
    }

    public void setMoving (boolean moving) {
        isMoving = moving;
    }

    public static JButton getDraw () {
        return draw;
    }

    public static JButton getErase () {
        return erase;
    }

    public static JButton getLaunchMenu () {
        return launchMenu;
    }

    public boolean isDrawing () {
        return isDrawing;
    }

    public boolean isErasing () {
        return isErasing;
    }

    public boolean isMoving () {
        return isMoving;
    }

    public static JButton getText () {
        return text;
    }

    public static JButton getMove () {
        return move;
    }

    public static JButton getDebugMenu () {
        return debugMenu;
    }

    public static JButton getAddTab () {
        return addTab;
    }

    public static JButton getRemoveTab () {
        return removeTab;
    }

    public ArrayList<Cross> getCrossArrayList () {
        return cutViews.get (tabbedPane.getSelectedIndex ()).getCrossArrayList ();
    }

    public ArrayList<CutView> getCutViews () {
        return cutViews;
    }


    private void printArray()
    {
        for (Cross cross:crossArrayList) {
            System.out.println ("Point a l'index :" + crossArrayList.indexOf (cross) + "au coordonee X :"+ cross.getCenter().getX ()+" Y:" + cross.getCenter ().getY ());
        }
    }

    public JRadioButton getMoelle () {
        return moelle;
    }

    public JRadioButton getLimite () {
        return limite;
    }
}
