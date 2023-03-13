package pl.polsl.tomasz.michalik.exceptions;

/**
 * Any turing machine exception
 *
 * @version: 1.0
 * @author Tomasz Michalik
 */
public class TMException extends Exception {

    /**
     * constructor for nonspecific exception
     */
    public TMException() {
    }

    /**
     * Constructor for an exception with message
     *
     * @param message message
     */
    public TMException(String message) {
        super(message);
    }

}
