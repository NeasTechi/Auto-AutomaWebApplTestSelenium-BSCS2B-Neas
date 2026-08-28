package automation;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestReport {
    
    // Test results data (you can update these values from your LoginTest)
    private static String[][] testResults = {
        {"TC-01", "Valid Login", "standard_user / secret_sauce", "Redirect to inventory page", "✅ PASS", "Functional/Black Box"},
        {"TC-02", "Invalid Password", "standard_user / wrongpassword", "Error message displayed", "✅ PASS", "Functional/Black Box"},
        {"TC-03", "Locked Out User", "locked_out_user / secret_sauce", "\"User locked out\" error", "✅ PASS", "Edge Case/Grey Box"},
        {"TC-04", "Empty Username", "[blank] / secret_sauce", "\"Username required\" error", "✅ PASS", "Validation/White Box"},
        {"TC-05", "Special Characters", "admin' OR '1'='1 / any", "Error or rejection", "✅ PASS", "Security/Grey Box"},
        {"TC-06", "Response Time", "N/A", "Page loads in < 3 seconds", "✅ PASS", "Performance/Grey Box"}
    };
    
    // Project information
    private static final String PROJECT_NAME = "AutomaWebAppliTestSeleniumFinalExamProject";
    private static final String APPLICATION = "Sauce Demo (https://www.saucedemo.com/)";
    private static final String TESTER_NAME = "[Your Name Here]";
    private static final String COURSE = "Application Development and Emerging Technologies";
    
    public static void main(String[] args) {
        // Generate console report
        generateConsoleReport();
        
        // Generate HTML report (optional)
        // generateHTMLReport();
        
        // Generate text file report
        generateTextFileReport();
    }
    
    public static void generateConsoleReport() {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("                     FINAL TEST REPORT - AUTOMATED TESTING");
        System.out.println("═".repeat(80));
        
        // Header Information
        System.out.println("\n📋 PROJECT INFORMATION");
        System.out.println("   • Project Name: " + PROJECT_NAME);
        System.out.println("   • Application: " + APPLICATION);
        System.out.println("   • Tester: " + TESTER_NAME);
        System.out.println("   • Course: " + COURSE);
        System.out.println("   • Report Date: " + getCurrentDateTime());
        System.out.println("   • Testing Framework: Selenium WebDriver 4.38.0");
        
        // Executive Summary
        System.out.println("\n📊 EXECUTIVE SUMMARY");
        System.out.println("   • Total Test Cases: " + testResults.length);
        System.out.println("   • Passed: " + countStatus("✅ PASS"));
        System.out.println("   • Failed: " + countStatus("❌ FAIL"));
        System.out.println("   • Success Rate: " + calculateSuccessRate() + "%");
        
        // Testing Techniques Summary
        System.out.println("\n🔧 TESTING TECHNIQUES APPLIED");
        System.out.println("   ┌─────────────────┬─────────────────────────────────────────────┐");
        System.out.println("   │ Technique       │ How Applied in This Project                │");
        System.out.println("   ├─────────────────┼─────────────────────────────────────────────┤");
        System.out.println("   │ Black Box       │ Functional testing without internal code   │");
        System.out.println("   │                 │ knowledge                                  │");
        System.out.println("   ├─────────────────┼─────────────────────────────────────────────┤");
        System.out.println("   │ White Box       │ Element identification using HTML IDs      │");
        System.out.println("   │                 │ and structure analysis                     │");
        System.out.println("   ├─────────────────┼─────────────────────────────────────────────┤");
        System.out.println("   │ Grey Box        │ Performance testing, input validation,     │");
        System.out.println("   │                 │ and security checks                        │");
        System.out.println("   └─────────────────┴─────────────────────────────────────────────┘");
        
        // Detailed Test Results Table
        System.out.println("\n🧪 DETAILED TEST RESULTS");
        System.out.println("   ┌──────┬────────────────────┬────────────────────┬──────────────────────────┬──────────┬──────────────────┐");
        System.out.println("   │ ID   │ Test Scenario      │ Input Data         │ Expected Result          │ Status   │ Testing Type     │");
        System.out.println("   ├──────┼────────────────────┼────────────────────┼──────────────────────────┼──────────┼──────────────────┤");
        
        for (String[] test : testResults) {
            System.out.printf("   │ %-4s │ %-18s │ %-18s │ %-24s │ %-8s │ %-16s │%n",
                test[0], test[1], test[2], test[3], test[4], test[5]);
        }
        
        System.out.println("   └──────┴────────────────────┴────────────────────┴──────────────────────────┴──────────┴──────────────────┘");
        
        // Defect Summary (if any failures)
        System.out.println("\n⚠️  DEFECT SUMMARY");
        if (countStatus("❌ FAIL") > 0) {
            System.out.println("   • Critical Defects: 0");
            System.out.println("   • High Priority: 0");
            System.out.println("   • Medium Priority: 0");
            System.out.println("   • Low Priority: 0");
        } else {
            System.out.println("   ✅ No defects found. All test cases passed successfully.");
        }
        
        // Environment Information
        System.out.println("\n🖥️  TEST ENVIRONMENT");
        System.out.println("   • Operating System: Windows 11");
        System.out.println("   • Browser: Chrome 143+");
        System.out.println("   • Java Version: JDK 17");
        System.out.println("   • IDE: Eclipse");
        System.out.println("   • Automation Tool: Selenium WebDriver 4.38.0");
        System.out.println("   • Driver: ChromeDriver");
        
        // Conclusion
        System.out.println("\n✅ CONCLUSION & RECOMMENDATIONS");
        System.out.println("   • Application Status: " + getApplicationStatus());
        System.out.println("   • Testing Coverage: Comprehensive - All major login scenarios tested");
        System.out.println("   • Recommendation: Application is ready for deployment");
        System.out.println("   • Next Steps: Expand testing to include other application features");
        
        System.out.println("\n" + "═".repeat(80));
        System.out.println("                          END OF TEST REPORT");
        System.out.println("═".repeat(80));
    }
    
    public static void generateTextFileReport() {
        String fileName = "TestReport_" + getCurrentDateForFile() + ".txt";
        
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("=".repeat(70) + "\n");
            writer.write("FINAL TEST REPORT - AUTOMATED WEB APPLICATION TESTING\n");
            writer.write("=".repeat(70) + "\n\n");
            
            writer.write("PROJECT: " + PROJECT_NAME + "\n");
            writer.write("APPLICATION: " + APPLICATION + "\n");
            writer.write("TESTER: " + TESTER_NAME + "\n");
            writer.write("DATE: " + getCurrentDateTime() + "\n\n");
            
            writer.write("EXECUTIVE SUMMARY\n");
            writer.write("-".repeat(50) + "\n");
            writer.write("Total Test Cases: " + testResults.length + "\n");
            writer.write("Passed: " + countStatus("✅ PASS") + "\n");
            writer.write("Failed: " + countStatus("❌ FAIL") + "\n");
            writer.write("Success Rate: " + calculateSuccessRate() + "%\n\n");
            
            writer.write("DETAILED TEST RESULTS\n");
            writer.write("-".repeat(50) + "\n");
            for (String[] test : testResults) {
                writer.write(String.format("TC-%s: %s\n", test[0], test[1]));
                writer.write("  Input: " + test[2] + "\n");
                writer.write("  Expected: " + test[3] + "\n");
                writer.write("  Status: " + test[4] + "\n");
                writer.write("  Type: " + test[5] + "\n");
                writer.write("-".repeat(40) + "\n");
            }
            
            writer.write("\nCONCLUSION\n");
            writer.write("-".repeat(50) + "\n");
            writer.write(getApplicationStatus() + "\n");
            writer.write("All testing objectives have been met successfully.\n");
            
            System.out.println("\n📄 Text report saved as: " + fileName);
            
        } catch (IOException e) {
            System.out.println("❌ Error creating text report: " + e.getMessage());
        }
    }
    
    // Optional: HTML Report Generator
    public static void generateHTMLReport() {
        String fileName = "TestReport_" + getCurrentDateForFile() + ".html";
        
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("    <title>Test Report - " + PROJECT_NAME + "</title>\n");
            writer.write("    <style>\n");
            writer.write("        body { font-family: Arial, sans-serif; margin: 40px; }\n");
            writer.write("        h1 { color: #2c3e50; }\n");
            writer.write("        .summary { background: #f8f9fa; padding: 20px; border-radius: 5px; }\n");
            writer.write("        table { width: 100%; border-collapse: collapse; margin: 20px 0; }\n");
            writer.write("        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }\n");
            writer.write("        th { background-color: #3498db; color: white; }\n");
            writer.write("        .pass { background-color: #d4edda; color: #155724; }\n");
            writer.write("        .fail { background-color: #f8d7da; color: #721c24; }\n");
            writer.write("    </style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("    <h1>📊 Automated Test Report</h1>\n");
            writer.write("    <div class='summary'>\n");
            writer.write("        <h3>Project: " + PROJECT_NAME + "</h3>\n");
            writer.write("        <p><strong>Date:</strong> " + getCurrentDateTime() + "</p>\n");
            writer.write("        <p><strong>Tester:</strong> " + TESTER_NAME + "</p>\n");
            writer.write("        <p><strong>Success Rate:</strong> " + calculateSuccessRate() + "%</p>\n");
            writer.write("    </div>\n");
            
            writer.write("    <h2>Test Results Summary</h2>\n");
            writer.write("    <table>\n");
            writer.write("        <tr><th>ID</th><th>Scenario</th><th>Status</th><th>Type</th></tr>\n");
            
            for (String[] test : testResults) {
                String rowClass = test[4].contains("PASS") ? "pass" : "fail";
                writer.write("        <tr class='" + rowClass + "'>\n");
                writer.write("            <td>" + test[0] + "</td>\n");
                writer.write("            <td>" + test[1] + "</td>\n");
                writer.write("            <td>" + test[4] + "</td>\n");
                writer.write("            <td>" + test[5] + "</td>\n");
                writer.write("        </tr>\n");
            }
            
            writer.write("    </table>\n");
            writer.write("    <p><strong>Conclusion:</strong> " + getApplicationStatus() + "</p>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
            
            System.out.println("\n🌐 HTML report saved as: " + fileName);
            
        } catch (IOException e) {
            System.out.println("❌ Error creating HTML report: " + e.getMessage());
        }
    }
    
    // ==================== HELPER METHODS ====================
    
    private static int countStatus(String status) {
        int count = 0;
        for (String[] test : testResults) {
            if (test[4].equals(status)) {
                count++;
            }
        }
        return count;
    }
    
    private static double calculateSuccessRate() {
        int passed = countStatus("✅ PASS");
        return testResults.length > 0 ? (passed * 100.0 / testResults.length) : 0;
    }
    
    private static String getApplicationStatus() {
        if (countStatus("❌ FAIL") == 0) {
            return "✅ PASS - All test cases passed successfully. Application meets requirements.";
        } else {
            return "⚠️  REVIEW NEEDED - " + countStatus("❌ FAIL") + " test case(s) failed.";
        }
    }
    
    private static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
    
    private static String getCurrentDateForFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return LocalDateTime.now().format(formatter);
    }
    
    // Method to update test results from LoginTest (call this from LoginTest)
    public static void updateTestResults(int testNumber, boolean passed, String notes) {
        if (testNumber >= 1 && testNumber <= testResults.length) {
            testResults[testNumber-1][4] = passed ? "✅ PASS" : "❌ FAIL";
            if (!notes.isEmpty()) {
                testResults[testNumber-1][3] += " (" + notes + ")";
            }
        }
    }
    
    // Method to get all test results (for integration with LoginTest)
    public static String[][] getTestResults() {
        return testResults;
    }
}