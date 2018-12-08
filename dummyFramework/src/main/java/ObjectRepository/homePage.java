package ObjectRepository;


import java.util.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;
import SupportLibraries.report;

public class homePage {

	WebDriver driver;
	ExtentTest test;

	public homePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(xpath= "//a[text()='Sign Up']")
	WebElement signUpLink;
	
	
	public void clickOnSignUp() {
		signUpLink.click();
		report.InfoTest(test, "Clicked on sign up link");
	}
	
	
	@FindBy(xpath= "//input[@id='jfHeader_suUsername']")
	WebElement userNameTextBox;
	@FindBy(xpath= "//input[@id='jfHeader_suEmail']")
	WebElement EmailTextBoxLink;
	@FindBy(xpath= "//input[@id='jfHeader_suPassword']")
	WebElement passwordTextBox;
	@FindBy(xpath= "//input[@id='jfHeader_suPasswordConf']")
	WebElement confirmPasswordTextBox;
	
	@FindBy(xpath= "//button[@id='jfHeader_signupButton']")
	WebElement confirmBtn;
	
	
	public void fillSignupForm(String username, String email,String password) {
		
		userNameTextBox.sendKeys(username);
		EmailTextBoxLink.sendKeys(email);
		passwordTextBox.sendKeys(password);
		confirmPasswordTextBox.sendKeys(password);
		
		report.PassTest(test, driver, "sign up form filled", "signup");
		confirmBtn.click();
		report.InfoTest(test, "btn Clicked");
		
	}
	
	}
