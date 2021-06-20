
import java.util.function.IntBinaryOperator;

public class TestFunctionalInterface {
    public static void main(String[] args) {
        IntBinaryOperator op1 = (a,b) -> {
                System.out.println("executing (a+b)*5)");
                return (a + b) * 5;
            };
        IntBinaryOperator op2 = TestFunctionalInterface::compute;
        System.out.println("created op1");
        System.out.println(op1.applyAsInt(4,5));
        System.out.println(op2.applyAsInt(2,3));
    }

    public static int compute(int a, int b) {
        System.out.println("compute> executing (a+b)*5)");
        return (a + b) * 5;
    }
}

