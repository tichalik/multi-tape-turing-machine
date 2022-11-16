/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.tomasz.michalik.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import  org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.tomasz.michalik.controller.TMReader;
import pl.polsl.tomasz.michalik.exceptions.TMException;

/**
 *
 * @author huawei
 */
public class TuringMachineTest {
    
    TMReader tmr;
    TuringMachine instance;
    public TuringMachineTest() {
        tmr = new TMReader();
    }
    
    /**
     * this is a test-test
     * to show that for some reason the tests do not work
     */
    @Test
    public void test()
    {
        fail();
    }
    
    
    /**
     * Test for moving to the left
     * @param noSteps nubmer of steps
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,3,10,50,100000})
    
    public void testMoveLeft(int noSteps) {
        //GIVEN 
        try{
            instance = tmr.readTMFromFile("moveLeft.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        for (int i =0; i<noSteps; i++){
            instance.next();
        }
        //THEN
        
        //noting -- if there was no exception everything works
    }
    
    /**
     * Test for moving to the right
     * @param noSteps number of steps
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,3,10,50,100000})
    public void testMoveRight(int noSteps) {
        //GIVEN 
        try{
            instance = tmr.readTMFromFile("moveRight.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        for (int i =0; i<noSteps; i++){
            instance.next();
        }
        //THEN
        
        //noting -- if there was no exception everything works
    }

    /**
     * Test for not moving 
     * @param noSteps number of steps
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,3,10,50,100000})
    public void testNotMove(int noSteps) {
        //GIVEN 
        try{
            instance = tmr.readTMFromFile("noMove.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        for (int i =0; i<noSteps; i++){
            instance.next();
        }
        //THEN
        
        //noting -- if there was no exception everything works
    }
    
    /**
     * Test adding null transition
     * there is no need to parametrize this!!!!
     * 
     */
    @org.junit.jupiter.api.Test
    public void testAddEmptyTransition() {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("input.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        //WHEN
        Transition t  = new Transition(0,null, null, null, null, null);
        try{
            instance.addTransition(t);
           //THEN it shoud be caught
        }catch (TMException ex ){
            
        }
        //else if not caught 
        fail("no exception was thrown");
        
    }
    
    /**
     * 
     * Test adding transition with a wrong size of iSymbols
     * @param noTapes nubmer of tapes
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,4,10,0,-2,-3})
    public void testAddBadISymbolsTranstion(int noTapes) {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("input.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try{
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();
            
            for (int i =0; i<noTapes; i++){
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
        }catch (TMException ex ){
            
        }
        //else if not caught 
        fail("no exception was thrown");
        
    }
    
    
    /**
     * 
     * Test adding transition with a wrong size of oSymbols
     * @param noTapes nubmer of tapes
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,4,10,0,-2,-3})
    public void testAddBadOSymbolsTranstion(int noTapes) {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("input.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try{
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();
            
            for (int i =0; i<noTapes; i++){
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
        }catch (TMException ex ){
            
        }
        //else if not caught 
        fail("no exception was thrown");
        
    }
    
    /**
     * 
     * Test adding transition with a wrong size of iSymbols
     * @param noTapes nubmer of tapes
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,4,10,0,-2,-3})
    public void testAddBadOMovesTranstion(int noTapes) {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("input.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try{
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();
            
            for (int i =0; i<noTapes; i++){
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
        }catch (TMException ex ){
            
        }
        //else if not caught 
        fail("no exception was thrown");
        
    }
    
    
    
    
    /**
     * Test adding n-tape transtion
     * @param noTapes number of tapes
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,4,10,0,-2,-3})
    public void testAddNTapesTransition(int noTapes) {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("input.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try{
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();
            
            for (int i =0; i<noTapes; i++){
                iSymbols.add("0");
                oSymbols.add("0");
                oMoves.add(Move.R);
            }
            //WHEN
            instance.addTransition(
                    new Transition(noTapes, "a", iSymbols, "a", oSymbols, oMoves));
           //THEN it shoud be caught
        }catch (TMException ex ){
            if (noTapes>0){
                fail("no exception should be thrown");
            }
        }
        //else if not caught 
        
    }
    /**
     * Test addign transition with bad symbols
     * @param initialState initial state of the machine
     */
    @ParameterizedTest
    @ValueSource (strings = {"0","1", "a","b","c", "  asdfasdfasfasf"})
    public void testAddBadSymbolTransition(String initialState) {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("input.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        try{
            ArrayList<String> iSymbols = new ArrayList<>();
            ArrayList<String> oSymbols = new ArrayList<>();
            ArrayList<Move> oMoves = new ArrayList<>();
            
            for (int i =0; i<10; i++){
                iSymbols.add("0");
                oSymbols.add("0");
                oMoves.add(Move.R);
            }
            //WHEN
            instance.addTransition(
                    new Transition(10, initialState, iSymbols, "a", oSymbols, oMoves));
           //THEN it shoud be caught
        }catch (TMException ex ){
            
        }
        //else if not caught 
    }
    
    
    /**
     * Test moving to the blank symbol to the left of the tape
     * THERE'S MO NEED TO PARAMETRIZE THIS!!
     */
    @org.junit.jupiter.api.Test
    public void checkLeftBlank() {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("moveLeft.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        tapeContents.add(new ArrayList<>());
        tapeContents.add(new ArrayList<>());
        
        instance.setTapes(tapeContents);
        //WHEN 
        instance.next();
        //THEN
        ArrayList<Tape> tapes = instance.getTapes();
        ArrayList<String> currentSymbols = new ArrayList<>();
        for (int i=0; i<2; i++){
            currentSymbols.add(tapes.get(i).getCurrentSymbol());
        }
        ArrayList<String> expected = new ArrayList<>();
        expected.add("0");
        expected.add("0");
        
        assumeTrue(currentSymbols.equals(expected ));
        
    }
    
    /**
     * Test moving to the blank symbol to the right of the tape
     */
    @org.junit.jupiter.api.Test
    public void checkRightBlank() {
        //GIVEN
        try{
            instance = tmr.readTMFromFile("moveRight.txt");
        }
        catch (FileNotFoundException ex){
            fail("something went wrong with the test itsllf, check for filename");
        }
        catch (TMException ex){
            fail("something went wrong with hte test tislef, the input file has bugs");
        }
        ArrayList<ArrayList<String>> tapeContents = new ArrayList<>();
        tapeContents.add(new ArrayList<>());
        tapeContents.add(new ArrayList<>());
        
        instance.setTapes(tapeContents);
        //WHEN 
        instance.next();
        //THEN
        ArrayList<Tape> tapes = instance.getTapes();
        ArrayList<String> currentSymbols = new ArrayList<>();
        for (int i=0; i<2; i++){
            currentSymbols.add(tapes.get(i).getCurrentSymbol());
        }
        ArrayList<String> expected = new ArrayList<>();
        expected.add("0");
        expected.add("0");
        
        assumeTrue(currentSymbols.equals(expected ));
    }
    
}
