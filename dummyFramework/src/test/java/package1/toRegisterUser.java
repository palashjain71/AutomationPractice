package package1;

import org.testng.annotations.Test;
import ObjectRepository.homePage;
import ObjectRepository.register;

public class toRegisterUser extends BaseClass {

	@Test()
	public void toTestRegisteruserFunctionality() {
		try {

			homePage p = new homePage(driver, test);
			p.clickOnHeaderFreeDemo();
			
			register r = new register(driver,test);
			r.isPageOpened();
			r.registerPagefillformDetails("Palash", "Jain");
			
			

		} catch (Exception e) {

		}

	}

}
