package utilities.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return extentTest.get();
    }
 
    public static void startTest(String testName) {
        ExtentReports extent = ExtentManager.getReporter();
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
    }
    
    
//    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//    
//    private static ExtentReports extent = ExtentManager.getReporter();
//
//    public static synchronized ExtentTest startTest(String testName) {
//        ExtentTest extentTest = extent.createTest(testName);
//        test.set(extentTest);
//        return extentTest;
//    }
//
//    public static synchronized ExtentTest getTest() 
//    {
//        return test.get();
//    }

}
 