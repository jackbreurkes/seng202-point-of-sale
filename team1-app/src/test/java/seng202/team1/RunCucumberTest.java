package seng202.team1;

//import cucumber.api.CucumberOptions;
//import cucumber.api.junit.Cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        //features = "src/test/resources/seng202/team1/features/datahandling.feature",
        features = "src/test/resources/seng202/team1/features",

        glue = "seng202.team1.stepdefs",
        plugin = {"pretty"})
public class RunCucumberTest {


}