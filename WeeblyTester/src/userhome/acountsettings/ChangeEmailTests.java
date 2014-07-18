package userhome.acountsettings;

import static org.junit.Assert.*;
import home.Home;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.junit.runners.MethodSorters;

import config.Config;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChangeEmailTests {
	private Home homepage;
	private AcountSettings page;
	private static String curEmail;
	@Before
	public void setUp() throws Exception {
		FirefoxDriver driver= new FirefoxDriver();
		Thread.sleep(1000);
		homepage = new Home(driver);
		homepage.goToPage();
		if(curEmail == null){
			curEmail=Config.defaultEmail;
		}
		homepage.loginAs(curEmail,Config.defaultPassword);
		page = new AcountSettings(driver);
		page.goToPage();
	}

	@After
	public void tearDown() throws Exception {
		page.quit();
	}

	@Test
	public void changeEmailToAlternate() throws InterruptedException {
		page.changeEmail(Config.alternateEmail);
		curEmail=Config.alternateEmail;
		assertEquals(Config.alternateEmail , page.getCurrentemail());
	}
	@Test
	public void changeEmailToDefault() throws InterruptedException {
		page.changeEmail(Config.defaultEmail);
		curEmail=Config.defaultEmail;
		assertEquals(Config.defaultEmail , page.getCurrentemail());
	}
	@Test
	public void changeEmailWithCancelTest() throws InterruptedException {
		page.changeEmailExpectFail("");
		page.cancelEmailChange();
	}
	@Test
	public void changeEmailWithInvalidEmails() throws InterruptedException {
		page.changeEmailExpectFail(Config.defaultEmailSlug);
		assertEquals("This email address is not valid. Please try again.",page.getChangeErrorMessage());
		page.cancelEmailChange();
		Thread.sleep(2000);
		page.changeEmailExpectFail(Config.defaultGmailSlug);
		assertEquals("This email address is not valid. Please try again.",page.getChangeErrorMessage());
	}
	@Test
	public void changeEmailWithExistingEmail() throws InterruptedException {
		page.changeEmail(curEmail);
		assertEquals("This email address is already in use by another account. Please choose another.",page.getChangeErrorMessage());
	}
}
