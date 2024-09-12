package Edureka;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonProductScraper {

    public static class Product {
        String name;
        int price;

        public Product(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }

    public static void main(String[] args) {
        // Set up ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\\\selenium webdriver\\\\ChromeDriver\\\\chromedriver-win32\\\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        // Open amazon.in
        driver.get("https://www.amazon.in");

        // Search for 'lg soundbar'
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("lg soundbar");
        searchBox.submit();

        // Wait for results to load (optional: add WebDriverWait for better performance)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get product names and prices from the first page
        List<WebElement> productElements = driver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
        List<WebElement> priceElements = driver.findElements(By.xpath("//span[@class='a-price-whole']"));

        // Store product names and prices in a list of Product objects
        List<Product> productPriceList = new ArrayList<>();

        for (int i = 0; i < productElements.size(); i++) {
            String productName = productElements.get(i).getText();

            // Handle case where price is missing
            int price;
            if (i < priceElements.size()) {
                String priceString = priceElements.get(i).getText().replace(",", "");
                price = Integer.parseInt(priceString);
            } else {
                price = 0; // Consider 0 if price is not present
            }

            // Add product name and price to the list
            productPriceList.add(new Product(productName, price));
        }

        // Sort the list by price
        Collections.sort(productPriceList, Comparator.comparingInt(p -> p.price));

        // Print sorted products and prices
        for (Product product : productPriceList) {
            System.out.println(product.price + " " + product.name);
        }

        // Close the browser
        driver.quit();
    }
}
