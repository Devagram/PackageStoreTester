  

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/** 
* ShippingStore Tester. 
* 
* @author Thomas Anderson
* @since <pre>Nov 2, 2017</pre> 
* @version 1.0 
*/ 

public class ShippingStoreTest { 
    public static ShippingStore shippingstore = null; 
    public static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    public static PrintStream stdout = System.out;
    public static String separator = System.getProperty("line.separator");

    @Before
    public void before() throws Exception {
        shippingstore = new ShippingStore();
        shippingstore.addOrder("00001", "Postcard", "Fragile", "First-Class", "1.0", "1");
        shippingstore.addOrder("00002", "Postcard", "Fragile", "First-Class", "2.0", "2");
        shippingstore.addOrder("00003", "Postcard", "Fragile", "First-Class", "3.0", "3");
        System.out.println("Set Up Before");
    } 

    @After
    public void after() throws Exception { 
        shippingstore = null;
        System.out.println("Cleared After");
    } 
    
    /** 
    * 
    * Method: getDataFile() 
    * Tests to see if File returned matches expected
    */ 
    @Test
    public void testGetDataFile() throws Exception { 
       //TODO: Test goes here...
       File result = shippingstore.getDataFile();
       File expected = new File("PackageOrderDB.txt");
       assertEquals(expected, result);
    } 

    /** 
    * 
    * Method: showPackageOrders() 
    * Tests output with test arraylist and compares to expected
    */ 
    @Test
    public void testShowPackageOrders() throws Exception { 
        //TODO: Test goes here... 
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        String expected = " -------------------------------------------------------------------------- " + separator +
                          "| Tracking # | Type    | Specification | Class       | Weight(oz) | Volume |" + separator +
                          " -------------------------------------------------------------------------- " + separator +
                          "| 00001      | Postcard| Fragile       | First-Class | 1.00       | 1      |" + separator +
                          "| 00002      | Postcard| Fragile       | First-Class | 2.00       | 2      |" + separator +
                          "| 00003      | Postcard| Fragile       | First-Class | 3.00       | 3      |" + separator +
                          " --------------------------------------------------------------------------"+ separator + separator;
        shippingstore.showPackageOrders();
        assertEquals(expected, outContent.toString());
        System.setOut(stdout);
    } 

    /** 
    * 
    * Method: showPackageOrdersRange(float low, float high) 
    * Tests for a failed range and successful, compares output to expected
    */ 
    @Test
    public void testShowPackageOrdersRange() throws Exception { 
        //TODO: Test goes here... 
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        String expected = " -------------------------------------------------------------------------- " + separator +
                          "| Tracking # | Type    | Specification | Class       | Weight(oz) | Volume |" + separator +
                          " -------------------------------------------------------------------------- " + separator +
                          "| 00001      | Postcard| Fragile       | First-Class | 1.00       | 1      |" + separator +
                          "| 00002      | Postcard| Fragile       | First-Class | 2.00       | 2      |" + separator +
                          "| 00003      | Postcard| Fragile       | First-Class | 3.00       | 3      |" + separator +
                          " --------------------------------------------------------------------------" + separator + separator;
        shippingstore.showPackageOrdersRange(0.1f, 4.0f);
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "No packages found with weight within the given range." + separator + separator;
        shippingstore.showPackageOrdersRange(0.1f, 0.5f);
        assertEquals(expected, outContent.toString());
        System.setOut(stdout);
    } 

    /** 
    * 
    * Method: findPackageOrder(String trackingNumber) 
    *  Compares where package was found with expected, tests on none existent package.
    */ 
    @Test
    public void testFindPackageOrder() throws Exception { 
        //TODO: Test goes here... 
        
        int result = shippingstore.findPackageOrder("00001");
        assertEquals(0, result);
        result = shippingstore.findPackageOrder("00008");
        assertEquals(-1, result);
    } 

    /** 
    * 
    * Method: searchPackageOrder(String trackingNumber) 
    * tests every case for bad inputs(none existent, each field), compares expected outputs
    */ 
    @Test
    public void testSearchPackageOrder() throws Exception { 
        //TODO: Test goes here... 
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        String expected = separator + "Here is the order that matched:" + separator + separator +
                          " -------------------------------------------------------------------------- " + separator +
                          "| Tracking # | Type    | Specification | Class       | Weight(oz) | Volume |" + separator +
                          " -------------------------------------------------------------------------- " + separator +
                          "| 00001      | Postcard| Fragile       | First-Class | 1.00       | 1      |" + separator +
                          " --------------------------------------------------------------------------"+ separator + separator;
        shippingstore.searchPackageOrder("00001");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        shippingstore.searchPackageOrder("00000");
        expected = separator + "Search did not find a match."+ separator + separator;
        assertEquals(expected, outContent.toString());
        System.setOut(stdout);
    } 

    /** 
    * 
    * Method: addOrder(String trackingnumber, String type, String specification, String mailingclass, String weight, String volume) 
    * Tests output and bad inputs for adding orders to the array list
    */ 
    @Test
    public void testAddOrder() throws Exception { 
        //TODO: Test goes here... 
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        
        String expected = "Package Order has been added." + separator + separator;
        shippingstore.addOrder("00004", "Postcard", "Fragile", "First-Class", "4.0", "4");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "Package Order already exists in database. " + separator + separator;
        shippingstore.addOrder("00001", "Postcard", "Fragile", "First-Class", "4.0", "4");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "Invalid Tracking Number: not proper format." + 
                   "Tracking Number must be at least 5 alphanumeric characters." + separator;
        shippingstore.addOrder("xxxxxxx", "Postcard", "Fragile", "First-Class", "4.0", "4");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "Invalid type:" + separator + 
                   "Type must be one of following: Postcard, Letter, Envelope, Packet, Box, Crate, Drum, Roll, Tube." + separator;
        shippingstore.addOrder("00005", "xxxxxx", "Fragile", "First-Class", "5.0", "5");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "Invalid specification:" + separator + 
                   "Specification must be one of following: Fragile, Books, Catalogs, Do-not-Bend, N/A." + separator;
        shippingstore.addOrder("00005", "Postcard", "xxxxxxx", "First-Class", "5.0", "5");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "Invalid Mailing Class:" + separator + 
                   "Mailing Class must be one of following: First-Class, Priority, Retail, Ground, Metro." + separator;
        shippingstore.addOrder("00005", "Postcard", "Fragile", "xxxxxxxxxxx", "5.0", "5");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "The weight of package cannot be negative." + separator;
        shippingstore.addOrder("00005", "Postcard", "Fragile", "First-Class", "-5.0", "5");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = "Invalid volume:" + separator + 
                   "The package's volume has to be an integer number between 0 and 999999. " + separator;
        shippingstore.addOrder("00005", "Postcard", "Fragile", "First-Class", "5.0", "1000000");
        assertEquals(expected, outContent.toString());
        System.setOut(stdout);
    } 

    /** 
    * 
    * Method: removeOrder(String trackingNum) 
    * tests removing orders and compares output
    */ 
    @Test
    public void testRemoveOrder() throws Exception { 
        //TODO: Test goes here... 
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        String expected = separator + "Action successful. Package order has been removed from the database." + separator + separator;
        shippingstore.removeOrder("00001");
        assertEquals(expected, outContent.toString());
        outContent.reset();
        
        expected = separator + "Action failed. No package order with the given tracking # exist in database." + separator + separator;
        shippingstore.removeOrder("00001");
        assertEquals(expected, outContent.toString());
        System.setOut(stdout);
    } 

    /** 
    * 
    * Method: getPackageOrder(int i) 
    * tests if an order is grabbed or not, tests bad inputs and an instance it doesnt exist
    */ 
    @Test
    public void testGetPackageOrder() throws Exception { 
        //TODO: Test goes here... 
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        
        PackageOrder order = null;
        PackageOrder expected = new PackageOrder("00001", "Postcard", "Fragile", "First-Class", 1.0f, 1);
        String expected2 = "Invalid Index. Please enter another command or 'h' to list the commands." + separator;
        
        order = shippingstore.getPackageOrder(0);
        assertEquals(expected.toString(), order.toString());
        
        order = shippingstore.getPackageOrder(5);
        assertEquals(expected2, outContent.toString());
        assertEquals(null, order);
        System.setOut(stdout);
    } 

    /** 
    * 
    * Method: read(Reader dataReader) 
    *  Tests if the dataReader class reads in that proper information after writting it to a file
    */ 
    @Test
    public void testRead() throws Exception { 
        //TODO: Test goes here... 
        PrintWriter pw = new PrintWriter("PackageOrderDB.txt");
        shippingstore.flush(pw);
        
        shippingstore.removeOrder("00001");
        shippingstore.removeOrder("00002");
        shippingstore.removeOrder("00003");
        
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        
        String expected = " -------------------------------------------------------------------------- " + separator +
                          "| Tracking # | Type    | Specification | Class       | Weight(oz) | Volume |" + separator +
                          " -------------------------------------------------------------------------- " + separator +
                          "| 00001      | Postcard| Fragile       | First-Class | 1.00       | 1      |" + separator +
                          "| 00002      | Postcard| Fragile       | First-Class | 2.00       | 2      |" + separator +
                          "| 00003      | Postcard| Fragile       | First-Class | 3.00       | 3      |" + separator +
                          " --------------------------------------------------------------------------"+ separator + separator;        
        
        FileReader dataReader = new FileReader(shippingstore.getDataFile());
        
        shippingstore.read(dataReader);
        shippingstore.showPackageOrders();
        
        assertEquals(expected, outContent.toString());
    } 

    /** 
    * 
    * Method: flush(Writer dataWriter) 
    * tests if the inputs in a file are correct after writting to it and reading it back idnetical to read class
    */ 
    @Test
    public void testFlush() throws Exception { 
        //TODO: Test goes here... 
        PrintWriter pw = new PrintWriter("PackageOrderDB.txt");
        shippingstore.flush(pw);
        
        shippingstore.removeOrder("00001");
        shippingstore.removeOrder("00002");
        shippingstore.removeOrder("00003");
        
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        
        String expected = " -------------------------------------------------------------------------- " + separator +
                          "| Tracking # | Type    | Specification | Class       | Weight(oz) | Volume |" + separator +
                          " -------------------------------------------------------------------------- " + separator +
                          "| 00001      | Postcard| Fragile       | First-Class | 1.00       | 1      |" + separator +
                          "| 00002      | Postcard| Fragile       | First-Class | 2.00       | 2      |" + separator +
                          "| 00003      | Postcard| Fragile       | First-Class | 3.00       | 3      |" + separator +
                          " --------------------------------------------------------------------------"+ separator + separator;        
        
        FileReader dataReader = new FileReader(shippingstore.getDataFile());
        
        shippingstore.read(dataReader);
        shippingstore.showPackageOrders();
        
        assertEquals(expected, outContent.toString());
    }
} 
