package com.jass.exmpale.tests;

import com.jass.core.handlers.NavigationHandler;
import com.jass.core.test.BaseTest;
import org.testng.annotations.Test;

public class TestClass extends BaseTest {

    @Test
    public void googleTest() {
        NavigationHandler.open("http://google.com");
    }
}
