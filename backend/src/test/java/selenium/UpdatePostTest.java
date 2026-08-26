package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdatePostTest extends BaseTest {

    @Test
    void updateSocialMediaPost() {

        String existingTitle = "Selenium Test Post";
        String updatedTitle = "Selenium Updated Post";
        String updatedContent =
                "This post was updated using Selenium WebDriver.";

        // 1. Wait for Content Calendar
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(), 'Content Calendar')]")
                )
        );

        // 2. Find the existing post
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + existingTitle
                                        + "']"
                        )
                )
        );

        // 3. Click Edit for Selenium Test Post
        driver.findElement(
                By.xpath(
                        "//tr[.//*[normalize-space(text())='"
                                + existingTitle
                                + "']]//button[contains(text(), 'Edit')]"
                )
        ).click();

        // 4. Verify Update Post modal
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(), 'Update Post')]")
                )
        );

        // 5. Update title
        var titleInput = driver.findElement(By.name("title"));
        titleInput.clear();
        titleInput.sendKeys(updatedTitle);

        // 6. Update content
        var contentInput = driver.findElement(By.name("content"));
        contentInput.clear();
        contentInput.sendKeys(updatedContent);

        // 7. Keep LinkedIn platform
        // driver.findElement(By.name("platform"))
        //         .sendKeys("LinkedIn");

        // 8. Update date
        var dateInput = driver.findElement(
                By.name("scheduledDate")
        );

        dateInput.click();
        dateInput.sendKeys("08262026");

        // 9. Update time
        var timeInput = driver.findElement(
                By.name("scheduledTime")
        );

        timeInput.click();
        timeInput.sendKeys("1100AM");

        // 10. Verify date and time
        assertTrue(
                "2026-08-26".equals(
                        dateInput.getAttribute("value")
                ),
                "Scheduled date should be 2026-08-26"
        );

        assertTrue(
                "11:00".equals(
                        timeInput.getAttribute("value")
                ),
                "Scheduled time should be 11:00"
        );

        // 11. Submit update
        driver.findElement(
                By.xpath(
                        "//button[@type='submit' and contains(text(), 'Update Post')]"
                )
        ).click();

        // 12. Wait for success alert
        var alert = wait.until(
                ExpectedConditions.alertIsPresent()
        );

        String alertText = alert.getText();

        System.out.println(
                "Application Alert: " + alertText
        );

        assertTrue(
                alertText.contains("Post updated successfully"),
                "Post should be updated successfully"
        );

        alert.accept();

        // 13. Verify updated post appears
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + updatedTitle
                                        + "']"
                        )
                )
        );

        // 14. Final assertion
        assertTrue(
                driver.findElement(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + updatedTitle
                                        + "']"
                        )
                ).isDisplayed(),
                "Updated post should be displayed"
        );
    }
}