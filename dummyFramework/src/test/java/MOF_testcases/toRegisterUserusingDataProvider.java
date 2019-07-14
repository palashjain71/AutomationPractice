package MOF_testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ObjectRepository.MOF_HomePage;
import ObjectRepository.homePage;
import ObjectRepository.register;
import SupportLibraries.ExcelUtil;
import SupportLibraries.report;
import package1.BaseClass;

public class toRegisterUserusingDataProvider extends BaseClass {

	@Test(dataProvider = "mofUserDetails")
	public void toTestRegisteruserFunctionality(String username, String password) {
		try {

			MOF_HomePage hm = new MOF_HomePage(driver, test);
			hm.isPageOpened();
			hm.enterloginDetials(username, password);
			
			report.PassTest(test, driver, username + password, "p1");

		} catch (Exception e) {
			report.FailTest(test, driver, e.getMessage(), "failed");
		}

	}

	@DataProvider(name = "mofUserDetails")
	public Object[][] signUpData() throws Exception {
		ExcelUtil ex = new ExcelUtil();
		Object[][] arrayObject = ex.getTableArray(systemDir + "\\src\\main\\java\\TestData\\mofUserData.xlsx",
				"userData");
		return arrayObject;
	}

}
