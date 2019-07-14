package MOF_testcases;

import org.testng.annotations.Test;

import ObjectRepository.MOF_HomePage;
import ObjectRepository.MOF_SignUpPage;
import ObjectRepository.homePage;
import ObjectRepository.register;
import SupportLibraries.Util;
import TestData.GlobalTestData;
import package1.BaseClass;

public class toRegisterUser extends BaseClass {

	@Test()
	public void toTestRegisteruserFunctionality() {
		try {

		MOF_HomePage hm = new MOF_HomePage(driver, test);
		hm.isPageOpened();
		hm.clickOnresgisterHere();
		
		MOF_SignUpPage sign =new MOF_SignUpPage(driver, test);
		sign.isPageOpened();
		String email = "automation"+Util.randomNum()+"@mailinator.com";
		sign.enterSignupDetails(email, GlobalTestData.FirstName,GlobalTestData.mobileNubmer , GlobalTestData.officePhonNumber);
		
		
			

		} catch (Exception e) {

		}

	}

}
