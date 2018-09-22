package TestData;

import SupportLibraries.Util;

public class GlobalTestData {

	public static final String CurrentDateFolder = Util.DateString(); 
	public static final String arry_zip[]={"91304","75560","94602","77008","12828","77009","77041","77346","93311","75247","77092","77002","77373","77077"};
	public static final String FirstName = "TestFirst";
	public static final String LastName = "TestLastName";
	public static final String Address = "TestAddress";
	public static final String Address2 = "Houston";
	public static final String City = "testCity";
	public static final String Zip = "12828";
	public static final String LAZip = "91304";
	public static final String PhoneNumber = "9874563210";
	public static final String Comment="TestArea";
	public static final String CustomerCompanyDetails = "TestCustomerCompanyDetails";
	public static final String CustomerID = "";
	public static final String BulkLocation = "TestLocation";
	public static final String ContainerColor = "TestColor";
	public static final String ToCompareDBReportProb= Comment+";"+FirstName+";"+LastName+";"+Address+";"+City+";"+PhoneNumber;
	public static final String CSRegisteredUser= "pj2@mailinator.com";
	public static final String CSRegisteredUserPassword= "waste1234";
	public static final String ToCompareDBReportProbWithNoAddress= Comment+";"+FirstName+";"+LastName+";0;0;"+PhoneNumber+";0;";
	public static final String ToCompareDBReportProbLogin= Comment+";"+FirstName+";"+LastName+";"+Address+";Houston;"+PhoneNumber;
	public static final String ToCompareDBReportProbLALogin= Comment+";"+FirstName+";"+LastName+";"+"1930 ARGYLE AVE;HOLLYWOOD;"+PhoneNumber+";CA;90068";
	
}
