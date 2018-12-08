package SupportLibraries;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestData.GlobalTestData;

public class report {
	public static String reportName;
	public static SoftAssert s_assert = new SoftAssert();

	public static ExtentHtmlReporter StartHtmlFinalReport(ExtentHtmlReporter htmlReporter, String browserName,
			String ResultFolderPath) {

		htmlReporter = new ExtentHtmlReporter(
				(ResultFolderPath + "Consolidated_ExtentReport_" + browserName + Util.randomNum() + ".html"));
		htmlReporter.config().setDocumentTitle("Automation Execution Report");
		htmlReporter.config().setReportName("Test Execution Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		return htmlReporter;
	}

	public static ExtentHtmlReporter StartHtmlIndividualTestCaseReport(ExtentHtmlReporter htmlReporter,
			String browserName, String TcName, String ResultFolderPath) {
		if (TcName.length() > 20) {
			TcName = TcName.trim().replaceAll(" ", "").substring(0, 20);
		} else {
			TcName = TcName.trim().replaceAll(" ", "");
		}
		String IndividualReports = ResultFolderPath + "IndividualTestReports/";
		Util.CreateFolder(IndividualReports);
		reportName = IndividualReports + browserName + "_" + TcName + Util.DateTimeString() + ".html";
		htmlReporter = new ExtentHtmlReporter(reportName);
		htmlReporter.config().setDocumentTitle("Automation Execution Report");
		htmlReporter.config().setReportName("Test Execution Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		return htmlReporter;
	}

	public static ExtentReports StartExtentReport(ExtentHtmlReporter htmlReporter, ExtentReports extent) {
		Properties prop = propertiesRead.readProperty();
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", prop.getProperty("OS"));
		extent.setSystemInfo("Host Name", prop.getProperty("HostName"));
		extent.setSystemInfo("Env", prop.getProperty("Env"));
		return extent;
	}

	@Test
	public static ExtentTest testCreate(ExtentReports extent, ExtentTest test, String Stepdetails) {
		test = extent.createTest(Stepdetails, Stepdetails);
		return test;
	}

	public static void PassTest(ExtentTest test, String Stepdetails) {
		try {
			test.pass(MarkupHelper.createLabel(Stepdetails, ExtentColor.GREEN));
			AssertJUnit.assertTrue(true);
		} catch (Exception e) {
			report.InfoTest(test, "Error while reporting" + e.getMessage());
			
		}
	}

	public static void InfoTest(ExtentTest test, String Stepdetails) {
		try {
			test.info(MarkupHelper.createLabel(Stepdetails, ExtentColor.ORANGE));
			
		} catch (Exception e) {
			report.InfoTest(test, "Error while reporting" + e.getMessage());
		}
	}

	public static void PassTest(ExtentTest test, WebDriver driver, String Stepdetails, String screenshotName) {
		try {

			String ResultFolderPath = System.getProperty("user.dir") + "\\test-output\\"
					+ GlobalTestData.CurrentDateFolder + "\\IndividualTestReports\\ScreenShot";
			Util.CreateFolder(ResultFolderPath);
			String screenshotPath = Util.capture(driver, screenshotName, ResultFolderPath);
			String relativePath = "ScreenShot/" + screenshotPath;
			test.pass(MarkupHelper.createLabel(Stepdetails, ExtentColor.GREEN));
			test.pass(MarkupHelper.createLabel(" <a href='" + relativePath
					+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> ScreenShot link :" + screenshotName
					+ "</a>", ExtentColor.BLUE));

		} catch (IOException e) {
			report.InfoTest(test, " message " + e.getMessage());
			report.FailTest(test, driver, "File IO Exception  ", "Exception");
		}
	}

	public static void StartTest(ExtentTest test, String Stepdetails) {
		try {
			test.info(MarkupHelper.createLabel(Stepdetails, ExtentColor.INDIGO));
			
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}

	public static void EndTest(ExtentTest test, WebDriver driver) {
		try {
			report.PassTest(test, driver, "Complete", "End");
			test.info(MarkupHelper.createLabel("Test Execution Completed", ExtentColor.INDIGO));
			
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}

	public static void EndTestAPI(ExtentTest test) {
		try {
			test.info(MarkupHelper.createLabel("Test Execution Completed", ExtentColor.INDIGO));
			
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}

	public static void reportLink(ExtentTest test) {
		test.info(MarkupHelper.createLabel("  <a href='" + reportName
				+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> Test Case link : " + reportName + "</a>",
				ExtentColor.BLUE));
	}

	public static void FailTest(ExtentTest test, WebDriver driver, String Stepdetails, String screenshotName) {
		try {
			test.fail(MarkupHelper.createLabel(Stepdetails, ExtentColor.RED));
			AssertJUnit.assertTrue(false);
			if (screenshotName != "") {
				String ResultFolderPath = System.getProperty("user.dir") + "\\test-output\\"
						+ GlobalTestData.CurrentDateFolder + "\\IndividualTestReports\\ScreenShot";
				Util.CreateFolder(ResultFolderPath);
				String screenshotPath = Util.capture(driver, screenshotName, ResultFolderPath);
				String relativePath = "ScreenShot/" + screenshotPath;
				test.fail(MarkupHelper.createLabel(" <a href='" + relativePath
						+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> ScreenShot link : " + screenshotName
						+ "</a>", ExtentColor.BLUE));
			}
		} catch (IOException e) {
			report.FailTest(test, driver, "Test Case fail while capturing the screenshot", screenshotName);

		}
	}

	public static void FailTest(ExtentTest test, String Stepdetails) {
		try {
			test.fail(MarkupHelper.createLabel(Stepdetails, ExtentColor.RED));
			AssertJUnit.assertTrue(false);
		} catch (Exception e) {
			report.FailTest(test, "failed test case ");
		}
	}

	public static void ConditionCheckTest(ExtentTest test, String expectedstring, String actualstring, String ObjDesc) {

		if (actualstring.replaceAll(" ", "").toLowerCase().trim().replaceAll(",","")
				.contains(expectedstring.replaceAll(" ", "").replaceAll(",","").toLowerCase().trim())) {
			test.log(Status.PASS, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.GREEN));
		} else {
			test.log(Status.FAIL, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.RED));
		}
	}

	public static void ConditionCheckTest_containsValueNegative(ExtentTest test, String actualstring,
			String expectedstring, String ObjDesc) {

		if (actualstring.contains(expectedstring)) {
			test.log(Status.FAIL, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.RED));
		} else {
			test.log(Status.PASS, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.GREEN));
		}
	}

	public static void FailTestStop(ExtentTest test, String Stepdetails) {

		test.fail(MarkupHelper.createLabel(Stepdetails, ExtentColor.RED));
		AssertJUnit.assertTrue(false);
	}

	public static void ConditionCheckTest_containsValuePositive(ExtentTest test, String actualstring,
			String expectedstring, String ObjDesc) {
		if (actualstring.contains(expectedstring)) {
			test.log(Status.PASS, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.GREEN));
		} else {
			test.log(Status.FAIL, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.RED));
		}
	}

	public static void ConditionCheck_doubleValues(ExtentTest test, double actualstring, double expectedstring,
			String ObjDesc) {
		if (Double.compare(actualstring, expectedstring) == 0) {
			test.log(Status.PASS, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.GREEN));
		} else {
			test.log(Status.FAIL, MarkupHelper.createLabel(
					("Object>>" + ObjDesc + "Actual value :" + actualstring + " Expected Value :" + expectedstring),
					ExtentColor.RED));
		}

	}

	public static void endReport(ExtentReports extent) {
		extent.flush();
	}

}
