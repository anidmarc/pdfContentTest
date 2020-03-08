package PDFContent;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class PDF {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "D:\\Chromedriver\\chromedriver.exe");
		FileInputStream fis;
		try {
			
			// The below line will read the data from the list mentioned in the .xlsx file
			fis = new FileInputStream("D:\\Aniruddha Workspace\\PDFContentTesting\\src\\test\\java\\PDFContent\\url.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet Sheet1 = wb.getSheetAt(0);
			int count = 0;
			int rowcount = Sheet1.getPhysicalNumberOfRows();
			int colcount = Sheet1.getRow(0).getPhysicalNumberOfCells();
			System.out.println("Total Number of Rows is ::" + rowcount);
			System.out.println("Total number of Col is ::" + colcount);
			
			
			for (int k = 0; k < rowcount; k++) {
				for (int j = 0; j < colcount; j++) {
					String testdata1 = Sheet1.getRow(k).getCell(j).getStringCellValue();
					System.out.println("Test data from excel cell  :" + testdata1);
					
			    	WebDriver driver = new ChromeDriver();
			    	
			    	// You need to open the pdf file in the browser and need to give the browse url location
			    	driver.get("file:///D:/Aniruddha%20Workspace/PDFContentTesting/src/test/java/PDFContent/"+testdata1);
			    	String currentURL = driver.getCurrentUrl();
			    	System.out.println("Current URL is "+currentURL);
					URL url = new URL(currentURL);
					
					InputStream is = url.openStream();
					
					BufferedInputStream fileParse = new BufferedInputStream(is);
					PDDocument document = null;
					
					// The below line will read the content of the pdf
					document = PDDocument.load(fileParse);
					String  pdfContent = new PDFTextStripper().getText(document);
					
					// The below block will verify the below mentioned text is present on the pdf
					if(!pdfContent.contains("Savvas Learning")) {
						System.out.println("Pearson Text is present in " + testdata1);
					}
					driver.close();
					wb.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
