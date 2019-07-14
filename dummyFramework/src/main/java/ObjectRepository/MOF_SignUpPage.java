package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;

import SupportLibraries.Util;
import SupportLibraries.report;

public class MOF_SignUpPage {

	WebDriver driver;
	ExtentTest test;

	public MOF_SignUpPage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h3[text()='Sign up']")
	private WebElement heading_MOF_SignUp;

	public boolean isPageOpened() {
		Util.waitForElement(driver, heading_MOF_SignUp);
		if (driver.getCurrentUrl().contains("signup")) {
			report.PassTest(test, driver, "sign up page opened", "signup");
			return true;
		} else {
			report.FailTest(test, driver, "sign up page not opened", "signup");
			return false;
		}
	}

	
	@FindBy(xpath = "//input[@placeholder='Email address']")
	private WebElement txt_email;

	@FindBy(xpath = "//input[@placeholder='Name']")
	private WebElement txt_name;

	@FindBy(xpath = "//select[contains(@id,'MobileCountryCode')]")
	private WebElement selectCountryCode;

	@FindBy(xpath = "//input[@placeholder='Mobile Phone Number']")
	private WebElement txtMobilePhn;

	
	public void enterSignupDetails(String emailAddress,String name,String mobileNum,String OfficeNum) {
		
		Util.sendKeysToElement(driver, txt_email, emailAddress, true);
		Util.sendKeysToElement(driver, txt_name, name, true);
		Util.SelectOption(driver, test, selectCountryCode, "India (+91)");
		Util.sendKeysToElement(driver, txtMobilePhn, mobileNum, true);
		
	}
	
	
	
	
	@FindBy(xpath = "//div[@class='register-now-link']//a[@href='/signup']")
	private WebElement txt_username;

	@FindBy(xpath = "//div[@class='register-now-link']//a[@href='/signup']")
	private WebElement txt_password;

	public void enterloginDetials(String username, String password) {

		Util.sendKeysToElement(driver, txt_username, username, true);
		Util.sendKeysToElement(driver, txt_password, password, true);
		report.InfoTest(test, "usernme entered -->" + username + 
				"password enter --> " + password);
	}

}
