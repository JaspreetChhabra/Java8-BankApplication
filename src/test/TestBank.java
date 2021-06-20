
package test;

import bank.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class TestBank {
    public static void main(String[] args) throws Exception {
        String bankName = "ICICI";
        int bankCode = 000123;
        Bank bank = new Bank(bankName, bankCode);
        long acno1 = bank.openAccount(Bank.AccountType.CURRENT, "James", 1_00_000);
        long acno2 = bank.openAccount(Bank.AccountType.CURRENT, "Joshua", 1_00_000);
        long acno3 = bank.openAccount(Bank.AccountType.SAVINGS, "Jugal", 2_00_000);
        long acno4 = bank.openAccount(Bank.AccountType.CURRENT, "Jinal", 1_00_000);
        long acno5 = bank.openAccount(Bank.AccountType.SAVINGS, "Jeanne", 2_00_000);
        long acno6 = bank.openAccount(Bank.AccountType.CURRENT, "Nidhi", 1_00_000);
        long acno7 = bank.openAccount(Bank.AccountType.CURRENT, "Natalia", 3_00_000);
        long acno8 = bank.openAccount(Bank.AccountType.SAVINGS, "Namrata", 3_00_000);
        long acno9 = bank.openAccount(Bank.AccountType.CURRENT, "Tina", 11_00_000);
        long acno10 = bank.openAccount(Bank.AccountType.SAVINGS, "Tilly", 4_00_000);
        long acno11 = bank.openAccount(Bank.AccountType.SAVINGS, "Andrey", 10_00_000);
        long acno12 = bank.openAccount(Bank.AccountType.CURRENT, "Johanna", 4_00_000);
        long acno13 = bank.openAccount(Bank.AccountType.SAVINGS, "Cyna", 1_00_000);
        long acno14 = bank.openAccount(Bank.AccountType.SAVINGS, "Careena", 5_00_000);
        long acno15 = bank.openAccount(Bank.AccountType.SAVINGS, "Priyanka", 9_00_000);
        long acno16 = bank.openAccount(Bank.AccountType.SAVINGS, "Dipika", 5_00_000);
        long acno17 = bank.openAccount(Bank.AccountType.SAVINGS, "Salman", 7_00_000);
        long acno18 = bank.openAccount(Bank.AccountType.SAVINGS, "Salman", 8_00_000);
        bank.deposit(acno1, 11_000);
        bank.deposit(acno3, 1_000);
        bank.deposit(acno4, 8_000);
        bank.deposit(acno5, 25_000);
        bank.deposit(acno6, 95_000);
        bank.deposit(acno7, 2_99_000);
        bank.deposit(acno8, 100);
        bank.deposit(acno9, 10);


        bank.withdraw(acno10, 3_99_000);
        bank.withdraw(acno8, 2_99_000);
        bank.withdraw(acno7, 8_000);
        bank.withdraw(acno6, 25_000);
        bank.withdraw(acno5, 95_000);
        bank.withdraw(acno4, 99_000);
        bank.withdraw(acno3, 100);
        bank.withdraw(acno2, 95_000);

        bank.display(acno1);
        bank.deposit(acno1, 10_000);
        bank.display(acno1);
        bank.withdraw(acno1, 1_05_000);
        bank.withdraw(acno1, 9_000);
        bank.printPassbook(acno1);
        bank.listAccounts();
        bank.listAccountWithPenalty();
        // TODO add a method in Account class called hasPenalty, if the account has any penalty transaction - done
        // TODO display all accounts which have a penalty
        bank.getAccountStream().filter(Account::hasPenalty).forEach(Account::display);
        // TODO getList of Accounts with penalty - displayed penalty, how to display accounts?
        List<Account> accountWithPenalty = bank.getAccountStream().filter(Account::hasPenalty).collect(Collectors.toList());

        // TODO sort the list, on different criteria

        // TODO create the Collector using the four components
        // TODO get summary statistics for all the account balances
        LongSummaryStatistics st = bank.getAccountStream().collect(Collectors.summarizingLong(Account::getBalance));
        // TODO get the Account with the maximum number of transactions
        Account maxTransAccount = bank.getAccountStream().max(Comparator.comparingInt(ac -> ac.getPassbook().size())).get();
        // TODO get the highest transaction amount
        Long highesttransAmt = bank.getAccountStream().flatMap(Account::getTransactionStream).mapToLong(Account.Transaction::getAmount).max().getAsLong();
        // TODO get the summary statistics for the transaction amounts
        LongSummaryStatistics highesttransAmt1 = bank.getAccountStream().flatMap(Account::getTransactionStream).mapToLong(Account.Transaction::getAmount).summaryStatistics();

        // TODO get the summary statistics for the transaction net-amounts
// using Collectors.groupingBy
        // TODO get a map of namewise list of accounts
        Map<String, List<Account>> nameWsieAccounts = bank.getAccountStream().collect(Collectors.groupingBy(Account::getName));
        // TODO get a partition of accounts with and without penalty
        Map<Boolean, List<Account>> part = bank.getAccountStream().collect(Collectors.partitioningBy(Account::hasPenalty));
        // TODO get a map of namewise sum of balance
        Map<String, Long> sumbal = bank.getAccountStream().collect(Collectors.groupingBy(Account::getName, Collectors.summingLong(Account::getBalance)));
    }
}
