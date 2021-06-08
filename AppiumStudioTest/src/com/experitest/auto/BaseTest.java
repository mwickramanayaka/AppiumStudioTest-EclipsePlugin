package com.experitest.auto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class BaseTest {
	protected Client client = null;
	protected GridClient gridClient = null;
	protected Properties cloudProperties = new Properties();

	public void init(String deviceQuery, String testName) throws Exception {
		initCloudProperties();
		String url = getProperty("url", cloudProperties);
		String accessKey = getProperty("accessKey", cloudProperties);
		if (accessKey != null && !accessKey.isEmpty()) {
			gridClient = new GridClient(accessKey, url);
		} else {
			String username = getProperty("username", cloudProperties);
			String password = getProperty("password", cloudProperties);
			String project = getProperty("project", cloudProperties);
			gridClient = new GridClient(username, password, project, url);
		}
		String adhocDeviceQuery = System.getenv("deviceQuery");
		if (adhocDeviceQuery != null) {
			System.out.println("[INFO] Redirecting test to the current device.");
			deviceQuery = adhocDeviceQuery;
		}
		client = gridClient.lockDeviceForExecution(testName, deviceQuery, 30, 300000);
		File reporterDir = new File(System.getProperty("user.dir"), "reports");
		reporterDir.mkdirs();
		client.setReporter("xml", reporterDir.getAbsolutePath(), testName);
	}

	protected String getProperty(String property, Properties props) {
		if (System.getProperty(property) != null) {
			return System.getProperty(property);
		} else if (System.getenv().containsKey(property)) {
			return System.getenv(property);
		} else if (props != null) {
			return props.getProperty(property);
		}
		return null;
	}

	private void initCloudProperties() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader("cloud.properties");
		cloudProperties.load(fr);
		fr.close();
	}

	@AfterMethod
	public void tearDown() {
		// Generates a report of the test case.
		// For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
		client.generateReport(false);
		// Releases the client so that other clients can approach the agent in the near future.
		client.releaseClient();
	}
}
