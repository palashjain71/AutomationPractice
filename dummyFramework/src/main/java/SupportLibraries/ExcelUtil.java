package SupportLibraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	XSSFSheet ExcelWSheet;
	XSSFWorkbook ExcelWBook;

	public Object[][] getTableArray(String FilePath, String SheetName) throws Exception {

		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1; // as first row is header / column values
			int startCol = 0;
			int totalRows = ExcelWSheet.getLastRowNum();
			// you can write a function as well to get Column count
			int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
			tabArray = new String[totalRows][totalCols];
			for (int i = startRow; i <= totalRows; i++) {
				for (int j = startCol; j < totalCols; j++) {
					tabArray[i - 1][j] = getCellData(i, j);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}

		catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}

	
	public String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			XSSFRow row = ExcelWSheet.getRow(RowNum);
			String CellVal = row.getCell(ColNum).toString();
			return CellVal.trim();

		} catch (Exception e) {
			System.out.println("Read Excel inside" + e.getMessage());
			throw (e);
		}
	}
}
