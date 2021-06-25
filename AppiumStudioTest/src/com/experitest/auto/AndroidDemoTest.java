package com.experitest.auto;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class AndroidDemoTest extends BaseTest {

	@BeforeMethod
	@Parameters("deviceQuery")
	public void setUp(@Optional("@os='android'") String deviceQuery) throws Exception{
		init(deviceQuery, "AndroidDemoTest");
		client.install("cloud:com.appsgallery.sagar.loginregistrationexample/.MainActivity:3.0", false, false);
		client.launch("com.appsgallery.sagar.loginregistrationexample/.MainActivity", false, true);
		Thread.sleep(2000);
	}

	@Test
	public void signin() throws InterruptedException{
		ExtentSparkReporter htmlReporter;
		ExtentReports extent;
		htmlReporter = new ExtentSparkReporter("extent.html");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		String username = "Steve";
		String password = "1qaz2wsx";

		client.click(in.Repo.zone("SigninPage.REGISTER"), in.Repo.element("SigninPage.REGISTER"), 0, 1);
		Thread.sleep(2000);
		
		client.elementSendText(in.Repo.zone("Register.editTextUserName"), in.Repo.element("Register.editTextUserName"), 0, username);
		client.elementSendText(in.Repo.zone("Register.editTextPassword"), in.Repo.element("Register.editTextPassword"), 0, password);
		client.elementSendText(in.Repo.zone("Register.editTextConfirmPassword"), in.Repo.element("Register.editTextConfirmPassword"), 0, password);
		client.click(in.Repo.zone("Register.CREATE"), in.Repo.element("Register.CREATE"), 0, 1);
		Thread.sleep(2000);
		
		client.launch("com.appsgallery.sagar.loginregistrationexample/.MainActivity", false, true);
		Thread.sleep(2000);
		
		client.elementSendText(in.Repo.zone("SigninPage.editLogin"), in.Repo.element("SigninPage.editLogin"), 0, username);
		client.elementSendText(in.Repo.zone("SigninPage.editPasswordLogin"), in.Repo.element("SigninPage.editPasswordLogin"), 0, password);
		client.click(in.Repo.zone("SigninPage.LOGIN"), in.Repo.element("SigninPage.LOGIN"), 0, 1);
		Thread.sleep(2000);
		
	}
	
		
}
