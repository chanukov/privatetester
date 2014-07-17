package home;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SignupTests {
	private Home page;
	private final static String defaultName ="Weebly Tester";
	private final static String defaultEmailSlug="weeblytester7";
	private final static String defaultGmailSlug="@gmail.com";
	private final static String defaultPassword ="T3st1t";

	@Before
	public void setUp() throws Exception {
		page = new Home(new FirefoxDriver());
		page.goToPage();
	}
	@Test
	public void testLogin() throws InterruptedException {
		String email = defaultEmailSlug+"+"+(System.currentTimeMillis())+defaultGmailSlug;//generate a unique user every time
		page.signupAs(defaultName,email, defaultPassword,"free","site");
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
