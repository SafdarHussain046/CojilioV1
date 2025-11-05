package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.WaitUtils;

public class MainPage extends BasePage {

	public MainPage(WebDriver driver) {
		super(driver);
	} 
  
	// (By locators)
	private By guestBtn = By.id("GoAsGuestBtn");
	private By serviceTab = By.xpath("//*[@id=\"SelectionContainer\"]/div/ul/li[3]");
	private By loginRegisterBtn = By.xpath("//span[normalize-space()='login or register an account']");
	private By usernameField = By.cssSelector("#LoginPopup input#Username");
	private By passwordField = By.cssSelector("#LoginPopup input#Password");
	private By loginBtn = By.id("LoginBtn");

	private By timeSlots = By.cssSelector("#AvailabilityStartTimes span.btn.btn-time");
	private By confirmBtn = By.id("GoToConfirmationBtn");
	private By agreePolicyChk = By.id("AgreeToCojilioPolicy");
	private By bookBtn = By.id("BookBtn");
	private By viewAccountBtn = By.id("ViewAccountBtn");
	private By apptItems = By.cssSelector("div.appointment-list-item.clickable");
	
	

	@FindBy(xpath = "//*[@id='ServiceListDetails']//label[normalize-space()='WHAT']/following-sibling::span")
	WebElement whatField;
	@FindBy(xpath = "//*[@id='ServiceListDetails']//label[normalize-space()='WHERE']/following-sibling::span")
	WebElement whereField;
	@FindBy(xpath = "//*[@id='ServiceListDetails']//label[normalize-space()='WHO']/following-sibling::span")
	WebElement whoField;
	@FindBy(xpath = "//*[@id='ServiceListDetails']//label[normalize-space()='WHEN']/following-sibling::span")
	WebElement whenField;

	public void clickGuestButton() {
		WaitUtils.safeClick(driver, guestBtn);
	} 

	public void clickServiceTab() {
		WaitUtils.safeClick(driver, serviceTab);
	}

	public void clickLoginRegister() {
		WaitUtils.safeClick(driver, loginRegisterBtn);
		WaitUtils.waitForVisible(driver, By.id("LoginPopup"));

	} 

	public void enterUsername(String uname) {

		WaitUtils.sendKeysWhenVisible(driver, usernameField, uname);
		 
	}

	public void enterPassword(String pwd) {
		WaitUtils.sendKeysWhenVisible(driver, passwordField, pwd);
	}

	public void clickLogin() {
	    
	    WaitUtils.clickLastElement(driver, loginBtn);

	}

	public void selectTimeSlot() {
	    WaitUtils.clickTimeSlot(driver);
	}

	public void proceedConfirmation() {
		WaitUtils.safeClick(driver, confirmBtn);
	}
 
	public void checkAgreePolicy() {
		if (!driver.findElement(agreePolicyChk).isSelected()) {
			WaitUtils.safeClick(driver, agreePolicyChk);
		}
	}

	public void clickBook() {
		WaitUtils.safeClick(driver, bookBtn);
	}

	public void viewAccountDetails() {
	    
	    WaitUtils.safeClick(driver, viewAccountBtn);
	    WaitUtils.waitForVisible(driver, By.id("ClientAccountContainer"));

	}
	
	public void findAndOpenAppointment(String what, String where, String who, String when) throws InterruptedException {
	    WaitUtils.openAppointment(driver, what, where, who, when);
	}
	
	
	 
	//Logout  Case when booking validations are done
	public void clickLogout() {
	    By logoutBtn = By.id("LogoutBtn");
	    WaitUtils.safeClick(driver, logoutBtn);
	}
	
	public void confirmLogout() { 
	    By logoutCnfrmBtn= By.xpath("//span[@class='btn btn-primary popup-btn']");
	    WaitUtils.safeClick(driver, logoutCnfrmBtn);
	}
	  
	 
	//Error message alert
	public String getLoginErrorMessage() {
	    By errorContainer = By.cssSelector(".summary-message-container.error");
	    By errorAlert = By.id("LoginError");
	    try { 
	        // Wait for error popup container
	        WaitUtils.waitForVisible(driver, errorContainer, 10);
 
	        // Wait for text to appear inside the span
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert));

	        return driver.findElement(errorAlert).getText().trim();
	    } catch (Exception e) {
	        return "No login error message found!";
	    }
	} 
	   

	//WHAT - WHERE - WHO - WHEN METHODS
	public String getWhat() {
		return whatField.getText().trim();
	}

	public String getWhere() {
		return whereField.getText().trim();
	}

	public String getWho() {
		return whoField.getText().trim();
	}

	public String getWhen() {
		return whenField.getText().trim();
	}
	
}
