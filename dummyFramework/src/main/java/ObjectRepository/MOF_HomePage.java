package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;

import SupportLibraries.Util;
import SupportLibraries.report;

public class MOF_HomePage {

	WebDriver driver;
	ExtentTest test;

	public MOF_HomePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h3[text()='Login']")
	private WebElement heading_MOF_Home;

	public boolean isPageOpened() {
		Util.waitForElement(driver, heading_MOF_Home);
		if (driver.getCurrentUrl().contains("login")) {
			report.PassTest(test, driver, "Home page opened", "homepage");
			return true;
		} else {
			report.FailTest(test, driver, "homepage not opened", "homepage");
			return false;
		}
	}

	@FindBy(xpath = "//div[@class='register-now-link']//a[@href='/signup']")
	private WebElement btn_loginFormSignUp;

	public void clickOnresgisterHere() {
		btn_loginFormSignUp.click();
		report.InfoTest(test, "Sign Up link clicked");
	}

	@FindBy(xpath = "//input[@placeholder='Email address']")
	private WebElement txt_username;

	@FindBy(xpath = "//input[@placeholder='Password']")
	private WebElement txt_password;

	public void enterloginDetials(String username, String password) {

		Util.sendKeysToElement(driver, txt_username, username, true);
		Util.sendKeysToElement(driver, txt_password, password, true);
		report.InfoTest(test, "usernme entered -->" + username + 
				"password enter --> " + password);
		Util.HardWait(1000);
	}

}
