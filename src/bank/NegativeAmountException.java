
package bank;

public class NegativeAmountException extends Exception {
    private long amount;
    NegativeAmountException(String msg, long amt) {
        super(msg);
        this.amount = amt;
    }
    public long getAmount() {
        return this.amount;
    }
    public String toString() {
        return super.toString() + ":" + this.getAmount();
    }
}
