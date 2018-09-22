package SupportLibraries;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.openqa.selenium.Keys;

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

	/**
	 * function to put value in the
	 * 
	 * @since July/13/2018
	 * @author srathore
	 * @param driver
	 * @param element
	 * @param Value2Put
	 * @param test
	 * @param ClearField
	 * @param Desc
	 */
	public static void sendKeysWithClear(WebDriver driver, ExtentTest test, WebElement element, String Value2Put,
			boolean ClearField, String Desc) {
		try {
			if (element.isEnabled() && (isNull_Empty_WhiteSpace(Value2Put) == false)) {
				if (ClearField) {
					element.click();
					element.clear();
				}
				element.sendKeys(Value2Put);
			} else {
				report.FailTest(test, driver, " Object " + Desc + " not present OR Input value is blank ", "UtlObj");
			}
		} catch (Exception e) {
			report.FailTest(test, driver, Desc + ":: Exception  " + e.getMessage(), "test");
		}

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

	public static void Checkexistencewithresult(WebDriver driver, ExtentTest test, WebElement element,
			String elementname) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
			report.PassTest(test, "Element :" + elementname + "exists");
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

	public static void highLightElement(WebDriver driver, WebElement element, ExtentTest test) {
		Util.waitForElement(driver, test, element);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px blue');", element);
	}

	public static void SelectOption(WebDriver driver, ExtentTest test, WebElement element, String selectOptionValue) {
		Util.waitForElement(driver, test, element);
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(selectOptionValue);
	}

	public static String randomNum() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
	}

	public static void openSecondTab(WebDriver driver, String Browser) throws IOException {
		switch (Browser.toUpperCase()) {
		case "IE":
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			// driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL
			// +"\t");
			break;
		default:
			((JavascriptExecutor) driver).executeScript("window.open()");
		}
	}

	public static void Openednewwindow(WebDriver driver, WebElement element) {
		String newwindow = null;
		int oldsize = driver.getWindowHandles().size();
		String oldWindow = driver.getWindowHandle();
		element.click();
		if (driver.getWindowHandles().size() > oldsize) {
			for (String handle : driver.getWindowHandles()) {
				if (!handle.equals(oldWindow)) {
					driver.switchTo().window(handle);
					// do any validation
				}
			}
		}
	}

	public static void SwtichtoOriginalwindow(WebDriver driver, String oldwindow) {
		driver.close();
		driver.switchTo().window(oldwindow);
	}

	public static String datepicker(String DateToSelect, String dateFormat) throws IOException {
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
		String formatters = DateTimeFormatter.ofPattern("MM/dd/YYYY").format(localDateT_1);
		String formatters1 = DateTimeFormatter.ofPattern("MM-dd-YYYY").format(localDateT_1);
		String formatters3 = DateTimeFormatter.ofPattern("dd-MM-YYYY").format(localDateT_1);
		return formatters;
	}

	public static String Phonenumber(String phonenumberstring) {

		String replacestring = phonenumberstring.replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
		return replacestring;

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

	public static void ReadCsv(WebDriver driver, ExtentTest test, String csvFile, String ColumnNames)
			throws IOException {
		BufferedReader br = null;
		String line = "";
		String Valuenotfound = "";
		String cvsSplitBy = ",";
		br = new BufferedReader(new FileReader(csvFile));
		boolean firstLine = true;
		boolean result = true;
		int counter = 0;
		while ((line = br.readLine()) != null) {
			if (firstLine) {
				firstLine = false;
				continue;
			} else {
				// use comma as separator
				String[] b = line.split(cvsSplitBy);
				for (int i = 0; i <= b.length - 1; i++) {
					if (ColumnNames.contains(b[i])) {
						result = true;
					} else {
						result = false;
						Valuenotfound = b[i];
						break;
					}
				}
				counter = 1;
			}
			if (counter == 1)
				break;
		}
		if (result == false) {
			report.FailTest(test, driver, "ColumnName not found in the CSV file :-" + Valuenotfound,
					"Valuenotfoundincsv");
		} else {
			report.PassTest(test, "All the values found in CSV");
		}
		br.close();
	}

	public static String datepicker(WebDriver driver, WebElement CaledrEleWbTbl, String DateToSelect) {
		try{
		List<WebElement> columns = CaledrEleWbTbl.findElements(By.tagName("td"));
		LocalDate localDateT = LocalDate.now();
		LocalDate localDateT_1 = null;
		String SelectedDate;
		if (DateToSelect.toLowerCase().contains("+".toLowerCase())) {
			String[] DateToSelectArr = DateToSelect.split("\\+");
			String DayNum = DateToSelectArr[1];
			int DaysToIncrement = Integer.parseInt(DayNum.trim());
			localDateT_1 = localDateT.plus(DaysToIncrement, ChronoUnit.DAYS);

		} else if (DateToSelect.toLowerCase().contains("-".toLowerCase())) {
			String[] DateToSelectArr = DateToSelect.split("\\-");
			String DayNum = DateToSelectArr[1];
			int DaysToIncrement = Integer.parseInt(DayNum.trim());
			System.out.println("Days to decrease >>" + DaysToIncrement);
			localDateT_1 = localDateT.minus(DaysToIncrement, ChronoUnit.DAYS);
		} else {

			localDateT_1 = localDateT.plus(0, ChronoUnit.DAYS);
		}
		SelectedDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDateT_1);
		String[] dateParts = SelectedDate.split("-");

		String SrvcDay = dateParts[2];

		if (Integer.parseInt(SrvcDay) < 15) {
			if (Integer.parseInt(SrvcDay) == 1) {
				driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[1]/span")).click();
				SrvcDay = "28";
			} else {
				SrvcDay = "1";
			}
		} else if (Integer.parseInt(SrvcDay) >= 15) {
			if (Integer.parseInt(SrvcDay) == 16) {
				SrvcDay = "10";
			} else {
				SrvcDay = "16";
			}
		} else {
			System.out.println("Date Range Exceeds");
		}

		List<WebElement> columns2 = CaledrEleWbTbl.findElements(By.tagName("td"));
		if (SrvcDay.trim().subSequence(0, 1).equals("0")) {
			SrvcDay = SrvcDay.trim().replace("0", "");
		}
		for (WebElement cell : columns2) {
			if (Util.isNotNullNotEmptyNotWhiteSpace(cell.getText()) == false) {
				if (Integer.parseInt(cell.getText()) >= (Integer.parseInt(SrvcDay))
						&& (cell.getAttribute("class")).trim().contains("disabled") == false) {
					cell.findElement(By.linkText(cell.getText())).click();

					break;
				}
			}
		}
		return SelectedDate;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
			
		}
	}

	public static boolean isNotNullNotEmptyNotWhiteSpace(String CmpVal) {
		CmpVal = CmpVal.replaceAll("\u00a0", "");
		CmpVal = CmpVal.replaceAll("&nbsp", "").trim();
		if (CmpVal.trim() != "" && CmpVal != null && (CmpVal.isEmpty()) == false) {
			return false;
		} else {
			return true;
		}
	}

	public static void generateExcelReport() {
		
		Map<Integer, List<String>> testNGResult = new HashMap<>();
		String test_name;
		String class_name;
		String startTime;
		String endTime;
		String executionTime;
		String test_method_status;

		File xmlFile = new File(System.getProperty("user.dir") + "/target/surefire-reports/testng-results.xml");
		try {
			if (xmlFile.isFile()) {
				DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
				DocumentBuilder build = fact.newDocumentBuilder();
				org.w3c.dom.Document doc = build.parse(xmlFile);
				doc.getDocumentElement().normalize();
				NodeList test_list = doc.getElementsByTagName("test");
				for (int i = 0; i < test_list.getLength(); i++) {
					List<String> testResultItems = new ArrayList<>();
					Node test_node = test_list.item(i);
					if (test_list.item(i).getNodeType() == Node.ELEMENT_NODE) {
						test_name = ((Element) test_node).getAttribute("name");
						executionTime = ((Element) test_node).getAttribute("duration-ms");
						startTime = ((Element) test_node).getAttribute("started-at");
						endTime = ((Element) test_node).getAttribute("finished-at");

						Long millis = Long.parseLong(executionTime);
						executionTime = String.format("%02d:%02d",
								TimeUnit.MILLISECONDS.toMinutes(millis)
										- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
								TimeUnit.MILLISECONDS.toSeconds(millis)
										- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

						testResultItems.add(Integer.toString(i + 1));
						testResultItems.add(test_name);
						NodeList class_list = ((Element) test_node).getElementsByTagName("class");

						for (int j = 0; j < class_list.getLength(); j++) {
							Node class_node = class_list.item(j);
							if (class_list.item(j).getNodeType() == Node.ELEMENT_NODE) {
								class_name = ((Element) class_node).getAttribute("name");

								testResultItems.add(class_name);
								testResultItems.add(startTime);
								testResultItems.add(endTime);
								testResultItems.add(executionTime);

								NodeList test_method_list = ((Element) class_node).getElementsByTagName("test-method");

								String final_test_method_status = "FAIL";
								boolean AnyFailFlag = false;
								for (int k = 0; k < test_method_list.getLength(); k++) {
									Node test_method_node = test_method_list.item(k);
									if (test_method_list.item(k).getNodeType() == Node.ELEMENT_NODE) {
										test_method_status = ((Element) test_method_node).getAttribute("status");
										if (test_method_status.equals("PASS")) {
											final_test_method_status = "PASS";
										} else {
											AnyFailFlag = true;
										}
									}
								}
								if (AnyFailFlag == true) {
									testResultItems.add("FAIL");
								} else {
									testResultItems.add(final_test_method_status);
								}
							}
						}
						testNGResult.put(i + 1, testResultItems);
					}
				}
			} else {
				System.out.println("Testng result xml is not available");
			}
		

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Get date difference in days
	public static long difference_between_twodates(String duedate) throws ParseException {

		String datearray[] = duedate.split(",");
		String datearray1[] = datearray[0].split(" ");

		Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(datearray1[0]);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);

		LocalDate endofCentury = LocalDate.of(Integer.parseInt(datearray[1].trim()), month + 1,
				Integer.parseInt(datearray1[1].trim()));
		LocalDate now = LocalDate.now();

		Period diff = Period.between(endofCentury, now);
		long diffInDays = ChronoUnit.DAYS.between(endofCentury, now);
		return diffInDays;
	}

	// Get day of week using date, if saturday or sunday, make it a weekday

	public static LocalDate Weekdayfromdate(long daystoadd, String limit) {
		LocalDate now = LocalDate.now();

		Calendar cal = Calendar.getInstance();
		LocalDate dropofdate = LocalDate.now().plusDays(daystoadd);
		// LocalDate dropofdate = LocalDate.now().plusDays(1);
		DayOfWeek weekday = dropofdate.getDayOfWeek();
		if (limit.equalsIgnoreCase("MIN")) {
			if (weekday == DayOfWeek.SATURDAY) {
				dropofdate = dropofdate.plusDays(2);
			} else if (weekday == DayOfWeek.SUNDAY) {
				dropofdate = dropofdate.plusDays(1);
			}
		} else if (limit.equalsIgnoreCase("MAX")) {
			if (weekday == DayOfWeek.SATURDAY) {
				dropofdate = dropofdate.minusDays(1);
			} else if (weekday == DayOfWeek.SUNDAY) {
				dropofdate = dropofdate.minusDays(2);
			}
		}
		return dropofdate;

	}

	public static String Todaysdateinparticluarformat() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		return dateFormat.format(date).toUpperCase();

	}

	// Srathore

	public static boolean compare2DateDiffFormat(String ExpDateFormat, String ExpDate, String ActDateFormat,
			String ActDate) {
		boolean isEqual = false;

		try {
			DateFormat ExpDF = new SimpleDateFormat(ExpDateFormat);
			ExpDF.setLenient(false); // this is important!
			Date FromDateDF = ExpDF.parse(ExpDate); //// String to Date convert

			DateFormat ActDF = new SimpleDateFormat(ActDateFormat);
			ActDF.setLenient(false); // this is important!
			Date DFActDate = ActDF.parse(ActDate);

			if (FromDateDF.compareTo(DFActDate) == 0) {
				isEqual = true;
				System.out.println("both date are equal");
			} else if (FromDateDF.compareTo(DFActDate) > 0) {
				System.out.println("Date1 is after Date2");
			} else if (FromDateDF.compareTo(DFActDate) < 0) {
				System.out.println("Date1 is before Date2");
			} else {
				System.out.println("Dates are not in proper format");
			}

		} catch (Exception e) {

		}
		return isEqual;

	}

	public static boolean compareStringAnySequance(WebDriver driver, String ExpStr, String ExpSeparator, String ActStr,
			String ActSeparator, ExtentTest test, String VerificationFor) {
		boolean fnTrn = false;
		report.InfoTest(test, "Link compare Actual >> " + ActStr + "<<< Exptected >>> " + ExpStr);
		try {
			String[] ExpArr = ExpStr.trim().split("\\" + ExpSeparator);
			String[] ActArr = ActStr.trim().split("\\" + ActSeparator);

			int ExpArrSize = ExpStr.trim().split("\\" + ExpSeparator).length;
			int ActArrSize = ActStr.trim().split("\\" + ActSeparator).length;

			if (ExpArrSize == ActArrSize) {
				report.PassTest(test, "Exp message for blank field appeared");

				/////////// Collection of Exp values ///////////////
				ArrayList<String> ExpColl = new ArrayList<>();
				Collections.addAll(ExpColl, ExpArr);

				/////////// Collection of Act values ///////////////
				ArrayList<String> ActColl = new ArrayList<>();
				Collections.addAll(ActColl, ActArr);

				if (ActColl.equals(ExpColl)) {
					report.PassTest(test, "Act and Exp collection are same. ");
					fnTrn = true;
				} else {

					ArrayList<String> ActMismatchColl = new ArrayList<>();
					Collections.addAll(ActMismatchColl, ActArr);
					ActMismatchColl.removeAll(ExpColl);
					report.FailTest(test, driver,
							"Following " + VerificationFor + " is are Extra in Actual values >> " + ActMismatchColl,
							"compare");

					ArrayList<String> ExpMismatchColl = new ArrayList<>();
					Collections.addAll(ExpMismatchColl, ExpArr);
					ExpMismatchColl.removeAll(ActColl);
					report.FailTest(test, driver,
							"Following " + VerificationFor + " is are Extra in Exp values >> " + ExpMismatchColl,
							"compare");

				}

			} else {
				report.FailTest(test, driver,
						"Exp column count " + ExpArrSize + " and Act column count  " + ActArrSize + " are not same.",
						"count");
			}
		} catch (Exception e) {
			report.FailTest(test, driver, "Error occuerred " + e.getMessage(), "compare");
		}

		return fnTrn;
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

	public static void CloseAllBrowserProcess() throws InterruptedException {
		String BatchFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\Tools\\KillAllBrowser.bat";
		try {
			String[] command = { "cmd.exe", "/C", "Start", BatchFilePath };
			Process p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void StopRunProcess() throws InterruptedException {
		String BatchFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\Tools\\StopRun.bat";
		try {
			String[] command = { "cmd.exe", "/C", "Start", BatchFilePath };
			Process p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static boolean isFileUploaded(String filePath, WebDriver driver, ExtentTest test) {
		boolean flag = false;
		try {

			System.out.println("");
			StringSelection s = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
			System.out.println("File Path-----" + filePath);

			Robot robot = new Robot();
			Util.HardWait(500);

			// Press Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			// Release Enter
			robot.keyRelease(KeyEvent.VK_ENTER);
			Util.HardWait(500);

			// Press CTRL+V
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			// Release CTRL+V
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			Util.HardWait(500);

			// Press and Release TAB
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Util.HardWait(500);

			// Press and Release TAB
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Util.HardWait(500);

			// Press Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Util.HardWait(500);
			flag = true;

		} catch (Exception e) {
			report.FailTest(test, driver, "failed to upload file. err" + e.getMessage(), "test");
		}

		return flag;
	}

	public static void saveFileinIE(WebDriver driver) {

		try {
			Util.HardWait(500);
			Robot r = new Robot();
			// Press and release F6 key
			r.keyPress(KeyEvent.VK_F6);
			// r.keyRelease(KeyEvent.VK_F6);
			Util.HardWait(500);

			// Press and release TAB key
			r.keyPress(KeyEvent.VK_TAB);
			Util.HardWait(500);
			r.keyRelease(KeyEvent.VK_TAB);
			// Press and release Arrow Down key
			Util.HardWait(500);
			r.keyPress(KeyEvent.VK_DOWN);
			Util.HardWait(500);
			r.keyRelease(KeyEvent.VK_DOWN);
			// Press and release Arrow Down key
			Util.HardWait(500);
			r.keyPress(KeyEvent.VK_DOWN);
			Util.HardWait(500);
			r.keyRelease(KeyEvent.VK_DOWN);
			Util.HardWait(500);
			// Press and release Enter key
			r.keyPress(KeyEvent.VK_ENTER);
			Util.HardWait(500);
			r.keyRelease(KeyEvent.VK_ENTER);
			Util.HardWait(500);
			// Press and release Enter key
			r.keyPress(KeyEvent.VK_ENTER);
			Util.HardWait(500);
			r.keyRelease(KeyEvent.VK_ENTER);
			Util.HardWait(500);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void saveIeDownloadAutoIT(String FullSavePath) {
		try {

			Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\main\\java\\AutoIt\\IE_SaveAsParam.exe"
					+ " " + FullSavePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String acceptRejectBrowserPopup(WebDriver driver, ExtentTest test, boolean Accept) {
		String RtnText = "";
		try {
			RtnText = driver.switchTo().alert().getText();
			if (Accept) {
				driver.switchTo().alert().accept();
			} else {
				driver.switchTo().alert().dismiss();
			}

		} catch (Exception e) {
			report.FailTest(test, driver, "Alert popup not found", "alert");
		}
		Util.HardWait(3000);
		return RtnText;
	}

	public static void waitForElement(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.getMessage();

		}
	}

	public static void SwitchToMenuFrame(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(1);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public static void SwitchToMainActionFrame(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(0);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public static String GetLinkStringFromDiv(WebDriver driver, String FindByStrXpath, String Separator) {
		String LinkTextAll = "";
		int NoOfLinks = driver.findElements(By.xpath(FindByStrXpath)).size();

		if (NoOfLinks > 0) {
			for (int i = 1; i <= NoOfLinks; i++) {
				String TmpLinkText = driver.findElement(By.xpath(FindByStrXpath + "[" + Integer.toString(i) + "]/a"))
						.getText();
				if (i == 1) {
					LinkTextAll = TmpLinkText;
				} else {
					LinkTextAll = LinkTextAll + Separator + TmpLinkText;
				}
			}
		} else {
			
		}
		return LinkTextAll;
	}

	public static void verifyAllLinks(ExtentTest test, WebDriver driver, String VerifyPage) {
		List<WebElement> links = findAllLinks(driver);
		report.InfoTest(test, " No of links on page " + VerifyPage + links.size());
		// System.out.println("Total number of links on the page : " +
		// links.size());
		for (int i = 0; i < links.size(); i++) {
			WebElement element = links.get(i);
			try {
				String url = element.getAttribute("href");
				report.PassTest(test, "URL: " + element.getAttribute("href") + " " + verifyLinkActive(url));
			} catch (Exception exp)

			{
				// try {
				report.FailTest(test, driver,
						"At " + element.getAttribute("innerHTML") + " Exception occured " + exp.getMessage(), "links");
				/*
				 * } catch (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } catch (InterruptedException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 */
			}
		}
	}

	public static List findAllLinks(WebDriver driver) {
		List elementList = new ArrayList();
		List<WebElement> LinkArray = driver.findElements(By.tagName("a"));
		// elementList.addAll(driver.findElements(By.tagName("img")));
		List<WebElement> finalList = new ArrayList();
		for (WebElement element : LinkArray) {
			if (element.getAttribute("href") != null) {
				finalList.add(element);
			}
		}
		return finalList;
	}

	public static String verifyLinkActive(String linkUrl) {
		try {
			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();
			if (httpURLConnect.getResponseCode() == 200) {
				return httpURLConnect.getResponseCode() + " " + httpURLConnect.getResponseMessage();
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {

				return httpURLConnect.getResponseCode() + " " + httpURLConnect.getResponseMessage();
			} else {
				return httpURLConnect.getResponseCode() + " " + httpURLConnect.getResponseMessage();
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return "Link is not working";
		}
	}

	public static String GetKendoPageRecordCount(WebDriver driver) {

		String RecordCount = null;
		//// We have such element )
		if (driver.findElements(By.xpath("//*[@class='k-pager-info k-label'] ")).size() != 0) {
			String rawRcdVal = driver.findElement(By.xpath("//*[@class='k-pager-info k-label'] ")).getText();
			if ((rawRcdVal.toLowerCase().contains("of")) && (rawRcdVal.toLowerCase().contains("-"))) {
				String ofArry[] = rawRcdVal.trim().toLowerCase().split("of");
				String noOfRcdArray[] = ofArry[1].trim().split(" ");
				RecordCount = noOfRcdArray[0];
			}
		}

		return RecordCount;
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

	/*
	 * public static void pressEnterbutton() throws AWTException { Robot r = new
	 * Robot(); r.keyPress(KeyEvent.VK_ENTER); r.keyRelease(KeyEvent.VK_ENTER);
	 * }
	 */

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

	public static String ReturnTableHeaderStringWithLink(WebDriver driver, String TableName) {
		String TableHeaderStr = null;
		if (driver.findElements(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th")).size() != 0) {

			int ColumnCount = driver.findElements(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th")).size();
			System.out.println("No of columns in the table" + ColumnCount);
			for (int iCount = 1; iCount <= ColumnCount; iCount++) {
				if (iCount == 1) {

					if (driver
							.findElements(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]/a"))
							.size() != 0) {
						TableHeaderStr = driver
								.findElement(
										By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]/a"))
								.getAttribute("innerText").trim();
					} else {
						TableHeaderStr = driver
								.findElement(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]"))
								.getAttribute("innerText").trim();
					}
				} else {

					if (driver
							.findElements(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]/a"))
							.size() != 0) {
						TableHeaderStr = TableHeaderStr + "|"
								+ driver.findElement(
										By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]/a"))
										.getAttribute("innerText").trim();
					} else {
						TableHeaderStr = TableHeaderStr + "|"
								+ driver.findElement(
										By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]"))
										.getAttribute("innerText").trim();
					}

				}

			}

		} else if (driver.findElements(By.xpath("//*[@id='" + TableName + "']/thead/tr/th")).size() != 0) {
			int ColumnCount = driver.findElements(By.xpath("//*[@id='" + TableName + "']/thead/tr/th")).size();
			System.out.println("No of columns in the table >>" + ColumnCount);
			for (int iCount = 1; iCount <= ColumnCount; iCount++) {
				if (iCount == 1) {

					if (driver.findElements(By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]/a"))
							.size() != 0) {
						TableHeaderStr = driver
								.findElement(By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]/a"))
								.getAttribute("innerText").trim();
					} else {
						TableHeaderStr = driver
								.findElement(By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]"))
								.getAttribute("innerText").trim();
					}
				} else {

					if (driver.findElements(By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]/a"))
							.size() != 0) {
						TableHeaderStr = TableHeaderStr + "|"
								+ driver.findElement(
										By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]/a"))
										.getAttribute("innerText").trim();
					} else {
						TableHeaderStr = TableHeaderStr + "|"
								+ driver.findElement(
										By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]"))
										.getAttribute("innerText").trim();
					}

				}

			}

		}
		return TableHeaderStr;
	}

	public static String ReturnTableHeaderString(WebDriver driver, String TableName) {
		String TableHeaderStr = null;
		if (driver.findElements(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th")).size() != 0) {

			int ColumnCount = driver.findElements(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th")).size();
			System.out.println("No of columns in the table" + ColumnCount);
			for (int iCount = 1; iCount <= ColumnCount; iCount++) {
				if (iCount == 1) {

					TableHeaderStr = driver
							.findElement(By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]"))
							.getAttribute("innerText").trim();
				} else {
					TableHeaderStr = TableHeaderStr + "|"
							+ driver.findElement(
									By.xpath("//*[@id='" + TableName + "']/table/thead/tr/th[" + iCount + "]"))
									.getAttribute("innerText").trim();
				}

			}

		} else if (driver.findElements(By.xpath("//*[@id='" + TableName + "']/thead/tr/th")).size() != 0) {
			int ColumnCount = driver.findElements(By.xpath("//*[@id='" + TableName + "']/thead/tr/th")).size();
			System.out.println("No of columns in the table" + ColumnCount);
			for (int iCount = 1; iCount <= ColumnCount; iCount++) {
				if (iCount == 1) {
					TableHeaderStr = driver
							.findElement(By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]"))
							.getAttribute("innerText").trim();
				} else {
					TableHeaderStr = TableHeaderStr + "|"
							+ driver.findElement(By.xpath("//*[@id='" + TableName + "']/thead/tr/th[" + iCount + "]"))
									.getAttribute("innerText").trim();
				}
			}
		}
		return TableHeaderStr;
	}

	public static void compareTwoStringSeparator(ExtentTest test, WebDriver driver, String ExpStr, String ActStr,
			String Separator, String CompFor) {
		report.InfoTest(test, "Item Compare :: |||||||||| Actual ===== >> " + ActStr
				+ "|||||||||| Exptected ============>>> " + ExpStr);
		try {
			String[] ExpArr = ExpStr.trim().split("\\" + Separator);
			String[] ActArr = ActStr.trim().split("\\" + Separator);

			int ExpArrSize = ExpStr.trim().split("\\" + Separator).length;
			int ActArrSize = ActStr.trim().split("\\" + Separator).length;

			if (ExpArrSize == ActArrSize) {
				report.PassTest(test, "Exp message for blank field appeared");
				for (int i = 0; i < ExpArrSize; i++) {
					String ExpData = ExpArr[i].trim().replace("\n", "").replace("\r", "");
					String ActData = ActArr[i].trim().replace("\n", "").replace("\r", "");
					if (ExpData.toLowerCase().equals(ActData.trim().toLowerCase())) {
						report.PassTest(test,
								CompFor + " ||| == Exp column >>" + ExpData + "<< and Act column is same");
					} else {
						report.FailTest(test, driver, CompFor + " ||| Exp column >>" + ExpData + "<< and Act column >>"
								+ ActData + "<< are NOT same", "compare");
					}

				}

			} else {
				report.FailTest(test, driver,
						"  Exp column count " + ExpArrSize + " and Act column count  " + ActArrSize + " are not same.",
						"compare");
			}
		} catch (Exception e) {
			report.FailTest(test, driver, "Error occuerred " + e.getMessage(), "compare");
		}

	}

	public static void waitForElementInvisible(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.invisibilityOf(element));
		} catch (Exception e) {
			System.out.println("Expected element not found " + e.getMessage());
		}

	}

	public static void SelectOptionByIndex(WebDriver driver, WebElement element, int selectoptionindex) {
		try {
			Util.waitForElement(driver, element);
			Select dropdown = new Select(element);
			dropdown.selectByIndex(selectoptionindex);
		} catch (Exception e) {
			System.out.println("Element Not found " + e.getMessage());
		}
	}

	public static void ClickButtonLinkOnAllBrowser(WebElement element, WebDriver driver, ExtentTest test) {

		Util.waitForElement(driver, element);
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();

		switch (browserName) {
		case ("internet explorer"):
			ClickButtonLinkObjectIE(element, driver, test);
			break;
		case ("chrome"):
			ClickButtonLinkObjectChrome(element, driver, test);
			break;
		case ("firefox"):
			ClickButtonLinkObjectFireFox(element, driver, test);
			break;
		case ("microsoftedge"):
			ClickButtonLinkObjectChrome(element, driver, test);
			break;

		default:
			throw new IllegalArgumentException("Invalid  Browser type:  " + browserName);
		}
		Util.HardWait(1000);
		waitForPageToLoad(driver);
	}

	/*
	 * public static void ClickButtonLinkObjectChrome(WebElement element,
	 * WebDriver driver, ExtentTest test) { WebDriverWait d = new
	 * WebDriverWait(driver, 2);
	 * d.until(ExpectedConditions.elementToBeClickable(element)); try {
	 * element.click(); } catch (Exception e) {
	 * report.InfoTest(test, "Chrome: Object not found" + e.getMessage()); } }
	 */

	public static void ClickButtonLinkObjectChrome(WebElement element, WebDriver driver, ExtentTest test) {
		WebDriverWait d = new WebDriverWait(driver, 2);
		d.until(ExpectedConditions.elementToBeClickable(element));
		try {
			element.click();
		} catch (Exception e) {
			report.InfoTest(test, "Chrome: Object not found" + e.getMessage());
		}
	}

	public static void ClickButtonLinkObjectFireFox(WebElement element, WebDriver driver, ExtentTest test) {

		WebDriverWait d = new WebDriverWait(driver, 2);
		d.until(ExpectedConditions.elementToBeClickable(element));
		try {
			element.click();
		} catch (Exception e) {
			ScrollDown(500);
			report.InfoTest(test, "Chrome: Object not found" + e.getMessage());
		}
	}

	public static void sendKeysJS(WebDriver driver, ExtentTest test, WebElement element, String NewValue,
			boolean need2Clear, String Description) {

		Util.waitForElement(driver, element);
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		HardWait(2000);
		try {
			switch (browserName) {
			case ("internet explorer"):
				goToObjectJS(element, driver, test);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				if (need2Clear) {
					executor.executeScript("arguments[0].setAttribute('value',  '')", element);
					Util.HardWait(1000);
				}
				executor.executeScript("arguments[0].setAttribute('value',  '" + NewValue + "')", element);
				Util.HardWait(3000);
				break;
			case ("chrome"):
				if (need2Clear) {
					element.clear();
					Util.HardWait(1000);
				}
				element.click();
				element.sendKeys(NewValue);
				break;
			case ("firefox"):
				if (need2Clear) {
					element.clear();
					Util.HardWait(1000);
				}
				element.click();
				element.sendKeys(NewValue);
				break;
			case ("edge"):
				if (need2Clear) {
					element.clear();
					Util.HardWait(1000);
				}
				element.click();
				element.sendKeys(NewValue);
				break;
			default:
				throw new IllegalArgumentException("Invalid  Browser type:  " + browserName);
			}

		} catch (Exception e) {
			report.InfoTest(test, "Object not found: SendKeys " + e.getMessage());
		}
	}

	public static void ScrollDownPageEndJS(WebDriver driver, ExtentTest test) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 2000)");

			HardWait(2000);
			ScrollDownFull();
		} catch (Exception e) {
			report.InfoTest(test, "Object not found, while getting  object's Y Position" + e.getMessage());
		}
	}

	public static void ScrollUpPageEndJS(WebDriver driver, ExtentTest test) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -2000)");
			HardWait(1000);
		} catch (Exception e) {
			report.InfoTest(test, "Object not found, while getting  object's Y Position" + e.getMessage());
		}
	}
	
	public static void ScrollRightPageEndJS(WebDriver driver, ExtentTest test) {
		try {

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000, 0)");
			HardWait(2000);

		} catch (Exception e) {
			report.InfoTest(test, "Object not found, while getting  object's Y Position" + e.getMessage());
		}
	}

	public static void goToObjectJS(WebElement element, WebDriver driver, ExtentTest test) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + element.getLocation().y + ")");
			HardWait(300);
		} catch (Exception e) {
			report.InfoTest(test, "Object not found, while getting  object's Y Position" + e.getMessage());
		}
	}

	public static void ClickButtonLinkObjectIE(WebElement element, WebDriver driver, ExtentTest test) {

		try {
			goToObjectJS(element, driver, test);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", element);
			Util.HardWait(500);
		} catch (Exception e) {
			report.InfoTest(test, "Object not found" + e.getMessage());
		}
	}

	public static void CheckBoxCheckObjectIE(WebDriver driver, WebElement element, boolean ChkNeeded, ExtentTest test) {
		try {
			Util.waitForElement(driver, element);
			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();
			switch (browserName) {
			case ("internet explorer"):
				/// do nothing
				break;
			case ("chrome"):
				goToObjectJS(element, driver, test);
				break;
			case ("firefox"):
				goToObjectJS(element, driver, test);
				break;
			case ("microsoftedge"):
				goToObjectJS(element, driver, test);
				break;
			default:
				throw new IllegalArgumentException("Invalid  Browser type:  " + browserName);
			}

			element.click();
			Util.HardWait(1000);
			if ((element.isSelected() == false) && (ChkNeeded)) {
				element.sendKeys(Keys.SPACE);
			}
			if ((element.isSelected()) && (ChkNeeded == false)) {
				element.sendKeys(Keys.SPACE);
			}
		} catch (Exception e) {
			report.InfoTest(test, " Object not found" + e.getMessage());
		}

	}


	public static String ClientCodeGeneration() {
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGH";
		// Calendar.get
		int Myhours = LocalDateTime.now().getHour();
		int Mymins = LocalDateTime.now().getMinute();
		int Mysecs = LocalDateTime.now().getSecond();

		if (Myhours == 0) {
			Myhours = 1;
		}
		String hour = alphabets.substring(Myhours, (Myhours + 1));
		if (Mymins == 0) {
			Mymins = 1;
		}
		String mins = alphabets.substring(Mymins, (Mymins + 1));
		if (Mysecs == 0) {
			Mysecs = 1;
		}
		String secs = alphabets.substring(Mysecs, (Mysecs + 1));

		final String ClientCode = (secs + mins + hour);
		return ClientCode;

	}

	public static int RandomNumber(int min, int max) {
		Random rnd = new Random();
		int n = min + rnd.nextInt(max);
		return n;
	}

	public static void highLightElement(WebDriver driver, WebElement element) {
		try {
			Util.waitForElement(driver, element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid green;');", element);
			js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
		} catch (Exception e) {
			System.out.println("Error occured " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void selectUlLiDropdown(WebDriver driver, WebElement element, String selectOptionValue,
			ExtentTest test) {
		// Util.ClickButtonLinkOnAllBrowser(element, driver, test);
		List<WebElement> options = element.findElements(By.tagName("li"));
		for (WebElement option : options) {
			String ActValue = option.getText().trim();
			if (ActValue.equalsIgnoreCase(selectOptionValue)) {
				Util.ClickButtonLinkOnAllBrowser(option, driver, test);
				break;
			}
		}

	}

	public static void SelectOption(WebDriver driver, WebElement element, String selectOptionValue, ExtentTest test) {
		Util.waitForElement(driver, element);
		Select dropdown = new Select(element);
		goToObjectJS(element, driver, test);
		if (element.isEnabled()) {
			try {
				dropdown.selectByVisibleText(selectOptionValue);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ElementNotSelectableException e) {
				// try {
				report.FailTest(test, driver, "Select Option " + selectOptionValue + " not present " + e.getMessage(),
						"optionSelected");
				/*
				 * } catch (IOException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } catch (InterruptedException e1) { //
				 * TODO Auto-generated catch block e1.printStackTrace(); }
				 */
			}
		} else {
			System.out.println("Listbox obj is not enable");
		}

	}

	public static void SelectOptionDateChar(WebDriver driver, WebElement element, String selectOptionValue,
			ExtentTest test) {
		selectOptionValue = selectOptionValue.replace("/", "").trim().toUpperCase();
		Util.waitForElement(driver, element);
		Select dropdown = new Select(element);
		if (element.isEnabled()) {
			try {
				List<WebElement> Listoptions = dropdown.getOptions();
				String DDVals;
				for (int j = 0; j < Listoptions.size(); j++) {
					DDVals = Listoptions.get(j).getText().trim();
					DDVals = DDVals.replace("/", "");
					if (DDVals.trim().toUpperCase().equals(selectOptionValue)) {
						dropdown.selectByIndex(j);
						break;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ElementNotSelectableException e) {
				// try {
				report.FailTest(test, driver, "Select Option " + selectOptionValue + " not present " + e.getMessage(),
						"OptionSelected");
				/*
				 * } catch (IOException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } catch (InterruptedException e1) { //
				 * TODO Auto-generated catch block e1.printStackTrace(); }
				 */
			}
		} else {
			System.out.println("Listbox obj is not enable");
		}

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

	public static void openSecondTabSR(WebDriver driver, String Browser) throws IOException, InterruptedException {
		switch (Browser.toUpperCase()) {
		case ("CHROME"):
			((JavascriptExecutor) driver).executeScript("window.open()");
			break;
		case ("IE"):
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			Thread.sleep(20000);
			break;
		default:
			((JavascriptExecutor) driver).executeScript("window.open()");

		}
	}

	public static void SwtichtoOriginalwindow(WebDriver driver) {
		driver.close();
		driver.switchTo().defaultContent();
	}

	public static String GetListBoxValue(WebElement element) throws IOException {

		Select selectItem = new Select(element);
		String ListVal = selectItem.getFirstSelectedOption().getText();
		return ListVal;
	}

	public static void waitForPageToLoad(WebDriver driver) {
		try {
			String pageLoadStatus;
			do {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				pageLoadStatus = (String) js.executeScript("return document.readyState");
				Util.HardWait(1000);
				System.out.print(".");
			} while (!pageLoadStatus.equals("complete"));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static boolean isNull_Empty_WhiteSpace(String CmpVal) {
		CmpVal = CmpVal.replaceAll("\u00a0", "");
		CmpVal = CmpVal.replaceAll("&nbsp", "").trim();
		if (CmpVal.trim() != "" && CmpVal != null && (CmpVal.isEmpty()) == false) {
			return false;
		} else {
			return true;
		}
		// return CmpVal != null && !string.isEmpty() &&
		// !string.trim().isEmpty();
	}

	public static void RefreshBrowserFnKey() {

		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.keyPress(KeyEvent.VK_F5);
		r.keyRelease(KeyEvent.VK_F5);
		Util.HardWait(1000);
		r.keyPress(KeyEvent.VK_F5);
		r.keyRelease(KeyEvent.VK_F5);
		// driverIE.manage().deleteAllCookies();
		Util.HardWait(1000);
		r.keyPress(KeyEvent.VK_F5);
		r.keyRelease(KeyEvent.VK_F5);
		Util.HardWait(1000);

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

		if (!(isNull_Empty_WhiteSpace(dateFormat))) {
			formatters = DateTimeFormatter.ofPattern(dateFormat).format(localDateT_1);
		} else {
			formatters = DateTimeFormatter.ofPattern("MM/dd/YYYY").format(localDateT_1);
		}
		// String formatters1 =
		// DateTimeFormatter.ofPattern("MM-dd-YYYY").format(localDateT_1);
		// String formatters3 =
		// DateTimeFormatter.ofPattern("dd-MM-YYYY").format(localDateT_1);

		return formatters;
	}

	///////////////// srathore 12/20/2017 /// function will return the string
	///////////////// from
	///////////////// and To date Period: '1/1/2018' to '1/31/2018'

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
