package listeners;

import config.parallel.ParallelConfig;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;
import java.util.Objects;

public class SuiteListener implements ISuiteListener {
    private static final String ALLURE_REPORT_FOLDER_PATH = "reports/allure-results/";

    @Override
    public void onStart(ISuite suite) {
        deleteAllureReportFolder();
        defineThreadCount(suite);
    }

    private void deleteAllureReportFolder() {
        File file = new File(ALLURE_REPORT_FOLDER_PATH);
        if (file.exists())
            for (File subfile : file.listFiles())
                subfile.delete();
    }

    private void defineThreadCount(ISuite suite) {
        ParallelConfig parallelConfig = ParallelConfig.loadConfig();
        if (Objects.nonNull(parallelConfig) && Objects.nonNull(parallelConfig.getThreadCount()))
            suite.getXmlSuite().setDataProviderThreadCount(parallelConfig.getThreadCount());
    }
}
