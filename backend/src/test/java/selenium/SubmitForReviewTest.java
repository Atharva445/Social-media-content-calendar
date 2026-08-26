package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubmitForReviewTest extends BaseTest {

    @Test
    void submitPostForReview() {

        String postTitle = "Selenium Updated Post";

        // 1. Wait for Content Calendar
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(), 'Content Calendar')]")
                )
        );

        // 2. Wait for the post
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + postTitle
                                        + "']"
                        )
                )
        );

        // 3. Locate the table row containing the post
        var postRow = driver.findElement(
                By.xpath(
                        "//*[normalize-space(text())='"
                                + postTitle
                                + "']/ancestor::tr[1]"
                )
        );

        // 4. Verify DRAFT status
        assertTrue(
                postRow.findElement(
                        By.xpath(".//*[contains(normalize-space(), 'DRAFT')]")
                ).isDisplayed(),
                "Post should initially have DRAFT status"
        );

        // 5. Click Submit for Review
        postRow.findElement(
                By.xpath(
                        ".//button[contains(normalize-space(), 'Submit for Review')]"
                )
        ).click();

        // 6. Wait for status to change to IN_REVIEW
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + postTitle
                                        + "']/ancestor::tr[1]"
                                        + "//*[contains(normalize-space(), 'IN_REVIEW')]"
                        )
                )
        );

        // 7. Final assertion
        assertTrue(
                driver.findElement(
                        By.xpath(
                                "//*[normalize-space(text())='"
                                        + postTitle
                                        + "']/ancestor::tr[1]"
                                        + "//*[contains(normalize-space(), 'IN_REVIEW')]"
                        )
                ).isDisplayed(),
                "Post status should change to IN_REVIEW"
        );

        System.out.println(
                "Post successfully submitted for review."
        );
    }
}