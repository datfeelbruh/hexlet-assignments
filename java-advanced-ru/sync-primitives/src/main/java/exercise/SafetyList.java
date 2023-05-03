package exercise;

class SafetyList {
    // BEGIN
    private final int INITIAL_SIZE = 10;
    private int[] elements = new int[INITIAL_SIZE];
    private int size = 0;
    public synchronized void add(int value) {
        if (size == elements.length - 1) {
            int[] elementsCopy = new int[(elements.length * 3 / 2) + 1];
            System.arraycopy(elements, 0, elementsCopy, 0, elements.length);
            elements = elementsCopy;
        }
        elements[size] = value;
        size++;
    };
    public int get(int index) {
        return elements[index];
    };
    public int getSize() {
        return size;
    };
    // END
}
