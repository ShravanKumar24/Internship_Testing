package com.ecom.testCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.ecom.Exceldataproviders.DataExtractors;
import com.ecom.pageObjects.CartPage;
import com.ecom.pageObjects.CheckOutPage;
import com.ecom.pageObjects.LoginPage;
import com.ecom.pageObjects.ProductPage;
import com.ecom.pageObjects.WishlistPage;

public class TestCase1 extends BaseClass {

	LoginPage loginPage;
	ProductPage productPage;
	CartPage cartPage;
	WishlistPage wishlistPage;
	CheckOutPage checkoutPage;

	@Test
	public void test01() {

		/* Verifying the sorting feature by Price */

		productPage = new ProductPage(TestDriver.getDriver());
		// Clicking on Mobile section
		productPage.mobileSection();
		// Getting the list of product price webElements
		List<WebElement> productPriceElements = productPage.getProductPrice();
		// Getting element text values, sorting and collecting into list
		List<String> expPrices = productPriceElements.stream().map(s -> s.getText()).sorted()
				.collect(Collectors.toList());
		// Sorting the products by Price
		productPage.getSorting("Price");
		// Getting element text values and collecting into list
		List<String> actPrices = productPriceElements.stream().map(s -> s.getText()).collect(Collectors.toList());
		Assert.assertEquals(actPrices, expPrices);
	}

	@Test
	public void testDay1TestCase1() {

		/*
		 * Verifying the sorting feature By Name The products are sorted as per the
		 * expected results
		 */
		productPage = new ProductPage(TestDriver.getDriver());
		// Clicking on Mobile section
		productPage.mobileSection();
		// Getting the list of product names webElements
		List<WebElement> productNames = productPage.getProductNames();
		// Getting element text values, sorting and collecting into list
		List<String> expProductNames = productNames.stream().map(s -> s.getText()).sorted()
				.collect(Collectors.toList());
		// Sorting the mobile products by Name
		productPage.getSorting("Name");
		// Getting element text values and collecting into list
		List<String> actProductNames = productNames.stream().map(s -> s.getText()).collect(Collectors.toList());

		Assert.assertEquals(actProductNames, expProductNames);

	}

	@Test(dataProvider = "testCase2Data", dataProviderClass = DataExtractors.class)
	public void testDay2TestCase2(String expPrice, String productName) {
		/*
		 * Verifying the cost of the product is same in list page as well as details
		 * page
		 */
		String actPrice = "";
		productPage = new ProductPage(TestDriver.getDriver());
		productPage.mobileSection();
		List<WebElement> productsList = productPage.getProductNames();
		List<WebElement> productsPrice = productPage.getProductsPrice();

		for (WebElement product : productsList) {
			if (product.getText().equalsIgnoreCase(productName)) {
				for (WebElement productPrice : productsPrice) {

					if (productPrice.getText().substring(0, 4).equals(expPrice)) {
						actPrice = productPrice.getText().substring(0, 4);
					}
				}
			}
		}

		Assert.assertEquals(actPrice, expPrice);

	}

	@Test(dataProvider = "testCase3Data", dataProviderClass = DataExtractors.class)
	public void testDay3TestCase3(String productName, String expErrorMsg, String expEmptyMsg)
			throws InterruptedException {
		/*
		 * Verifying the quantity of the product are not adding more than the products
		 * available in the store
		 */

		String expCartPageTitle = getValues("cartPageTitle");
		int quantity = 1000;
		productPage = new ProductPage(TestDriver.getDriver());
		productPage.mobileSection();
		List<WebElement> productsList = productPage.getProductNames();
		List<WebElement> cartBtns = productPage.addToCart();

		/*
		 * for (int i = 0; i < productsList.size(); i++) { if
		 * (productsList.get(i).getText().equalsIgnoreCase(productName)) {
		 * productPage.addToCart().get(i).click(); break; } }
		 */

		IntStream.range(0, productsList.size()).filter(i -> productsList.get(i).getText().equalsIgnoreCase(productName))
				.findFirst().ifPresent(i -> cartBtns.get(i).click());

		Thread.sleep(3000);
		cartPage = new CartPage(TestDriver.getDriver());

		String actCartPageTitle = cartPage.getTitle();
		cartPage.addQuantity(quantity);
		cartPage.updateQuantity();
		String actErrorMsg = cartPage.getErrorMsg();
		cartPage.emptyCart();
		String actEmptyMsg = cartPage.getTitle();

		SoftAssert soft = new SoftAssert();
		soft.assertEquals(actCartPageTitle, expCartPageTitle);
		soft.assertEquals(actErrorMsg, expErrorMsg);
		soft.assertEquals(actEmptyMsg, expEmptyMsg);
		soft.assertAll();

	}

	@Test(dataProvider = "testCase4Data", dataProviderClass = DataExtractors.class)
	public void testDay4TestCase4(String product1, String product2) throws InterruptedException {
		/*
		 * Comparing the two products
		 */

		List<String> actProduct = new ArrayList<>();
		List<String> expProducts = Arrays.asList(product1, product2);
		productPage = new ProductPage(TestDriver.getDriver());
		productPage.mobileSection();
		List<WebElement> productsList = productPage.getProductNames();
		List<WebElement> compareBtn = productPage.addToCompare();

		for (int i = 0; i < productsList.size(); i++) {
			if (expProducts.contains(productsList.get(i).getText())) {
				compareBtn.get(i).click();
			}
		}

		productPage.goToCompare();
		Set<String> wind = TestDriver.getDriver().getWindowHandles();
		Iterator<String> it = wind.iterator();
		String parentId = it.next();
		String childId = it.next();

		TestDriver.getDriver().switchTo().window(childId);
		List<WebElement> names = TestDriver.getDriver().findElements(By.tagName("h2"));
		for (WebElement name : names) {
			actProduct.add(name.getText());
		}

		TestDriver.getDriver().findElement(By.cssSelector("button[title='Close Window']")).click();
		TestDriver.getDriver().switchTo().window(parentId);
		Assert.assertEquals(actProduct, actProduct);
	}

}
