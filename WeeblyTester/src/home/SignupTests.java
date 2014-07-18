package home;

import static org.junit.Assert.*;
import home.Home.Plan;
import home.Home.WebsiteType;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.firefox.FirefoxDriver;
import config.Config;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignupTests {
	private Home page;
	@Before
	public void setUp() throws Exception {
		page = new Home(new FirefoxDriver());
		page.goToPage();
	}
	@Test
	public void testSignupAsFreeSite() throws InterruptedException {
		String email = Config.defaultEmailSlug+"+"+(System.currentTimeMillis())+Config.defaultGmailSlug;//generate a unique user every time
		page.signupAs(Config.defaultName,email, Config.defaultPassword,Plan.Free,WebsiteType.site);
		//now go check you can login to the new account
		page.goToPage();
		page.loginAs(email, Config.defaultPassword);
		assertTrue(page.isLoggedIn());
	}
	@Test
	public void testSignupAsFreeBlog() throws InterruptedException {
		String email = Config.defaultEmailSlug+"+"+(System.currentTimeMillis())+Config.defaultGmailSlug;//generate a unique user every time
		page.signupAs(Config.defaultName,email, Config.defaultPassword,Plan.Free,WebsiteType.blog);
		//now go check you can login to the new account
		page.goToPage();
		page.loginAs(email, Config.defaultPassword);
		assertTrue(page.isLoggedIn());
	}
	@Test
	public void testSignupAsFreeStore() throws InterruptedException {
		String email = Config.defaultEmailSlug+"+"+(System.currentTimeMillis())+Config.defaultGmailSlug;//generate a unique user every time
		page.signupAs(Config.defaultName,email, Config.defaultPassword,Plan.Free,WebsiteType.store);
		//now go check you can login to the new account
		page.goToPage();
		page.loginAs(email, Config.defaultPassword);
		assertTrue(page.isLoggedIn());
	}
	@Test
	public void testSignupAsFreeStoreWithMinimumSizePass() throws InterruptedException {
		String email = Config.defaultEmailSlug+"+"+(System.currentTimeMillis())+Config.defaultGmailSlug;//generate a unique user every time
		page.signupAs(Config.defaultName,email, Config.defaultPassword.substring(0, 4),Plan.Free,WebsiteType.store);//use 4 chars
		//now go check you can login to the new account
		page.goToPage();
		page.loginAs(email, Config.defaultPassword.substring(0, 4));
		assertTrue(page.isLoggedIn());
	}
	@Test
	public void testSignupAsFreeStoreWithComplexPass() throws InterruptedException {
		String email = Config.defaultEmailSlug+"+"+(System.currentTimeMillis())+Config.defaultGmailSlug;//generate a unique user every time
		page.signupAs(Config.defaultName,email, Config.complexPass,Plan.Free,WebsiteType.store);//use 4 chars
		//now go check you can login to the new account
		page.goToPage();
		page.loginAs(email, Config.complexPass);
		assertTrue(page.isLoggedIn());
	}
	@Test
	public void testSignupWithoutNameEmailAndPassword() throws InterruptedException {
		page.signupAsExpectingError("","","");
		assertEquals("Please provide a password.", page.getSignupErrorMessage());
	}
	@Test
	public void testSignupWithoutEmail() throws InterruptedException {
		page.signupAsExpectingError(Config.defaultName,"",Config.defaultPassword);
		assertEquals("Please enter an email address.", page.getSignupErrorMessage());
	}
	@Test
	public void testSignupWithoutPassword() throws InterruptedException {
		String email = Config.defaultEmailSlug+"+"+(System.currentTimeMillis())+Config.defaultGmailSlug;//generate a unique user every time
		page.signupAsExpectingError(Config.defaultName,email,"");
		assertEquals("Please provide a password.", page.getSignupErrorMessage());
	}
	@Test
	public void testSignupWithInvalidEmail() throws InterruptedException {
		page.signupAsExpectingError(Config.defaultName,"invalid",Config.defaultPassword);
		assertEquals("Please enter a valid email address.", page.getSignupErrorMessage());
	}
	@Test
	public void testSignupWithShortPassword() throws InterruptedException {
		String email = Config.defaultEmailSlug+"+"+(System.currentTimeMillis())+Config.defaultGmailSlug;//generate a unique user every time
		page.signupAsExpectingError(Config.defaultName,email,"sho");
		assertEquals("Your password is not long enough. It must be at least 4 characters long.", page.getSignupErrorMessage());
	}
	@Test
	public void testSignupWithExistingEmail() throws InterruptedException {
		assertFalse(page.onLoginPage());
		page.signupAsExistingEmail(Config.defaultName,Config.defaultEmailSlug+Config.defaultGmailSlug,Config.defaultPassword);
		assertTrue(page.onLoginPage());
		
	}
	@After
	public void tearDown() throws Exception {
		//page.quit();
	}
}
