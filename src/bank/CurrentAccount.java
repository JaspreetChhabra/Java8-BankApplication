
package bank;

// TODO use a function for penalty instead of a value, a function of balance and minimumBalance
public class CurrentAccount extends Account {
    private static long minimumBalance = 10_000;
//    private long penalty = 100;
    private Penalty penalty = Penalty.DEFAULT_PENALTY; // Penalty.fixed(100);
    public CurrentAccount(long acno, String n, long openBal) throws NegativeAmountException {
        super(acno, n, openBal);
    }
    public void setPenalty(Penalty pa) {
        this.penalty = pa;
    }
    public Penalty getPenalty() {
        return this.penalty;
    }
    public CurrentAccount(String n, long openBal) throws NegativeAmountException {
        super(n, openBal);
    }
    @Override
    public boolean withdraw(long amt) throws NegativeAmountException {
        if (!super.withdraw(amt)) {
            return false;
        }
        /*
            apply penalty if balance < minimumBalance
        */
        if (this.getBalance() < minimumBalance) {
            new Transaction("Penalty", TransType.DEBIT, this.getPenalty().compute(minimumBalance, this.getBalance()));
        }
        return true;
    }
}

