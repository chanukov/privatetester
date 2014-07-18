package page;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
/**
 * BH
 * @author Alon Chanukov
 *
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
* Page class which will be extended by all other page classes
 */
public abstract class Page {
	protected WebDriver driver;
	protected static WebDriverWait wait;

	public Page(WebDriver driver){
		this.driver = driver;
		wait= new WebDriverWait(driver, 10);
	}
	/*
	 * goes to whatever page extends this page
	 */
	public abstract void goToPage();
	public void quit() {
		driver.quit();
	}
	/*
	 * returns weather a user is logged in
	 */
	public boolean isLoggedIn() {
		try{
			driver.findElement(By.className("user-home-page-main"));
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/*
	 * returns weather a user is on Login Page
	 */
	public boolean onLoginPage() {
		try{
			driver.findElement(By.className("login-overlay"));
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	/*
	 * returns weather a user is on account settings page
	 */
	public boolean onAcountSettingsPage() {
		try{
			driver.findElement(By.className("account-settings"));
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	
}
