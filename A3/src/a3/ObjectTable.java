//Object table contains an ArrayList table of Nodes that can be drawn or used
//to as a building block in a new assembly
//The add___ methods add an object or assembly to the table
package a3;
import java.awt.Color;
import java.util.ArrayList;
public class ObjectTable
{
    ArrayList<Node> table;
    
    void addHall()
    {
        final double hallWidth = 144;
        final double hallHeight = 138;
        final double hallLength = 724;
        final double alcHeight = 103;
        final double alcWidth = 84;
        final double alcDepth = 121;
        final double doorheight = 84;
        final double doorwidth = 36;
        
        addWindowDoor();
        
        AssemblyNode hall = new AssemblyNode("hall", Code.IDENTITY,0);
        hall.transform.buildElementaryTransform(Code.SCALE, .04);
        hall.transform.buildElementaryTransform(Code.Z_TRANS, 4);
        hall.transform.buildElementaryTransform(Code.X_TRANS, 5);
        hall.list.add(make3dRectSkipEdge(0,0,0, hallWidth, hallHeight, hallLength, 0));
        hall.list.add(getLineObject(-hallWidth, -hallHeight,-151, -hallWidth, -hallHeight, -0 ));
        hall.list.add(getLineObject(-hallWidth, -hallHeight,-235, -hallWidth, -hallHeight, -hallLength ));
        
        //doors on right
        hall.list.add(getWindowDoor(0,-hallHeight+doorheight,-18, 0));
        hall.list.add(getWindowDoor(0,-hallHeight+doorheight,-215, 0));
        hall.list.add(getWindowDoor(0,-hallHeight+doorheight,-332, 0));
        hall.list.add(getWindowDoor(0,-hallHeight+doorheight,-531, 0));
        hall.list.add(getWindowDoor(0,-hallHeight+doorheight,-607, 0));
        //hall.list.add(getWindowDoor());
        
        //doors on left
        hall.list.add(getWindowDoor(-hallWidth,-hallHeight+doorheight,-472, 0));
        hall.list.add(make3dRect(-hallWidth,-hallHeight+doorheight,-652, 0, doorheight, doorwidth-1));
        hall.list.add(make3dRect(-hallWidth,-hallHeight+doorheight,-689, 0, doorheight, doorwidth-1));

        //alcove
        hall.list.add(make3dRectSkipEdge(-hallWidth,-hallHeight+alcHeight,-151, alcDepth, alcHeight, alcWidth, 1));
        hall.list.add(make3dRect(-hallWidth,-hallHeight+alcHeight,-184, 18, alcHeight, 18));
        hall.list.add(getWindowDoor(151, -hallHeight+doorheight, -hallWidth-17, 90));
        hall.list.add(getWindowDoor(235, -hallHeight+doorheight, -hallWidth-17, 90));
        hall.list.add(getWindowDoor(-hallWidth - alcDepth,-hallHeight+doorheight, -151, 0));
        hall.list.add(getWindowDoor(-hallWidth - alcDepth,-hallHeight+doorheight, -151-48, 0));

        //main doors
        hall.list.add(make3dRect(-36, -hallHeight+doorheight, 0, doorwidth-1, doorheight, 0));
        hall.list.add(make3dRect(-72, -hallHeight+doorheight, 0, doorwidth-1, doorheight, 0));
      
        //window and door
        hall.list.add(make3dRect(-9, -hallHeight+doorheight, -hallLength, doorwidth, doorheight, 0));
        hall.list.add(make3dRect(-hallWidth +72+19, -hallHeight+40+44, -hallLength, 72, 44, 0));
        table.add(hall);
    }
    
    AssemblyNode getWindowDoor(double startx, double starty, double startz, int rotval)
    {
        AssemblyNode n = new AssemblyNode("", Code.IDENTITY,0);
        n.transform.buildElementaryTransform(Code.X_TRANS, startx);
        n.transform.buildElementaryTransform(Code.Y_TRANS, starty);
        n.transform.buildElementaryTransform(Code.Z_TRANS, startz);
        n.transform.buildElementaryTransform(Code.Y_ROT,rotval );
        n.list.add(getNodeByName("windowdoor"));
        return n;
    }
    
    void addWindowDoor()
    {
        final double doorheight = 84;
        final double doorwidth = 36;
        final double windowheight = 36;
        final double windowwidth = 24;
        AssemblyNode n = make3dRect(0, 0, 0, 0, doorheight, doorwidth);
        n.name = "windowdoor";
        n.list.add( make3dRect(0, -6, -6, 0, windowheight, windowwidth));
        table.add(n);
    }
    
    ObjectNode getLineObject(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        ObjectNode obj;
        Point3d points[] = new Point3d[2];
        points[0] = new Point3d(x1, y1, z1);
        points[1] = new Point3d(x2, y2, z2);
        obj = new ObjectNode("", points, false);
        return obj;
    }
    
    AssemblyNode make3dRect(double xmax, double ymax, double zmax, double width, double height, double raised)
    {
        AssemblyNode rect = new AssemblyNode("", Code.IDENTITY, 0);
        ObjectNode obj = makeRect(xmax, ymax, zmax, width, height);
        rect.list.add(obj);
        AssemblyNode r2 = new AssemblyNode("", Code.Z_TRANS, -raised);
        r2.list.add(obj);
        rect.list.add(r2);

        rect.list.add(getLineObject(xmax-width, ymax-height, zmax, xmax-width, ymax-height, zmax-raised  ));
        rect.list.add(getLineObject( xmax, ymax-height, zmax, xmax, ymax-height, zmax-raised ));
        rect.list.add(getLineObject( xmax, ymax, zmax, xmax, ymax, zmax-raised ));
        rect.list.add(getLineObject(xmax-width, ymax, zmax, xmax-width, ymax, zmax-raised ));

        return rect;
    }
        
    AssemblyNode make3dRectSkipEdge(double xmax, double ymax, double zmax, double width, double height, double raised, int skip)
    {
        AssemblyNode rect = new AssemblyNode("", Code.IDENTITY, 0);
        ObjectNode obj = makeRect(xmax, ymax, zmax, width, height);
        rect.list.add(obj);
        AssemblyNode r2 = new AssemblyNode("", Code.Z_TRANS, -raised);
        r2.list.add(obj);
        rect.list.add(r2);

        if(skip != 0) rect.list.add(getLineObject(xmax-width, ymax-height, zmax, xmax-width, ymax-height, zmax-raised  ));
        if(skip != 1) rect.list.add(getLineObject( xmax, ymax-height, zmax, xmax, ymax-height, zmax-raised ));
        if(skip != 2) rect.list.add(getLineObject( xmax, ymax, zmax, xmax, ymax, zmax-raised ));
        if(skip != 3) rect.list.add(getLineObject(xmax-width, ymax, zmax, xmax-width, ymax, zmax-raised ));

        return rect;
    }
    
    ObjectNode makeRect(double xmax, double ymax, double zmax, double width, double height)
    {
        Point3d points[] = new Point3d[5];
        points[0] = new Point3d(xmax, ymax, zmax);
        points[1] = new Point3d(xmax, ymax-height, zmax);
        points[2] = new Point3d(xmax-width, ymax-height, zmax);
        points[3] = new Point3d(xmax-width, ymax, zmax);
        points[4] = new Point3d(xmax, ymax, zmax);
        return new ObjectNode("", points, false);
    }
    
    void addPlot()
    {
        ObjectNode obj;
        int num = 16;
        double interval = 2.5/num;
        AssemblyNode plot = new AssemblyNode("plot", Code.X_ROT, 270);
        plot.transform.buildElementaryTransform(Code.Y_SCALE, .06);
        plot.transform.buildElementaryTransform(Code.Z_ROT, -5);
        plot.transform.buildElementaryTransform(Code.Y_ROT, -20);
        plot.transform.buildElementaryTransform(Code.SCALE, 3.0);
        Point3d points1[][] = new Point3d[num][num];
        Point3d points2[][] = new Point3d[num][num];
        double x,y,zmax,zmin;
        zmin = zmax = 0;
        for(int i = 0 ; i < num; i++)
        {
            for(int j = 0; j < num; j++)
            {
                x = -1.25 + interval * i;
                y = -1.25 + interval * j;
                points1[j][i] = new Point3d(x, y, 0 );
                points1[j][i].z = x*x + y*y - x*x*x - 8*x*y*y*y*y;
                if (points1[j][i].z > zmax) zmax = points1[j][i].z;
                if (points1[j][i].z < zmin) zmin = points1[j][i].z;
            }
        }
        
        plot.list.add( make3dRect(1.25, 1.25, zmax, 2.5, 2.5, zmax-zmin));
        for(int i = 0 ; i < num; i++)
        {
            obj = new ObjectNode("", points1[i], false);
            plot.list.add(obj);
        }

        for(int i = 0 ; i < num; i++)
        {
            for(int j = 0; j < num; j++)
            {
                x = -1.25 + interval * i;
                y = -1.25 + interval * j;
                points2[i][j] = new Point3d(x, y, 0 );
                points2[i][j].z = x*x + y*y - x*x*x - 8*x*y*y*y*y;
            }
        }
        for(int i = 0 ; i < num; i++)
        {
            obj = new ObjectNode("", points2[i], false);
            plot.list.add(obj);
        }
        
        plot.strings = new ArrayList();
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", 1.25, 1.25, zmax), 1.25, 1.25, zmax));
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", -1.25, 1.25, zmax), -1.25, 1.25, zmax));
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", 1.25, -1.25, zmax), 1.25, -1.25, zmax));
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", -1.25, -1.25, zmax), -1.25, -1.25, zmax));
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", 1.25, 1.25, zmin), 1.25, 1.25, zmin));
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", -1.25, 1.25, zmin), -1.25, 1.25, zmin));
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", 1.25, -1.25, zmin), 1.25, -1.25, zmin));
        plot.strings.add( new StringWCoords( String.format("(%.2f, %.2f, %.2f)", -1.25, -1.25, zmin), -1.25, -1.25, zmin));
        
        
        table.add(plot);
    }
    

    void addAlex()
    {
        addA(); addL(); addE(); addX();
        AssemblyNode Alex = new AssemblyNode("alex", Code.SCALE, .8);
        Alex.transform.buildElementaryTransform(Code.Y_ROT, 80);
        AssemblyNode letter;
        table.add(Alex);

        letter = (AssemblyNode)this.getNodeByName("a");
        letter.transform.defineElementaryTransform(Code.X_TRANS, -6.75);
        Alex.list.add(letter);
        letter = (AssemblyNode)this.getNodeByName("l");
        letter.transform.defineElementaryTransform(Code.X_TRANS, -2.25);
        Alex.list.add(letter);
        letter = (AssemblyNode)this.getNodeByName("e");
        letter.transform.defineElementaryTransform(Code.X_TRANS, 2.25);
        Alex.list.add(letter);
        letter = (AssemblyNode)this.getNodeByName("x");
        letter.transform.defineElementaryTransform(Code.X_TRANS, 6.75);
        Alex.list.add(letter);
    }
    
    void addA()
    {
        AssemblyNode A  = new AssemblyNode("A", Code.IDENTITY, 0);
        AssemblyNode a1 = new AssemblyNode("", Code.IDENTITY, 0);
        AssemblyNode a2 = new AssemblyNode("", Code.Z_TRANS, .75);
        a2.list.add(a1);
        A.list.add(a1);
        A.list.add(a2);
        A.color = Color.BLUE;
        table.add(A);
        
        Point3d points[] = new Point3d[8];
        points[0] = new Point3d(-2, -3, 1);
        points[1] = new Point3d(0, 3, 1);
        points[2] = new Point3d(2, -3, 1);
        points[3] = new Point3d(1, -3, 1);
        points[4] = new Point3d(.3, -.2, 1);
        points[5] = new Point3d(-.3, -0.2, 1);
        points[6] = new Point3d(-1, -3, 1);
        points[7] = new Point3d(-2, -3, 1);
        a1.list.add(new ObjectNode("", points, false));
        
        points = new Point3d[3];
        points[0] = new Point3d(.2, .5, 1);
        points[1] = new Point3d(0, 1.5, 1);
        points[2] = new Point3d(-.2, .5, 1);
        a1.list.add(new ObjectNode("", points, true));

        A.list.add(getLineObject(-2, -3, 1, -2, -3, 1.75));
        A.list.add(getLineObject(0, 3, 1, 0, 3, 1.75));
        A.list.add(getLineObject(2, -3, 1, 2, -3, 1.75));
        A.list.add(getLineObject(1, -3, 1.75, 1, -3, 1));
        A.list.add(getLineObject(.3, -.2, 1, .3, -.2, 1.75));
        A.list.add(getLineObject(-.3, -0.2, 1, -.3, -0.2, 1.75));
        A.list.add(getLineObject(-1, -3, 1, -1, -3, 1.75));
        A.list.add(getLineObject(.2, .5, 1, .2, .5, 1.75));
        A.list.add(getLineObject(0, 1.5, 1, 0, 1.5, 1.75));
        A.list.add(getLineObject(-.2, .5, 1.75, -.2, .5, 1));
    }
    
    void addL()
    {
        AssemblyNode L  = new AssemblyNode("L", Code.IDENTITY, 0);
        AssemblyNode l1 = new AssemblyNode("", Code.IDENTITY, 0);
        AssemblyNode l2 = new AssemblyNode("", Code.Z_TRANS, .75);
        l2.list.add(l1);
        L.list.add(l1);
        L.list.add(l2);
        L.color = Color.GREEN;
        table.add(L);
        
        Point3d points[] = new Point3d[6];
        points[0] = new Point3d(-2, 3, 1);
        points[1] = new Point3d(-2, -3, 1);
        points[2] = new Point3d(2, -3, 1);
        points[3] = new Point3d(2, -2, 1);
        points[4] = new Point3d(-1, -2, 1);
        points[5] = new Point3d(-1, 3, 1);
        l1.list.add(new ObjectNode("", points, true));

        L.list.add(getLineObject(-2, 3, 1, -2, 3, 1.75));
        L.list.add(getLineObject(-2, -3, 1,-2, -3, 1.75));
        L.list.add(getLineObject(2, -3, 1, 2, -3, 1.75));
        L.list.add(getLineObject(2, -2, 1, 2, -2, 1.75));
        L.list.add(getLineObject(-1, -2, 1, -1, -2, 1.75));
        L.list.add(getLineObject(-1, 3, 1, -1, 3, 1.75));
    }
    
    void addE()
    {
        AssemblyNode E  = new AssemblyNode("E", Code.IDENTITY, 0);
        AssemblyNode e1 = new AssemblyNode("", Code.IDENTITY, 0);
        AssemblyNode e2 = new AssemblyNode("", Code.Z_TRANS, .75);
        e2.list.add(e1);
        table.add(E);
        E.list.add(e1);
        E.list.add(e2);
        E.color = Color.RED;
        
        Point3d points[] = new Point3d[12];
        points[0] = new Point3d(-2, 3, 1);
        points[1] = new Point3d(-2, -3, 1);
        points[2] = new Point3d(2, -3, 1);
        points[3] = new Point3d(2, -2, 1);
        points[4] = new Point3d(-1, -2, 1);
        points[5] = new Point3d(-1, -.5, 1);
        points[6] = new Point3d(2, -.5, 1);
        points[7] = new Point3d(2, .5, 1);
        points[8] = new Point3d(-1, .5, 1);
        points[9] = new Point3d(-1, 2, 1);
        points[10] = new Point3d(2, 2, 1);
        points[11] = new Point3d(2, 3, 1);
        e1.list.add(new ObjectNode("", points, true));
        
        E.list.add(getLineObject(-2, 3, 1, -2, 3, 1.75));
        E.list.add(getLineObject(-2, -3, 1, -2, -3, 1.75));
        E.list.add(getLineObject(2, -3, 1, 2, -3, 1.75));
        E.list.add(getLineObject(2, -2, 1, 2, -2, 1.75));
        E.list.add(getLineObject(-1, -2, 1, -1, -2, 1.75));
        E.list.add(getLineObject(-1, -.5, 1.75, -1, -.5, 1));
        E.list.add(getLineObject(2, -.5, 1.75, 2, -.5, 1));
        E.list.add(getLineObject(2, .5, 1, 2, .5, 1.75));
        E.list.add(getLineObject(-1, .5, 1, -1, .5, 1.75));
        E.list.add(getLineObject(-1, 2, 1, -1, 2, 1.75));
        E.list.add(getLineObject(2, 2, 1, 2, 2, 1.75));
        E.list.add(getLineObject(2, 3, 1, 2, 3, 1.75));
    }
    
    void addX()
    {
        AssemblyNode X  = new AssemblyNode("X", Code.IDENTITY, 0);
        AssemblyNode x1 = new AssemblyNode("", Code.IDENTITY, 0);
        AssemblyNode x2 = new AssemblyNode("", Code.Z_TRANS, .75);
        x2.list.add(x1);
        X.list.add(x1);
        X.list.add(x2);
        X.color = Color.MAGENTA;
        table.add(X);
        
        Point3d points[] = new Point3d[12];
        points[0] = new Point3d(-1.5, 3, 1);
        points[1] = new Point3d(-2, 2, 1);
        points[2] = new Point3d(-.5, 0, 1);
        points[3] = new Point3d(-2, -2, 1);
        points[4] = new Point3d(-1.5, -3, 1);
        points[5] = new Point3d(0, -.5, 1);
        points[6] = new Point3d(1.5, -3, 1);
        points[7] = new Point3d(2, -2, 1);
        points[8] = new Point3d(.5, 0, 1);
        points[9] = new Point3d(2, 2, 1);
        points[10] = new Point3d(1.5, 3, 1);
        points[11] = new Point3d(0, .5, 1);
        
        x1.list.add(new ObjectNode("", points, true));
        
        X.list.add(getLineObject(-1.5, 3, 1, -1.5, 3, 1.75));
        X.list.add(getLineObject(-2, 2, 1, -2, 2, 1.75));
        X.list.add(getLineObject(-.5, 0, 1, -.5, 0, 1.75));
        X.list.add(getLineObject(-2, -2, 1, -2, -2, 1.75));
        X.list.add(getLineObject(-1.5, -3, 1, -1.5, -3, 1.75));
        X.list.add(getLineObject(0, -.5, 1, 0, -.5, 1.75));
        X.list.add(getLineObject(1.5, -3, 1, 1.5, -3, 1.75));
        X.list.add(getLineObject(2, -2, 1, 2, -2, 1.75));
        X.list.add(getLineObject(.5, 0, 1, .5, 0, 1.75));
        X.list.add(getLineObject(2, 2, 1, 2, 2, 1.75));
        X.list.add(getLineObject(1.5, 3, 1, 1.5, 3, 1.75));
        X.list.add(getLineObject(0, .5, 1, 0, .5, 1.75));
    }
    
    void addRubicsCube()
    {
        AssemblyNode cube = new AssemblyNode("RubicsCube", Code.SCALE, 1.8);
        AssemblyNode grid = (AssemblyNode)getNodeByName("cubegrid");
        grid.color = Color.red;
        AssemblyNode cubeFront = new AssemblyNode("cubeFront", Code.Z_TRANS, 2);
        cubeFront.color = Color.BLUE;
        AssemblyNode cubeBack = new AssemblyNode("cubeback", Code.X_ROT, 180);
        cubeBack.color = Color.GRAY;
        cubeFront.list.add(grid);
        cube.list.add(cubeFront);
        cube.list.add(cubeBack);
        cube.list.add(grid);
        cubeBack.list.add(cubeFront);
        table.add(cube);
    }
    
    void addCubeGrid()
    {
        AssemblyNode grid = new AssemblyNode("cubegrid", Code.IDENTITY, 0);
        AssemblyNode square = (AssemblyNode)getNodeByName("cube");
        AssemblyNode gridMid = new AssemblyNode("gridMid", Code.SCALE, .3);
        AssemblyNode midRight = new AssemblyNode("midRight", Code.X_TRANS, 2);
        AssemblyNode midLeft = new AssemblyNode("midLeft", Code.X_TRANS, -2);
        AssemblyNode botRight = new AssemblyNode("botRight", Code.X_TRANS, 2);
        AssemblyNode botMid = new AssemblyNode("botMid", Code.Y_TRANS, -2);
        AssemblyNode botLeft = new AssemblyNode("botLeft", Code.X_TRANS, -2);
        AssemblyNode topRight = new AssemblyNode("topRight", Code.X_TRANS, 2);
        AssemblyNode topMid = new AssemblyNode("topMid", Code.Y_TRANS, 2);
        AssemblyNode topLeft = new AssemblyNode("topLeft", Code.X_TRANS, -2);
        
        gridMid.list.add(square);
        
        midLeft.list.add(gridMid);
        midRight.list.add(gridMid);
        botMid.list.add(gridMid);
        topMid.list.add(gridMid);
       
        topLeft.list.add(topMid);
        topRight.list.add(topMid);
        botLeft.list.add(botMid);
        botRight.list.add(botMid);
        
        grid.list.add(gridMid);
        grid.list.add(midRight);
        grid.list.add(midLeft);
        grid.list.add(topRight);
        grid.list.add(topMid);
        grid.list.add(topLeft);
        grid.list.add(botRight);
        grid.list.add(botMid);
        grid.list.add(botLeft);

        table.add(grid);
    }
    
    void addCube()
    {
        AssemblyNode cube = new AssemblyNode("Cube", Code.IDENTITY, 0);
        ObjectNode cubeFront = (ObjectNode)getNodeByName("square");
        AssemblyNode cubeTop = new AssemblyNode("cubeTop", Code.X_ROT, -90 );
        AssemblyNode cubeRight = new AssemblyNode("cuberight", Code.Y_ROT, 90);
        AssemblyNode cubeLeft = new AssemblyNode("cubeleft", Code.Y_ROT, -90);
        AssemblyNode cubeBottom = new AssemblyNode("cubebottom", Code.Y_TRANS, -2);
        AssemblyNode cubeBack = new AssemblyNode("cubeback", Code.Z_TRANS, -2);
        
        cube.list.add(cubeFront);
        cube.list.add(cubeTop);
        cube.list.add(cubeLeft);
        cube.list.add(cubeBottom);
        cube.list.add(cubeBack);
        cube.list.add(cubeRight);
        
        cubeTop.list.add(cubeFront);
        cubeRight.list.add(cubeFront);
        cubeLeft.list.add(cubeFront);
        cubeBottom.list.add(cubeTop);
        cubeBack.list.add(cubeFront);
        
        table.add(cube);
    }
    
    void addSquare()
    {
        Point3d points[] = new Point3d[4];
        points = new Point3d[4];
        points[0] = new Point3d(-1, -1, 1);
        points[1] = new Point3d(-1, 1, 1);
        points[2] = new Point3d(1, 1, 1);
        points[3] = new Point3d(1, -1, 1);
        table.add(new ObjectNode("Square", points, true));
    }
    
    // call with name of desired assembly or aobject and if it exists, it will
    // be returned
    Node getNodeByName(String name)
    {
        for(Node n : table)
        {
            if(name.equalsIgnoreCase(n.name))
            {
                return n;
            }
        }
        return null;
    }
    
    // constructor calls add functions to build models and populate table 
    public ObjectTable()
    {
        table = new ArrayList();
        addSquare();
        addCube();
        addPlot();
        addCubeGrid();
        addRubicsCube();
        addAlex();
        addHall();
    }
}