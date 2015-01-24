// Basis for assembly and object nodes. both types of nodes extend this class,
// so they can be stored together
package a3;
public class Node 
{
    String name; // for top-level nodes in object table, so they can be found
    Boolean isObject; // true if object, false if assembly
    
    Node(String name)
    {
        this.name = name;
    }
}
