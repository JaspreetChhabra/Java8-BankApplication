
package bank;

@FunctionalInterface
public interface Penalty {
    long compute(long minBal, long bal);
    public static final Penalty ZERO_PENALTY = (minBal, bal) -> 0;
    public static Penalty fixed(long penaltyAmount) {
        return (minBal, bal) -> bal < minBal ? penaltyAmount : 0;
    }
    public static final Penalty DEFAULT_PENALTY = (long minBal, long bal) -> {
                        long shortFall = minBal - bal;
                        if (shortFall <= 0) return 0;
                        if (shortFall <= 1_000) return 100;
                        if (shortFall <= 2_000) return 200;
                        if (shortFall <= 5_000) return 500;
                        return 700;
                    };
    public static Penalty percent(double percentage) {
        // TODO returns a Penalty function which computes
        // penaltyamout as a percentage of the shortfall
        return null;
    }
}

