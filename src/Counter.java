// 327721544 Bar Kirshenboim

/**
 * counter class.
 */
public class Counter {
    private int countVal = 0;

    /**
     * add number to current count.
     *
     * @param number num
     */
    public void increase(int number) {
        this.countVal = this.countVal + number;
    }

    /**
     * subtract number from current count.
     *
     * @param number num
     */
    public void decrease(int number) {
        this.countVal = this.countVal - number;

    }

    /**
     * @return counter value
     */
    public int getValue() {
        return this.countVal;
    }
}