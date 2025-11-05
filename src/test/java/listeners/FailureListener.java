package listeners;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import testBase.BaseTest;
import utilities.EmailUtils;
//import testBase.BaseTest;
import utilities.reporting.ExtentManager;
import utilities.reporting.ExtentTestManager;


public class FailureListener implements ITestListener{

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
	 
	 ExtentTestManager.getTest().pass("Test Passed: " + result.getName());
        //ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
    }
    
    @Override 
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().skip("Test Skipped: " + result.getName());
    }
 
    @Override
    public void onTestFailure(ITestResult result) {
        //ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
	
	 WebDriver driver = null;
 
	    try {
	        driver = (WebDriver) result.getTestContext().getAttribute("driver");

	        if (driver == null) {
	            System.out.println("Driver is null in listener, trying BaseTest.driver...");
	            driver = BaseTest.driver;
	        }

	        if (driver != null) {
	            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            String screenshotPath = System.getProperty("user.dir") 
	                    + "/screenshots/" + result.getName() + ".png";
	            File dest = new File(screenshotPath);
	            FileUtils.copyFile(src, dest);
 
	            System.out.println("Screenshot captured in listener: " + screenshotPath);
	        } else {
	            System.out.println("Driver is still null — screenshot skipped.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
 
        String screenshotPath = BaseTest.takeScreenshot(result.getName());
        ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
        EmailUtils.sendEmailWithAttachment("addressgen046@gmail.com", "Automation Test Failed:" + result.getName(),"Test Failed. Screenshot attached. \n\nRegards, \nAutomation Framework", screenshotPath);
        
        
    }

   @Override
   public void onFinish(ITestContext context) {   
       ExtentManager.getReporter().flush();
       
       System.out.println("New Extent report created!");
   }

    }
    

