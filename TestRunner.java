import org.junit.runner.JUnitCore;  
import org.junit.runner.Result;  
import org.junit.runner.notification.Failure; 

/**
 * Write a description of class TestRunner here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestRunner
{
    public static void main(String[] args) throws Exception {
        Result result = JUnitCore.runClasses(ShippingStoreTest.class);  
        if(result.getFailures().size() == 0)  
        {  
            System.out.println("All tests successful !!!");  
        }  
        else  
        {     
            System.out.println("No. of failed test cases="+result.getFailures().size());  
            for (Failure failure : result.getFailures())  
                System.out.println(failure.toString());  
        }  
    }
}
