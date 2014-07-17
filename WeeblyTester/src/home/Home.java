/**
 * BH
 * @author Alon Chanukov
 *
 */
package home;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import page.Page;
/*
 * A class to deal with the Website's home page and the actions on it while logged off
 */
public class Home extends Page{
	WebDriverWait wait = new WebDriverWait(driver, 10);
	public Home(WebDriver driver){
		super(driver);
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
		WebElement login =wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
		login.click();
		WebElement email =wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-username")));
		email.sendKeys(username);
		Thread.sleep(300);
		WebElement pass =wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-password")));
		pass.sendKeys(password);
		Thread.sleep(300);
		login =wait.until(ExpectedConditions.elementToBeClickable(By.className("login-btn")));
		login.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("user-home-page-main")));

	}
	/*
	 * failed login here, maybe because one or both of the username and password are wrong
	 * waits for a login error at end
	 */
	public void loginAsExpectingError(String username, String password) throws InterruptedException {
		WebElement login =wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
		login.click();
		WebElement email =wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-username")));
		email.sendKeys(username);
		Thread.sleep(300);
		WebElement pass =wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-password")));
		pass.sendKeys(password);
		Thread.sleep(300);
		login =wait.until(ExpectedConditions.elementToBeClickable(By.className("login-btn")));
		login.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("weebly-login-error")));
	}
	
	/*
	 * Go through the minimal sign up things to allow you to reach your homepage on login
	 */
	public void signupAs(String name,String username, String password,String acountType,String websiteType) throws InterruptedException {
		WebElement user =wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-name")));
		user.sendKeys(username);
		Thread.sleep(300);
		WebElement email =wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-email")));
		email.sendKeys(username);
		Thread.sleep(300);
		WebElement pass =wait.until(ExpectedConditions.elementToBeClickable(By.id("header-signup-form-password")));
		pass.sendKeys(password);
		Thread.sleep(300);
		WebElement submit = driver.findElement(By.id("header-signup-form")).findElement(By.xpath("//input[@type='submit']"));
		submit.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("plan-details")));
		Thread.sleep(3000);

	}
	/*
	 * Signs up follows all basic steps to get you into homepage
	 */
	public void signupWithThemeAs(String name,String username, String password,String acountType,String websiteType,String theme) {
        
	}
	
	/*
	 * 
	 */
	public void signupAsExpectingError(String name,String username, String password) {
        
	}

	/*
	 * returns the error message in login
	 */
	public String getLoginErrorMessage() {
		return driver.findElement(By.id("weebly-login-error")).getText();
	}
	/*
	 * returns the error message in sight up
	 */
	public String getSignupErrorMessage() {
		WebElement el=driver.findElement(By.id("weebly-login-error"));
		return el.getText();
	}
}
