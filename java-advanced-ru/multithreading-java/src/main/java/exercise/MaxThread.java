package exercise;

// BEGIN
public class MaxThread extends Thread {
    private int[] numbers;
    private int max = 0;
    public MaxThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        for (int number : numbers) {
            max = Math.max(max, number);
        }
    }

    public int getMax() {
        return max;
    }
}
// END
