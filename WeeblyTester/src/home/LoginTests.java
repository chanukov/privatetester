package home;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LoginTests {
	private Home page;
	private final static String defaultEmail ="weeblytester7@gmail.com";
	private final static String defaultPassword ="T3st1t";

	@Before
	public void setUp() throws Exception {
		page = new Home(new FirefoxDriver());
		page.goToPage();
	}
	@Test
	public void testLogin() throws InterruptedException {
		page.loginAs(defaultEmail, defaultPassword);
		assertTrue(page.isLoggedIn());
	}
	@Test
	public void testloginWithoutUserAndPassword() throws InterruptedException {
		page.loginAsExpectingError("","");
		assertEquals("Wrong username or password.", page.getLoginErrorMessage());
	}
	@Test
	public void testloginWithoutUser() throws InterruptedException {
		page.loginAsExpectingError("", defaultPassword);
		assertEquals("Wrong username or password.", page.getLoginErrorMessage());
	}
	@Test
	public void testloginWithoutPassword() throws InterruptedException {
		page.loginAsExpectingError(defaultEmail, "");
		assertEquals("Wrong username or password.", page.getLoginErrorMessage());
	}
	@Test
	public void testloginWithInvalidPassword() throws InterruptedException {
		page.loginAsExpectingError(defaultEmail, "invalid");
		assertEquals("Wrong username or password", page.getLoginErrorMessage());
	}
	@After
	public void tearDown() throws Exception {
		page.quit();
	}
}
