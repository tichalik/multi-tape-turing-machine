package pl.polsl.tomasz.michalik.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.addAll;
import java.util.Scanner;
import java.util.stream.Stream;
import pl.polsl.tomasz.michalik.exceptions.*;
import pl.polsl.tomasz.michalik.model.TuringMachine;
import pl.polsl.tomasz.michalik.model.Transition;
import pl.polsl.tomasz.michalik.view.TuringMachineView;

import pl.polsl.tomasz.michalik.model.Move;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/** the controller class, has the app;s main method
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
public class Controller {
    /** main method controlling entire app
    * @param args path to intput file
    */
    public static void main(String[] args) {
        //parameters for the ruring machine
        int noTapes = 0;
        ArrayList<String> states = new ArrayList<>();
        //dummy initialization
        String machineInitialState = "";
        //dummy initialization
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        ArrayList<Transition> transitions =  new ArrayList<>();
        String blank = "";
        
    
        // source file
        if (args.length == 1){
            // to do- reading from the file
            try{
                FileInputStream fis = new FileInputStream(args[0]);
                Scanner scanner = new Scanner(fis);
                
                //first line in the file -- number of tapes
                noTapes = Integer.parseInt(scanner.nextLine());
                //second line in the file -- internal states
                addAll(states, scanner.nextLine().split(" "));
                
                //third line in the file -- initial state
                machineInitialState = scanner.nextLine();
                
                //fourth line in the file -- blank symbol
                blank = scanner.nextLine();
                
                //reading the tape contents
                for(int i=0; i<noTapes; i++){
                    ArrayList<String> tmp = new ArrayList<>();
                    addAll(tmp, scanner.nextLine().split(" "));
                    tapeContents.add(tmp);
                }
               
                
                //reading the transitions
                
                while (scanner.hasNextLine()){
                    Scanner tokens = new Scanner(scanner.nextLine());
                    
                    //the first position is the initial state
                    String initialState = tokens.next(); 
                    //then theres the symbols on the tapes 
                    ArrayList<String> initialSymbols = new ArrayList<>();
                    for (int i=0; i<noTapes; i++){
                        initialSymbols.add(tokens.next());
                    }
                    //the resulting state of the machine 
                    String resultState = tokens.next();
                    //the resulting symbols on the tapes
                    ArrayList<String> resultSymbols = new ArrayList<>();
                    for (int i=0; i<noTapes; i++){
                        resultSymbols.add(tokens.next()) ;
                    }
                    //teh movements of the heads
                    ArrayList<Move> resultMoves = new ArrayList<>();
                    for (int i=0; i<noTapes; i++){
                        resultMoves.add(Move.valueOf(tokens.next()));
                    }
                    
                    Transition t = new Transition(noTapes,initialState, initialSymbols,
                    resultState, resultSymbols, resultMoves);
                    
                    transitions.add(t);
                }
                scanner.close();
            }
            catch (FileNotFoundException ex){
                
            }
            
        }
        // gettting parameters from the command line
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter number of tapes: ");
            noTapes = Integer.parseInt(scanner.nextLine());
            System.out.println("enter internal states");
            addAll(states, scanner.nextLine().split(" "));
            System.out.println("enter initial state");
            machineInitialState = scanner.nextLine();
            //entering tape contents
            for (int i =0; i<noTapes; i++){
                System.out.println("enter configuration of tape #" + i);
                ArrayList<String> tmp = new ArrayList<>();
                addAll(tmp, scanner.nextLine().split(" "));
                tapeContents.add(tmp);
            }
            
            
            Boolean end = false;
            //getting the transitions
            while (end!=true){
                System.out.println("add new rule? [y/n]");
                String yn = scanner.nextLine();
                if ("n".equals(yn)){
                    break;
                }
                else if ("y".equals(yn))
                {
                    System.out.println("enter initial state:");
                    String initialState = scanner.nextLine();
                    System.out.println("enter blank symbol:");
                    blank = scanner.nextLine();
                    System.out.println("enter initial tape configuration");
                    ArrayList<String> initialSymbols = new ArrayList<>();
                    addAll(initialSymbols, scanner.nextLine().split(" "));
                    
                    System.out.println("enter resulting state:");
                    String resultState = scanner.nextLine();
                    System.out.println("enter resulting tape configuration");
                    ArrayList<String> resultSymbols = new ArrayList<>();
                    addAll(resultSymbols, scanner.nextLine().split(" "));
                    System.out.println("enter movements of the heads [L/N/R]");
                    ArrayList<Move> moves = new ArrayList<>();
                    Stream s = Arrays.stream(scanner.nextLine().split(" "));
                    s.forEach(m -> moves.add(Move.valueOf(m.toString())));
                    
                    Transition t = new Transition(noTapes, initialState, initialSymbols,
                    resultState, resultSymbols, moves);
                    transitions.add(t);
                    
                }
            }
        }
        
        
        //initializing the turing machine
        TuringMachine turingMachine = new TuringMachine(noTapes, states, blank);
        //adding transitions
        try{
            for (Transition t: transitions){
                turingMachine.addTransition(t);
            }
        }
        catch (TMException ex){
            System.out.println(ex.getMessage());
        }
        //initializing tapes
        turingMachine.setTapes(tapeContents);
        
        //setting up the initial state
        turingMachine.setInitialState(machineInitialState);
        
        //managing the view
        TuringMachineView view = new TuringMachineView();
        view.showCurrentState(turingMachine.getCurrentState());
        view.showTapes(turingMachine.getTapes());
        
        Scanner scanner = new Scanner(System.in);
        
        Boolean goOn = true;
        while (goOn){
            turingMachine.next();
            view.showCurrentState(turingMachine.getCurrentState());
            view.showTapes(turingMachine.getTapes());
            System.out.println("continue? [y/n]");
            goOn = "y".equals(scanner.nextLine());
        }
        
    }
}
