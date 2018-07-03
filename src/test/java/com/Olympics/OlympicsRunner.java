package com.Olympics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsRunner {

	WebDriver driver;

	@BeforeClass // runs only once at the beginning of the class
	public void setingUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}

	@BeforeMethod
	public void navigatingToWebPage() {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
	}

	@Test(priority = 1, enabled = false)
	public void verifySortedByRank() throws InterruptedException {

		List<WebElement> rank = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/td[1]"));

		List<Integer> rankList = new ArrayList<>();

		for (int i = 0; i < rank.size() - 1; i++) {
			int j = Integer.parseInt(rank.get(i).getText());
			rankList.add(j);

		}
		List<Integer> countBySeq = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Assert.assertEquals(countBySeq, rankList);
		Thread.sleep(1000);
	}

	@Test(priority = 2, enabled = true)
	public void verifySortedByCountry() throws InterruptedException {
		// clicking NOC
		driver.findElement(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])/thead/tr/th[2]"))
				.click();
		Thread.sleep(500);

		List<WebElement> countryList = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/th"));

		List<String> actualList = new ArrayList<>();

		for (WebElement str : countryList) {
			actualList.add(str.getText());
		}

		List<String> expectedList = new ArrayList<>(actualList);
		Collections.sort(expectedList);

		Assert.assertEquals(expectedList, actualList);
	}

	@Test(priority = 3, enabled = true)
	public void verifyNotSortedByRank() throws InterruptedException {

		List<WebElement> rank = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/td[1]"));

		List<Integer> rankList = new ArrayList<>();

		for (int i = 0; i < rank.size() - 1; i++) {
			int j = Integer.parseInt(rank.get(i).getText());
			rankList.add(j);

		}
		List<Integer> countBySeq = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Assert.assertNotEquals(countBySeq, rankList);

	}

	@Test(priority = 4, enabled = true)
	public void testMethodsByMedals() throws InterruptedException {
		// Thread.sleep(1000);
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics");

		goldMedals();
		silverMedals();
		bronzeMedals();
		totalMedals();
		silverCount18();
	}

	@AfterClass
	public void closeBrowser() {
		driver.close();
	}

	@Parameter
	public void goldMedals() {
		List<WebElement> country = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/th"));
		List<WebElement> gold = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/td[2]"));

		Map<Integer, String> listCountry = new HashMap<>();

		for (int i = 0; i < gold.size() - 1; i++) {
			int medal = Integer.parseInt(gold.get(i).getText());
			String coun = country.get(i).getText();
			listCountry.put(medal, coun);
		}

		Map.Entry<Integer, String> firstEntry = listCountry.entrySet().iterator().next();
		int largest = firstEntry.getKey();
		String largestValue = firstEntry.getValue();
		
		for (Entry<Integer, String> entry : listCountry.entrySet()) {
			int key = entry.getKey();
			if(key>largest) {
				largest=key;
				largestValue=entry.getValue();
			}
		}
		System.out.println("Most Gold Medals: "+largest+", "+largestValue);
		
		
//		TreeMap<Integer, String> sorted = new TreeMap<>(Collections.reverseOrder());
//		sorted.putAll(listCountry);
//
//		for (Entry<Integer, String> entry : sorted.entrySet()) {
//			System.out.println("Gold = " + entry.getKey() + ", Value = " + entry.getValue());
//		}
	}
	
	@Parameter
	public void silverMedals() {
		List<WebElement> country = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/th"));
		List<WebElement> gold = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/td[3]"));

		Map<Integer, String> listCountry = new HashMap<>();

		for (int i = 0; i < gold.size() - 1; i++) {
			int medal = Integer.parseInt(gold.get(i).getText());
			String coun = country.get(i).getText();
			listCountry.put(medal, coun);
		}

		Map.Entry<Integer, String> firstEntry = listCountry.entrySet().iterator().next();
		int largest = firstEntry.getKey();
		String largestValue = firstEntry.getValue();
		
		for (Entry<Integer, String> entry : listCountry.entrySet()) {
			int key = entry.getKey();
			if(key>largest) {
				largest=key;
				largestValue=entry.getValue();
			}
		}
		System.out.println("Most Silver Medals: "+largest+", "+largestValue);
	}
	
	@Parameter
	public void bronzeMedals() {
		List<WebElement> country = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/th"));
		List<WebElement> gold = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/td[4]"));

		Map<Integer, String> listCountry = new HashMap<>();

		for (int i = 0; i < gold.size() - 1; i++) {
			int medal = Integer.parseInt(gold.get(i).getText());
			String coun = country.get(i).getText();
			listCountry.put(medal, coun);
		}

		Map.Entry<Integer, String> firstEntry = listCountry.entrySet().iterator().next();
		int largest = firstEntry.getKey();
		String largestValue = firstEntry.getValue();
		
		for (Entry<Integer, String> entry : listCountry.entrySet()) {
			int key = entry.getKey();
			if(key>largest) {
				largest=key;
				largestValue=entry.getValue();
			}
		}
		System.out.println("Most Bronze Medals: "+largest+", "+largestValue);
	}
	
	@Parameter
	public void totalMedals() {
		List<WebElement> country = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/th"));
		List<WebElement> gold = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/td[5]"));

		Map<Integer, String> listCountry = new HashMap<>();

		for (int i = 0; i < gold.size() - 1; i++) {
			int medal = Integer.parseInt(gold.get(i).getText());
			String coun = country.get(i).getText();
			listCountry.put(medal, coun);
		}

		Map.Entry<Integer, String> firstEntry = listCountry.entrySet().iterator().next();
		int largest = firstEntry.getKey();
		String largestValue = firstEntry.getValue();
		
		for (Entry<Integer, String> entry : listCountry.entrySet()) {
			int key = entry.getKey();
			if(key>largest) {
				largest=key;
				largestValue=entry.getValue();
			}
		}
		System.out.println("Total count Medals: "+largest+", "+largestValue);
	}
	
	@Parameter
	public void silverCount18() {
		List<WebElement> country = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/th"));
		List<WebElement> gold = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter'])//tbody/tr/td[3]"));

		Map<Integer, String> listCountry = new HashMap<>();

		for (int i = 0; i < gold.size() - 1; i++) {
			int medal = Integer.parseInt(gold.get(i).getText());
			String coun = country.get(i).getText();
			listCountry.put(medal, coun);
		}

		Map.Entry<Integer, String> firstEntry = listCountry.entrySet().iterator().next();
		int eighteen = firstEntry.getKey();
		String eightValue = firstEntry.getValue();
		
		for (Entry<Integer, String> entry : listCountry.entrySet()) {
			int key = entry.getKey();
			if(key==18) {
				eighteen=key;
				eightValue=entry.getValue();
			}
		}
		System.out.println("Total count of Silver Medals equals 18: "+eighteen+", "+eightValue);
	}
	
	

}
