package java_example;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class test {

	private RemoteWebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws Exception {

		// Example test environment. NOTE: Gridlastic auto scaling requires all
		// these 3 environment variables in each request.
		String platform_name = "win8";
		String browser_name = "chrome";
		String browser_version = ""; // for Chrome leave empty

		// optional video recording
		String record_video = "False";

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
			capabilities.setCapability("video", "True");
		} else {
			capabilities.setCapability("video", "False");
		}
		
		if (browser_name.equalsIgnoreCase("chrome")){
			ChromeOptions options = new ChromeOptions();
			options.addArguments(Arrays.asList("--start-maximized"));
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			} 
		
		driver = new RemoteWebDriver(
				new URL(
						"http://ec2-54-176-153-50.us-west-1.compute.amazonaws.com:47665/wd/hub"),
				capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        if (!browser_name.equalsIgnoreCase("chrome")){
        driver.manage().window().maximize(); //the window maximize is buggy for chrome using latest chrome 38 version
        }

		if (record_video.equalsIgnoreCase("True")) {
			System.out
					.println("Test Video: https://s3-us-west-1.amazonaws.com/027a15f2-530d-31e5-f8cc-7ceaf6355377/8dc7b04e-6a48-58e9-b901-17ba724ce4ee/play.html?"
							+ ((RemoteWebDriver) driver).getSessionId());
		}
	}

	@Test(enabled = true)
	public void test_google() {
		driver.get("http://www.java.com");
		driver.findElementByLinkText("About Java").click();
		driver.findElementByLinkText("Troubleshoot Java").click();
		driver.findElementByLinkText("Support");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
	}

}

