package pl.polsl.tomasz.michalik.view;

import java.util.ArrayList;
import pl.polsl.tomasz.michalik.model.Tape;

/**
 * console interface
 *
 * @version 2.0
 * @author Tomasz Michalik
 */
public class TuringMachineView {

    /**
     * shows the tapes of turing machine
     *
     * @param tapes tapes of the turing machine
     */
    public void showTapes(ArrayList<Tape> tapes) {
        for (Tape t : tapes) {
            //printing empty spacces before the pointer
            for (int i = 0; i < t.getPosition(); i++) {
                System.out.print("\t");
            }
            System.out.print(" v \n");
            ArrayList<String> contents = t.getContents();
            for (String s : contents) {
                System.out.print(s + "\t");
            }
            System.out.print("\n");
        }

    }

    /**
     * shows current state
     *
     * @param state state of the turing machine
     */
    public void showCurrentState(String state) {
        System.out.println("current state: " + state);
    }

}
