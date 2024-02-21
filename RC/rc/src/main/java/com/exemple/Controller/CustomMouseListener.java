package main.java.com.exemple.Controller;

import main.java.com.exemple.View.MenuView;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class CustomMouseListener implements MouseInputListener {

    private MenuView menuView;

    public CustomMouseListener (MenuView menuView) {
        this.menuView = menuView;
    }

    @Override
    public void mouseClicked (MouseEvent e) {
        System.out.println ("X :"+ e.getX ()+" Y:" + e.getY ());
        if(menuView.isDrawing ())
        {
            menuView.drawPoint (e.getX (),e.getY ());
        }
        else if(menuView.isErasing()){
            menuView.clearPoint(e.getX (),e.getY ());
        }
    }

    @Override
    public void mousePressed (MouseEvent e) {

    }

    @Override
    public void mouseReleased (MouseEvent e) {

    }

    @Override
    public void mouseEntered (MouseEvent e) {

    }

    @Override
    public void mouseExited (MouseEvent e) {

    }

    @Override
    public void mouseDragged (MouseEvent e) {

    }

    @Override
    public void mouseMoved (MouseEvent e) {

    }
}
