/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.tomasz.michalik.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.addAll;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;
import pl.polsl.tomasz.michalik.exceptions.FileFormatException;
import pl.polsl.tomasz.michalik.exceptions.TMException;
import pl.polsl.tomasz.michalik.model.Move;
import pl.polsl.tomasz.michalik.model.Transition;
import pl.polsl.tomasz.michalik.model.TuringMachine;

/**
 * Reads a turing machine configuration from a given input
 * @author Tomasz Michalik
 * @version 1.2
 */
public class TMReader {
    /**
     * reads a TM from the given file
     * @param filename path to the file 
     * @return read TM
     * @throws TMException in case the input is wrong
     * @throws pl.polsl.tomasz.michalik.exceptions.FileFormatException something went wrong with the file
     */
    public TuringMachine readTMFromFile(String filename) throws  TMException, FileFormatException
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
        
        try {
            File f= new File(filename);
            if (!f.exists()){
                throw new FileFormatException("no such file: "+ filename);
            }
            
            Scanner scanner = new Scanner(f);

            //first line in the file -- number of tapes
            String sNoTapes = scanner.nextLine();
            if (sNoTapes.isBlank()){
                throw new FileFormatException("no number of tapes specified");
            }
            try{
                noTapes = Integer.parseInt(sNoTapes);
            }catch (NumberFormatException ex){
                throw new FileFormatException("Failed to convert <<"+sNoTapes+">> to integer");
            }
            if (noTapes <=0){
                throw new FileFormatException("tape number cannot be lesser or equal 0" );
            }
            
            //second line in the file -- internal states
            addAll(states, scanner.nextLine().split(" "));
            if (states.isEmpty()){
                throw new FileFormatException("no internal states specified");
            }

            //third line in the file -- initial state
            machineInitialState = scanner.nextLine();
            if (machineInitialState.isEmpty()){
                throw new FileFormatException("no initial state specified");
            }else if (machineInitialState.contains(" ")){
                throw new FileFormatException("initial state must be one symbol");
            }

            //fourth line in the file -- blank symbol
            blank = scanner.nextLine();
            if (blank.isEmpty()){
                throw new FileFormatException("no blank symbol specified");
            }else if (blank.contains(" ")){
                throw new FileFormatException("blank must be one symbol");
            }

            TuringMachine tM = new TuringMachine(noTapes, states, blank);
            tM.setInitialState(machineInitialState);

            //reading the tape contents
            int maxLineSize = 0;
            for(int i=0; i<noTapes; i++){
                if (!scanner.hasNextLine()){
                    throw new FileFormatException("undefined tape contents for tape" 
                            + i +". if you wish to enter empty tape, use empty line");
                }
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
                try{
                    for (int i=0; i<noTapes; i++){
                        resultMoves.add(Move.valueOf(tokens.next()));
                    }
                }catch (IllegalArgumentException ex){
                    throw new FileFormatException("bad movement - not L/N/R");
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
        }catch (FileNotFoundException ex){
            //do nothing - we manually check for existence of the file
        }catch (NoSuchElementException ex){
            throw new FileFormatException("some lines are missing");
        }
        return null;
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
