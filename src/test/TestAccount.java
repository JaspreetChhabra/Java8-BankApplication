
package test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import bank.*;
import com.sun.source.doctree.SeeTree;

public class TestAccount {
    private static void display(Account[] accounts) {
//        for (Account ac : accounts) {
//            ac.display();
//        }
        Stream.of(accounts).forEach(System.out::println);
        //Another way
        //  Arrays.stream(accounts).forEach(System.out::println);
    }
    public static void main(String[] args) throws NegativeAmountException {
        CurrentAccount ca = new CurrentAccount("James", 1_00_000);
        System.out.println("before penalty");
        ca.display();
// TODO set the penalty function on ca according to the function
/*
(minBal, bal) -> {
                    long diff = minBal - bal;
                    if (diff <= 0) return 0;
                    if (diff <= 1_000) return 100;
                    if (diff <= 2_000) return 200;
                    if (diff <= 3_000) return 300;
                    return 500;
                }
*/
// TODO then withdraw to get the penalty applied on the object ca.
        ca.withdraw(91_000);
        ca.display();
        ca.setPenalty(Penalty.ZERO_PENALTY);
        ca.setPenalty(Penalty.fixed(300));
        ca.setPenalty(Penalty.percent(0.15));
        ca.setPenalty( (minBal, bal) -> {
            long diff = minBal - bal;
            if (diff <= 0) return 0;
            if (diff <= 1_000) return 100;
            if (diff <= 2_000) return 200;
            if (diff <= 3_000) return 300;
            return 500;
        });
        System.out.println("BALANCE: "+ ca.getBalance());
//        ca.withdraw(8000);
        ca.printPassbook();
        System.out.println("Has any Penalty: "+ ca.hasPenalty());
        System.out.println("Account with Penalty");
        ca.displayAccountsWithPenalty();
//        CurrentAccount ca1 = new CurrentAccount("James", 1_00_000);
//        System.out.println("Has any Penalty2: "+ ca1.hasPenalty());

        Account[] accounts = new Account[6];
        try {
            accounts[0] = new SavingsAccount(12321, "John", 1_00_000);
            accounts[1] = new CurrentAccount(23123, "Ann", 1_00_000);
            accounts[2] = new CurrentAccount(12312, "Boss", 50_000);
            accounts[3] = new SavingsAccount(33411, "Zen", 1_50_000);
            accounts[4] = new CurrentAccount(23344, "Roy", 75_000);
            accounts[5] = new CurrentAccount(23344, "Ann", 15_000);


            System.out.println("before sorting");
            display(accounts);
        } catch (NegativeAmountException nae) {
            nae.printStackTrace();
        }
// TODO create a comparator to order by name, then sort and display
        System.out.println("after sorting by name");

        Comparator<Account> nameComparator = Comparator.comparing(ac -> ac.getName());
        Arrays.sort(accounts, nameComparator);
        display(accounts);
// TODO create a comparator to order by balances, then sort and display
        System.out.println("after sorting by balance");

        Comparator<Account> balanceComparator = Comparator.comparingLong(Account::getBalance);
        Arrays.sort(accounts, balanceComparator);
        display(accounts);
// TODO create a comparator to order by the natural ordering of Account, ie. according to Comparable<Account>
        System.out.println("after sorting by natural order");

        Arrays.sort(accounts, Comparator.naturalOrder());
        display(accounts);
// TODO create a comparator to order by the reverse ordering of Account, ie. according to Comparable<Account>
        System.out.println("after sorting by reverse order");

        Arrays.sort(accounts, Comparator.reverseOrder());
        display(accounts);
// TODO create a comparator to order by name reversed, then display then
        System.out.println("after sorting by name reversed");
//        Comparator<Account> reverseNameComparator = Comparator.comparing( ac -> {
//            StringBuffer sb = new StringBuffer(ac.getName());
//            return sb.reverse();
//        });
        Comparator<Account> reverseNameComparator = Comparator.comparing(Account::getName).reversed();
        Arrays.sort(accounts, reverseNameComparator);
        display(accounts);
// TODO create a comparator to order by name ignoring the case, then display
        System.out.println("after sorting by name case insensitive");

        Comparator<Account> nameComparatorIgnoringCase = Comparator.comparing(Account::getName, String.CASE_INSENSITIVE_ORDER);
        Arrays.sort(accounts, nameComparatorIgnoringCase);
        display(accounts);
// TODO sort the array by name and then on balances.
        System.out.println("after sorting by name then on balance");

        Comparator<Account> nameThenBalanceComparator = nameComparator.thenComparing(balanceComparator);
        Arrays.sort(accounts, nameThenBalanceComparator);
        //Another way
        //Arrays.sort(accounts, nameThenBalanceComparator.thenComparingLong(Account::getBalance));
        display(accounts);
// TODO create a Comparator by Account type. then sort them by balance and display
        System.out.println("after sorting by type then on balance");
        Arrays.sort(accounts, Comparator.comparing(Account::getClass, Comparator.comparing(Class::getName)));
        display(accounts);

    }
}

