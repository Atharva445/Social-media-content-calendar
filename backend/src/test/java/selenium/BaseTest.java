package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class BaseTest implements TestWatcher {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String BASE_URL = "http://localhost:5173";

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void testFailed(
            ExtensionContext context,
            Throwable cause
    ) {

        takeFailureScreenshot(
                context.getDisplayName()
        );
    }

    protected void takeFailureScreenshot(String testName) {

        if (driver == null) {
            return;
        }

        try {

            Path directory =
                    Path.of("screenshots", "failures");

            Files.createDirectories(directory);

            String safeTestName =
                    testName.replaceAll(
                            "[^a-zA-Z0-9._-]",
                            "_"
                    );

            String timestamp =
                    LocalDateTime.now()
                            .format(
                                    DateTimeFormatter.ofPattern(
                                            "yyyyMMdd_HHmmss"
                                    )
                            );

            Path destination =
                    directory.resolve(
                            safeTestName
                                    + "_"
                                    + timestamp
                                    + ".png"
                    );

            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.BYTES
                            );

            Files.write(
                    destination,
                    screenshot
            );

            System.out.println(
                    "Failure screenshot saved: "
                            + destination.toAbsolutePath()
            );

        } catch (IOException e) {

            System.err.println(
                    "Could not save failure screenshot: "
                            + e.getMessage()
            );
        }
    }
}