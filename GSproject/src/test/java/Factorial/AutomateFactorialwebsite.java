package Factorial;

import org.testng.annotations.Test;

//import com.beust.jcommander.Parameters;
import org.testng.annotations.Parameters;

import org.testng.annotations.Optional;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;


public class AutomateFactorialwebsite {
	
	public static Properties p;
	public WebDriver driver;
	public static WebElement element;
	public static WebElement buttonelement;
	public boolean Flag;
	public static WebElement factorial;
	public static WebElement issue;
	public static int count = 1;
	public static int calculate=1;
	
	
	@Parameters("browserName")
	@BeforeClass
	public void launchApplication(String browserName) throws IOException {
		if(browserName.contains("Chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.contains("Edge"))
		{
			 WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		p = new Properties();
		FileInputStream file = new FileInputStream("src/test/resources/Configuration/config.properties");
		p.load(file);
		String request = p.getProperty("url");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(request);
	}

	@DataProvider(name = "Dataprovider")
	public Object[][] dpmethod() {
		return new Object[][] { { "//input[@name='number']" }, { "//button[@id='getFactorial']" },
				{ "//a[@href='/privacy']" }, { "//a[@href='/terms']" } };
	}
	
   @Test
	public void step1(String data) {
		Flag = driver.findElement(By.xpath(data)).isEnabled();
		if (Flag) {
			System.out.println("################# Step " + count + " passed ##############################");
			count++;
		} else {
			Assert.assertTrue(false);
		}
	}
   
    @Test
	public void Class1() throws InterruptedException {
		boolean flag = true;
		Thread.sleep(5000);
		element = driver.findElement(By.xpath("//input[@name='number']"));
		String placeholderr = element.getAttribute("placeholder");
		if (placeholderr.equals("Enter an integer")) {
			System.out.println(
					"################# Step 2 Passed (Placeholder in Textbox should = 'Enter an integer')##############################");
		} else {
			System.out.println(
					"################# Step 2 Failed (Placeholder in Textbox should = 'Enter an integer')##############################");
			flag = false;
		}
		if (driver.getTitle().equals("Factorial")) {
			System.out.println(
					"################# Step 2 Passed Verify the Title of the page = 'Factorial' ##############################");
		} else {
			System.out.println(
					"################# Step 2 Failed Verify the Title of the page = 'Factorial' ##############################");
			flag = false;
		}
		if (driver.getCurrentUrl().contains("https")) {
			System.out.println(
					"################# Step 2 Passed Verify the url contains = 'https' ##############################");
		} else {
			System.out.println(
					"################# Step 2 Failed Verify the url contains = 'https' ##############################");
			flag = false;
		}

		if (flag) {
			System.out.println(" Class 2 Passed");
		} else {
			System.out.println(" Class 2 Failed");
			Assert.assertTrue(false);
		}
	}

	@Test
	public void Class3() throws InterruptedException {
	    boolean flag = true;

	    for (int i = 2; i <= 10; i++) {
	    	Thread.sleep(3000);
	        element = driver.findElement(By.xpath("//input[@name='number']"));
	        element.clear();
	        String numbertoint = Integer.toString(i);
	        element.sendKeys(numbertoint);
	        buttonelement = driver.findElement(By.xpath("//button[@id='getFactorial']"));
	        buttonelement.click();
	        factorial=driver.findElement(By.xpath("//p[@id='resultDiv']"));
	        // Wait for the resultDiv element to be visible
	        Thread.sleep(5000);
	        String text = factorial.getText();
	        System.out.println(text);
	        int expected = calculateFactorial(i);
	        String expectedText = "The factorial of " + i + " is: " + expected ;
	        System.out.println("Expected: " + expectedText);
	        System.out.println("Actual: " + text);
	        if (!text.equals(expectedText)) {
	            flag = false;
	            break;
	        }
	    }

	    if (flag) {
	        System.out.println("################# Class 3 Passed ##############################");
	    } else {
	        System.out.println("################# Class 3 Failed ##############################");
	        Assert.assertTrue(false);
	    }
	}
	
	@Test
	public void Class4() throws InterruptedException {
	    boolean flag=true;

	    issue = driver.findElement(By.xpath("//a[text()='Privacy']"));
	    issue.click();
	    Thread.sleep(3000); // Wait for page transition
	    String actualPrivacyUrl = driver.getCurrentUrl();
	    System.out.println(actualPrivacyUrl);
	    if (actualPrivacyUrl.equals("https://qainterview.pythonanywhere.com/privacy")!=true) {
	        flag = false;
	    }

	    driver.navigate().back();
	    Thread.sleep(3000); // Wait for page transition

	    issue = driver.findElement(By.xpath("//a[text()='Terms and Conditions']"));
	    issue.click();
	    Thread.sleep(3000); // Wait for page transition

	    String actualTermsUrl = driver.getCurrentUrl();
	    if (actualTermsUrl.equals("https://qainterview.pythonanywhere.com/terms")!=true) {
	        flag = false;
	    }

	    driver.navigate().back();
	    Thread.sleep(3000); // Wait for page transition

	    if (flag) {
	        System.out.println("################# Class 4 Passed ##############################");
	    } else {
	        System.out.println("################# Class 4 Failed ##############################");
	        Assert.assertTrue(false);
	    }
	}
	@AfterClass
	public void closeapplication() {
		driver.close();
	}

	
	public static int calculateFactorial(int n) {
        if (n == 0)
            return 1;
        else
            return n * calculateFactorial(n - 1);
    }
}
