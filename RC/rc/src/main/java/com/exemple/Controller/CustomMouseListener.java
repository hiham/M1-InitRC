package main.java.com.exemple.Controller;

import main.java.com.exemple.Tools.CloseCrossFinder;
import main.java.com.exemple.Tools.Cross;
import main.java.com.exemple.Tools.Type;
import main.java.com.exemple.View.MenuView;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CustomMouseListener implements MouseInputListener {

    private MenuView menuView;
    private Point point;
    private Cross cross;
    private int index;


    public CustomMouseListener (MenuView menuView) {
        this.menuView = menuView;
    }

    @Override
    public void mouseClicked (MouseEvent e) {
        if(menuView.isDrawing ())
        {
            System.out.println ("X :"+ e.getX ()+" Y:" + e.getY ());
            if(menuView.getLimite ().isSelected ())
            {
                menuView.drawPoint (e.getX (),e.getY (),index, Type.LIMITE);
            }else if(menuView.getMoelle ().isSelected ())
            {
                menuView.drawPoint (e.getX (),e.getY (),index,Type.MOELLE);
            }

        }
        else if(menuView.isErasing()){
            System.out.println ("X :"+ e.getX ()+" Y:" + e.getY ());
            index  = menuView.clearPoint(e.getX (),e.getY ());
        }
    }


    @Override
    public void mousePressed (MouseEvent e) {
        if(menuView.getCrossArrayList ().size () > 0 && menuView.isMoving ())
        {
            System.out.println ("X pressed:"+ e.getX ()+" Y pressed:" + e.getY ());
            cross = CloseCrossFinder.returnClosestCross (menuView.getCrossArrayList (),e.getX (),e.getY ());
            point =  new Point(cross.getCenter().x,cross.getCenter ().y);
        }

    }

    @Override
    public void mouseReleased (MouseEvent e) {
        if(menuView.getCrossArrayList ().size () > 0 && menuView.isMoving ()) {
            System.out.println ("X supp:"+ e.getX ()+" Y supp:" + e.getY ());
            menuView.replaceCross(cross,e.getX (),e.getY ());
        }
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
