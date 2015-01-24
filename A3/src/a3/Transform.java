// Class for representing [4][4] arrays of doubles, along with some 
// functions: multiply two Transforms, define an elementary transform,
// build on a transform, set a transform to the identity matrix
package a3;

class Transform
{
    double m[][];

    Transform()
    {
        m = new double[4][4];
        for(int i = 0; i < 4; i++)        
            for(int j = 0; j < 4; j++)
                m[i][j] = 0.0;
    }
        
    // this = id matrix
    void setIdMatrix()
    {
        for(int i = 0; i < 4; i++)        
            for(int j = 0; j < 4; j++)
                m[i][j] = 0.0;
        
        m[0][0] = 1;
        m[1][1] = 1;
        m[2][2] = 1;
        m[3][3] = 1;
    }    
        
    // create new elementary transform based on code and value
    void defineElementaryTransform( int code, double value )
    {
        this.setIdMatrix();
        switch(code)
        {
            case 0:     // x translation
                m[3][0] = value;
                break;
            case 1:     // y translation
                m[3][1] = value;
                break;
            case 2:     // z translation
                m[3][2] = value;
                break;
            case 3:     // x rotate
                value = Math.toRadians(value);
                m[1][1] = Math.cos(value);
                m[2][2] = Math.cos(value);
                m[2][1] = -Math.sin(value);
                m[1][2] = Math.sin(value);
                break;
            case 4:     // y rotate
                value = Math.toRadians(value);
                m[0][0] = Math.cos(value);
                m[2][2] = Math.cos(value);
                m[0][2] = -Math.sin(value);
                m[2][0] = Math.sin(value);
                break;
            case 5:     // z rotate
                value = Math.toRadians(value);
                m[0][0] = Math.cos(value);
                m[1][1] = Math.cos(value);
                m[1][0] = -Math.sin(value);
                m[0][1] = Math.sin(value);
                break;    
            case 6:     // identity
                break;
            case 7:     // perspective
                m[2][3] = -1/value;
                break;
            case 8:     // scale
                m[0][0] = value;
                m[1][1] = value;
                m[2][2] = value;
                m[3][3] = 1;
                break;
            case 9:     // xscale
                m[0][0] = value;
                m[1][1] = 1;
                m[2][2] = 1;
                m[3][3] = 1;
                break;
            case 10:     // yscale
                m[0][0] = 1;
                m[1][1] = value;
                m[2][2] = 1;
                m[3][3] = 1;
                break;
            case 11:     // zscale
                m[0][0] = 1;
                m[1][1] = 1;
                m[2][2] = value;
                m[3][3] = 1;
                break;
            default:
                break;
        }
    }
    
    // create new elementary transform based on args, this = this * newTransform
    void buildElementaryTransform(int tfCode, double tfValue )
    {
        Transform M = new Transform();
        M.defineElementaryTransform( tfCode, tfValue );
        this.multiplyTransforms(this, M);
    }
    
    // this = m1 * m2;  safe to use this as m1 or m2
    void multiplyTransforms(Transform m1, Transform m2 )
    {
        Transform a = new Transform();
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    a.m[i][j] += m1.m[i][k]*m2.m[k][j];
                }
            }
        }
        this.copy(a);
    }
    
    // turn this into a copy of source
    void copy(Transform source)
    {
        for(int i = 0; i < 4; i++)        
            for(int j = 0; j < 4; j++)
                m[i][j] = source.m[i][j];
    }
}
