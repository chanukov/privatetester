package home;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import config.Config;

public class LoginTests {
	private Home page;

	@Before
	public void setUp() throws Exception {
		page = new Home(new FirefoxDriver());
		page.goToPage();
	}
	@Test
	public void testLogin() throws InterruptedException {
		page.loginAs(Config.defaultEmail, Config.defaultPassword);
		assertTrue(page.isLoggedIn());
	}
	@Test
	public void testloginWithoutUserAndPassword() throws InterruptedException {
		page.loginAsExpectingError("","");
		assertEquals("Wrong username or password.", page.getLoginErrorMessage());
	}
	@Test
	public void testloginWithoutUser() throws InterruptedException {
		page.loginAsExpectingError("", Config.defaultPassword);
		assertEquals("Wrong username or password.", page.getLoginErrorMessage());
	}
	@Test
	public void testloginWithoutPassword() throws InterruptedException {
		page.loginAsExpectingError(Config.defaultEmail, "");
		assertEquals("Wrong username or password.", page.getLoginErrorMessage());
	}
	@Test
	public void testloginWithInvalidPassword() throws InterruptedException {
		page.loginAsExpectingError(Config.defaultEmail, "invalid");
		assertEquals("Wrong username or password", page.getLoginErrorMessage());
	}
	@After
	public void tearDown() throws Exception {
		page.quit();
	}
}
