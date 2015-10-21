package tools;

import information.Information;

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
    	
        Double bits[] = {-1.0, 2.0, 0.0};
    	Information<Double> informationInitiale = new Information<>(bits);
        Double bitsT1[] = {1.0, -2.0, 0.0};
        Information<Double> informationT1 = new Information<>(bitsT1);
        Double bitsResult[] = {0.0, 0.0, 0.0};
        Information<Double>informationResult = new Information<>(bitsResult);
        
        Information<Double>informationTotale = ArrayTool.sumArrays(informationInitiale, informationT1);
      
        assertEquals(informationResult, informationTotale);
        
    }
    @Test
    public void testSumArraysTableaux(){
    	
        double bits[] = {-1f, 2f, 0f};
        double bitsT1[] = {1f, -2f, 0f};
        double bitsResult[] = {0f, 0f, 0f};
        double resultat[] = ArrayTool.sumArrays(bits, bitsT1);

        assertArrayEquals(resultat,bitsResult, 0.0f);
        
    }
    
    @Test
    public void testSumArraysFacto(){
    	
        Double bits[] = {2.0, -2.0, 0.0};
        double facteur = 0.5f;
        Double bitsResult[] = {1.0, -1.0, 0.0};
        Information<Double>info = new Information<>(bits);
        Information<Double>resultatAttendu = new Information<>(bitsResult);
        Information<Double>resultat = ArrayTool.factArrays(info, facteur);
        
        assertEquals(resultatAttendu, resultat);
    }
    
    
}
