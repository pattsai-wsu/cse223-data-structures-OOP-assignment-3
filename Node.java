import java.io.PrintWriter;

public class Node {                    // Node class defines the Node inputs for the linked list
  Node yes;                            // yes points to the next node if the user selects yes
  String response;                     // response is the question or answer associated with the node
  String id;                           // id holds the "Q:" for question or "A:" for answer
  Node no;                             // no points to the next node if the user answer is no

  
  public Node (String input) {         // constructor - assigns the input from db or user to the response field
    response=input;                    // response var equals the input value
  }

  public void rebuildDB(PrintWriter output) {   // public method to rebuild the database
    output.println(id);                         // prints the id field first - PREorder
    output.println(response);                   // then prints the response - PREorder

    if(yes!=null){                              // use recursion to traverse down the left side of BST
      yes.rebuildDB(output);                  
    }

    if (no!=null) {
      no.rebuildDB(output);                     // use recursion to traverse down the right side of BST
    }
  }

  public void traverse() {            // traverse method is not used in the final program, it was used for development
    System.out.println(id);           // pre-order root,left,right
    System.out.println(response);     // pre-order root,left,right
    if(yes!=null){
      yes.traverse();
    }
//    System.out.println(response);   // LNR - inorder left,root,right - UNCOMMENT FOR IN ORDER
    if (no!=null) {
      no.traverse();
    }
//    System.out.println(data);       //post-order  left,right,root - UNCOMMENT FOR POST ORDER
  }
}
