package pl.polsl.tomasz.michalik.exceptions;

/**
 * Any turing machine exception
 *
 * @version: 1.0
 * @author Tomasz Michalik
 */
public class TMException extends Exception {

    public TMException() {
    }

    public TMException(String message) {
        super(message);
    }

}
