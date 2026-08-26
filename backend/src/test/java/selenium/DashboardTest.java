package selenium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DashboardTest extends BaseTest {

    @Test
    void dashboardLoadsSuccessfully() {

        assertTrue(
                driver.getTitle() != null,
                "Application page should load"
        );

        assertTrue(
                driver.getPageSource()
                        .contains("Social Media Content Calendar XYZ"),
                "Application heading should be displayed"
        );

        assertTrue(
                driver.getPageSource()
                        .contains("Content Calendar"),
                "Content Calendar section should be displayed"
        );
    }
}