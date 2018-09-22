package SupportLibraries;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.aventstack.extentreports.ExtentTest;


import package1.BaseClass;

public class functionLibrary {

	static String systemDir = System.getProperty("user.dir");
	static String DriverPath;

	public static WebDriver StartBrowsercookied(WebDriver driver, String browserName, Set<Cookie> allCookies) {
		if (browserName.equals("firefox")) {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("log", "{level: trace}");
			capabilities.setCapability("marionette", true);
			capabilities.setCapability("moz:firefoxOptions", options);
			System.setProperty("webdriver.gecko.driver", systemDir + "\\src\\main\\java\\driver\\" + "geckodriver.exe");
			driver = new FirefoxDriver(capabilities);
		} else if (browserName.equals("chrome")) {
			DriverPath = systemDir + "\\src\\main\\java\\driver\\" + "chromedriver.exe";
			/*
			 * System.setProperty("webdriver.chrome.driver", DriverPath); ChromeOptions
			 * options = new ChromeOptions(); HashMap<String, Object> chromePref = new
			 * HashMap<>(); chromePref.put("download.default_directory",
			 * "C:\\Users\\" + System.getenv("USERNAME") + "\\Downloads");
			 * options.setExperimentalOption("prefs", chromePref); driver = new
			 * ChromeDriver(options);
			 */

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("user-data-dir=/path/to/your/custom/profile");
			System.setProperty("webdriver.chrome.driver", DriverPath);
			driver = new ChromeDriver(options);

		} else if (browserName.equals("ie")) {
			DriverPath = systemDir + "\\src\\main\\java\\driver\\" + "IEDriverServer32.exe";
			System.setProperty("webdriver.ie.driver", DriverPath);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
			capabilities.setCapability("nativeEvents", true);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("disable-popup-blocking", true);
			capabilities.setCapability("enablePersistentHover", true);
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability("ForceCreateProcessApi", true);
			// capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			System.setProperty("webdriver.ie.driver", DriverPath);
			driver = new InternetExplorerDriver(capabilities);

		} else if (browserName.equals("edge")) {

			DriverPath = systemDir + "\\src\\main\\java\\driver\\" + "MicrosoftWebDriver.exe";
			// Set the driver path
			System.setProperty("webdriver.edge.driver", DriverPath);

			EdgeOptions options = new EdgeOptions();

			options.setPageLoadStrategy("eager");
			// Start Edge Session
			driver = new EdgeDriver(options);

			// Start Edge Session

		}
		for (Cookie cookie : allCookies) {
			driver.manage().addCookie(cookie);
			// System.out.println(String.format("%s -> %s", cookie.getName(),
			// cookie.getValue()));
		}
		// driver.manage().addCookie(cookie);
		// driver.manage().window().maximize();
		// driver.get(URL);
		return driver;
	}
}
