package testers;

import main.Reporter;

public class ReporterTester {
    public static void main(final String[] args) {
        showResults();
        // viewPerformance(1);
    }

    /**
     * May take between 5.5 to 6.25 hours to complete.
     */
    public static void showResults() {
        System.out.println(Reporter.showResults());
    }

    /**
     * Takes around 1.5 minutes to complete.
     * 
     * @param function
     */
    public static void viewPerformance(final int function) {
        Reporter.viewPerformance(function);
    }
}
