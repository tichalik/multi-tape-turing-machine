package pl.polsl.tomasz.michalik.model;

import java.util.ArrayList;

/**
 * class representing unit transtion of the turing machine
 *
 * @version 2.0
 * @author Tomasz Michalik
 */
public class Transition {

    /*
    * number of tapes specific to the machine
     */
    private final int noTapes;

    /*
    * input state
     */
    private final String initialState;
    /*
    * input symbols; their lenght must be equal to no_tapes
     */
    private final ArrayList<String> initialSymbols;
    /*
    * state to transition to 
     */
    private final String resultState;
    /*
    * symbols to write on on the consecutive tapes; their lenght must be equal to no_tapes
     */
    private final ArrayList<String> resultSymbols;

    /*
    * moves the consecutive heads make; their lenght must be equal to no_tapes
     */
    private final ArrayList<Move> resultMoves;

    /**
     * constructor for the transition
     *
     * @param noTapes when number of tapes the machine operates on
     * @param iState initial state
     * @param iSymbols initial configuration of symbols on the tapes
     * @param oState resulting state
     * @param oSymbols resulting configuration of symbols on the tapes
     * @param oMoves resulting moves of the head
     */
    public Transition(int noTapes, String iState, ArrayList<String> iSymbols,
            String oState, ArrayList<String> oSymbols, ArrayList<Move> oMoves) {
        this.noTapes = noTapes;
        //exceptions here!!
        initialState = iState;
        initialSymbols = iSymbols;
        resultState = oState;
        resultSymbols = oSymbols;
        resultMoves = oMoves;

    }

    /**
     * getter for the result state
     *
     * @return result state
     */
    public String getResultState() {
        return resultState;
    }

    /**
     * getter for the result symbols
     *
     * @return result symbols
     */
    public ArrayList<String> getResultSymbols() {
        return resultSymbols;
    }

    /**
     * getter for the result moves
     *
     * @return result moves
     */
    public ArrayList<Move> getResultMoves() {
        return resultMoves;
    }

    /**
     * getter for initial state
     *
     * @return initial state
     */
    public String getInitialState() {
        return initialState;
    }

    /**
     * getter for initial symbols
     *
     * @return initial symbols
     */
    public ArrayList<String> getInitialSymbols() {
        return initialSymbols;
    }
    
    @Override
    public String toString(){
        String result = new String();
        result = "(" + initialState + ", [";
        for (String s: initialSymbols){
            result += s + ",";
        }
        result = result.substring(0, result.length()-2);
        result += "]) -> (" + resultState + ", [";
        
        for (String s: resultSymbols){
            result += s + ",";
        }
        
        result = result.substring(0, result.length()-2);
        result += "])";
        
        return result;
                
    }

}
