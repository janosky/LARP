/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestClasses;

import entities.Archtypes;
import entities.Creature;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aejan
 */
public class GetCreatureDataTest {
    
    public GetCreatureDataTest() {
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
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreatureTypes method, of class GetCreatureData.
     */
    @Test
    public void testGetCreatureTypes() {
        System.out.println("getCreatureTypes");
        GetCreatureData instance = new GetCreatureData();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getCreatureTypes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreaturesByType method, of class GetCreatureData.
     */
    @Test
    public void testGetCreaturesByType() {
        System.out.println("getCreaturesByType");
        String creatureType = "";
        GetCreatureData instance = new GetCreatureData();
        ArrayList<Creature> expResult = null;
        ArrayList<Creature> result = instance.getCreaturesByType(creatureType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArchtypesByCreature method, of class GetCreatureData.
     */
    @Test
    public void testGetArchtypesByCreature() {
        System.out.println("getArchtypesByCreature");
        String creatureID = "";
        GetCreatureData instance = new GetCreatureData();
        ArrayList<Archtypes> expResult = null;
        ArrayList<Archtypes> result = instance.getArchtypesByCreature(creatureID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
