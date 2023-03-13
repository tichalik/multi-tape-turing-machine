package pl.polsl.tomasz.michalik.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import pl.polsl.tomasz.michalik.exceptions.*;

/**
 * class representing the turing machine
 *
 * @author Tomasz Michalik
 * @version 3.0
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

    /**
     * gets the tapes of the turing machine
     *
     * @return tapes of the machine
     */
    public ArrayList<Tape> getTapes() {
        return tapes;
    }

    /**
     * gets the current state of TM
     *
     * @return surrent state of TM
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * collection of transtions
     *
     */
    ArrayList<Transition> transitions = new ArrayList<Transition>();
    /**
     * current state of the machine
     *
     */
    String currentState;
    /**
     * all states of hte machine
     *
     */
    Set<String> states;

    /**
     * dummy constructor
     *
     */
    public TuringMachine() {
        noTapes = 0;
    }

    /**
     * oonstructor
     *
     * @param noTapes number of tapes the machine has
     * @param states internal states
     * @param blank blank state on the tapes
     */
    public TuringMachine(int noTapes, ArrayList<String> states, String blank) {
        this.noTapes = noTapes;
        this.states = new HashSet<String>();
        for (String s : states) {
            this.states.add(s);
        }
        this.tapes = new ArrayList<Tape>();
        for (int i = 0; i < noTapes; i++) {
            this.tapes.add(new Tape(blank));
        }
        this.blank = blank;
    }

    /**
     * executes one step of turing machine
     *
     * @return transition that was used in this step
     * @throws pl.polsl.tomasz.michalik.exceptions.TMException
     */
    public Transition next() throws TMException {
        //getting current symbols
        ArrayList<String> currentSymbols = new ArrayList<String>();
        for (int i = 0; i < noTapes; i++) {
            currentSymbols.add(tapes.get(i).getCurrentSymbol());
        }

        //looking for matching transition
        boolean foundTransition = false;
        for (Transition t : transitions) {
            //if transition found
            if (t.getInitialState().equals(currentState)
                    && t.getInitialSymbols().equals(currentSymbols)) {
                foundTransition = true;

                //updating state of the machine
                currentState = t.getResultState();

                ArrayList<Move> resultMoves = t.getResultMoves();
                ArrayList<String> resultSymbols = t.getResultSymbols();

                for (int i = 0; i < noTapes; i++) {
                    tapes.get(i).move(resultMoves.get(i), resultSymbols.get(i));
                }

                return t;
            }
        }
        if (!foundTransition) {
            throw new TMException("no matching transition could be found "
                    + "for state <" + currentState + "> and symbols " + currentSymbols.toString());
        }

        return null;
    }

    /**
     * sets the contnet of the tapes
     *
     * @param content array of size noTapes containing arrays of tape contents
     */
    public void setTapes(ArrayList<ArrayList<String>> content) {
        for (int i = 0; i < noTapes; i++) {
            tapes.get(i).setContents(content.get(i));
        }
    }

    /**
     * sets the initial state
     *
     * @param initialState state to be set
     */
    public void setInitialState(String initialState) {
        currentState = initialState;
    }

    /**
     * adding a transition into the machine
     *
     * @param t the transition
     * @throws TMException in case of any error
     */
    public void addTransition(Transition t) throws TMException {
        if (t.getResultSymbols().size() != noTapes) {
            throw new TMException("incompatible size of result symbols: "
                    + t.getResultSymbols().size() + " should be " + noTapes);
        }
        if (t.getInitialSymbols().size() != noTapes) {
            throw new TMException("incompatible size of initial symbols: "
                    + t.getInitialSymbols().size() + " should be " + noTapes);
        }
        if (t.getResultMoves().size() != noTapes) {
            throw new TMException("incompatible size of moves: "
                    + t.getResultMoves().size() + " should be " + noTapes);
        }
        if (!states.contains(t.getInitialState())) {
            throw new TMException("there's no"
                    + t.getInitialState() + "state in the machine's set ");
        }
        if (!states.contains(t.getResultState())) {
            throw new TMException("there's no"
                    + t.getResultState() + "state in the machine's set ");
        }
        transitions.add(t);

    }

    /**
     *
     * @return
     */
    public String getBlank() {
        return blank;
    }

}
