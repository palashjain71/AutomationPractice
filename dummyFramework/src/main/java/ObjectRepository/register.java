package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;

import SupportLibraries.Util;
import SupportLibraries.report;

public class register {

	WebDriver driver;
	ExtentTest test;

	public register(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	String url = "/coursedemo_reg.php";

	@FindBy(xpath = "//div[text()='Register']")
	WebElement heading_register;

	public void isPageOpened() {
		if (driver.getCurrentUrl().contains(url)) {
			Util.waitForElement(driver, heading_register);
			report.PassTest(test, driver, "page opened", "register");
		} else {
			report.FailTest(test, driver, "page not opened", "register");
		}
	}

	
	@FindBy(xpath = "//*[@name='fname']")
	WebElement fname;
	
	@FindBy(xpath = "//*[@name='lastname']")
	WebElement lname;
	
	public void registerPagefillformDetails(String firstname,String lastname) {
		
		fname.sendKeys(firstname);
		lname.sendKeys(lastname);
		

	}
	
	
	
}
