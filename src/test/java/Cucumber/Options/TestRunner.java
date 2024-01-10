package Cucumber.Options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions( 
		features = "src/test/java/features", 
		plugin ="json:target/jsonReports/cucumber-report.json",
		glue = {"stepDefinations"},
		tags = "@AddPlace"   //@DeletePlace
		)

public class TestRunner extends AbstractTestNGCucumberTests{


}


/**
 * <-----JUnit execution---->
 * import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions( 
		features = "src/test/java/features", 
		plugin ="json:target/jsonReports/cucumber-report.json",
		glue = {"stepDefinations"},
		tags = "@AddPlace"   //@DeletePlace
		)

public class TestRunner {

 */
