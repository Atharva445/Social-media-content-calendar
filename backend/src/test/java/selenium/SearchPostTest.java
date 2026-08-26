package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchPostTest extends BaseTest {

    @Test
    void searchSocialMediaPost() {

        String searchKeyword = "Selenium Test Post";

        // 1. Verify Content Calendar is loaded
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(), 'Content Calendar')]")
                )
        );

        // 2. Find search box
        var searchBox = driver.findElement(
                By.xpath(
                        "//input[@placeholder='Search by title, content or platform...']"
                )
        );

        // 3. Enter search keyword
        searchBox.sendKeys(searchKeyword);

        // 4. Click Search
        driver.findElement(
                By.xpath("//button[normalize-space()='Search']")
        ).click();

        // 5. Wait for matching post
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + searchKeyword
                                        + "']"
                        )
                )
        );

        // 6. Verify matching post is displayed
        assertTrue(
                driver.findElement(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + searchKeyword
                                        + "']"
                        )
                ).isDisplayed(),
                "Matching post should be displayed after search"
        );
    }
}