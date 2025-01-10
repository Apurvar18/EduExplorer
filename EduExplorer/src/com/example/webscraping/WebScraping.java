package com.example.webscraping;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebScraping {
    public static void main(String[] args) throws InterruptedException {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/apurvarajput/Downloads/chromedriver-mac-arm64/chromedriver");
        //System.setProperty("webdriver.chrome.driver", "/Users/apurvarajput/Downloads/chromedriver-mac-arm642/chromedriver");

        WebDriver driver = null; // Initialize WebDriver
        WebDriverWait wait = null; // Initialize WebDriverWait
        try {
            // Create a new instance of the Chrome driver
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Open a webpage
            driver.get("https://www.linkedin.com/learning/?u=56973065");

            WebScraping webScraping = new WebScraping();

            webScraping.searchKeyWord(driver);

            Thread.sleep(5000);

            webScraping.selectLevel(driver);

            Thread.sleep(5000);

            //Select type filter
            webScraping.selectType(driver);

            //Select timecompletion filter
            webScraping.selectTimeCompletion(driver);

            //select software filter
            webScraping.selectTSoftware(driver);

            //write data into csv file
            webScraping.WriteCSVFile(driver, wait);

            Thread.sleep(5000);

            // Create an instance of JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll to the bottom of the page
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            WebElement scrolldown = driver.findElement(By.xpath("/html/body/div/section[2]/div/div[1]/ul/li[3]/a"));
            scrolldown.click();

            Thread.sleep(5000);

            //write multiple page data into csv file
            webScraping.WriteCSVFile(driver, wait);

            //Navigate Back
            driver.navigate().back();

            Thread.sleep(5000);

            // Scroll back to the top of the page
            js.executeScript("window.scrollTo(0, 0);");

            //Rest Filter
            webScraping.RestFilter(driver);

            Thread.sleep(5000);

            // Close the browser
            driver.quit();
        }
        catch (Exception e) {
        e.printStackTrace();
        }
        finally {
            if (driver != null) {
                driver.quit(); // Close the driver explicitly
            }
        }
    }

    //Function searchKeyWord
    public static void searchKeyWord(WebDriver driver)
    {
        // Locate the input element using its data-tracking-control-name attribute
        //WebElement searchInput = driver.findElement(By.cssSelector("input[data-tracking-control-name='homepage-learning_learning-search-bar_keywords_dismissable-input']"));
       // WebElement searchInput = driver.findElement(By.xpath("//*[@id=\"ember18\"]/div/input"));
       // WebElement searchInput = driver.findElement(By.cssSelector("input[placeholder='Search for learning content']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = driver.findElement(By.cssSelector("input[data-tracking-control-name='homepage-learning_learning-search-bar_keywords_dismissable-input']"));
        //WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search for learning content']")));

        //WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search for learning content']"));




        searchInput.sendKeys("Data Science");
        searchInput.sendKeys(Keys.RETURN);
    }

    //Function selectLevel
    public static void selectLevel(WebDriver driver)
    {
        // Locate and click the dropdown button
        WebElement LevelDropDown = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[2]/div/div/button"));
        LevelDropDown.click();

        WebElement BeginnerOption = driver.findElement(By.xpath("//*[@id=\"difficultyLevel-0\"]"));
        BeginnerOption.click();

        WebElement DoneButton = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[2]/div/div/div/button"));
        DoneButton.click();

    }

    //Function selectType
    public static void selectType(WebDriver driver)
    {
        WebElement TypeDropDown = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[3]/div/div/button"));
        TypeDropDown.click();

        WebElement CourseOption = driver.findElement(By.xpath("//*[@id=\"entityType-0\"]"));
        CourseOption.click();

        WebElement DoneButton = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[3]/div/div/div/button"));
        DoneButton.click();
    }

    //Function selectTimeCompletion
    public static void selectTimeCompletion(WebDriver driver)
    {
        WebElement TimeDropDown = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[4]/div/div/button"));
        TimeDropDown.click();

        WebElement TimeOption = driver.findElement(By.xpath("//*[@id=\"durationV2-3\"]"));
        TimeOption.click();

        WebElement DoneButton = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[4]/div/div/div/button"));
        DoneButton.click();
    }

    //Function selectTSoftware
    public static void selectTSoftware(WebDriver driver)
    {
        WebElement SoftwareDropDown = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[5]/div/div/button"));
        SoftwareDropDown.click();

        WebElement SoftwareOption = driver.findElement(By.xpath("//*[@id=\"softwareNames-0\"]"));
        SoftwareOption.click();

        WebElement DoneButton = driver.findElement(By.xpath("//*[@id=\"learning-filters\"]/ul/li[5]/div/div/div/button"));
        DoneButton.click();
    }

    //Function Rest Filter
    public static void RestFilter(WebDriver driver)
    {
        WebElement restFilter = driver.findElement(By.xpath("/html/body/div/section[1]/div/div/div[2]/form[2]/button"));
        restFilter.click();
    }

    //Function WriteCSVFile
    public static void WriteCSVFile(WebDriver driver,WebDriverWait wait)
    {
        // Locate the anchor elements (assuming there are multiple courses)
        List<WebElement> linkElements = driver.findElements(By.xpath("//a[contains(@class, 'base-card__full-link')]"));

        // Create or open the CSV file in append mode
        try (FileWriter csvWriter = new FileWriter("scraped_courses.csv", true)) {
            // Check if the file is empty and write headers only if it's the first entry
            if (new java.io.File("scraped_courses.csv").length() == 0) {
                csvWriter.append("Course Title,Course URL,Duration,Creator,Views");
                csvWriter.append("\n");
            }

            int index = 1;
            // Loop through each link element and scrape data
            for (WebElement linkElement : linkElements) {
                // Get the href attribute (link)
                String courseUrl = linkElement.getAttribute("href");

                // Get the title from the span with the class "sr-only"
                String courseTitle = linkElement.findElement(By.xpath(".//span[@class='sr-only']")).getText().trim();

                courseTitle = courseTitle.replace(",", " ");
                // Locate the duration element
                String duration = "N/A"; // Default value


                try {
                    // Wait for the duration element to be present
                    WebElement durationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(@class, 'base-card__full-link')]/following::div[contains(@class, 'search-entity-media__duration')]["+index+"]")));
                    // Extract the text for duration
                    duration = durationElement.getText().trim();
                    index++;
                } catch (Exception e) {
                    System.out.println("Duration not found for: " + courseTitle);
                }

                // Extract the author
                String authorName = "N/A";
                try {

                    // Assuming the author is under a specific class
                    WebElement authorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(@class, 'base-card__full-link')]/following::h4[contains(@class, 'base-search-card__subtitle')][1]")));

                    authorName = authorElement.getText().trim();
                } catch (Exception e) {
                    System.out.println("Author not found for: " + courseTitle);
                }

                // Extract the views count
                String viewerText = "N/A";
                try {
                    // Assuming the views count is under a specific class
                    WebElement viewsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[contains(@class, 'base-card__full-link')]/following::span[contains(@class, 'base-search-card__metadata-item')]["+index+"]")));
                    viewerText = viewsElement.getText().trim();
                    viewerText =viewerText.replace(",","");
                } catch (Exception e) {
                    System.out.println("Views count not found for: " + courseTitle);
                }

                // Debug information
                /*System.out.println("Title: " + courseTitle);
                System.out.println("URL: " + courseUrl);
                System.out.println("Duration: " + duration);
                System.out.println("Author: " + authorName);
                System.out.println("Viewer Count: " + viewerText);
                System.out.println("----------");*/

                // Write the scraped data to the CSV file
                csvWriter.append(courseTitle);
                csvWriter.append(",");
                csvWriter.append(courseUrl);
                csvWriter.append(",");
                csvWriter.append(duration);
                csvWriter.append(",");
                csvWriter.append(authorName);
                csvWriter.append(",");
                csvWriter.append(viewerText);
                csvWriter.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

