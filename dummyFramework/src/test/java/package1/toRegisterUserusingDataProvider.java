package package1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ObjectRepository.homePage;
import ObjectRepository.register;
import SupportLibraries.ExcelUtil;
import SupportLibraries.report;

public class toRegisterUserusingDataProvider extends BaseClass {

	@Test(dataProvider = "registerSheetData")
	public void toTestRegisteruserFunctionality(String firstName,String lastName) {
		try {

			homePage p = new homePage(driver, test);
			p.clickOnHeaderFreeDemo();
			
			register r = new register(driver,test);
			r.isPageOpened();
			r.registerPagefillformDetails(firstName	,lastName);

		} catch (Exception e) {
			report.FailTest(test, driver, e.getMessage(), "failed");
		}

	}

	
	@DataProvider(name = "registerSheetData")
	public Object[][] signUpData() throws Exception {
		ExcelUtil ex = new ExcelUtil();
		Object[][] arrayObject = ex.getTableArray(systemDir + "\\src\\main\\java\\TestData\\registerUserData.xlsx", "userData");
		return arrayObject;
	}
	
}
