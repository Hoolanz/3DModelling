// This class recieves mouse messages.
// When the mouse is dragged, it rotates the current picture on the x and y axis
// based on the mouse drag
// When the mouse is clicked, it displays the next picture
package a3;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

public class MyMouseListener extends MouseInputAdapter
{
    Draw draw;  // set in constructor to the component the MouseListener serves
    // in mouseDragged event, this is used to keep track of where mouse was dragged from
    double lastx, lasty;  
    // xrot and yrot store the angles to rotate the picture by due to mouse drags
    double xrot, yrot;
    int state; // used to track which picture to show after next mouse click
    
    MyMouseListener(Draw draw)
    {
        this.draw = draw;
        lastx = 0;
        lasty = 0;
        xrot = 0;
        yrot = 0;
        state = 0;
    }
    
    //when mouse is dragged, update viewing angle
    @Override
    public void mouseDragged(MouseEvent e)
    {
        Transform t = new Transform();
        double xdif;
        double ydif;
        
        if(lastx == 0 || lasty == 0)
        {
            lastx = e.getX();
            lasty = e.getY();
            return;
        }

        xdif = lastx - e.getX();
        ydif = lasty - e.getY();
        lastx = e.getX();
        lasty = e.getY();
        
        if(xdif > 0)
            xrot -= 2;
        else if(xdif < 0)
            xrot += 2;
        
        if(ydif > 0)
            yrot -= 2;
        else if(ydif < 0)
            yrot += 2;
        
        if(draw.transformStack.peek() != draw.CAMERA)
            draw.transformStack.pop();
        
        t.defineElementaryTransform(Code.X_ROT, yrot);
        t.buildElementaryTransform(Code.Y_ROT, xrot);
        t.multiplyTransforms(t, draw.transformStack.peek());
        draw.transformStack.push(t);
        draw.repaint();
    }
    
    //when mouse is clicked, change to next picture
    @Override
    public void mouseClicked(MouseEvent e)
    {
        draw.drawList.remove(0);
        switch(state)
        {
            case 0:
                draw.drawList.add("hall");
                state++;
                break;
            case 1:
                draw.drawList.add("rubicscube");
                state++;
                break;
            case 2:
                draw.drawList.add("plot");
                state++;
                break;
            case 3:
                draw.drawList.add("alex");
                state = 0;
                break;
            default:
                break;
        }
        draw.repaint();
    }
}
