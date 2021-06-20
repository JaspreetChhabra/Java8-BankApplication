
package bank;

import java.awt.desktop.SystemEventListener;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
An abstract class to represent a bank account. It has methods for deposit, withdraw, toString, display.
Two Account instances are considered equal if their account numbers match.
TODO Override the equals and hashCode methods - done
TODO add a method called getPassbook to return a List<Transaction> - done
TODO implement the Comparable interface - done
TODO use LocalDate class from the java.time package instead of long for the date in Transaction class - Localdate.now()
TODO add the method called getTransactionStream to return Stream<Transaction>
TODO add a method called hasPenalty to return true if the passbook contains a penalty entry
TODO try to get rid of the balance field from the Account class, change the implementation of getBalance method to return balance from the List of Transaction. - done
*/
public abstract class Account implements Comparable<Account> {
    private static long lastAccountNumber=1000;
    private long accountNumber;
    private String name;
    //private long balance;
    private List<Transaction> passbook = new ArrayList<>();

    @Override
    public int compareTo(Account ac) {
        return ((Long)this.accountNumber).compareTo(ac.accountNumber);
    }
    public List<Transaction> getPassbook() {
//        return this.passbook;
        return Collections.unmodifiableList(this.passbook);
    }
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Account)) return false;
        return this.accountNumber == ((Account)obj).accountNumber;
    }
    public int hashCode() {
        return ((Long)accountNumber).hashCode();
    }
    public Stream<Transaction> getTransactionStream() {
        return this.passbook.stream();
    }

    public boolean hasPenalty() {
         return this.getTransactionStream().anyMatch( tr -> tr.getNaration().equals("Penalty"));
    }

    public void displayAccountsWithPenalty() {
        this.getTransactionStream().filter(tr -> tr.getNaration().equals("Penalty")).peek(System.out::println);
    }
    public class Transaction {
        private long date = System.currentTimeMillis();
        private String naration;
        private TransType transType;
        private long amount;
        Transaction(String n, TransType t, long amt) throws NegativeAmountException {
            if (amt < 0) {
                throw new NegativeAmountException("negative amount", amt);
            }
            this.naration = n;
            this.transType = t;
            this.amount = amt;
            //Account.this.balance += this.getNetAmount();
            Account.this.passbook.add(this);
        }
        public long getDate() {
            return this.date;
        }
        public String getNaration() {
            return this.naration;
        }
        public TransType getTransType() {
            return this.transType;
        }
        public long getAmount() {
            return this.amount;
        }
        public long getNetAmount() {
            return this.transType.getSign()*this.getAmount();
        }
        @Override
        public String toString() {
            return "Transaction:"+String.format("%tF, %15s, %10s, %12d", this.date, this.naration, this.transType.toString(), this.amount);
        }
    }   // end of Transaction class
    public enum TransType {
        CREDIT(1),
        DEBIT(-1),
        ;
        private int sign;
        TransType(int sign) {
            this.sign = sign;
        }
        public int getSign() {
            return this.sign;
        }
    }   // end of enum TransType
    public Account(long acno, String n, long openBal) throws NegativeAmountException {
        this.accountNumber = acno;
        this.name = n;
        new Transaction("Opening balance", TransType.CREDIT, openBal);
    }
    public Account(String n, long openBal) throws NegativeAmountException {
        this(++lastAccountNumber, n, openBal);
    }
    public long getAccountNumber() {
        return this.accountNumber;
    }
    public String getName() {
        return this.name;
    }
    public long getBalance() {
        //return this.balance;
        return getTransactionStream().mapToLong(Transaction::getNetAmount).sum();
    }
    public void deposit(long amt) throws NegativeAmountException {
        new Transaction("Deposit", TransType.CREDIT, amt);
    }
    public boolean withdraw(long amt) throws NegativeAmountException {
        if (amt > this.getBalance()) {
            return false;
        }
        new Transaction("Withdrawal", TransType.DEBIT, amt);
        return true;
    }
    public String toString() {
        String fullClassName = this.getClass().getName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".")+1);
        return className+":"+this.getAccountNumber()+", "+this.getName()+", "+this.getBalance();
    }
    public void display() {
        System.out.println(this);
    }
    public void printPassbook() {
        System.out.println("Passbook of "+this.getName());
        long runningBalance = 0;
        for (Transaction t : this.passbook) {
            System.out.printf("%tF\t%15s\t%12d\t%12d\t%12d\n"
                    ,t.getDate(), t.getNaration()
                    ,t.getTransType()==TransType.DEBIT?t.getAmount():0
                    ,t.getTransType()==TransType.CREDIT?t.getAmount():0
                    ,runningBalance);
        }
        System.out.println("End of passbook");
    }
}

