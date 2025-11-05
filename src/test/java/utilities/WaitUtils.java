package utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	 
	 
	  
	private static final int DEFAULT_TIMEOUT = 20;
	
	public static void clickElement(WebDriver driver, WebElement element) {
	    try {
	        scrollIntoView(driver, element);
	        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
	                .until(ExpectedConditions.elementToBeClickable(element));
	        element.click();
	    } catch (Exception e) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	    }
	}
 
	public static void clickLastElement(WebDriver driver, By locator) {
	    try {
	        List<WebElement> elements = driver.findElements(locator);
	        WebElement last = elements.get(elements.size() - 1);
	        clickElement(driver, last);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed clicking last element for locator: " + locator);
	    }
	}

	 //TimeSlot method
	public static void clickTimeSlot(WebDriver driver) {
	    List<WebElement> slots = driver.findElements(By.cssSelector("#AvailabilityStartTimes span.btn.btn-time"));
	    if (slots.isEmpty()) throw new RuntimeException("No time slots found");
	    
	    WebElement slot = slots.stream().filter(s -> s.isDisplayed() && s.isEnabled()).findFirst().orElse(slots.get(0));
	    scrollIntoView(driver, slot);
	    clickElement(driver, slot);
	}


     //Wait until the element is visible on screen 
	
	 
	public static WebElement waitForVisible(WebDriver driver, By locator, int timeout) {
	    return new WebDriverWait(driver, Duration.ofSeconds(timeout))
	            .until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
 
    //Wait until the element is clickable 
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

     //Wait until element is present in the DOM
    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    } 

   // Safely click an element with scroll and JS fallback
    public static void safeClick(WebDriver driver, By locator) {
    	try {
            WebElement element = waitForClickable(driver, locator);
            scrollIntoView(driver, element);
            element.click();
           // System.out.println("Clicked element: " + locator);
        } catch (Exception e) {

            // Try again with JS fallback
            jsClick(driver, locator);
        }
    }

   // Click an element using JavaScript 
    public static void jsClick(WebDriver driver, By locator) {
    	 try {
    	        // Re-locate element freshly before JS click
    	        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
    	                .until(ExpectedConditions.presenceOfElementLocated(locator));

    	        // Ensure it's visible and enabled
    	        new WebDriverWait(driver, Duration.ofSeconds(10))
    	                .until(ExpectedConditions.visibilityOf(element));

    	        scrollIntoView(driver, element);

    	        // Small delay to allow overlays to fade 
    	        Thread.sleep(500);

    	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    	       

    	    } catch (StaleElementReferenceException stale) {
    	       // System.out.println("Element went stale, re-trying JS click for: " + locator);
    	        WebElement element = driver.findElement(locator);
    	        scrollIntoView(driver, element);
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    	    } catch (Exception e) {
    	        throw new RuntimeException("JS click failed for locator: " + locator + " — " + e.getMessage(), e);
    	    }
    	}  

    //Retrieve visible text from an element safely
    public static String safeGetText(WebDriver driver, By locator) {
        try {
            return waitForVisible(driver, locator).getText().trim();
        } catch (Exception e) {
           
            return "";
        }
    }

   // Scroll element into view
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    
    public static void sendKeysWhenVisible(WebDriver driver, By locator, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));

            //  Ensure the element exists in the DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Find the first visible instance (handles duplicates)
            WebElement field = driver.findElements(locator).stream()
                    .filter(WebElement::isDisplayed).findFirst().orElseThrow(() ->
                            new RuntimeException("No visible element found for locator: " + locator));

            //  Ensure it’s clickable before typing
            scrollIntoView(driver, field);
            wait.until(ExpectedConditions.elementToBeClickable(field));

            // Clear existing text & type 
            field.clear();
            field.sendKeys(text);
           // System.out.println(" Successfully entered text into: " + locator);

        } catch (Exception e) {
           // System.out.println("Normal sendKeys failed, trying JS fallback for: " + locator);
            try {
                WebElement visibleField = driver.findElements(locator).stream()
                        .filter(WebElement::isDisplayed).findFirst().orElseThrow(() ->
                                new RuntimeException("No visible element found for JS fallback: " + locator));

                scrollIntoView(driver, visibleField);
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1];", visibleField, text);


            } catch (Exception jsEx) {
                throw new RuntimeException("Failed to send keys even with JS fallback for locator: " + locator, jsEx);
            } 
        } 
    }  
     
    public static void openAppointment(WebDriver driver, String what, String where, String who, String when) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    WebElement scrollContainer = wait
	            .until(ExpectedConditions.visibilityOfElementLocated(By.id("ClientAccountContainer")));

	    boolean found = false;
	    int scrollAttempts = 0;

	    while (!found && scrollAttempts < 20) {


		List<WebElement> cards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.appointment-list-item.clickable")));

		
		
		String[] whenParts = when.split("-");
		String startTime = whenParts[0].trim();  // 9:00 AM

		String[] dateParts = when.split("-");
		String dateSection = dateParts[2].trim(); // "Wed Nov 12"
		String[] dateTokens = dateSection.split(" "); 
		String whenMonth = dateTokens[1].trim();  // Nov
		String whenDay = dateTokens[2].trim();    // 12

		for (WebElement card : cards) {

		    String cardDay = card.findElement(By.cssSelector(".day")).getText().trim();            // 12
		    String cardMonth = card.findElement(By.cssSelector(".month")).getText().trim();        // Nov
		    String cardDateTime = card.findElement(By.cssSelector(".details .subtitle")).getText().trim(); // "Wed 9:00 AM"
		    String cardTime = cardDateTime.split(" ")[1] + " " + cardDateTime.split(" ")[2];      // 9:00 AM
		    String cardService = card.findElement(By.tagName("strong")).getText().trim();          // Sservice

		    // Match ALL values
		    if (cardDay.equals(whenDay) &&  cardMonth.equalsIgnoreCase(whenMonth) &&   cardTime.equalsIgnoreCase(startTime) &&   what.contains(cardService)) {

		        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", card);
		        Thread.sleep(500);
		        js.executeScript("arguments[0].click();", card);
		        found = true;
		        break;
		    }

	           
	        }

	        if (!found) {

	            js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].offsetHeight;", scrollContainer);

	            Thread.sleep(1000);
	            scrollAttempts++;
	        }
	    }

	    if (!found) throw new AssertionError("Appointment not found in list");
	}

} 
