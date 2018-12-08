package package1;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SupportLibraries.ExcelUtil;
import SupportLibraries.report;

public class testDataProvider extends BaseClass {

	@Test(dataProvider = "DataProvider1")
	public void testDataProvider2(String MailAddress, String DeliveryAdd, String FirstName) throws Exception {
		try {
			
			System.out.println(MailAddress);
			System.out.println(DeliveryAdd);
			System.out.println(FirstName);

		} catch (Exception e) {
			report.InfoTest(test, " message " + e.getMessage());
			report.FailTest(test, driver, "Element not visible ", "Element");
		}
	}

	@DataProvider(name = "DataProvider1")
	public Object[][] loginData() throws Exception {
		ExcelUtil ex = new ExcelUtil();
		Object[][] arrayObject = ex.getTableArray("E:\\testdata.xlsx", "test");
		return arrayObject;
	}


	
}
