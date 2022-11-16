package pl.polsl.tomasz.michalik.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.tomasz.michalik.controller.TMReader;
import pl.polsl.tomasz.michalik.exceptions.TMException;
import pl.polsl.tomasz.michalik.model.TuringMachine;

/**
 *
 * @author huawei
 */
public class TMReaderTest {
    
//    TMReader tmr;
    
    public TMReaderTest() {
        
    }

    /**
     * Test of readTMFromFile method, of class TMReader.Various files included, with these it should not fail
     * @param filename name of the file
     */
    @ParameterizedTest
    @ValueSource (strings = {"input.txt", "good1.txt", "good2.txt", "good3.txt"})
    public void testGoodReadTMFromFile(String filename)  {
        //GIVEN
        TMReader tmr = new TMReader();
                
        try{
            TuringMachine  tm = tmr.readTMFromFile(filename);
        }catch(TMException ex){
            fail("it should throw no exceptions");
        }catch (Exception ex){
            fail("iit should throw no exceptions");
        }
    }

    /**
     * Test of readTMFromFile method, of class TMReader.Various files included, with these it should not fail
     * @param filename name of the file
     */
    @ParameterizedTest
    @ValueSource (strings = {"bad1.txt", "bad2.txt", "bad3.txt"})
    public void testBadReadTMFromFile(String filename) {
        //GIVEN
        TMReader tmr = new TMReader();
                
        try{
            TuringMachine  tm = tmr.readTMFromFile(filename);
        }catch(TMException ex){
            
        }catch (Exception ex){
            fail("it should have not thrown TMException");
        }
        fail("it should throw exceptions");
    }
   
}
