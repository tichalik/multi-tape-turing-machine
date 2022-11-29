package pl.polsl.tomasz.michalik.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.tomasz.michalik.exceptions.FileFormatException;
import pl.polsl.tomasz.michalik.exceptions.TMException;
import pl.polsl.tomasz.michalik.model.TuringMachine;

/**
 * class for checking whether the reader reports errors as it should
 *
 * @author Tomasz Michalik
 * @version 1.2
 */
public class TMReaderTest {

    public TMReaderTest() {

    }

    /**
     * Test of readTMFromFile method, of class TMReader.Various files included,
     * with these it should not fail
     *
     * @param fileId number of the file; read files are
     * "input_files/good"+fileId+".txt"
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void testGoodReadTMFromFile(int fileId) {
        //GIVEN
        TMReader tmr = new TMReader();

        try {
            TuringMachine tm = tmr.readTMFromFile("input_files\\good" + fileId + ".txt");
        } catch (Exception ex) {
            fail("iit should throw no exceptions");
        }
    }

    /**
     * Test of readTMFromFile method, of class TMReader.Various files included,
     * with these it should not fail
     *
     * @param fileId number of the file; read files are
     * "input_files/bad"+fileId+".txt"
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    public void testBadReadTMFromFile(int fileId) {
        //GIVEN
        TMReader tmr = new TMReader();

        try {
            TuringMachine tm = tmr.readTMFromFile("input_files\\bad" + fileId + ".txt");
        } catch (TMException ex) {

        } catch (FileFormatException ex) {

        } catch (Exception ex) {
            fail("it should have thrown TMException, got " + ex.getMessage());
        }
    }

}
