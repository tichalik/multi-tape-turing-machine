package pl.polsl.tomasz.michalik.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import static java.util.Collections.addAll;
import java.util.NoSuchElementException;
import java.util.Scanner;
import pl.polsl.tomasz.michalik.exceptions.FileFormatException;
import pl.polsl.tomasz.michalik.exceptions.TMException;
import pl.polsl.tomasz.michalik.model.Move;
import pl.polsl.tomasz.michalik.model.Transition;
import pl.polsl.tomasz.michalik.model.TuringMachine;

/**
 * Reads a turing machine configuration from a given input
 *
 * @author Tomasz Michalik
 * @version 1.2
 */
public class TMReader {

    /**
     * reads a TM from the given string
     *
     * @param input string
     * @return read TM
     * @throws TMException in case the input is wrong
     * @throws pl.polsl.tomasz.michalik.exceptions.FileFormatException something
     * went wrong with the file
     */
    public TuringMachine readTMFromString(String input) throws TMException, FileFormatException {
        //parameters for the ruring machine
        int noTapes = 0;
        ArrayList<String> states = new ArrayList<String>();
        //dummy initialization
        String machineInitialState;
        //dummy initialization
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<ArrayList<String>>();
        ArrayList<Transition> transitions = new ArrayList<Transition>();
        String blank;

        try {
            Scanner scanner = new Scanner(input);

            //first line in the file -- number of tapes
            String sNoTapes = scanner.nextLine();
            if (sNoTapes.isBlank()) {
                throw new FileFormatException("no number of tapes specified");
            }
            try {
                noTapes = Integer.parseInt(sNoTapes);
            } catch (NumberFormatException ex) {
                throw new FileFormatException("Failed to convert <<" + sNoTapes + ">> to integer");
            }
            if (noTapes <= 0) {
                throw new FileFormatException("tape number cannot be lesser or equal 0");
            }

            //second line in the file -- internal states
            addAll(states, scanner.nextLine().split(" "));
            if (states.isEmpty()) {
                throw new FileFormatException("no internal states specified");
            }

            //third line in the file -- initial state
            machineInitialState = scanner.nextLine();
            if (machineInitialState.isEmpty()) {
                throw new FileFormatException("no initial state specified");
            } else if (machineInitialState.contains(" ")) {
                throw new FileFormatException("initial state must be one symbol");
            }

            //fourth line in the file -- blank symbol
            blank = scanner.nextLine();
            if (blank.isEmpty()) {
                throw new FileFormatException("no blank symbol specified");
            } else if (blank.contains(" ")) {
                throw new FileFormatException("blank must be one symbol");
            }

            TuringMachine tM = new TuringMachine(noTapes, states, blank);
            tM.setInitialState(machineInitialState);

            //reading the tape contents
            int maxLineSize = 0;
            for (int i = 0; i < noTapes; i++) {
                if (!scanner.hasNextLine()) {
                    throw new FileFormatException("undefined tape contents for tape"
                            + i + ". if you wish to enter empty tape, use empty line");
                }
                ArrayList<String> tmp = new ArrayList<String>();
                addAll(tmp, scanner.nextLine().split(" "));
                tapeContents.add(tmp);

            }

            tM.setTapes(tapeContents);

            //reading the transitions
            while (scanner.hasNextLine()) {
                Scanner tokens = new Scanner(scanner.nextLine());

                //the first position is the initial state
                String initialState = tokens.next();
                //then theres the symbols on the tapes 
                ArrayList<String> initialSymbols = new ArrayList<String>();
                for (int i = 0; i < noTapes; i++) {
                    initialSymbols.add(tokens.next());
                }
                //the resulting state of the machine 
                String resultState = tokens.next();
                //the resulting symbols on the tapes
                ArrayList<String> resultSymbols = new ArrayList<String>();
                for (int i = 0; i < noTapes; i++) {
                    resultSymbols.add(tokens.next());
                }
                //teh movements of the heads
                ArrayList<Move> resultMoves = new ArrayList<Move>();
                try {
                    for (int i = 0; i < noTapes; i++) {
                        resultMoves.add(Move.valueOf(tokens.next()));
                    }
                } catch (IllegalArgumentException ex) {
                    throw new FileFormatException("bad movement - not L/N/R");
                }

                try {
                    tM.addTransition(new Transition(noTapes, initialState, initialSymbols,
                            resultState, resultSymbols, resultMoves));
                } catch (TMException e) {
                    throw e;
                }

            }
            scanner.close();

            return tM;
        } catch (NoSuchElementException ex) {
            throw new FileFormatException("some lines are missing");
        }
    }

    /**
     * reads a TM from the given file used for tests
     *
     * @param filename path to the file
     * @return read TM
     * @throws TMException in case the input is wrong
     * @throws pl.polsl.tomasz.michalik.exceptions.FileFormatException something
     * went wrong with the file
     */
    public TuringMachine readTMFromFile(String filename) throws TMException, FileFormatException {
        //parameters for the ruring machine
        int noTapes = 0;
        ArrayList<String> states = new ArrayList<>();
        //dummy initialization
        String machineInitialState;
        //dummy initialization
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        ArrayList<Transition> transitions = new ArrayList<>();
        String blank;

        try {
            File f = new File(filename);
            if (!f.exists()) {
                throw new FileFormatException("no such file: " + filename);
            }

            Scanner scanner = new Scanner(f);

            //first line in the file -- number of tapes
            String sNoTapes = scanner.nextLine();
            if (sNoTapes.isBlank()) {
                throw new FileFormatException("no number of tapes specified");
            }
            try {
                noTapes = Integer.parseInt(sNoTapes);
            } catch (NumberFormatException ex) {
                throw new FileFormatException("Failed to convert <<" + sNoTapes + ">> to integer");
            }
            if (noTapes <= 0) {
                throw new FileFormatException("tape number cannot be lesser or equal 0");
            }

            //second line in the file -- internal states
            addAll(states, scanner.nextLine().split(" "));
            if (states.isEmpty()) {
                throw new FileFormatException("no internal states specified");
            }

            //third line in the file -- initial state
            machineInitialState = scanner.nextLine();
            if (machineInitialState.isEmpty()) {
                throw new FileFormatException("no initial state specified");
            } else if (machineInitialState.contains(" ")) {
                throw new FileFormatException("initial state must be one symbol");
            }

            //fourth line in the file -- blank symbol
            blank = scanner.nextLine();
            if (blank.isEmpty()) {
                throw new FileFormatException("no blank symbol specified");
            } else if (blank.contains(" ")) {
                throw new FileFormatException("blank must be one symbol");
            }

            TuringMachine tM = new TuringMachine(noTapes, states, blank);
            tM.setInitialState(machineInitialState);

            //reading the tape contents
            int maxLineSize = 0;
            for (int i = 0; i < noTapes; i++) {
                if (!scanner.hasNextLine()) {
                    throw new FileFormatException("undefined tape contents for tape"
                            + i + ". if you wish to enter empty tape, use empty line");
                }
                ArrayList<String> tmp = new ArrayList<>();
                addAll(tmp, scanner.nextLine().split(" "));
                tapeContents.add(tmp);

            }

            tM.setTapes(tapeContents);

            //reading the transitions
            while (scanner.hasNextLine()) {
                Scanner tokens = new Scanner(scanner.nextLine());

                //the first position is the initial state
                String initialState = tokens.next();
                //then theres the symbols on the tapes 
                ArrayList<String> initialSymbols = new ArrayList<>();
                for (int i = 0; i < noTapes; i++) {
                    initialSymbols.add(tokens.next());
                }
                //the resulting state of the machine 
                String resultState = tokens.next();
                //the resulting symbols on the tapes
                ArrayList<String> resultSymbols = new ArrayList<>();
                for (int i = 0; i < noTapes; i++) {
                    resultSymbols.add(tokens.next());
                }
                //teh movements of the heads
                ArrayList<Move> resultMoves = new ArrayList<>();
                try {
                    for (int i = 0; i < noTapes; i++) {
                        resultMoves.add(Move.valueOf(tokens.next()));
                    }
                } catch (IllegalArgumentException ex) {
                    throw new FileFormatException("bad movement - not L/N/R");
                }

                try {
                    tM.addTransition(new Transition(noTapes, initialState, initialSymbols,
                            resultState, resultSymbols, resultMoves));
                } catch (TMException e) {
                    throw e;
                }

            }
            scanner.close();

            return tM;
        } catch (FileNotFoundException ex) {
            //do nothing - we manually check for existence of the file
        } catch (NoSuchElementException ex) {
            throw new FileFormatException("some lines are missing");
        }
        return null;
    }

}
