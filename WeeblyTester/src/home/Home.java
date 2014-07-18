/**
 * BH
 * @author Alon Chanukov
 *
 */
package home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import page.Page;
/*
 * A class to deal with the Website's home page and the actions on it while logged off
 */
public class Home extends Page{
	public Home(WebDriver driver){
		super(driver);
	}
	
	
	//The options for  Plans and their corresponding Element Id
	public enum Plan {
	    Free ("plan-choice-free-description"),
	    Starter ("plan-choice-starter-description"),
	    Pro ("plan-choice-pro-description"),
	    Business ("plan-choice-business-description");

	    public final String elementId; 
	    Plan(String elementId) {
	        this.elementId = elementId;
	    }
    }
	
	//The options for Website types
	public enum WebsiteType {
	    site,
	    blog,
	    store;
	}
	/*
	 * goes to the homepage
	 */
	public void goToPage() {
		driver.get("http://www.weebly.com");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-name")));	
	}
	/*
	 * failed login here, maybe because one or both of the username and password are wrong
	 * waits for a user-homepage
	 */
	public void loginAs(String username, String password) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-username"))).sendKeys(username);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-password"))).sendKeys(password);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.className("login-btn"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("user-home-page-main")));

	}
	/*
	 * Expected failed login here, maybe because one or both of the username and password are wrong
	 * waits for a login error at end
	 */
	public void loginAsExpectingError(String username, String password) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-username"))).sendKeys(username);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-password"))).sendKeys(password);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.className("login-btn"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-login-error")));
	}
	
	/*
	 * Go through the minimal sign up things to allow you to reach your Homepage on login
	 * Since Themes are not named nor a part of this test suite I will always select first one
	 */
	public void signupAs(String name,String email, String password,Plan plan,WebsiteType type) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-name"))).sendKeys(name);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-email"))).sendKeys(email);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-pass"))).sendKeys(password);
		Thread.sleep(300);
		WebElement submit = driver.findElement(By.id("header-signup-form")).findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("plan-details")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id(plan.elementId))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("choose-site-type")));
		wait.until(ExpectedConditions.elementToBeClickable(By.className(type.name()))).click();
		
		Actions actions = new Actions(driver);
		WebElement hover = driver.findElement(By.className("theme-select"));
		actions.click(hover);
		WebElement choose = hover.findElement(By.className("btn-primary"));
		actions.moveToElement(choose);
		actions.click(choose);
		actions.perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("new-editor")));

	}
	
	/*
	 * Expected failed sign up here, because one or both of the username and password are wrong
	 * waits for a login error at end
	 */
	public void signupAsExpectingError(String name,String email, String password) throws InterruptedException {
		WebElement user =wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-name")));
		user.sendKeys(name);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-email"))).sendKeys(email);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-pass"))).sendKeys(password);
		Thread.sleep(300);
		WebElement submit = driver.findElement(By.id("header-signup-form")).findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-error")));
	}
	/*
	 * Expected to go to login page, because email already registered
	 */
	public void signupAsExistingEmail(String name,String email, String password) throws InterruptedException {
		WebElement user =wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-name")));
		user.sendKeys(name);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-email"))).sendKeys(email);
		Thread.sleep(300);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-pass"))).sendKeys(password);
		Thread.sleep(300);
		WebElement submit = driver.findElement(By.id("header-signup-form")).findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-login")));
	}
	/*
	 * returns the error message in login
	 */
	public String getLoginErrorMessage() {
		return driver.findElement(By.id("weebly-login-error")).getText();
	}
	/*
	 * returns the error message in sign up header
	 */
	public String getSignupErrorMessage() {
		return driver.findElement(By.id("header-signup-form-error")).getText();
	}
}
