package exercise;

// BEGIN
public class MinThread extends Thread {
    private int[] numbers;
    private int min = 0;
    public MinThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        for (int number : numbers) {
            min = Math.min(min, number);
        }
    }

    public int getMin() {
        return min;
    }
}
// END
