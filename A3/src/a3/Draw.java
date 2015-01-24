// This is the component that does all drawing to the frame
package a3;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;
import java.util.Stack;

public class Draw extends JComponent{
    // set size of frame window
    static int frameWidth  = 650;
    static int frameHeight = 650;
    
    // point that is set in MoveTo2D, and that DrawTo2D draws from
    Point3d startPoint; // point that is set in MoveTo2D
    Window w; // window
    Window viewport; // viewport
    Transform CAMERA;
    Stack<Transform> transformStack;
    ArrayList<String> drawList;     //list of models to draw
    ObjectTable objectTable;        //table to hold models
    
    Draw()
    {
        startPoint = new Point3d(0,0,0); // initialize 
        w = new Window(-8.0, 8.0, -8.0, 8.0); //initialize
        viewport = new Window(0, 1.0, 0, 1.0); //initialize
        CAMERA = new Transform();  
        objectTable = new ObjectTable();
        transformStack = new Stack();
        drawList = new ArrayList();
    }
    
    void addHeader(Graphics g)
    {
        g.drawString("Alex Eklund, CS 324", 10, 20);
    }
    
    // draw everything in drawList
    @Override
    public void paintComponent( Graphics g )
    {
        frameWidth = getWidth();
        frameHeight = getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);
        //g.setColor(Color.W);
       
        addHeader(g);
        
        for(String s : drawList)
            drawName(g, s);
    }
    
    //get assembly or object from objectTable and call drawThing on it
    void drawName(Graphics g, String name)
    {
        //get node with matching name from objectTable
        Node node = objectTable.getNodeByName(name);
        if(node != null)
            drawNode(g, node, false);
        else
            System.out.println("Object not found");
    }
    
    // Recurse through digraph assemblies/ojects to draw models
    // Add Transforms to stack and then pop them before and after calling drawNode
    // for children
    // If color != Black, Set color before calling drawNode for children. 
    // Lock color when you set it, so the 
    // highest level Node's color is used for everything under it.
    // After calling drawNode of children, set color to black again.
    void drawNode(Graphics g, Node node, Boolean colorlock)
    {
        Transform t;
        AssemblyNode anode;
        if(node.isObject)
        {
            drawObject(g, (ObjectNode)node, colorlock);
        }
        else
        {
            t = new Transform();
            anode = (AssemblyNode)node;
            t.multiplyTransforms( anode.transform, transformStack.peek() );
            transformStack.push(t);
 
            if(anode.strings != null) drawStrings(g, anode.strings);
            if(anode.color != Color.BLACK && !colorlock)
            {
                g.setColor(anode.color);
                for(Node n : anode.list)
                    drawNode(g, n, true);
            }
            else
                for(Node n : anode.list)
                    drawNode(g, n, colorlock);
            
            if(!colorlock)
                g.setColor(Color.BLACK);
            
            transformStack.pop();
        }
    }
    
    //draws lines between object points
    void drawObject(Graphics g, ObjectNode obj, Boolean col)
    { 
        if(!col)
            g.setColor(Color.white);
        if(obj.isPolygon)
        {
            for(int i = 0; i < obj.points.length; i++)
            {
                MoveTo3D(   obj.points[i].x, obj.points[i].y, obj.points[i].z);
                DrawTo3D(g, obj.points[(i+1)%obj.points.length].x, obj.points[(i+1)%obj.points.length].y, obj.points[(i+1)%obj.points.length].z);
            }
        }
        else
        {
            for(int i = 1; i < obj.points.length; i++)
            {
                MoveTo3D(   obj.points[i-1].x, obj.points[i-1].y, obj.points[i-1].z);
                DrawTo3D(g, obj.points[i].x, obj.points[i].y, obj.points[i].z);
            }
        }
        if(!col)
            g.setColor(Color.black);
    }
    
    void drawStrings(Graphics g, ArrayList<StringWCoords> strings)
    {   
        g.setColor(Color.WHITE);
        Point2dInt p2d = new Point2dInt();
        for(StringWCoords s : strings)
        {
            MoveTo3D(s.p.x, s.p.y, s.p.z);
            getFrameWindowPoint(startPoint, p2d);
            g.drawString(s.str, p2d.x, p2d.y);
        }
        g.setColor(Color.BLACK);
        
        
        
        
        
        
        
        
        
    }
    
    // Set up the camera transform
    void defineCameraTransform( double fx, double fy, double fz,
        double theta, double phi, double alpha, double r )
    {
        CAMERA.defineElementaryTransform( Code.X_TRANS, -fx);
        CAMERA.buildElementaryTransform(  Code.Y_TRANS, -fy);
        CAMERA.buildElementaryTransform(  Code.Z_TRANS, -fz);
        CAMERA.buildElementaryTransform( Code.Y_ROT, -theta);
        CAMERA.buildElementaryTransform( Code.X_ROT, phi);
        CAMERA.buildElementaryTransform( Code.Z_ROT, -alpha);
        CAMERA.buildElementaryTransform( Code.PERSPECTIVE, r);
    }
    
    // change drawing startPoint
    public void MoveTo3D(double x, double y, double z)
    {
        Point3d moveto = new Point3d(x, y, z);
        applyTransform(moveto, transformStack.peek());
        MoveTo2D(moveto.x, moveto.y);
    }    
    
    // draw from startPoint to (x, y, z)
    void DrawTo3D(Graphics g, double x, double y, double z)
    {
        Point3d drawto = new Point3d(x,y,z);
        applyTransform(drawto, transformStack.peek());
        DrawTo2D(g, drawto.x, drawto.y);
    }
    
    // apply transform at top of transformStack to p
    void applyTransform(Point3d p, Transform aT)
    {
        Vector4 v = new Vector4();
        v.pointToVector(p);
        v.multiply(aT);
        p.x = v.v[0]/v.v[3];
        p.y = v.v[1]/v.v[3];
        p.z = v.v[2]/v.v[3];
    }

    // change drawing startPoint
    public void MoveTo2D(double x, double y)
    {
        startPoint.x = x;
        startPoint.y = y;
    }
    
    // Draw a line from startPoint to (x,y)
    // currently must be called from Paint component of function with access
    // to pointCOmponent's graphics context
    public void DrawTo2D(Graphics g, double x, double y)
    {
        Point2dInt drawTo = new Point2dInt();  
        Point2dInt drawFrom = new Point2dInt();
        Point2d drawTod = new Point2d();
        Point2d drawFromd = new Point2d();
        WindowToViewport(startPoint.x, startPoint.y, drawFromd);
        WindowToViewport(x, y, drawTod);
        ViewportToFrameWindow(drawFromd.x,drawFromd.y,drawFrom);
        ViewportToFrameWindow(drawTod.x,drawTod.y,drawTo);
        g.drawLine(drawFrom.x, drawFrom.y, drawTo.x, drawTo.y);
    }
    
    void getFrameWindowPoint(Point3d p3d, Point2dInt p)
    {
        Point2d pD = new Point2d();
        WindowToViewport(p3d.x, p3d.y, pD);
        ViewportToFrameWindow(pD.x, pD.y, p);
    }

    // most initialization is done in A3.main or the constructor. 
    // here we initialize the camera and put it on the transform stack
    public void InitGraphics()
    {
        defineCameraTransform(1.0,0.0,1.0,20,30, 0, 20);
        transformStack.push(CAMERA);
    }

    // Function to transform (x, y) to viewport values
    public void WindowToViewport(double x, double y, Point2d vp)
    {
        vp.x = viewport.xmin + (x - w.xmin)*(viewport.xmax - viewport.xmin)/(w.xmax - w.xmin);
        vp.y = viewport.ymin + (y - w.ymin)*(viewport.ymax - viewport.ymin)/(w.ymax - w.ymin);
    }
    
    //function to transform (x,y) to to Frame window values
    public void ViewportToFrameWindow(double x, double y, Point2dInt fwp)
    {
        fwp.x = (int)(((x-viewport.xmin)*frameWidth)/(viewport.xmax - viewport.xmin));
        fwp.y = (int)(frameHeight- (((y-viewport.ymin)*frameHeight)/(viewport.ymax - viewport.ymin)));
    }
}