package automation;

public class TestImports {
    public static void main(String[] args) {
        System.out.println("Testing Selenium Server JAR...");
        
        // Try each import individually
        try {
            Class<?> c1 = Class.forName("org.openqa.selenium.WebDriver");
            System.out.println("✅ WebDriver: FOUND");
        } catch (Exception e) { System.out.println("❌ WebDriver: " + e.getMessage()); }
        
        try {
            Class<?> c2 = Class.forName("org.openqa.selenium.chrome.ChromeDriver");
            System.out.println("✅ ChromeDriver: FOUND");
        } catch (Exception e) { System.out.println("❌ ChromeDriver: " + e.getMessage()); }
        
        try {
            Class<?> c3 = Class.forName("org.openqa.selenium.By");
            System.out.println("✅ By: FOUND");
        } catch (Exception e) { System.out.println("❌ By: " + e.getMessage()); }
        
        try {
            Class<?> c4 = Class.forName("org.openqa.selenium.support.ui.WebDriverWait");
            System.out.println("✅ WebDriverWait: FOUND");
        } catch (Exception e) { System.out.println("❌ WebDriverWait: " + e.getMessage()); }
        
        System.out.println("\nIf ANY show ❌, your JAR is corrupted.");
    }
}