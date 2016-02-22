package com.jass.exmpale.objects;

import com.jass.core.elements.Button;
import com.jass.core.elements.InputField;
import com.jass.core.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;

import static com.jass.core.elements.Waiter.waitUntilHidden;

public class GoogleSearchPage extends BasePage {

    public GoogleSearchPage() {
        isPageObjectLoaded(Locators.PAGE_LOCATOR);
    }

    @Step
    public void selectApp(String appName) {
        Button APP_ICON =  Locators.APP_XPATH.format(appName);
        APP_ICON.click();
        waitUntilHidden(APP_ICON);
    }

    @Step
    public SearchResultsPage searchFor(String text) {
        Locators.PAGE_LOCATOR.type(text + Keys.ENTER);
        return new SearchResultsPage();
    }

    interface Locators {
        InputField PAGE_LOCATOR = new InputField(By.xpath("//*[@id='lst-ib']")).setDescription("Google Search Page").setTimeout(10000);
        Button APP_XPATH = new Button("//button[@name='%s']").setDescription("App with name: %s");
    }
}
