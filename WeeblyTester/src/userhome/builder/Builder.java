package userhome.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import page.Page;

public class Builder extends Page {
	//contains a name of the page mapped to a map of the element name and the element
	private HashMap<String,ArrayList<WebElement>> elementMap;
	
	//until you do something with multiple pages simply modify this object updates whenever curPage changes.
	private ArrayList<WebElement> curPage;	
	private String curPageText;
	public Builder(WebDriver driver) {
		super(driver);
		elementMap = new HashMap<String,ArrayList<WebElement>>();
	}

	@Override
	public void goToPage() {
		driver.get("http://www.weebly.com");
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-info"))).click();	
		wait.until(ExpectedConditions.elementToBeClickable(By.className("wsite-editor")));
		WebElement navigation = driver.findElement(By.id("navigation"));
		//Initialize the Map
		List<WebElement> elements = navigation.findElements(By.className("wsite-nav-handle"));
		for(WebElement el :elements){
			elementMap.put(el.getText(), new ArrayList<WebElement>());
		}
		curPageText = navigation.findElement(By.id("active")).getText();
		WebElement contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
	}
	//List should contain all optio0ns but will not have time for it.
	public enum Boxes{
	    Title ("w-icon-62850560"),
	    Text ("w-icon-44785763"),
	    Image ("w-icon-30621876"),
	    Gallery ("w-icon-18362204"),
	    Map ("w-icon-42844424"),
	    ContactForm ("w-icon-45444132"),
	    EmbededCode ("w-icon-92495494");

	    public final String elementClass; 
	    Boxes(String elementClass) {
	        this.elementClass = elementClass;
	    }
	}
	
	/*
	 * Drag box to the page at index
	 */
	public void dragBoxToPage(Boxes box) throws InterruptedException {
		Actions actions = new Actions(driver);
		WebElement contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
		WebElement boxEl =wait.until(ExpectedConditions.elementToBeClickable(By.className(box.elementClass)));	
		WebElement emptyArea = driver.findElement(By.id("secondlist"));
		
		Action dragAndDrop = actions.clickAndHold(boxEl)
			       .moveToElement(emptyArea)
			       .release(emptyArea)
			       .build();

		dragAndDrop.perform();
		Thread.sleep(600);
		int contentsize = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist"))).findElements(By.className("inside")).size();
		while(contentsize == curPage.size()){
			Thread.sleep(100);
			contentsize = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist"))).findElements(By.className("inside")).size();
		}
		contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
	}
	
	/*
	 * Drag box to the page at index
	 */
	public void dragBoxToPage(Boxes box,int index) throws InterruptedException {
		WebElement contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
		Actions actions = new Actions(driver);
		WebElement emptyArea;
		if( curPage.size() == index){
			this.dragBoxToPage(box);
			return;
		}else {
			emptyArea = curPage.get(index);
		}
		WebElement boxEl =wait.until(ExpectedConditions.elementToBeClickable(By.className(box.elementClass)));	
		
		Action dragAndDrop = actions.clickAndHold(boxEl)
			       .moveToElement(emptyArea)
			       .release(emptyArea)
			       .build();

		dragAndDrop.perform();
		Thread.sleep(600);
		contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		while(contents.findElements(By.className("inside")).size() == curPage.size()){
			Thread.sleep(100);
			contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		}
		curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
	}
	/*
	 * Removes an item from the page
	 */
	public void removeItemFromPage(int index) throws InterruptedException {
		Actions actions = new Actions(driver);
		if(index >= curPage.size()){
			throw new Error("Index too large");
		}
		WebElement el = curPage.get(index).findElement(By.className("element"));;
		actions.moveToElement(el);
		//actions.click(el);

		WebElement del =curPage.get(index).findElement(By.className("w-delete"));

		actions.moveToElement(del);
		actions.click(del);
		actions.perform();
		Thread.sleep(1000);
		WebElement contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-danger"))).click();
		contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		while(contents.findElements(By.className("inside")).size() == curPage.size()){
			Thread.sleep(100);
			contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		}
		curPage.remove(index);
	}
	/*
	 * removes all the items from the page
	 */
	public void removeAllItemFromPage() throws InterruptedException {
		WebElement contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
		while(curPage.size() >0){
			try{
			this.removeItemFromPage(0);
			}catch(StaleElementReferenceException e){
				break;
			}
			Thread.sleep(300);
			contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
			curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
		}
	}
	/*
	 * Check for a Dropbox on page with a particular name
	 * Name the element for later use
	 */
	public boolean hasBoxOnPage(int index) throws InterruptedException {
		WebElement contents = wait.until(ExpectedConditions.elementToBeClickable(By.id("secondlist")));
		try{
		curPage = (ArrayList<WebElement>) contents.findElements(By.className("inside"));
		}catch(StaleElementReferenceException e){
			return this.hasBoxOnPage(index);
		}
		if(index >= curPage.size()){
			return false;
		}
		String atr= curPage.get(index).getAttribute("id");
		return driver.findElement(By.id(atr)).isDisplayed();
	}
}
