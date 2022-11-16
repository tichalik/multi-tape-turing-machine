/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.tomasz.michalik.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.addAll;
import java.util.Scanner;
import java.util.stream.Stream;
import pl.polsl.tomasz.michalik.exceptions.TMException;
import pl.polsl.tomasz.michalik.model.Move;
import pl.polsl.tomasz.michalik.model.Transition;
import pl.polsl.tomasz.michalik.model.TuringMachine;

/**
 * Reads a turing machine configuration from a given input
 * @author Tomasz Michalik
 * @version 1.0
 */
public class TMReader {
    /**
     * reads a TM from the given file
     * @param filename path to the file 
     * @return read TM
     * @throws FileNotFoundException in case there is no such a file as filename
     * @throws TMException in case the input is wrong
     */
    public TuringMachine readTMFromFile(String filename) throws FileNotFoundException, TMException
    { 
        //parameters for the ruring machine
        int noTapes = 0;
        ArrayList<String> states = new ArrayList<>();
        //dummy initialization
        String machineInitialState;
        //dummy initialization
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        ArrayList<Transition> transitions =  new ArrayList<>();
        String blank;

        FileInputStream fis = new FileInputStream(filename);
        Scanner scanner = new Scanner(fis);

        //first line in the file -- number of tapes
        noTapes = Integer.parseInt(scanner.nextLine());
        //second line in the file -- internal states
        addAll(states, scanner.nextLine().split(" "));

        //third line in the file -- initial state
        machineInitialState = scanner.nextLine();

        //fourth line in the file -- blank symbol
        blank = scanner.nextLine();

        TuringMachine tM = new TuringMachine(noTapes, states, blank);
        tM.setInitialState(machineInitialState);
        
        //reading the tape contents
        for(int i=0; i<noTapes; i++){
            ArrayList<String> tmp = new ArrayList<>();
            addAll(tmp, scanner.nextLine().split(" "));
            tapeContents.add(tmp);
        }
        
        tM.setTapes(tapeContents);


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

            try{
            tM.addTransition( new Transition(noTapes,initialState, initialSymbols,
            resultState, resultSymbols, resultMoves) );
            } catch (TMException e){
                throw e;
            }
            
        }
        scanner.close();
        
        return tM;
    }
    
    
    /**
     * reads TM configuration from user input on the command line
     * @return obtained TM
     * @throws TMException  in case the input is wrong
     */
    public TuringMachine readFromCmd() throws TMException
    {
        Scanner scanner = new Scanner(System.in);
        TuringMachine tM;
        
        System.out.println("Enter number of tapes: ");
        int noTapes = Integer.parseInt(scanner.nextLine());
        
        System.out.println("enter internal states");
        ArrayList<String> states = new ArrayList<>();
        addAll(states, scanner.nextLine().split(" "));
        
        System.out.println("enter blank symbol:");
        String blank = scanner.nextLine();
        
        tM = new TuringMachine(noTapes, states, blank);
        
        System.out.println("enter initial state");
        String machineInitialState = scanner.nextLine();
        
        tM.setInitialState(machineInitialState);
        
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        //entering tape contents
        for (int i =0; i<noTapes; i++){
            System.out.println("enter configuration of tape #" + i);
            ArrayList<String> tmp = new ArrayList<>();
            addAll(tmp, scanner.nextLine().split(" "));
            tapeContents.add(tmp);
        }
        
        tM.setTapes(tapeContents);
                

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

                tM.addTransition(new Transition(noTapes, initialState, initialSymbols,
                resultState, resultSymbols, moves) );

            }
        }
        
        return tM;
    }
}
