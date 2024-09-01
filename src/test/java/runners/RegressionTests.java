package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import listeners.SuiteListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@CucumberOptions(
        tags = "not @bug",
        features = {"src/test/resources/features/"},
        glue = {"steps"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {
                "rerun:target/rerun/failed_scenarios.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        })

@Listeners(SuiteListener.class)
public class RegressionTests extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
