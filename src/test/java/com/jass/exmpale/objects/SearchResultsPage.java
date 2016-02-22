package com.jass.exmpale.objects;

import com.jass.core.elements.Element;
import com.jass.core.elements.InputField;
import com.jass.core.page.BasePage;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static com.jass.core.elements.ElementAttributes.getTexts;

public class SearchResultsPage extends BasePage {

    public SearchResultsPage() {
        isPageObjectLoaded(Locators.PAGE_LOCATOR);
    }

    @Step
    public List<String> getResultTitles() {
       return getTexts(Locators.RESULT_TITLES);
    }

    interface Locators {
        InputField PAGE_LOCATOR = new InputField(By.xpath("//*[@id='resultStats']"))
                .setDescription("Search Results Page")
                .setTimeout(10000);
        Element RESULT_TITLES = new Element(By.xpath("//h3[@class='r']/a")).setDescription("Results");
    }
}
