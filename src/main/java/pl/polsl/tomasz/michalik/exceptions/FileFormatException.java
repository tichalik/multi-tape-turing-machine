package pl.polsl.tomasz.michalik.exceptions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Exception when there was something wrong in the input file
 * @author Tomasz Michalik
 * @version 1.0
 */
public class FileFormatException extends Exception {

    public FileFormatException() {
    }

    public FileFormatException(String message) {
        super(message);
    }
    
}
