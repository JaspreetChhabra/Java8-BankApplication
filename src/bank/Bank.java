
package bank;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.stream.Stream;

// TODO add a method called getAccountStream to return stream of Account objects

public class Bank {
    private String name;
    private long lastAccountNumber;
    private Map<Long, Account> accountMap = new TreeMap<>();
    public Bank(String n, int code) {
        this.name = n;
        this.lastAccountNumber = code * 10_000L;
    }
    public enum AccountType {
        CURRENT {
            @Override
            public Account create(long acno, String n, long openBal) throws NegativeAmountException {
                return new CurrentAccount(acno, n, openBal);
            }
        },
        SAVINGS {
            @Override
            public Account create(long acno, String n, long openBal) throws NegativeAmountException {
                return new SavingsAccount(acno, n, openBal);
            }
        },
        ;
        public abstract Account create(long acno, String n, long openBal) throws NegativeAmountException;
    }
    public long openAccount(AccountType type, String n, long openBal) throws NegativeAmountException {
        Account ac = type.create(++lastAccountNumber, n, openBal);
        this.accountMap.put(ac.getAccountNumber(), ac);
        return ac.getAccountNumber();
    }
    private Account getAccount(long acno) throws NoSuchAccountException {
        Account ac = this.accountMap.get(acno);
        if (ac == null) {
            throw new NoSuchAccountException("invalid account number");
        }
        return ac;
    }
    public void deposit(long acno, long amt) throws NegativeAmountException, NoSuchAccountException {
        getAccount(acno).deposit(amt);
    }
    public boolean withdraw(long acno, long amt) throws NegativeAmountException, NoSuchAccountException {
        return getAccount(acno).withdraw(amt);
    }
    public void display(long acno) throws NoSuchAccountException {
        getAccount(acno).display();
    }
    public void printPassbook(long acno) throws NoSuchAccountException {
        getAccount(acno).printPassbook();
    }
    public void listAccounts() {
        System.out.println("List of Accounts for bank:"+this.name);
        Collection<Account> accounts = this.accountMap.values();
//        for (Account account : accounts) {
//            account.display();
//        }
        getAccountStream().forEach(Account::display);
        System.out.println("End of account list");
    }

    public void listAccountWithPenalty() {
        System.out.println("List of Accounts for bank with Penalty:"+this.name);
        Stream<Account> accountStream = accountMap.values().stream();
        accountStream.forEach(ac -> ac.getTransactionStream().filter(transaction -> transaction.getNaration().equals("Penalty")).peek(System.out::println).count());
    }

    public Stream<Account> getAccountStream() {
        return accountMap.values().stream();
    }
}

