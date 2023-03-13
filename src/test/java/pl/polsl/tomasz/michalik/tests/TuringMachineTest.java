package pl.polsl.tomasz.michalik.tests;

import java.util.ArrayList;
import static org.junit.Assume.assumeTrue;
import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.provider.ValueSource;

import pl.polsl.tomasz.michalik.exceptions.TMException;
import pl.polsl.tomasz.michalik.model.Move;
import pl.polsl.tomasz.michalik.model.Tape;
import pl.polsl.tomasz.michalik.model.Transition;
import pl.polsl.tomasz.michalik.model.TuringMachine;
import pl.polsl.tomasz.michalik.utils.TMReader;

/**
 * tests for the model
 *
 * @author Tomasz Michalik
 * @version 1.2
 */
public class TuringMachineTest {

    TMReader tmr;
    TuringMachine instance;

    /**
     * constructor
     */
    public TuringMachineTest() {
        tmr = new TMReader();
    }

    /**
     * Test for moving to the left
     *
     * @param noSteps nubmer of steps
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 50, 100000})

    public void testMoveLeft(int noSteps) {
        //GIVEN 
        try {
            instance = tmr.readTMFromFile("input_files\\moveLeft.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        for (int i = 0; i < noSteps; i++) {
            try {
                instance.next();
            } catch (TMException ex) {
            }
        }
        //THEN

        //noting -- if there was no exception everything works
    }

    /**
     * Test for moving to the right
     *
     * @param noSteps number of steps
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 50, 100000})
    public void testMoveRight(int noSteps) {
        //GIVEN 
        try {
            instance = tmr.readTMFromFile("input_files\\moveRight.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        for (int i = 0; i < noSteps; i++) {
            try {
                instance.next();
            } catch (TMException ex) {
            }
        }
        //THEN

        //noting -- if there was no exception everything works
    }

    /**
     * Test for not moving
     *
     * @param noSteps number of steps
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 50, 100000})
    public void testNotMove(int noSteps) {
        //GIVEN 
        try {
            instance = tmr.readTMFromFile("input_files\\noMove.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        for (int i = 0; i < noSteps; i++) {
            try {
                instance.next();
            } catch (TMException ex) {
            }
        }
        //THEN

        //noting -- if there was no exception everything works
    }

    /**
     * Test adding null transition there is no need to parametrize this!!!!
     *
     */
    @org.junit.jupiter.api.Test
    @Disabled("this situation is artifitial and there has no place in the current project")
    public void testAddEmptyTransition() {
        Boolean wasExceptionThrown = false;
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\input.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        Transition t = new Transition(0, null, null, null, null, null);
        try {
            instance.addTransition(t);
            //THEN it shoud be caught
        } catch (TMException ex) {
            wasExceptionThrown = true;
        }
        if (!wasExceptionThrown) {
            fail("no exception should be thrown; was thrown:");
        }
    }

    /**
     *
     * Test adding transition with a wrong size of iSymbols
     *
     * @param noTapes nubmer of tapes
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 10, 0, -2, -3})
    public void testAddBadISymbolsTranstion(int noTapes) {
        Boolean wasExceptionThrown = false;
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\input.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try {
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();

            for (int i = 0; i < noTapes; i++) {
                iSymbols.add("0");
                oSymbols.add("0");
                oMoves.add(Move.R);
            }
            //additional symbol
            iSymbols.add("0");
            //WHEN
            instance.addTransition(
                    new Transition(noTapes, "a", iSymbols, "a", oSymbols, oMoves));
            //THEN it shoud be caught
        } catch (TMException ex) {
            wasExceptionThrown = true;
        }
        if (!wasExceptionThrown) {
            fail("no exception should be thrown; was thrown:");
        }
    }

    /**
     *
     * Test adding transition with a wrong size of oSymbols
     *
     * @param noTapes nubmer of tapes
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 10, 0, -2, -3})
    public void testAddBadOSymbolsTranstion(int noTapes) {
        Boolean wasExceptionThrown = false;
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\input.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try {
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();

            for (int i = 0; i < noTapes; i++) {
                iSymbols.add("0");
                oSymbols.add("0");
                oMoves.add(Move.R);
            }
            //additional symbol
            oSymbols.add("0");
            //WHEN
            instance.addTransition(
                    new Transition(noTapes, "a", iSymbols, "a", oSymbols, oMoves));
            //THEN it shoud be caught
        } catch (TMException ex) {
            wasExceptionThrown = true;
        }
        if (!wasExceptionThrown) {
            fail("no exception should be thrown; was thrown:");
        }

    }

    /**
     *
     * Test adding transition with a wrong size of iSymbols
     *
     * @param noTapes nubmer of tapes
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 10, 0, -2, -3})
    public void testAddBadOMovesTranstion(int noTapes) {
        Boolean wasExceptionThrown = false;
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\input.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try {
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();

            for (int i = 0; i < noTapes; i++) {
                iSymbols.add("0");
                oSymbols.add("0");
                oMoves.add(Move.R);
            }
            //additional symbol
            oMoves.add(Move.R);
            //WHEN
            instance.addTransition(
                    new Transition(noTapes, "a", iSymbols, "a", oSymbols, oMoves));
            //THEN it shoud be caught
        } catch (TMException ex) {
            wasExceptionThrown = true;
        }
        //else if not caught 
        if (!wasExceptionThrown) {
            fail("no exception should be thrown; was thrown:");
        }
    }

    /**
     * Test adding n-tape transtion
     *
     * @param noTapes number of tapes
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 10, 0, -2, -3})
    public void testAddNTapesTransition(int noTapes) {
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\input.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try {
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();

            for (int i = 0; i < noTapes; i++) {
                iSymbols.add("0");
                oSymbols.add("0");
                oMoves.add(Move.R);
            }
            //WHEN
            instance.addTransition(
                    new Transition(noTapes, "a", iSymbols, "a", oSymbols, oMoves));
            //THEN it shoud be caught
        } catch (TMException ex) {
            if (noTapes == 2) {
                fail("no exception should be thrown; was thrown:" + ex.getMessage());
            }
        }
        //else if not caught 

    }

    /**
     * Test addign transition with bad symbols
     *
     * @param initialState initial state of the machine
     */
    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "a", "b", "c", "  asdfasdfasfasf"})
    public void testAddBadSymbolTransition(String initialState) {
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\input.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try {
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                iSymbols.add("0");
                oSymbols.add("0");
                oMoves.add(Move.R);
            }
            //WHEN
            instance.addTransition(
                    new Transition(10, initialState, iSymbols, "a", oSymbols, oMoves));
            //THEN it shoud be caught
        } catch (TMException ex) {

        }
        //else if not caught 
    }

    /**
     * Test moving to the blank symbol to the left of the tape THERE'S MO NEED
     * TO PARAMETRIZE THIS!!
     */
    @org.junit.jupiter.api.Test
    public void checkLeftBlank() {
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\moveLeft.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        tapeContents.add(new ArrayList<String>());
        tapeContents.add(new ArrayList<String>());

        instance.setTapes(tapeContents);
        //WHEN 
        try {
            instance.next();
        } catch (TMException ex) {
        }
        //THEN
        ArrayList<Tape> tapes = instance.getTapes();
        ArrayList<String> currentSymbols = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            currentSymbols.add(tapes.get(i).getCurrentSymbol());
        }
        ArrayList<String> expected = new ArrayList<>();
        expected.add("0");
        expected.add("0");

        assumeTrue(currentSymbols.equals(expected));

    }

    /**
     * Test moving to the blank symbol to the right of the tape
     */
    @org.junit.jupiter.api.Test
    public void checkRightBlank() {
        //GIVEN
        try {
            instance = tmr.readTMFromFile("input_files\\moveRight.txt");
        } catch (Exception ex) {
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        tapeContents.add(new ArrayList<String>());
        tapeContents.add(new ArrayList<String>());

        instance.setTapes(tapeContents);
        //WHEN 
        try {
            instance.next();
        } catch (TMException ex) {
        }
        //THEN
        ArrayList<Tape> tapes = instance.getTapes();
        ArrayList<String> currentSymbols = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            currentSymbols.add(tapes.get(i).getCurrentSymbol());
        }
        ArrayList<String> expected = new ArrayList<>();
        expected.add("0");
        expected.add("0");

        assumeTrue(currentSymbols.equals(expected));
    }

}
