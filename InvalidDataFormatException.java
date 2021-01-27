//Final Project
//Ebru Soezbir 1135390 nsoezbir

//This exception class handles invalid number of data fields
public class InvalidDataFormatException extends Exception {

    public InvalidDataFormatException () {
        super("WARNING: invalid data format in config file");
    }
    
    public InvalidDataFormatException (String message) {
        super(message);
    }
}
