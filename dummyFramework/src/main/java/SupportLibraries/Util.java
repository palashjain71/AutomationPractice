package SupportLibraries;

import java.io.IOException;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;


public class Util {

	public String ResultFolderPath = "";

	public static String capture(WebDriver driver, String screenshotName, String ResultFolderPath) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String FileName = screenshotName + randomNum() + ".jpg";
		String dest = ResultFolderPath + "\\" + screenshotName + randomNum() + ".jpg";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		return FileName;
	}

	
	public static void waitForElement(WebDriver driver, ExtentTest test, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {

			report.InfoTest(test, " message " + e.getMessage());
			report.FailTest(test, driver, "Element not visible ", "Element");
		}
	}

	public static void waitForElementMoreThan60Secs(WebDriver driver, ExtentTest test, WebElement element,
			int TimeToWait) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeToWait + 1);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {

			report.InfoTest(test, " message " + e.getMessage());
			report.FailTest(test, driver, "Element not visible ", "Element");
		}
	}


	public static void waitForElementInvisible(WebDriver driver, WebElement element, ExtentTest test)
			throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

public static void SelectOption(WebDriver driver, ExtentTest test, WebElement element, String selectOptionValue) {
		Util.waitForElement(driver, test, element);
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(selectOptionValue);
}

	public static String randomNum() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
	}

	public static int RandomNumberInRange(int max, int min) {
		Random rn = new Random();
		int result = rn.nextInt(max - min + 1) + min;
		return result;
	}

	public static void FileExistence(String Filepath) {
		File f = new File(Filepath);

		if (f.exists()) {
			f.delete();

		} else {
			System.out.println("File not exist!");
		}
	}

	public static String Todaysdateinparticluarformat() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		return dateFormat.format(date).toUpperCase();

	}

	public static boolean CreateFolder(String FolderPath) {
		boolean result = false;
		try {
			File directory = new File(FolderPath);
			if (!directory.exists()) {
				result = directory.mkdir();
			}
		} catch (Exception e) {
			System.out.println("Error while creating the specific folder. Location " + FolderPath + ". Error message "
					+ e.getMessage());
		}
		return result;

	}

	public static boolean cleanFolder(String FolderPath) {
		boolean result = false;
		try {
			FileUtils.cleanDirectory(new File(FolderPath));
		} catch (Exception e) {
			System.out.println("Error while creating the specific folder. Location " + FolderPath + ". Error message "
					+ e.getMessage());
		}
		return result;
	}


	public static void waitForElement(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.getMessage();

		}
	}

	public static void pressEscapebutton() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ESCAPE);
		r.keyRelease(KeyEvent.VK_ESCAPE);
	}

	// For Scroll Down
	public static void ScrollDown() {
		try {
			Util.HardWait(1000);
			Robot k = new Robot();
			k.mouseWheel(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ScrollDown(int loc) {
		try {
			Util.HardWait(1000);
			Robot k = new Robot();
			k.mouseWheel(loc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// For Scroll Down
	public static void ScrollDownFull() {
		try {
			Util.HardWait(1000);
			Robot k = new Robot();
			k.mouseWheel(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void pressEnterbutton() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}


	public static boolean sendKeysToElement(WebDriver driver, WebElement element, String Value, boolean need2Clean) {
		boolean successflag = false;
		try {
			waitForElement(driver, element);
			if (element.isEnabled()) {
				if (need2Clean) {
					element.clear();
				}
				element.sendKeys(Value);
				successflag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return successflag;

	}


	public static int RandomNumber(int min, int max) {
		Random rnd = new Random();
		int n = min + rnd.nextInt(max);
		return n;
	}

	public static void HardWait(int MilliSec) {
		try {
			Thread.sleep(MilliSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String randomNumMS() {
		return new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Calendar.getInstance().getTime()).toString();

	}

		public static String dateFormat(String DateToSelect, String dateFormat) throws IOException {
		LocalDate localDateT = LocalDate.now();
		LocalDate localDateT_1 = null;
		if (DateToSelect.toLowerCase().contains("+".toLowerCase())) {
			String[] DateToSelectArr = DateToSelect.split("\\+");
			String DayNum = DateToSelectArr[1];
			int DaysToIncrement = Integer.parseInt(DayNum.trim());
			localDateT_1 = localDateT.plus(DaysToIncrement, ChronoUnit.DAYS);
		} else if (DateToSelect.toLowerCase().contains("-".toLowerCase())) {
			// it contain the - and we need t - X value ///////

			String[] DateToSelectArr = DateToSelect.split("\\-");
			String DayNum = DateToSelectArr[1];
			int DaysToIncrement = Integer.parseInt(DayNum.trim());
			localDateT_1 = localDateT.minus(DaysToIncrement, ChronoUnit.DAYS);
		} else {
			//////// Do nothing we need current date only////////////////////
			localDateT_1 = localDateT.plus(0, ChronoUnit.DAYS);
		}
		String formatters = "";

		formatters = DateTimeFormatter.ofPattern("MM/dd/YYYY").format(localDateT_1);
		
		return formatters;
	}

	public static String DateTimeString() {
		Util.HardWait(1000);
		int MyDay = LocalDateTime.now().getDayOfMonth(); // dd
		int MyYear = LocalDateTime.now().getYear(); // yyyy
		int MyMonth = LocalDateTime.now().getMonthValue(); // yyyy
		int Myhours = LocalDateTime.now().getHour();
		int Mymins = LocalDateTime.now().getMinute();
		int Mysecs = LocalDateTime.now().getSecond();

		final String CureentDtTm = (String.valueOf(MyMonth) + String.valueOf(MyDay) + String.valueOf(MyYear)
				+ String.valueOf(Myhours) + String.valueOf(Mymins) + String.valueOf(Mysecs));
		return CureentDtTm;

	}

	public static String DateString() {
		Util.HardWait(1000);
		int MyDay = LocalDateTime.now().getDayOfMonth();
		int MyYear = LocalDateTime.now().getYear();
		int MyMonth = LocalDateTime.now().getMonthValue();
		final String CureentDtTm = (String.valueOf(MyMonth) + String.valueOf(MyDay) + String.valueOf(MyYear));
		return CureentDtTm;

	}

	}
