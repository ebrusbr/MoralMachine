//Final Project
//Ebru Soezbir 1135390 nsoezbir

//This exception class handles invalid input of the user
public class InvalidInputException extends Exception {

    public InvalidInputException () {
        super("Invalid Input!");
    }
    
    public InvalidInputException (String message) {
        super(message);
    }
}
