import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Tree {                              // Tree class - Binary Search Tree built around Node class
  Scanner sc;
  String currentLine;                            // the currentLine variable is used as a placeholder for  the "id" line of the DB file, this is "A:" or "Q:" from the Node type
  String inputString;                            // the inputString variable is used to to store the "response", either the actual anser or the question in the DB file
  Node root;                                     // root is the root Node, which is the first Node created from the processFile Method

  public void processFile(String filename) {     // the processFile Method - takes in a file name and 
    File myFile;                                 // uses a try catch to set file for scanning
    myFile=new File(filename);
    try {                                        // try catch handles the processing of the database
      sc=new Scanner(myFile);                    // if the file exists then it processes
     }
     catch (Exception e) {                       // if an exception is thrown then the program 
       System.out.println("Invalid Data Base file");
       System.out.println("Goodbye");            // will write message stating its an invalid file
       System.exit(0);                           // and will exit the program
     }
     currentLine=sc.nextLine();                  // if it is a good file then the first line is set to currentLine ["A:" or "Q:"]
     inputString=sc.nextLine();                  // and the line after that is set to inputString ["response"]
     root=this.ingest(inputString);              // the root is set to the "response" field of first Node and then the ingest method is called
                                                 // also root is set in the Method, this root is used in subsequent Methods to call the first Node of the BST
  sc.close();                                    // once the file has been ingest the scanner is closed
  return;                                        // and returns to PA3.java
  }

  public Node ingest(String inputString) {       // return the root of tree to which the new node has been added
       Node newNode=new Node(inputString);       // ingest is a recurrsive process, it creates a new Node (defined in Node.java) from the inputString (or response) var
       newNode.id=currentLine;                   // the currentLine variable is used to hold the line that signifies "A:" or "Q:" and assigns it to the id field of the Node

       if (currentLine.equals("Q:")) {           // if currentLIne is "Q:" it has nodes for yes and no
         currentLine=sc.nextLine();              // therefore it set the next lines as the id
         inputString=sc.nextLine();              // and inputString (or response)
         newNode.yes=ingest(inputString);        // and uses recurrsion to call ingest again with the new variables

         currentLine=sc.nextLine();              // since it is in BST form there are two (possible) Node associations
         inputString=sc.nextLine();              // therefore, once the yes side is set
         newNode.no=ingest(inputString);         // the no side then uses the same recurrsive behavior to create nodes
         return(newNode);
       }
       else {                                    // if the currentLine is not "Q:" it must be "A:"
         newNode.yes=newNode.no=null;            // "A:" is an answer or a leaf in BST terms, it doesn't have subsequent nodes associated with it
         return(newNode);                        // therefore the Node is created and returned and doesn't call itself recurrsively
       }
  }

  public Node getInitialNode() {                 // getInitialNode Method
    return(root);                                // made this to easily recall the BST root node from main
  }

  public Node getNode(Node rootIn, String userInput) {    // getNode Method returns a Node
    Node rootOut;                                         // based on the users response
    if (userInput.equals("yes")) {                        // if yes
      rootOut=rootIn.yes;                                 // returns node.yes
      return(rootOut);
    }
    else {
      rootOut=rootIn.no;                                  // if no
      return(rootOut);                                    // returns node.no
    }
  }

  public String getResponseText(Node rootResponse) {      // getResponseText Method
    return(rootResponse.response);                        // returns the text from the response field of the node
  }

  public void addNode(String Q, String Ayes, Node Ano, Node previousNode,String flag) {
    Node newQNode=new Node(Q);                            // addNode Method is used for creating a new answer and question if the program does not guess the user's object 
    newQNode.id="Q:";                                     // creates a new question node from Q string, and assigns "Q:" to the id of that node
    Node newAYesNode=new Node(Ayes);                      // creates a new Answer (leaf) node from the answer string provided from the user
    newAYesNode.id="A:";                                  // assigns "A:" to the id of that node
    newAYesNode.yes=newAYesNode.no=null;                  // assigns null to the yes and no fields of the Answer node

    if (flag.equals("yes")) {                             // if the flag is set to yes it signifies the answer to the last Q node
      previousNode.yes=newQNode;                          // and it then assigns the new Q node to the yes field of the previous Q node
    }
    else {                                                // if flag set to no
      previousNode.no=newQNode;                           // then program assigns the new Q node to the no field of the previous Q node
    }
    newQNode.yes=newAYesNode;                             // the new answer node is assigned to the yes field of the new Q node
    newQNode.no=Ano;                                      // and the program's wrongly guessed answer node is assigned to the no field of the new Q node
    return;
  }

  public int rebuild(Node seed, String dbName) {          // rebuild Method - rebuilds database with new nodes
    PrintWriter output;                                   // uses PrintWriter to print and replaceoriginal database  file
    try {                                                 // uses a try catch to make sure of access to database
      output=new PrintWriter(dbName);                     // if Printwriter does not have acccess it throws an exception 
    }                                                     // and returns 0, with message about access
    catch (Exception e) {
      return(0);
    }

    seed.rebuildDB(output);                               // if Printwriter does have access it uses the seed (or root node)
                                                          // to rebuild the database by recurrsively writing it with the
  output.close();                                         // rebuildDB Method from Node.java
  return(1);                                              // Printwriter is then closed and the method returns 1 for success
  }

  public void traverse() {                       // traverse method was only used for development
    if(root==null){                              // it was used to test pre order recurrsion was working properly
      return;                                    // i have left it in, in case we want to change order and it is needed for dev
    }
    root.traverse();                             // calls traverse from Node.java
  }

  public void pressEnter() {                     // pressEnter Method
    String inChar;                               // used to create a pause while user thinks of an object
    Scanner scE=new Scanner(System.in);          // a Scanner is used to wait for an input
    inChar=scE.nextLine();                       // once enter is clicked the game begins
    return;                                      // nothing is done with the inChar var value
  }

}
