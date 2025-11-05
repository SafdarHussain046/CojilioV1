package utilities.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getReporter() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("Cojilio Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

} 
 