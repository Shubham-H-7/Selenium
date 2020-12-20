package com.moolya.cleartrip;

import java.io.IOException;
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
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestSuite {
	
	public String baseUrl = "https://www.cleartrip.com/";
	public WebDriver driver;
	private SoftAssert sa;
	private SoftAssert sa1;
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	
	@BeforeTest
	public void setBaseURL() {
		htmlReporter = new ExtentHtmlReporter("extentReports.html");
		extent= new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		ExtentTest test1 = extent.createTest("Opening Cleartrip website");
		driver = new ChromeDriver();
		test1.log(Status.INFO, "Starting Test case");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseUrl);
		test1.pass("Navigated to Cleartrip.com");
		String ExpTitle="#1 Site for Booking Flights, Hotels, Packages, Trains & Local activities.";
		Assert.assertEquals(ExpTitle, driver.getTitle());
		test1.log(Status.INFO, "SetBaseURL Completed");
	}

	@Test(priority = 1)
	public void selectFight() {
		ExtentTest test2=extent.createTest("Select Fight");
		driver.findElement( By.xpath("//a[@title='Find flights from and to international destinations around the world']")).click();
		test2.pass("Clicked on search Fight");
	}

	@Test(priority = 2)
	public void selectOneWay() {
		ExtentTest test3=extent.createTest("Click on ‘One way’ radio button");
		WebElement oneWay= driver.findElement(By.id("OneWay"));
		oneWay.click();
		Assert.assertTrue(oneWay.isSelected(),"OneWay Unchecked");
		test3.pass("Clicked on One way radio button");
	}

	@Test(priority = 3)
	public void selectCities() {
		ExtentTest test4 = extent.createTest("Enter the ‘From city’ and ‘To city’ details");
		WebElement fromcity = driver.findElement(By.xpath("//input[@id='FromTag']"));
		Assert.assertTrue(fromcity.isDisplayed());
		fromcity.sendKeys("Bangalore");
		fromcity.click();
		test4.pass("Bangalore city is selected");

		WebElement tocity = driver.findElement(By.xpath("//input[@id='ToTag']"));
		Assert.assertTrue(tocity.isDisplayed(),"To city not displayed");
		tocity.sendKeys("New delhi");
		tocity.click();
		test4.pass("Delhi city is selected"); 
	}

	@Test(priority = 4)
	public void selectDate() throws IOException {
		ExtentTest test5 = extent.createTest("Select a date 30 days from today’s date as travel date");
		DateFormat dateformat = new SimpleDateFormat("DD/MM/YYYY");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 30);
		String res = dateformat.format(cal.getTime()); 
		WebElement date =driver.findElement(By.id("DepartDate"));
		date.sendKeys(res);
		test5.info("Date is selected +30 from todays date",MediaEntityBuilder.createScreenCaptureFromPath("screen.png").build());
		test5.pass("Date is selected");
	}

	@Test(priority = 5)
	public void selectPassengers() {
		ExtentTest test6 = extent.createTest("Select adults 1, children 1 and infants 1");
		Select adults = new Select(driver.findElement(By.id("Adults")));
		adults.selectByValue("1");

		Select child = new Select(driver.findElement(By.id("Childrens")));
		child.selectByValue("1");

		Select infants = new Select(driver.findElement(By.id("Infants")));
		infants.selectByValue("1");
		test6.pass("Passengers are selected");
	}

	@Test(priority = 6)
	public void searchFlights() {
		ExtentTest test7 = extent.createTest("Click on search flights");
		sa = new SoftAssert();
		WebElement search =driver.findElement(By.id("SearchBtn"));
		sa.assertTrue(search.isDisplayed(),"search not displayed");
		search.click();
		test7.pass("Cliked on search flights");
	}
	
	@Test(priority = 7)
	public void nonStop() {
		ExtentTest test8 = extent.createTest("Select Non-stop checkbox");
		driver.findElement(By.xpath("//div[.='Non-stop']")).click();
		test8.pass("Checked the Non-stop checkbox");
	}

	@Test(priority = 8)
	public void priceRange() throws InterruptedException {	
		ExtentTest test9 = extent.createTest("Select one way price as up-to 14000 range");
		WebElement slider = driver.findElement(By.xpath("(//div[@class='input-range'])[1]"));
		Actions a = new Actions(driver);
		a.click();
		a.dragAndDropBy(slider, -300, 0).build().perform();
		a.release();
		test9.pass("Price range is selected");
	}
	
	@Test(priority = 9)
	public void uncheckBox() {
		ExtentTest test10 = extent.createTest("Uncheck ‘show Multi line airline itineraries’ checkbox");
		WebElement ckb= driver.findElement(By.xpath("(//div[@class='flex flex-start pl-2'])[10]"));
		boolean status;
		status=ckb.isSelected();
		if(status == false) {
			ckb.click();
			System.out.println("checkbox is unchecked");
		}else {
			System.out.println("checkbox is already unchecked");
		}
		test10.pass("Unchecked Multi line airline checkbox");
	}

	@Test(priority = 10)
	public void book() {
		ExtentTest test11 = extent.createTest("Click on Book button on any airline");
		sa1 = new SoftAssert();
		WebElement book= driver.findElement(By.xpath("(//div/button[.='Book'])[1]"));
		sa1.assertTrue(book.isDisplayed(),"Book button not displayed");
		book.click();
		test11.pass("Clicked on book button");
	}
	 
	@Test(priority = 11)
	public void contactDetails() throws Exception {  
		ExtentTest test12 = extent.createTest("In the itinerary page, click on continue button");
		Set<String> windowsIds = driver.getWindowHandles();
		Iterator<String> iter = windowsIds.iterator();
			
		String mainwindow = iter.next();
		String childWindow = iter.next();
			
		driver.switchTo().window(childWindow);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2500)", "");
		driver.findElement(By.xpath("//dd/input[@class='booking']")).click();
		test12.pass("clicked on continue button");
		}
	
	@Test(priority = 12)
	public void details() {
		ExtentTest test13 = extent.createTest("In contact details page, enter mobile number and email id and click on continue");
		driver.findElement(By.xpath("//input[@etitle=\"Your email address\"]")).sendKeys("shubhamhiremath1996@gmail.com");
		driver.findElement(By.xpath("//input[@class='booking hotelButton']")).click();
		test13.pass("Contact details filled");
	}
	
	@Test(priority = 13)
	public void travellerDetails() {
		ExtentTest test14 = extent.createTest("Add the traveller details and click on continue to payment");
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
		test14.pass("Filled travellers details");
		
		driver.findElement(By.xpath("//input[@id='travellerBtn']")).click();
		test14.pass("Clicked on continue for payment");
	}
	
	@AfterTest
	public void closeBrowser() {
		sa.assertAll();
		sa1.assertAll();
		driver.quit();
		extent.flush();
		
	}
}

