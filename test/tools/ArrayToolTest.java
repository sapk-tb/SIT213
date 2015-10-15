package tools;

import information.Information;
import tools.Tool;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Antoine GIRARD
 * @author Cédric HERZOG
 */
public class ArrayToolTest {

    public ArrayToolTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testSumArraysInformations(){
    	
        Float bits[] = {-1f, 2f, 0f};
    	Information<Float> informationInitiale = new Information<>(bits);
        Float bitsT1[] = {1f, -2f, 0f};
        Information<Float> informationT1 = new Information<>(bitsT1);
        Float bitsResult[] = {0f, 0f, 0f};
        Information<Float>informationResult = new Information<>(bitsResult);
        
        Information<Float>informationTotale = ArrayTool.sumArrays(informationInitiale, informationT1);
      
        assertEquals(informationResult, informationTotale);
        
    }
    @Test
    public void testSumArraysTableaux(){
    	
        float bits[] = {-1f, 2f, 0f};
        float bitsT1[] = {1f, -2f, 0f};
        float bitsResult[] = {0f, 0f, 0f};
        float resultat[] = ArrayTool.sumArrays(bits, bitsT1);

        assertEquals(bitsResult.equals(resultat), true);
        
    }
    
    
}
