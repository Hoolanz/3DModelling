// A Node for basic building blocks of models
package a3;
public class ObjectNode extends Node
{
    Point3d points [];  // All the points to draw object
    Boolean isPolygon;  // basisically, connect first and last point or not
    
    ObjectNode(String name, Point3d points[], Boolean b)
    {
        super(name);
        this.points = new Point3d[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
        isObject = true;
        isPolygon = b;
    }
}
