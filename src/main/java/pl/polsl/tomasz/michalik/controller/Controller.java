package pl.polsl.tomasz.michalik.controller;

import java.util.ArrayList;
import pl.polsl.tomasz.michalik.exceptions.*;
import pl.polsl.tomasz.michalik.model.TuringMachine;
import pl.polsl.tomasz.michalik.view.GUI;

/**
 * the controller class, has the app;s main method
 *
 * @author Tomasz Michalik
 * @version 3.0
 */
public class Controller {

    private TuringMachine turingMachine;
    private GUI tmGUI;

    /**
     * main method starting the controller
     *
     * @param args path to intput file
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.initializeGUI();
//        controller.loadMachine(args[0]);
    }

    /**
     * loads new turing machine
     *
     * @param filepath path to file containing the machines's specification
     */
    public void loadMachine(String filepath) {
        TMReader tmr = new TMReader();

        try {
            turingMachine = tmr.readTMFromFile(filepath);
        } catch (Exception ex) {
            tmGUI.reporError(ex.getMessage());
            return;
        }

        tmGUI.setBlank(turingMachine.getBlank());
        tmGUI.showCurrentState(turingMachine.getCurrentState());
        tmGUI.showTapes(turingMachine.getTapes());

    }

    /**
     * executes new move in the machine
     *
     * @param steps number of moves to make
     */
    public void next(int steps) {
        if (turingMachine != null) {
            for (int i = 0; i < steps; i++) {
                try {
                    turingMachine.next();
                } catch (TMException ex) {
                    tmGUI.reporError(ex.getMessage());
                    return;
                }
                tmGUI.showCurrentState(turingMachine.getCurrentState());
                tmGUI.showTapes(turingMachine.getTapes());
            }

        } else {
            tmGUI.reporError("no machine has been loaded yet!");
        }

    }

    /**
     * initializes GUI
     */
    private void initializeGUI() {
        tmGUI = new GUI(this);
        tmGUI.setBlank("undefined");
        tmGUI.showTapes(new ArrayList<>());
        tmGUI.showCurrentState("undefined");
        tmGUI.run();
    }

}
