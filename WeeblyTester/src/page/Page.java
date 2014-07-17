package page;
import org.openqa.selenium.By;
/**
 * BH
 * @author Alon Chanukov
 *
 */
import org.openqa.selenium.WebDriver;

/**
* Page class which will be extended by all other page classes
 */
public abstract class Page {
	protected WebDriver driver;
	public Page(WebDriver driver){
		this.driver = driver;
	}
	public void quit() {
		driver.quit();
	}
	public boolean isLoggedIn() {
		//try{
			driver.findElement(By.className("user-home-page-main"));
			return true;
		//}catch(){
		//	return false;
		//}
	}
}
