package Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserOpen {
	static String systemDir = System.getProperty("user.dir");
	static String DriverPath;

	public static WebDriver StartBrowser(WebDriver driver, String browserName, String URL) {
		if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", systemDir + "\\src\\main\\java\\driver\\" + "geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();

		} else if (browserName.equals("chrome")) {
			DriverPath = systemDir + "\\src\\main\\java\\driver\\" + "chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", DriverPath);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (browserName.equals("ie")) {
			DriverPath = systemDir + "\\src\\main\\java\\driver\\" + "IEDriverServer32.exe";
			System.setProperty("webdriver.ie.driver", DriverPath);
			driver = new InternetExplorerDriver();

		} else if (browserName.equals("edge")) {
			DriverPath = systemDir + "\\src\\main\\java\\driver\\" + "MicrosoftWebDriver.exe";
			System.setProperty("webdriver.edge.driver", DriverPath);
			EdgeOptions options = new EdgeOptions();
			options.setPageLoadStrategy("eager");
			driver = new EdgeDriver(options);
			driver.manage().window().maximize();
		} else {

		System.out.println("No Browser Found");
		}

		driver.get(URL);
		return driver;
	}

}
