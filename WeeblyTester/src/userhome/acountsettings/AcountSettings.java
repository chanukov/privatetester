/**
 * BH
 * @author Alon Chanukov
 *
 */
package userhome.acountsettings;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import page.Page;

/*
 * Deals with all actions for the account admin
 * Must be logged in to do anything here
 */
public class AcountSettings extends Page {

	public AcountSettings(WebDriver driver) {
		super(driver);
	}
	/*
	 * @see page.Page#goToPage()
	 */
	public void goToPage() {
		driver.get("http://www.weebly.com/weebly/userHome.php?page=account");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("account-settings")));
	}
	/*
	 * Changes the password, will enter the same password twice the same
	 */
	public void changePassword(String pass) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change"))).click();
		WebElement newpass=wait.until(ExpectedConditions.elementToBeClickable(By.id("new-password")));
		newpass.clear();
		Thread.sleep(300);
		newpass.sendKeys(pass);
		
		WebElement repeatpass=wait.until(ExpectedConditions.elementToBeClickable(By.id("repeat-password")));
		repeatpass.clear();
		Thread.sleep(300);
		repeatpass.sendKeys(pass);
		Thread.sleep(300);
		WebElement frame=driver.findElement(By.id("account-change-password"));
		frame.findElement(By.className("btn-primary")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change")));

	}
	/*
	 * Expects a particular change of password to fail
	 * Allows entering different password to make it fail
	 */
	public void changePasswordExpectFail(String pass1,String pass2) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change"))).click();
		WebElement newpass=wait.until(ExpectedConditions.elementToBeClickable(By.id("new-password")));
		newpass.clear();
		Thread.sleep(300);
		newpass.sendKeys(pass1);
		
		WebElement repeatpass=wait.until(ExpectedConditions.elementToBeClickable(By.id("repeat-password")));
		repeatpass.clear();
		Thread.sleep(300);
		repeatpass.sendKeys(pass2);
		Thread.sleep(300);
		WebElement frame=driver.findElement(By.id("account-change-password"));
		frame.findElement(By.className("btn-primary")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("account-change-error")));
	}
	/*
	 * clicks the cancel button on Password change
	 */
	public void cancelPasswordChange(){
		driver.findElement(By.className("weebly-dialog-close")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change")));
	}
	/*
	 * returns the error message in any change of Password user or email
	 */
	public String getChangeErrorMessage() {
		return driver.findElement(By.id("account-change-error")).getText();
	}
	/*
	 * Changes email to a new email
	 */
	public void changeEmail(String email) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change")));
		List<WebElement> settings = driver.findElements(By.className("settings-change"));
		settings.get(1).click();
		WebElement newemail=wait.until(ExpectedConditions.elementToBeClickable(By.id("new-email")));
		newemail.clear();
		Thread.sleep(300);
		newemail.sendKeys(email);
		WebElement frame=driver.findElement(By.id("account-change-email"));
		frame.findElement(By.className("btn-primary")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change")));
		Thread.sleep(300);
	}
	/*
	 * expects a particular change of email to fail
	 */
	public void changeEmailExpectFail(String email) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change")));
		List<WebElement> settings = driver.findElements(By.className("settings-change"));
		settings.get(1).click();
		WebElement newemail=wait.until(ExpectedConditions.elementToBeClickable(By.id("new-email")));
		newemail.clear();
		Thread.sleep(300);
		newemail.sendKeys(email);
		WebElement frame=driver.findElement(By.id("account-change-email"));
		frame.findElement(By.className("btn-primary")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("account-change-error")));
	}
	/*
	 * clicks the cancel button on Password change
	 */
	public void cancelEmailChange(){
		driver.findElement(By.className("weebly-dialog-close")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("settings-change")));
	}
	/*
	 * returns the text of the current email
	 */
	public String getCurrentemail() {
		return driver.findElement(By.id("current-email")).getText();
	}
	
}
