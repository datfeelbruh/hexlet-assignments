package exercise;

import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");
    // BEGIN
    public static Map<String, Integer> getMinMax(int[] numbers) {
        MaxThread maxThread = new MaxThread(numbers);
        MinThread minThread = new MinThread(numbers);
        maxThread.start();
        LOGGER.info(maxThread.getName() + " started");
        minThread.start();
        LOGGER.info(minThread.getName() + " started");

        try {
            maxThread.join();
            minThread.join();
            LOGGER.info(maxThread.getName() + " finished");
            LOGGER.info(minThread.getName() + " finished");
        } catch (InterruptedException e) {
            LOGGER.warning("я насрал");
        }
        return Map.of("max", maxThread.getMax(), "min", minThread.getMin());
    }

    public static void main(String[] args) {
        int[] numbers = {10, -4, 67, 100, -100, 8};
        System.out.println(getMinMax(numbers));
    }
    // END
}
