package practice.Testing;

import java.time.Duration;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Fitpeo {

	static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		
		WebDriverManager.edgedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
		//STEP 1 : Navigate to the FitPeo Homepage
		
		driver.get("https://www.fitpeo.com/home");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//STEP 2 : Navigate to the Revenue Calculator Page
		
		driver.findElement(By.xpath("//*[text()='Revenue Calculator']")).click();
		WebElement slider = driver.findElement(By.xpath("//input[@aria-orientation='horizontal']"));
		WebElement value = driver.findElement(By.xpath("//div[@class='MuiBox-root css-19xu03j'][2]//p[2]"));
		
		//STEP 3 : Scroll Down to the Slider section
		
		WebElement input = driver.findElement(By.xpath("//input[@type='number']"));
		Actions a = new Actions(driver);
		a.moveToElement(input).build().perform();
		
		//STEP 4 : Adjust the Slider
		
		int currentValue = Integer.parseInt(value.getText());
		int expected = 820;
		for(int i = 1; i<=expected-currentValue; i++) {
			slider.sendKeys(Keys.ARROW_RIGHT);
		}
		Assert.assertTrue("validate that slider value should be 820", value.getText().contentEquals("820"));
		Thread.sleep(2000);
		
		//STEP 5 : Update the Text Field:
		
		input("560");
		
		//STEP 6 : Validate Slider Value
		
		/*
		 * to verify after the slider position we can do using mathematics 
		 * 2000 patients means slider moved 100% so to validate that the slider is moved to 560
		 * patients we can do is using the slider width 560/2000*100 = 28 % so if the
		 * slider width is 28% then the slider is moved its position to 560
		 */
		
		Thread.sleep(5000);
		WebElement width = driver.findElement(By.xpath("//span[contains(@style,'left')][1]"));
		
		String positionValue = width.getAttribute("style").substring(16, 20);
		
		Assert.assertTrue("Validate that the slider position changed to 560", positionValue.contains("28%"));
		
		Thread.sleep(5000);
		
		input("820");
		
		//STEP 7 : Select CPT Codes
		
		driver.findElement(By.xpath("//*[text()='CPT-99091']/..//label//input")).click();
		driver.findElement(By.xpath("//*[text()='CPT-99453']/..//label//input")).click();
		driver.findElement(By.xpath("//*[text()='CPT-99454']/..//label//input")).click();
		
		//STEP 8 : Validate Total Recurring Reimbursement
		
		WebElement totalRem = driver.findElement(By.xpath("(//p[@class='MuiTypography-root MuiTypography-body1 inter css-12bch19'])[3]"));
		
		Assert.assertTrue("validate the total reiumbersment value", totalRem.getText().contains("98400"));
		
		Thread.sleep(5000);
		
		//STEP 9 : QUIT the driver
		
		driver.quit();
		
	}
	
	public static void input(String value) {
		WebElement input = driver.findElement(By.xpath("//input[@type='number']"));
		input.click();
		input.sendKeys((Keys.CONTROL+"A"));
		input.sendKeys(Keys.BACK_SPACE);
		input.sendKeys(value);
	}
	
}
