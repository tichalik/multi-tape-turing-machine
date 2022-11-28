package pl.polsl.tomasz.michalik.controller;


import java.util.Scanner;
import pl.polsl.tomasz.michalik.exceptions.*;
import pl.polsl.tomasz.michalik.model.TuringMachine;
import pl.polsl.tomasz.michalik.view.GUI;
import pl.polsl.tomasz.michalik.view.TuringMachineView;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/** the controller class, has the app;s main method
 *
 * @author Tomasz Michalik
 * @version 3.0
 */
public class Controller {
    /** main method controlling entire app
    * @param args path to intput file
    */
    public static void main(String[] args) {
        
        TuringMachine turingMachine = new TuringMachine();
        TMReader tmr = new TMReader();
        
        try{
            // source file
            if (args.length == 1){
                try{
                    turingMachine = tmr.readTMFromFile(args[0]);
                }
                catch (FileFormatException ex){
                    System.out.print(ex.getMessage());
                    return;
                }
            }
            // gettting parameters from the command line
            else {
                turingMachine = tmr.readFromCmd();
            }
        }
        catch (TMException ex){
            System.out.println(ex.getMessage());
        }
        
        GUI tmGUI = new GUI();
        System.out.println(turingMachine.getBlank());
        tmGUI.setBlank(turingMachine.getBlank());
        tmGUI.showTapes(turingMachine.getTapes());
        tmGUI.run();
        
        
        
//        //managing the view
//        TuringMachineView view = new TuringMachineView();
//        
//        Scanner scanner = new Scanner(System.in);
//        
//        Boolean goOn = true;
//        while (goOn){
//            view.showCurrentState(turingMachine.getCurrentState());
//            view.showTapes(turingMachine.getTapes());
//            System.out.println("continue? [y/n]");
//            goOn = "y".equals(scanner.nextLine());
//            turingMachine.next();
//        }
        
    }
}
