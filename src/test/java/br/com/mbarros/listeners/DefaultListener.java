package br.com.mbarros.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;
import java.util.Objects;

import static org.testng.Assert.fail;

/**
 * The DefaultListener class implements the ISuiteListener interface to provide default behaviors for test suite execution.
 * It handles tasks such as deleting Allure report files, reading Maven parameters, and modifying test suite thread counts.
 */
public class DefaultListener implements ISuiteListener {

    private Integer featureThreadCount;
    private Integer scenarioThreadCount;

    /**
     * Performs actions before the test suite starts.
     *
     * @param suite The test suite object.
     */
    @Override
    public void onStart(ISuite suite) {
        deleteAllureReportFiles();
        deleteAllureReportFilesFolderReports();
        readMavenParameters();

        if (Objects.nonNull(featureThreadCount)) {
            suite.getXmlSuite().setThreadCount(featureThreadCount);
        }

        if (Objects.nonNull(scenarioThreadCount)) {
            suite.getXmlSuite().setDataProviderThreadCount(scenarioThreadCount);
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        //Do nothing
    }

    /**
     * Deletes all files in the "allure-results" folder.
     */
    public static void deleteAllureReportFiles() {
        File file = new File("allure-results/");

        if (file.exists()) {
            for (File subfile : file.listFiles()) {
                subfile.delete();
            }
        }
    }

    /**
     * Deletes all files in the "reports/allure-results" folder.
     */
    public static void deleteAllureReportFilesFolderReports() {
        File file = new File("reports/allure-results/");

        if (file.exists()) {
            for (File subfile : file.listFiles()) {
                subfile.delete();
            }
        }
    }

    /**
     * Reads Maven parameters and sets the featureThreadCount and scenarioThreadCount values.
     */
    private void readMavenParameters() {
        try {
            if (System.getProperty("featureThreadCount") != null) {
                featureThreadCount = Integer.parseInt(System.getProperty("featureThreadCount"));
            }
            if (System.getProperty("scenarioThreadCount") != null) {
                scenarioThreadCount = Integer.parseInt(System.getProperty("scenarioThreadCount"));
            }
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
