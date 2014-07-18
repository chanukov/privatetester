package userhome.builder;

import static org.junit.Assert.*;
import home.Home;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.firefox.FirefoxDriver;

import userhome.builder.Builder.Boxes;
import config.Config;

public class DragAndDropTests {
	private Home homepage;
	private Builder page;

	@Before
	public void setUp() throws Exception {
		FirefoxDriver driver= new FirefoxDriver();
		Thread.sleep(1000);
		homepage = new Home(driver);
		homepage.goToPage();
		homepage.loginAs(Config.defaultEmail,Config.defaultPassword);
		page = new Builder(driver);
		page.goToPage();
		page.removeAllItemFromPage();
	}
	@After
	public void tearDown() throws Exception {
		page.removeAllItemFromPage();
		page.quit();
	}

	@Test
	public void testDragAndDropAndDelete() throws InterruptedException {
		page.dragBoxToPage(Boxes.Title);
		assertTrue(page.hasBoxOnPage(0));
		page.removeItemFromPage(0);
		assertFalse(page.hasBoxOnPage(0));
	}
	@Test
	public void testDragAndDropAndDeleteAt2() throws InterruptedException {
		page.dragBoxToPage(Boxes.Title);
		page.dragBoxToPage(Boxes.Image);

		assertTrue(page.hasBoxOnPage(0));
		assertTrue(page.hasBoxOnPage(1));
		page.removeItemFromPage(1);
		assertFalse(page.hasBoxOnPage(1));
		assertTrue(page.hasBoxOnPage(0));

	}
	@Test
	public void testDragAndDrop2Items() throws InterruptedException {
		page.dragBoxToPage(Boxes.Title);
		page.dragBoxToPage(Boxes.Image);

		assertTrue(page.hasBoxOnPage(0));
		assertTrue(page.hasBoxOnPage(1));

	}
}
