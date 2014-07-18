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
public class ChangePasswordTests {
	private Home homepage;
	private AcountSettings page;
	private static String curPassword;
	@Before
	public void setUp() throws Exception {
		FirefoxDriver driver= new FirefoxDriver();
		Thread.sleep(1000);
		homepage = new Home(driver);
		homepage.goToPage();
		if(curPassword == null){
			curPassword=Config.defaultPassword;
		}
		homepage.loginAs(Config.defaultEmail,curPassword);
		page = new AcountSettings(driver);
		page.goToPage();
	}

	@After
	public void tearDown() throws Exception {
		page.quit();
	}

	@Test
	public void changePasswordToAlternate() throws InterruptedException {
		page.changePassword(Config.alternatePassword);
		curPassword=Config.alternatePassword;
	}
	@Test
	public void changePasswordToComplex() throws InterruptedException {
		page.changePassword(Config.complexPass);
		curPassword=Config.complexPass;
	}
	@Test
	public void changePasswordToDefault() throws InterruptedException {
		page.changePassword(Config.defaultPassword);
		curPassword=Config.defaultPassword;
	}
	@Test
	public void changePasswordToSame() throws InterruptedException {
		page.changePassword(curPassword);
	}
	@Test
	public void changePasswordWithCancelTest() throws InterruptedException {
		page.changePasswordExpectFail("", "");
		page.cancelPasswordChange();
	}
	@Test
	public void changePasswordWithOnlyOnePasswordEntered() throws InterruptedException {
		page.changePasswordExpectFail(Config.defaultPassword, "");
		assertEquals("New password must match repeat password and be at least 6 characters long",page.getChangeErrorMessage());
		page.cancelPasswordChange();
		Thread.sleep(2000);
		page.changePasswordExpectFail("", Config.defaultPassword);
		assertEquals("New password must match repeat password and be at least 6 characters long",page.getChangeErrorMessage());
	}
	@Test
	public void changePassworddWithShortPassword() throws InterruptedException {
		page.changePasswordExpectFail("short","short");// should fail since it is not 6 characters
		assertEquals("New password must match repeat password and be at least 6 characters long",page.getChangeErrorMessage());
	}
	@Test
	public void changePassworddWithNonMatchingPasswords() throws InterruptedException {
		page.changePasswordExpectFail(Config.defaultPassword,Config.defaultPassword.substring(1));// should fail since repeat is one char short
		assertEquals("New password must match repeat password and be at least 6 characters long",page.getChangeErrorMessage());
	}
}
