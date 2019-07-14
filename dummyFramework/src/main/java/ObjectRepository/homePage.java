package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;

import SupportLibraries.Util;
import SupportLibraries.report;

public class homePage {

	WebDriver driver;
	ExtentTest test;

	public homePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h2[contains(text(),\"Get Your Learner's Permit\")]")
	private WebElement heading_LearnerPermits;

	@FindBy(xpath = "//p[contains(text(),\"Get started with Your Drivers Ed driving school today!\")]")
	private WebElement subHeading_LearnerPermits;

	@FindBy(xpath = "//h2[contains(text(),\"Driver Education\")]")
	private WebElement heading_DriverEducation;

	@FindBy(xpath = "//a[text()='Free Demo']")
	private WebElement btn_headerFreeDemo;

	@FindBy(xpath = "//a[text()='Free Demo']")
	private WebElement btn_test;

	public void verifyHeaderSection() {

		if (heading_LearnerPermits.isDisplayed()) {
			report.PassTest(test, "header learner permit present ");
		} else {
			report.FailTest(test, "header learner permit not present");
		}

		if (subHeading_LearnerPermits.isDisplayed()) {
			report.PassTest(test, "subHeading_LearnerPermits present ");
		} else {
			report.FailTest(test, "subHeading_LearnerPermits not present");
		}

		if (heading_DriverEducation.isDisplayed()) {
			report.PassTest(test, "heading_DriverEducation present ");
		} else {
			report.FailTest(test, "heading_DriverEducation not present");
		}

	}

	public void clickOnHeaderFreeDemo() {
		btn_headerFreeDemo.click();
		report.InfoTest(test, "Clicked on Free Demo");
	}

}
