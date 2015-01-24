package a3;
public class StringWCoords {
    String str;
    Point3d p;
    
    StringWCoords(String s, double x, double y, double z)
    {
        p = new Point3d(x, y, z);
        str = s;
    }
}
