import java.io.File;
import java.util.Scanner;

// NAME: Patrick Tsai
// CLASS: CSE 223 M-F 10AM
// DUE: 5/20/2020 - 8:00 AM

// DESCRIPTION: This program, 20 Questions, asks the user to think of a thing (anything). The progrma then asks the user a series of questions in order to gues what the user is thinking of. The program will eventually hae an answer to present to the user, asking them is this what you are thinking of. If the user answers yes, then the program guessed correctly, if the user responds no, then the program will ask the user what they are thinking of and a question that would discern the coputers (wrong) guess with what the user's actual answer is. The program then documents the answer and question within it's database.
// This program uses LinkedList Nodes within a Binary Serch Tree structure. The questions and answers are contained within a database, which is read in at the begining of each game.

// Caveat - If starting from a Database with less than 20 questions in a line of inquiry, the database will update using user input  upon an incorrect guess from the program. However if the program asks 20 questions and still guesses incorrectly, the program will acknowledge it's loss and will not update the database in that line of inquiry due to "keeping within the spirit of the game".
// If a database containing an excess of 20 questions in a line inquiry is uploaded, the program will run through all the questions, however it will not update the database's line of inquiry due to "keeping within the spirit of the game of 20 questions".

class PA3 {                                   // PA3 class creation
  public static void main(String[] args) {    // main function, can take in args
    Tree tr=new Tree();                       // creation on new tree object
    Node rootMain;                            // declaring a Node var rootMain, holds the current node from main function
    Node previousNode;                        // declare node var, keeps previous node as user answers questions, helps with new node placement
    Node rebuildSeed;                         // declare node var, this keeps the first root node, used to seed the rebuilding of tree during print
    String flag;                              // declare a flag, used in while loop to check if user entered anything
    String dbName;
 
    if (args.length==0) {                     // this if else statement handles command line args - for database handling
      tr.processFile("20Q.txt");              // if no command line arg supplied then it uses 20Q.txt as the default database
      dbName="20Q.txt";
    }                                         // it uses the tr Object, and Tree Method "prossesfile" to create the Binary Search Tree of Linked List Nodes
    else {                                    // else if a command line arg is supplied then
      tr.processFile(args[0]);                // it uses the file that is supplied by the argument
      dbName=args[0];
    }

    rootMain=tr.getInitialNode();             // variable - grabs the first node in the binary search tree, variable is used as the current node in the program 
    rebuildSeed=rootMain;                     // saves the first node in the bianry search tree for the process of rebuilding the tree at the end of the program
    Scanner in=new Scanner(System.in);        // a scanner is used to read what the user inputs

    System.out.println("Welcome to 20 Questions");  // welcome message
    System.out.println("Think of an object, and I'll try to guess what you're thinking of");
    System.out.print("Press ENTER key to continue");
    tr.pressEnter();
    
    System.out.println("Answer the following questions by typing  \"yes\" or \"no\"");
    int questionCount=1;
    System.out.print("[q" + questionCount + "] " + tr.getResponseText(rootMain) + "? ");  // prints out the first question for the database
   
    String userInput=in.nextLine();           // var set to reads the response from the user

    while (userInput!=null) {                 // enter a while loop to parse the tree determined by user response
      userInput=userInput.toLowerCase();
      if (userInput.equals("yes") || userInput.equals("no")) {    // make sure the user response is valid, either "yes" or "no"
        flag=userInput;                                           // uses a flag to store if user entered yes or no
        previousNode=rootMain;                                    // once the user ansers,stores the current node as previous
        rootMain=tr.getNode(rootMain, userInput);                 // then makes the next node (based on user answer) the current node
        if (rootMain.yes==null && rootMain.no==null) {            // used to determine if the current node is an "Answer" - has no yes and no nodes 
          questionCount=questionCount+1;
          System.out.print("[q" + questionCount + "] Is what you're thinking of a " + tr.getResponseText(rootMain) + "? ");    // if it is an answer, then computer will gues the node

          int testVal=0;
          while (testVal!=1) {                                    // while loop handles the response if the programs answer is correct
            userInput=in.nextLine();                              // takes in the user's response to the question
            userInput=userInput.toLowerCase();
                                                                  // if the user does not enter a valid answer then the program will again 
            if (userInput.equals("yes") || userInput.equals("no")) {    //ask the user to enter a valid answer of yes or no
              testVal=1;
            }
            else {
              System.out.println("Invalid Input");                  // prints out "Invalid Input"
              System.out.println("Please answer either \"yes\" or \"no\" to the previous question");
            }
          }
          if (userInput.equals("yes")) {                          // if user responds yes
            System.out.println("Woohoo! I got it!!! Thanks for playing :)");           // the program "guessed" correctly
            System.exit(0);                                       // and program ends
          }
          else if (userInput.equals("no") && questionCount<20) {                      // if user answers no
            System.out.println("Okay, you stumped me!");
            System.out.println("What were you thinking of?");     // computer asks what they were thinking of
            
            String newUserAnswer=in.nextLine();                   // saves user response as variable

            System.out.println("Please type in a question for which a \"yes\" answer means " + newUserAnswer + " and a \"no\" answer means a " + rootMain.response);
            String newUserQuestion=in.nextLine();                 // program asks user to enter a question decerning what the program "guessed" and the 
                                                                  // actual answer the user was thinking off
            tr.addNode(newUserQuestion,newUserAnswer,rootMain,previousNode,flag);      // calls on the addNode method from Tree

            int returnRebuildVal=tr.rebuild(rebuildSeed,dbName);                       // calls the rebuild method from Tree - rewrites the database
            if (returnRebuildVal==1) {
              System.out.println("Successfully rebuilt database.");                    // returns success upon rebuild
            }
            else {
              System.out.println("Couldn't open databse, databse not updated");
              System.out.println("Please check if file still exists or write permissions are correct");
            }
            System.out.println("Thanks! I shall do better next time...");
            System.exit(0);                                       // exits program
          }
          else {                                                                        // if 20 questions have been asked in a line of inquiry
            System.out.println("I have lost, and I have asked 20 questions.");          // then the program will not add another node to that line of inquiry
            System.out.println("In the spirit of the 20 questions game, I can't add another Question to the database");
            System.out.println("Thanks for playing, goodbye! ");
            System.exit(0);                                       // exits program

          }
        }
        else {                                                    // if the node is a question not an answer (leaf)
          questionCount=questionCount+1;
          System.out.print("[q" + questionCount + "] " + tr.getResponseText(rootMain) + "? ");  // print out question
        }
      }
      else {                                                      // if the user inputs something other than yes or no
        System.out.println("Invalid Input");                      // prints out "Invalid Input"
        System.out.println("Please answer either \"yes\" or \"no\" to the previous question");                      // prints out "Invalid Input"
      } 

    userInput=in.nextLine();                                      // reads in user response
    }
  System.out.println(" ");
  }
}
