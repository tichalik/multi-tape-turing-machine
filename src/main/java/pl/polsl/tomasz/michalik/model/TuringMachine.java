/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.tomasz.michalik.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import pl.polsl.tomasz.michalik.exceptions.*;
import pl.polsl.tomasz.michalik.model.Move;

/**
 * class representing the turing machine
 * @author Tomasz Michalik
 * @version 1.0
 */

public class TuringMachine {
    
    /**
     * number of tapes
     */
    private final int noTapes;
    /**
     * collection of tapes
     */
    private ArrayList<Tape> tapes;
    
    /**
     * blank symbol on the tapes
     */
    private String blank;

    /** gets the tapes of the turing machine
     * 
     * @return tapes of the machine
     */
    public ArrayList<Tape> getTapes() {
        return tapes;
    }

    /** gets the current state of TM
     * 
     * @return surrent state of TM
     */
    public String getCurrentState() {
        return currentState;
    }
    
    /** collection of transtions
     * 
     */
    ArrayList<Transition> transitions = new ArrayList<>();
    /** current state of the machine
     * 
     */
    String currentState;
    /** all states of hte machine
     * 
     */
    Set<String> states;

    /**
     * oonstructor
     * @param noTapes number of tapes the machine has
     * @param states internal states
     * @param blank blank state on the tapes
     */
    public TuringMachine(int noTapes, ArrayList<String> states, String blank) {
        this.noTapes = noTapes;
        this.states = new HashSet<>();
        for (String s: states){
            this.states.add(s);
        }
        this.tapes = new ArrayList<>();
        for (int i=0; i<noTapes; i++){
            this.tapes.add(new Tape(blank));
        }
    }
    
    /**
     * executes one step of turing machine
     */
    public void next(){
        ArrayList<String> currentSymbols = new ArrayList<>();
        for (int i=0; i<noTapes; i++){
            currentSymbols.add(tapes.get(i).getCurrentSymbol());
        }
            
        
        for (Transition t: transitions)
        {
            if (t.getInitialState().equals(currentState) && 
                    t.getInitialSymbols().equals( currentSymbols ))
            {
                ArrayList<Move> resultMoves = t.getResultMoves();
                ArrayList<String> resultSymbols = t.getResultSymbols();
                
                for (int i = 0; i<noTapes; i++)
                    tapes.get(i).move(resultMoves.get(i), resultSymbols.get(i));
            }
        }
    }
    
    /**
     * sets the contnet of the tapes
     * @param content array of size noTapes containing arrays of tape contents
     */
    public void setTapes(ArrayList<ArrayList<String>> content){
        for (int i =0; i<noTapes; i++){
            tapes.get(i).setContents(content.get(i));
        }
    }
    /**
     * sets the initial state
     * @param initialState state to be set 
     */
    public void setInitialState(String initialState){
        currentState = initialState;
    }
    
    
    /**
     * adding a transition into the machine
     * @param t the transition
     * @throws TMException  in case of any error
     */
    public void addTransition(Transition t) throws TMException{
        if (t.getResultSymbols().size()!= noTapes
                || t.getInitialSymbols().size() != noTapes
                || t.getResultMoves().size() != noTapes){
            throw new TMException("the symbols in the transitions don't match number of tapes of the machine");
        }
        if (!states.contains(t.getInitialState())
                || !states.contains(t.getResultState())){
            throw new TMException("there's no such state in the machine's set ");
        }
        transitions.add(t);
        
    }
    
}
