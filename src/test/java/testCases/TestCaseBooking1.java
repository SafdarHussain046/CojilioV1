package testCases;

//import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pageObjects.MainPage;
import testBase.BaseTest;

  
@Listeners(listeners.FailureListener.class)
public class TestCaseBooking1 { 
	
	public WebDriver driver;
	   
   
	@BeforeClass 
	public void open() {
		 
		driver= new ChromeDriver();
		BaseTest.driver=driver;
		driver.manage().deleteAllCookies();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
		
		driver.get("https://qabooking.cojilio.com/12058");
		driver.manage().window().maximize();
	}
	   
	 
//	 
	 
	@Test (priority =1)
	public void booking_appointment() throws InterruptedException{
		
	 	 
	     
		MainPage mp = new MainPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
  
		mp.clickGuestButton();
		mp.clickServiceTab();
		mp.clickLoginRegister();
		mp.enterUsername("test_qa"); 
		mp.enterPassword("KWGAQ7CP");
		mp.clickLogin();
		mp.selectTimeSlot(); 
		 
		mp.proceedConfirmation();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ServiceListDetails")));
		 
		// Capture dynamic values
                String expectedWhat = mp.getWhat();
                String expectedWhere = mp.getWhere();
                String expectedWho  = mp.getWho();
                String expectedWhen = mp.getWhen();
         
                System.out.println("Captured from confirmation:");
                System.out.println("WHAT: " + expectedWhat);
                System.out.println("WHERE: " + expectedWhere);
                System.out.println("WHO: " + expectedWho);
                System.out.println("WHEN: " + expectedWhen);
        		
		   
		mp.checkAgreePolicy();
		mp.clickBook();
		mp.viewAccountDetails(); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ViewAccountBtn")));

	
  
		 mp.findAndOpenAppointment(expectedWhat, expectedWhere, expectedWho, expectedWhen);
		  
		    
		    System.out.println("----------------------------------------");
		    System.out.println("Service:        " + expectedWhat);
		    System.out.println("Location:       " + expectedWhere);
		    System.out.println("Appointment Date & Time:    " + expectedWhen);
		    System.out.println("Provider:  " + expectedWho);
		    System.out.println("Booking validated successfully!");
 
		     
		//mp.clickLogout();
		//mp.confirmLogout(); 
		// System.out.println("Successfully logged out after booking validation");
 
		 
	} 
	
//	@Test (priority =2)
//	public void verifyInvalidLoginCredentials() {
//	    MainPage mp = new MainPage(driver);
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("GoAsGuestBtn")));
//	    
//	    
//	    mp.clickGuestButton();
//	    mp.clickServiceTab();
//	    mp.clickLoginRegister();
//	    mp.enterUsername("dummy@example.com");
//	    mp.enterPassword("zxcasd");
//	    mp.clickLogin();
//
//	    
//	    String actualText = mp.getLoginErrorMessage(); 
//	    System.out.println("Login Error Message: " + actualText);
//	
//	    //System.out.println("Login Failed");
//	}

 
	 
//	@AfterClass
//	public void teardown() {
//		
//		driver.quit();
//		
//	}

}
