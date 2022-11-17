/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pl.polsl.tomasz.michalik.model;

import java.util.ArrayList;

/** class representing tape of the turing machine
 * @version 2.2
 * @author Tomasz Michalik
 */
public class Tape {
    /**
     * constructor
     * @param blank blank symbol of the tape
     */
    public Tape(String blank){
        this.blank = blank;
    }
    
    /**
     * blank symbol
     */
    private String blank;

    
    /**
     * the tape itseld
     */
    private ArrayList<String> contents;
    /**
     * current position on tape
     */
    private int position;

    /**
     * shows the entire tape
     * @return tape contents
     */
    public ArrayList<String> getContents() {
        return contents;
    }

    /**
     * gets posiiton on the tape
     * @return position on the tape counted from tape's beginning
     */
    public int getPosition() {
        return position;
    }

    /**
     * manually sets the tape contents
     * @param contents tape contents
     */
    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }
    
    /**
     * get symbol to which the pointer points
     * @return the current symbol
     */
    public String getCurrentSymbol(){
        if (contents.size()==0){
            contents.add(blank);
        }
        return contents.get(position);
    }
    
    /**
     * tape execution of a movement
     * @param moveDirection direcrtion to move [L/R/N]
     * @param newSymbol symbol to write at the previous position 
     */
    public void move(Move moveDirection, String newSymbol){
        contents.set((position), newSymbol);
        switch(moveDirection){

            case L:
                position -= 1;
                break;
            case R:
                position +=1;
                break;
        }
        
        if (position>=contents.size()){
            //moved past the tape -- correcting
            contents.add(blank);
        }
        else if (position<0){
            //moved bebfore the tape -- correcting
            contents.add(0, blank);
            position = 0;
        }
    }
    
}
