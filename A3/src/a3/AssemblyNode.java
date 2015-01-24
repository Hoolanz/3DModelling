// A Node that groups Nodes and transforms/colors them
package a3;
import java.awt.Color;
import java.util.ArrayList;
public class AssemblyNode extends Node
{
    ArrayList<Node> list;  // A list of nodes that make up the assembly
    Transform transform;    // if the assembly needs to transform stuff it draws
    Color color;            // if the assembly needs to color stuff it draws
    ArrayList<StringWCoords> strings;
    AssemblyNode(String name, int code, double value)
    {
        super(name);
        transform = new Transform();
        transform.defineElementaryTransform(code, value);
        this.list = new ArrayList();
        isObject = false;
        color = Color.BLACK;
    }    
}
