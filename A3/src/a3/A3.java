package a3;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class A3 {
    public static void main(String[] args) 
    {
        JFrame f = new JFrame(); // frame window
        Draw draw = new Draw(); // create draw object

        draw.InitGraphics();    // inititialize camera
        draw.drawList.add("alex");  // add name of thing to draw to drawlist
         
        // close frame window and exit program when user presses exit button
        f.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {  System.exit(0); }}
        );
        
        //set up a mouse listener for changing view and switching between pictures
        MyMouseListener mouse = new MyMouseListener(draw);
        f.addMouseMotionListener(mouse);
        f.addMouseListener(mouse);
        
        // set up frame to display draw
        f.getContentPane().add( draw );
        f.setSize(draw.frameWidth, draw.frameHeight);
        f.setVisible( true );
    }
}
