//Final Project
//Ebru Soezbir 1135390 nsoezbir

//This exception class handles invalid field values
public class InvalidCharacteristicException extends Exception {

    public InvalidCharacteristicException () {
        super("WARNING: invalid characteristic in config file");
    }
    
    public InvalidCharacteristicException (String message) {
        super(message);
    }
}
