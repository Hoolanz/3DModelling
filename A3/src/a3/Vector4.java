//vector of four values. used in Draw.applyTransform
package a3;
class Vector4
{
    double v[];
    Vector4()
    {
        v = new double[4];
    }
    void pointToVector(Point3d p)
    {
        v[0] = p.x;
        v[1] = p.y;
        v[2] = p.z;
        v[3] = 1;
    }
        
    void copy(Vector4 source)
    {
        for(int i = 0; i < 4; i++)
            v[i] = source.v[i];
    }
    
    //multipy this by m
    void multiply(Transform m)
    {
        Vector4 n = new Vector4();
        for(int i = 0; i < 4; i++)
        {
            n.v[i] = 0;
            for(int j = 0; j < 4; j++)
            {
                n.v[i] += v[j] * m.m[j][i];
            }
        }
        copy(n);
    }
}
