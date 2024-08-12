package com.ecom.testCases;

import java.io.IOException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.ecom.utilites.TestUtilities;

public class BaseClass extends TestUtilities{


	@BeforeMethod
	@Parameters("browserName")
	public void openWebSite(@Optional("chrome") String browserName) throws IOException {
		
		TestDriver.setUp(browserName,getValues("baseURL"));
	}

	@AfterMethod
	public void tearDown() {

		TestDriver.tearDown();

	}

}
