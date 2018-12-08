package package1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import Driver.BrowserOpen;
import SupportLibraries.Util;
import SupportLibraries.propertiesRead;
import SupportLibraries.report;
import TestData.GlobalTestData;

public class BaseClass {

	public static WebDriver driver;

	public static ExtentTest test;
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public static String systemDir = System.getProperty("user.dir");
	public static String ExcelPath;
	public static Properties prop;
	public static String ResultFolderPath;
	public static String TestDataSheetPath = System.getProperty("user.dir") + "\\src\\main\\java\\TestData\\";
	
	@BeforeSuite
	public void initializeWebdriver() throws IOException, InterruptedException {

		ResultFolderPath = systemDir + "/test-output" + "/" + GlobalTestData.CurrentDateFolder + "/";
		Util.CreateFolder(ResultFolderPath);
		prop = propertiesRead.readProperty();
		htmlReporter = report.StartHtmlFinalReport(htmlReporter, prop.getProperty("browserName"), ResultFolderPath);
		extent = report.StartExtentReport(htmlReporter, extent);
	}

	@BeforeClass
	public void beforeTestKillBrowser(ITestContext contextContext) {
		try {
			test = report.testCreate(extent, test, "Test :->> " + contextContext.getName());
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@BeforeMethod
	public void launchApplication(ITestContext contextContext) throws MalformedURLException {
		driver = BrowserOpen.StartBrowser(driver, prop.getProperty("browserName"), prop.getProperty("URL"));

	}

	@AfterMethod
	public void quitWebdriver(ITestContext contextContext) {
		driver.quit();
	}

	@AfterSuite
	public void sendReportEmail() {
		report.endReport(extent);
	}
}
