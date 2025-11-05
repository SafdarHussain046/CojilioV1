package testBase;

import java.awt.Desktop;
import java.io.File;
//import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class BaseTest {
    public static WebDriver driver; // make driver static

    public static ExtentReports extent;
    public static ExtentTest test;
    public static String reportPath; 
  

 
    public static String takeScreenshot(String testName)  {
    String path = System.getProperty("user.dir") + "/reports/screenshots/" + testName + ".png";
    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

    try {
        FileUtils.copyFile(src, new File(path));
    } catch (Exception e) {
        e.printStackTrace();
    }
    return path;
}
    
 
    @AfterClass
    public void tearDown() {
        extent.flush();
    
    try {
        File htmlFile = new File(System.getProperty("user.dir") + "/reports/ExtentReport.html");
        Desktop.getDesktop().browse(htmlFile.toURI());
        
    } catch (Exception e) {
        System.out.println("Failed to open Extent Report automatically: " + e.getMessage());
    }
  }
}

