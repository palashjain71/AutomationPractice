package ObjectRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import SupportLibraries.Util;
import SupportLibraries.functionLibrary;
import SupportLibraries.report;

public class homePage {

	WebDriver driver;
	ExtentTest test;
	Properties prop;

	public homePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(xpath = "//div[@class='NaturalLanguageDropdown third ']//span[@class='isvg loaded carret-down']")
	WebElement ServiceType;

	@FindBy(xpath = ".//a[@class='GetMeStarted btn btnGetStarted']")
	WebElement GetStarted;

	public void handleSurvey() {
		boolean present;
		try {
			
			present = true;
		} catch (NoSuchElementException e) {
			present = false;
		}
		if (present) {
			report.PassTest(test, "Survey_Option_Present");
		}
	}

	}
