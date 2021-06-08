package com.experitest.auto;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class AndroidDemoTest extends BaseTest {
	
	@BeforeMethod
	@Parameters("deviceQuery")
	public void setUp(@Optional("@os='android'") String deviceQuery) throws Exception{
		init(deviceQuery, "AndroidDemoTest");
		client.install("cloud:com.medium.reader/com.medium.android.donkey.start.SplashActivity:4.4.1089264", false, false);
		client.launch("com.medium.reader/com.medium.android.donkey.start.SplashActivity", false, true);

	}
	
	@Test
	public void test(){
		client.click(in.Repo.zone("LandingPage.Sign_in_"), in.Repo.element("LandingPage.Sign_in_"), 0, 1);
		
	}
	
	
}
