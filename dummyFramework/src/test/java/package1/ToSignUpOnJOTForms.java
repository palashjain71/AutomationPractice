package package1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ObjectRepository.homePage;
import SupportLibraries.ExcelUtil;
import SupportLibraries.report;

public class ToSignUpOnJOTForms extends BaseClass {

	@Test(dataProvider = "JOT_SignUp_Data")
	public void ToSignUpOnJOTFormsVerification(String UserName, String emaiId, String password) {

		try {

			report.InfoTest(test,
					"Test Data for Sign up -- >" + UserName + " email ID -->" + emaiId + "password  -->" + password);

			homePage hp = new homePage(driver,test);
			hp.clickOnSignUp();
			hp.fillSignupForm(UserName, emaiId, password);
			
			report.EndTest(test, driver);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@DataProvider(name = "JOT_SignUp_Data")
	public Object[][] signUpData() throws Exception {
		ExcelUtil ex = new ExcelUtil();
		Object[][] arrayObject = ex.getTableArray(systemDir + "\\src\\main\\java\\TestData\\SignUp.xlsx", "userData");
		return arrayObject;
	}

}
