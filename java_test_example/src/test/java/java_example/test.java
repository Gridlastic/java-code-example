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
		String platform_name = "win7";
		String browser_name = "internet explorer";
		String browser_version = "11"; // for Chrome leave empty

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
			capabilities.setCapability("video", "True");
		} else {
			capabilities.setCapability("video", "False");
		}
		
		if (browser_name.equalsIgnoreCase("chrome")){
			ChromeOptions options = new ChromeOptions();
			options.addArguments(Arrays.asList("--disable-extensions","--disable-logging","--test-type"));
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			} 
		
		driver = new RemoteWebDriver(
				new URL(
						"http://53YOotZpYNKdKrSRvUvKBd4e5ujPCohx:h5cwp1MdFnhfE0X0KiAcmvgiQqYiE5FM@6T9DOVV93KLAE2HR.gridlastic.com:80/wd/hub"),
				capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		if (record_video.equalsIgnoreCase("True")) {
			System.out
					.println("Test Video: https://s3-ap-southeast-2.amazonaws.com/b2729248-ak68-6948-a2y8-80e7479te16a/9ag7b09j-6a38-58w2-bb01-17qw724ce46t/play.html?"
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

