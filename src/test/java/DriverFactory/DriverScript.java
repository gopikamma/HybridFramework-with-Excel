package DriverFactory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;

import CommonFunctionaLibrary.FunctionLibrary;
import Utils.ExcelFileUtil;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DriverScript
{
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	public void startTest() throws Throwable
	{
		ExcelFileUtil excel = new ExcelFileUtil();
		for (int i = 1; i <=excel.rowCount("MasterTestCases"); i++) 
		{
			String ModuleStatus=" ";
			if (excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule = excel.getData("MasterTestCases", i, 1);
				report = new ExtentReports(".//Reports//"+TCModule+".html"+"_"+FunctionLibrary.getDate());
				logger = report.startTest(TCModule);
				int rowcount = excel.rowCount(TCModule);
				for (int j = 1; j <=rowcount; j++) 
				{
					String Description = excel.getData(TCModule, j, 0);
					String Object_Type = excel.getData(TCModule, j, 1);
					String Locator_Type = excel.getData(TCModule, j, 2);
					String Locator_Value = excel.getData(TCModule, j, 3);
					String Test_Data = excel.getData(TCModule, j, 4);
					try 
					{
						if (Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("openApplication"))
						{
							FunctionLibrary.openApplication(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("titleValidation"))
						{
							FunctionLibrary.titleValidation(driver, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value,Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						
						excel.setData(TCModule, j, 5, "PASS");
						ModuleStatus = "true";
						logger.log(LogStatus.PASS,Description+"   PASS");
					} catch (Exception e)
					{
						excel.setData(TCModule, j, 5, "FAIL");
						ModuleStatus = "false";
						logger.log(LogStatus.FAIL,Description+"   FAIL");
						File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcfile, new File(".//ScreenShots//"+Description+"_"+FunctionLibrary.getDate()+".jpg"));
						String image = logger.addScreenCapture(".//ScreenShots//"+Description+"_"+FunctionLibrary.getDate()+".jpg");
						
						logger.log(LogStatus.FAIL, "TCModule",image);
						
						break;
					} catch (AssertionError e)
					{
						excel.setData(TCModule, j, 5, "FAIL");
						ModuleStatus = "false";
						logger.log(LogStatus.FAIL,Description+"   FAIL");
						File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcfile, new File(".//ScreenShots//"+Description+"_"+FunctionLibrary.getDate()+".jpg"));
						String image = logger.addScreenCapture(".//ScreenShots//"+Description+"_"+FunctionLibrary.getDate()+".jpg");
						
						logger.log(LogStatus.FAIL, "TCModule",image);
						
						break;
					
					}
				}
				
				if (ModuleStatus.equalsIgnoreCase("true"))
				{
					excel.setData("MasterTestCases", i, 3, "PASS");
				}
				else
				{
					excel.setData("MasterTestCases", i, 3, "FAIL");
				}
				
			}
			
			else
			{
				excel.setData("MasterTestCases", i, 3, "Not Executed");
			}
			
			report.endTest(logger);
			report.flush();
		}
	}
}
