package package1;

import org.testng.annotations.Test;
import ObjectRepository.homePage;

public class toTestDriverHomePageContent extends BaseClass {

	@Test()
	public void toTestDriverHomePageContentHeaderSection() {
		try {

			homePage p = new homePage(driver, test);
			p.verifyHeaderSection();

		} catch (Exception e) {

		}

	}

}
