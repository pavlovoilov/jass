package com.jass.core.handlers;

import com.jass.core.webdrivers.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import ru.yandex.qatools.allure.annotations.Attachment;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class AttachmentsHandler {

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] makeScreenshot() {
        try {
            if (WebDriverManager.hasWebDriverStarted()) {
                return ((TakesScreenshot) DRIVER()).getScreenshotAs(OutputType.BYTES);
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    @Attachment(value = "Page HTML", type = "text/plain")
    public static byte[] getPageSource() {
        try {
            if (WebDriverManager.hasWebDriverStarted()) {
                return DRIVER().getPageSource().getBytes();
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
