package CommonFunctionaLibrary;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utils.PropertyFileUtil;

public class FunctionLibrary
{
	public static WebDriver startBrowser(WebDriver driver) throws FileNotFoundException, Throwable
	{
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
		}
		else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver",".\\CommonJarFiles\\chromedriver.exe" );
			driver = new ChromeDriver();
		}
		else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Ie"))
		{
			System.setProperty("webdriver.chrome.driver",".\\CommonJarFiles\\IEDriverServer.exe" );
			driver = new InternetExplorerDriver();
		}
		
		return driver;
	}

	public static void openApplication(WebDriver driver) throws FileNotFoundException, Throwable
	{
		driver.manage().window().maximize();
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}

	public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
	{
		if (LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).click();
		}
		else if (LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		else if (LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
	}

	public static void typeAction(WebDriver driver,String LocatorType,String LocatorValue,String data)
	{
		if (LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(data);
		}
		else if (LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(data);
	
		}
		else if (LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(data);
	
		}

	}

	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String waittime)
	{
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(waittime));
		if (LocatorType.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		else if (LocatorType.equalsIgnoreCase("name"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		else if (LocatorType.equalsIgnoreCase("xpath"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
	}

	public static void titleValidation(WebDriver driver,String validData) throws InterruptedException
	{
		Thread.sleep(5000);
		String act_title = driver.getTitle();
		Thread.sleep(2000);
		String exp_title = validData;
		Assert.assertEquals(act_title, exp_title);
	}

	public static String getDate()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd_ss");
		return sdf.format(date);
	}
}
