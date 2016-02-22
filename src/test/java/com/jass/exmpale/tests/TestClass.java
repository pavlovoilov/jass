package com.jass.exmpale.tests;

import com.jass.core.handlers.NavigationHandler;
import com.jass.core.test.BaseTest;
import com.jass.exmpale.objects.GoogleSearchPage;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TestClass extends BaseTest {

    @Test
    public void googleTest() {
        NavigationHandler.open("http://google.com");

        List<String> resultTitles = new GoogleSearchPage()
                .searchFor("automation testing")
                .getResultTitles();

        resultTitles.forEach(
                title -> assertThat("Incorrect result title!", title, containsString("automation")));
    }
}
