package com.jass.core.handlers;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.internal.ResultMap;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jass.core.webdrivers.WebDriverManager.DRIVER;

public class RetryAnalyzerImpl implements IRetryAnalyzer, ITestListener {

    private static final int MAX_COUNT = 1;
    private Map<String, AtomicInteger> retries = new HashMap<String, AtomicInteger>();
    private boolean isRetryHandleNeeded;
    private IResultMap failedCases = new ResultMap();
    private Logger log = LoggerFactory.getLogger(RetryAnalyzerImpl.class);


    public AtomicInteger getCount(ITestNGMethod result, Object[] params) {
        String id = getId(result, params);
        if (retries.get(id) == null) {
            retries.put(id, new AtomicInteger(MAX_COUNT));
        }
        return retries.get(id);
    }

    public String getId(ITestNGMethod result, Object[] params) {
        return result.getConstructorOrMethod().getMethod().toGenericString() +
                ":" + ArrayUtils.toString(params);
    }

    public boolean retry(ITestResult result) {
        boolean ret = false;
        if (getCount(result.getMethod(), result.getParameters()).intValue() > 0) {
            log.info("Retry test: " + result.getInstanceName() + ":" + result.getName());
            getCount(result.getMethod(), result.getParameters()).decrementAndGet();
            ret = true;
        } else {
            log.info("Finish retrying: " + result.getInstanceName() + ":" + result.getName());
        }
        return ret;
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test class started: " + result.getTestClass().getName());
        log.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test SUCCESS: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        makeScreenshot();
        log.info("Test FAILED: " + result.getName());
        if (result.getThrowable()!=null) {
            result.getThrowable().printStackTrace();
        }
        if (result.getMethod().getRetryAnalyzer() != null) {
            RetryAnalyzerImpl testRetryAnalyzer = (RetryAnalyzerImpl) result.getMethod().getRetryAnalyzer();
            int currentResult = testRetryAnalyzer.getCount(result.getMethod(), result.getParameters()).intValue();
//            int currentResult = result.getStatus();
            if (currentResult > 0) {
                result.setStatus(currentResult);
                Reporter.setCurrentTestResult(null);
            } else {
                failedCases.addResult(result, result.getMethod());
            }
            isRetryHandleNeeded = true;
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("Test SKIPPED: " + result.getName());
        if (result.getThrowable()!=null) {
            result.getThrowable().printStackTrace();
        }
        makeScreenshot();
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("Start suite: " + context.getSuite().getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Finish suite: " + context.getSuite().getName());
        if (isRetryHandleNeeded) {
            removeRerunTests(context);
            removeFailedTestsInTestNG(context);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }


    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] makeScreenshot() {
        return ((TakesScreenshot) DRIVER()).getScreenshotAs(OutputType.BYTES);
    }

    private void removeRerunTests(ITestContext test) {
        List<ITestResult> skipsToRemove = new ArrayList<ITestResult>();
        IResultMap skippedTests = test.getSkippedTests();
        for (ITestResult result : skippedTests.getAllResults()) {
            ITestNGMethod m = getMatchingMethod(result, failedCases.getAllResults());
            if (m != null) {
                skipsToRemove.add(result);
            } else {
                m = getMatchingMethod(result, test.getPassedTests().getAllResults());
                if (m != null) {
                    skipsToRemove.add(result);
                }
            }
        }
        for (ITestResult result : skipsToRemove) {
            log.info("Matches: " + countMatches(skippedTests, result));
            skippedTests.removeResult(result);
        }
    }

    private int countMatches(IResultMap skippedTests, Object m){
        int ret = 0;
        for (ITestNGMethod entry : skippedTests.getAllMethods()) {
            if (entry.equals(m)) {
                ret++;
            }
        }
        return ret;
    }

    private ITestNGMethod getMatchingMethod(ITestResult toFind, Set<ITestResult> results) {
        for (ITestResult result : results) {
            if (getId(toFind).equals(getId(result))) {
                return result.getMethod();
            }
        }
        return null;
    }

    private void removeFailedTestsInTestNG(ITestContext test) {
        IResultMap failedTests = test.getFailedTests();
        Comparator<ITestResult> comparator = new Comparator<ITestResult>() {
            public int compare(ITestResult o1, ITestResult o2) {
                return getId(o1).compareTo(getId(o2));
            }
        };
        List<ITestResult> duplicates = new ArrayList<ITestResult>();
        Set<ITestResult> resultsSet = new TreeSet<ITestResult>(comparator);
        for (ITestResult failed : failedTests.getAllResults()) {
            if (!resultsSet.add(failed)) {
                duplicates.add(failed);
            }

        }
        for (ITestResult testMethod : duplicates) {
            failedTests.removeResult(testMethod);
        }
    }

    private String getId(ITestResult result) {
        return getId(result.getMethod(), result.getParameters());
    }

}
