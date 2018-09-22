package pakge2;

import java.io.IOException;

import org.testng.annotations.Test;

import ObjectRepository.homePage;
import SupportLibraries.report;
import package1.BaseClass;

public class test1 extends BaseClass {
	@Test(groups = "Smoke")
	public void test12() throws InterruptedException, IOException {
		try {

			report.InfoTest(test, "Navigates to " + prop.getProperty("URL"));

			// Handle Survey

			

			homePage hp = new homePage(driver, test);
			report.InfoTest(test, "Navigate to customer support Page");
			

			report.EndTest(test, driver);

		} catch (Exception e) {
			report.InfoTest(test, " message " + e.getMessage());
			report.FailTest(test, driver, "Element not visible ", "Element");
		}
	}

}
