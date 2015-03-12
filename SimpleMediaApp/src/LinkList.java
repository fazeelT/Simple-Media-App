
import java.io.File;


public class LinkList {

    
private class Node{
    File f;
    Node next;
    
    public Node(File f){
        this.f=f;
        next=null;
        
    }
    public String toString(){
        return f.getAbsolutePath();
    }
    public int compareTo(Node node){
        return f.getName().compareTo(node.f.getName());
    }
}    
    Node top;
    
    public void insert(File f){
        insert(new Node(f));
    }
    
    private void insert(Node node){
        if(top==null)
            top=node;
        else if(node.compareTo(top)<0){
            node.next=top;
            top=node;
        }
        else {
            Node curr = top, prev=null;
            while(curr!=null && curr.compareTo(node)<0){
                prev=curr;
                curr=curr.next;
               
            }
             prev.next=node;
                node.next=curr;
                }
    }
    
    public String toString(){
        String results="";
        
        for(Node curr=top;curr!=null;curr=curr.next){
            results+=curr+"\n";
        }
            return results; 
    }
    
}
