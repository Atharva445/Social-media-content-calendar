package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreatePostTest extends BaseTest {

    @Test
    void createSocialMediaPost() {

        String title = "Selenium Test Post";
        String content = "This post was created using Selenium WebDriver.";
        String date = "2026-08-25";
        String time = "10:30";

        // 1. Open Create Post form
        driver.findElement(
                By.xpath("//button[contains(text(), '+ Create Post')]")
        ).click();

        // 2. Verify Create Post form is visible
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(), 'Create Post')]")
                )
        );

        // 3. Enter title
        driver.findElement(By.name("title"))
                .sendKeys(title);

        // 4. Enter content
        driver.findElement(By.name("content"))
                .sendKeys(content);

        // 5. Select platform
        driver.findElement(By.name("platform"))
                .sendKeys("LinkedIn");

        // 6. Set date
        var dateInput = driver.findElement(
                By.name("scheduledDate")
        );

        dateInput.click();
        dateInput.sendKeys("08252026");

// 7. Set time
        var timeInput = driver.findElement(
                By.name("scheduledTime")
        );

        timeInput.click();
        timeInput.sendKeys("1030AM");   

        // 8. Verify date value
        assertTrue(
                date.equals(dateInput.getAttribute("value")),
                "Scheduled date should be 2026-08-25"
        );

        // 9. Verify time value
        assertTrue(
                time.equals(timeInput.getAttribute("value")),
                "Scheduled time should be 10:30"
        );

        // 10. Submit form
        driver.findElement(
                By.xpath(
                        "//button[@type='submit' and contains(text(), 'Create Post')]"
                )
        ).click();

        // 11. Wait for success alert
        var alert = wait.until(
                ExpectedConditions.alertIsPresent()
        );

        String alertText = alert.getText();

        System.out.println(
                "Application Alert: " + alertText
        );

        assertTrue(
                alertText.contains("Post created successfully"),
                "Post should be created successfully"
        );

        alert.accept();

        // 12. Wait for created post
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + title
                                        + "']"
                        )
                )
        );

        // 13. Final assertion
        assertTrue(
                driver.findElement(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + title
                                        + "']"
                        )
                ).isDisplayed(),
                "Created post should appear in the Content Calendar"
        );
    }
}