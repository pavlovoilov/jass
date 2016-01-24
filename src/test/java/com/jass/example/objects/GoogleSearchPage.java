package com.jass.example.objects;

import com.jass.core.elements.Button;
import com.jass.core.elements.Element;
import com.jass.core.page.BasePage;
import org.openqa.selenium.By;

import static com.jass.core.elements.Waiter.waitUntilHidden;

public class GoogleSearchPage extends BasePage {

    public GoogleSearchPage() {
        isPageObjectLoaded(Locators.PAGE_LOCATOR);
    }

    public void selectApp(String appName) {
        Button APP_ICON =  Locators.APP_XPATH.format(appName).asButton();
        APP_ICON.click();
        waitUntilHidden(APP_ICON);
    }

    interface Locators {
        Element PAGE_LOCATOR = new Element(By.xpath(""), "Google Search Page", 10000);
        Element APP_XPATH = new Element("//button[@name='%s']", "App with name: %s", 5000);
    }
}
