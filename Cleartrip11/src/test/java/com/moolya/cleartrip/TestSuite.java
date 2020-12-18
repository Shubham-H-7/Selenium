package com.moolya.cleartrip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestSuite {
	public String baseUrl = "https://www.cleartrip.com/";
	public WebDriver driver;
	
	
	@BeforeTest
	public void setBaseURL() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		String ExpTitle="#1 Site for Booking Flights, Hotels, Packages, Trains & Local activities.";
		Assert.assertEquals(ExpTitle, driver.getTitle());
	}

	@Test(priority = 1)
	public void SelectFight() {
		driver.findElement( By.xpath("//a[@title='Find flights from and to international destinations around the world']")).click();
	}

	@Test(priority = 2)
	public void SelectOneWay() {
		driver.findElement(By.id("OneWay")).click();
	}

	@Test(priority = 3)
	public void SelectCities() {
		WebElement fromcity = driver.findElement(By.xpath("//input[@id='FromTag']"));
		Assert.assertTrue(fromcity.isDisplayed());
		fromcity.sendKeys("Bangalore");
		fromcity.click();

		WebElement tocity = driver.findElement(By.xpath("//input[@id='ToTag']"));
		Assert.assertTrue(tocity.isDisplayed(),"To city not displayed");
		tocity.sendKeys("New delhi");
		tocity.click();
	}

	@Test(priority = 4)
	public void SelectDate() {
		DateFormat dateformat = new SimpleDateFormat("DD/MM/YYYY");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 30);
		String res = dateformat.format(cal.getTime());
		System.out.println("Recent date i got**"+res); 
		
		WebElement date =driver.findElement(By.id("DepartDate"));
		date.sendKeys(res);
	}

	@Test(priority = 5)
	public void SelectPassengers() {
		Select adults = new Select(driver.findElement(By.id("Adults")));
		adults.selectByValue("1");

		Select child = new Select(driver.findElement(By.id("Childrens")));
		child.selectByValue("1");

		Select infants = new Select(driver.findElement(By.id("Infants")));
		infants.selectByValue("1");
	}

	@Test(priority = 6)
	public void SearchFlights() {
		driver.findElement(By.id("SearchBtn")).click();
	}
	
	@Test(priority = 7)
	public void NonStop() {
		driver.findElement(By.xpath("//div[.='Non-stop']")).click();
	}

	@Test(priority = 8)
	public void Price() throws InterruptedException {	
		WebElement slider = driver.findElement(By.xpath("(//div[@class='input-range'])[1]"));
		Actions a = new Actions(driver);
		a.click();
		a.dragAndDropBy(slider, -300, 0).build().perform();
		a.release();
	}
	
	@Test(priority = 9)
	public void uncheckBox() {
		WebElement ckb= driver.findElement(By.xpath("(//div[@class='flex flex-start pl-2'])[10]"));
		boolean status;
		status=ckb.isSelected();
		if(status == false) {
			ckb.click();
			System.out.println("checkbox is unchecked");
		}else {
			System.out.println("checkbox is already unchecked");
		}
	}

	@Test(priority = 10)
	public void Book() {
		driver.findElement(By.xpath("(//div/button[.='Book'])[1]")).click();
	}
	 
	@Test(priority = 11)
	public void ContactDetails() throws Exception {   
		String title =driver.getTitle();
		System.out.println("main window"+title);
		String your_title = "Cleartrip | Bangalore ? New Delhi";
		Set<String> windowsIds = driver.getWindowHandles();
		Iterator<String> iter = windowsIds.iterator();
			
		String mainwindow = iter.next();
		String childWindow = iter.next();
			
		driver.switchTo().window(childWindow);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String childt=driver.getTitle();
		System.out.println("child****"+ childt);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2500)", "");
		driver.findElement(By.xpath("//dd/input[@class='booking']")).click(); 
		
	   }
	@Test(priority = 12)
	public void details() {
	driver.findElement(By.xpath("//input[@etitle=\"Your email address\"]")).sendKeys("shubhamhiremath1996@gmail.com");
	driver.findElement(By.xpath("//input[@class='booking hotelButton']")).click();
	}
	
	@Test(priority = 13)
	public void travellerDetails() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@idfield=\"adultId1\"])[1]")));
		Select title1 = new Select(driver.findElement(By.xpath("(//select[@id='AdultTitle1'])[1]")));
		title1.selectByVisibleText("Mr");
		driver.findElement(By.xpath("(//input[@id='AdultFname1'])[1]")).sendKeys("shubham");
		driver.findElement(By.xpath("(//input[@id='AdultLname1' and @selflabel=\"Last Name / Surname\"])[1]")).sendKeys("Hiremath");
		
		Select title2 = new Select(driver.findElement(By.xpath("//select[@id='ChildTitle1']")));
		title2.selectByVisibleText("Mstr");
		driver.findElement(By.xpath("//input[@id='ChildFname1']")).sendKeys("niket");
		driver.findElement(By.xpath("(//input[@id='ChildLname1' and @selflabel=\"Last Name / Surname\"])[1]")).sendKeys("Hiremath");
		
		Select dd= new Select(driver.findElement(By.id("ChildDobDay1")));
		dd.selectByValue("7");
		Select mm= new Select(driver.findElement(By.id("ChildDobMonth1")));
		mm.selectByVisibleText("Sep");
		Select yy= new Select(driver.findElement(By.id("ChildDobYear1")));
		yy.selectByValue("2016");
		
		Select title3 = new Select(driver.findElement(By.xpath("//select[@name='InfantTitle1']")));
		title3.selectByVisibleText("Mstr");
		driver.findElement(By.xpath("//input[@id='InfantFname1']")).sendKeys("Sadanand");
		driver.findElement(By.xpath("(//input[@id='InfantLname1' and @selflabel=\"Last Name / Surname\"])[1]")).sendKeys("Hiremath");
		
		
		Select infd= new Select(driver.findElement(By.id("InfantDobDay1")));
		infd.selectByValue("18");
		Select infm= new Select(driver.findElement(By.id("InfantDobMonth1")));
		infm.selectByVisibleText("Nov");
		Select infy= new Select(driver.findElement(By.id("InfantDobYear1")));
		infy.selectByValue("2019");
		
		driver.findElement(By.xpath("(//dd/input[@id='mobileNumber'])[1]")).sendKeys("7411277939");
		
		driver.findElement(By.xpath("//input[@id='travellerBtn']")).click();
	}
	
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
}

