// NOTE: replace USERNAME:ACCESS_KEY@YOUR_SUBDOMAIN and VIDEO_URL with your credentials found in the Gridlastic dashboard
// ALSO SEE https://github.com/Gridlastic/demo1 FOR JAVA TESTNG EXAMPLES WITH PARALLEL TEST EXECUTIONS
// NOTE: THE FIRST TEST ON A GRID NODE AFTER BEING LAUNCHED CAN BE SLOW, FOLLOWING TESTS ARE MUCH FASTER AFTER THE NODE IS "WARMED" UP.

package java_example;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class test {

	// VIDEO_URL set to like "https://s3-ap-southeast-2.amazonaws.com/b2729248-ak68-6948-a2y8-80e7479te16a/9ag7b09j-6a38-58w2-bb01-17qw724ce46t/play.html?".
	// Find this VIDEO_URL value in your Gridlastic dashboard.
	private static final String VIDEO_URL = null; 
	private RemoteWebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {

		// Example test environment. NOTE: Gridlastic auto scaling requires all
		// these 3 environment variables in each request.
		String platform_name = "win7";
		String browser_name = "firefox";
		String browser_version = "47"; // for Chrome leave empty

		// optional video recording
		String record_video = "True";

		DesiredCapabilities capabilities = new DesiredCapabilities();
		if (platform_name.equalsIgnoreCase("win7")) {
			capabilities.setPlatform(Platform.VISTA);
		}
		if (platform_name.equalsIgnoreCase("win8")) {
			capabilities.setPlatform(Platform.WIN8);
		}
		if (platform_name.equalsIgnoreCase("win8_1")) {
			capabilities.setPlatform(Platform.WIN8_1);
		}
		if (platform_name.equalsIgnoreCase("linux")) {
			capabilities.setPlatform(Platform.LINUX);
		}
		capabilities.setBrowserName(browser_name);
		capabilities.setVersion(browser_version);

		// video record
		if (record_video.equalsIgnoreCase("True")) {
			capabilities.setCapability("video", "True"); // NOTE: "True" is a case sensitive string, not boolean.
		} else {
			capabilities.setCapability("video", "False"); // NOTE: "False" is a case sensitive string, not boolean.
		}
		
		if (browser_name.equalsIgnoreCase("chrome")){
			ChromeOptions options = new ChromeOptions();
			// On Linux start-maximized does not expand browser window to max screen size. Always set a window size.
			if (platform_name.equalsIgnoreCase("linux")) {
				options.addArguments(Arrays.asList("--window-position=0,0"));
				options.addArguments(Arrays.asList("--window-size=1920,1080"));	
				} else {
				options.addArguments(Arrays.asList("--start-maximized"));
				}
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			} 
		
	
		//replace USERNAME:ACCESS_KEY@SUBDOMAIN with your credentials found in the Gridlastic dashboard
		driver = new RemoteWebDriver(new URL("http://USERNAME:ACCESS_KEY@YOUR_SUBDOMAIN.gridlastic.com:80/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize(); // Always maximize firefox on windows
		
        // On LINUX/FIREFOX the "driver.manage().window().maximize()" option does not expand browser window to max screen size. Always set a window size.
    	if (platform_name.equalsIgnoreCase("linux") && browser_name.equalsIgnoreCase("firefox")) {
    		driver.manage().window().setSize(new Dimension(1920, 1080));	
    	}
        

		if (record_video.equalsIgnoreCase("True")) {
			System.out.println("Test Video: " + VIDEO_URL + ((RemoteWebDriver) driver).getSessionId());
		}
	}

	@Test(enabled = true)
	 public void test_site() throws Exception  { 	
		driver.get("https://www.google.com/ncr");
		Thread.sleep(10000); //slow down for demo purposes
		WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("webdriver");
        element.submit();
        Thread.sleep(5000); //slow down for demo purposes
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
	}

}

