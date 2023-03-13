package pl.polsl.tomasz.michalik.exceptions;

/**
 * Exception when there was something wrong in the input file
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
public class FileFormatException extends Exception {

    /**
     * constructor for nonspecific exception
     */
    public FileFormatException() {
    }

    /**
     * Constructor for an exception with message
     *
     * @param message message
     */
    public FileFormatException(String message) {
        super(message);
    }

}
