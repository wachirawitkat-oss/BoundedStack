import java.util.Arrays;
import java.util.List;

public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testPush();
        testPop();
        testObservers();
        testProducer();
        testExposure();
        testEdgeCases();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }
     private static void testCreators() {
        System.out.println("-- Creators --");
        BoundedStack stack = new BoundedStack(5);
        check("new BoundedStack(5) -> empty", stack.isEmpty());
        check("capacity is 5", stack.capacity() == 5);
          boolean threwInvalidCap = false;
        try {
            new BoundedStack(0);
        } catch (IllegalArgumentException e) {
            threwInvalidCap = true;
        }
        check("new BoundedStack(0) -> throws Exception", threwInvalidCap);
    }
     private static void testPush() {
        System.out.println("\n-- Push --");
        BoundedStack stack = new BoundedStack(2);
        stack.push("1");
        check("push('1') -> size 1", stack.size() == 1);
        check("peek() -> '1'", stack.peek().equals("1"));

        stack.push("100");
        check("push('100') -> isFull", stack.isFull());

        // Push ตอนเต็มต้องโดน IllegalStateException
        boolean threwFull = false;
        try {
            stack.push("50");
        } catch (IllegalStateException e) {
            threwFull = true;
        }
        check("push when full -> throws IllegalStateException", threwFull);

        // Push ค่าผิดเงื่อนไขต้องโดน IllegalArgumentException
        boolean threwOutOfRange = false;
        try {
            BoundedStack s2 = new BoundedStack(5);
            s2.push("101");
        } catch (IllegalArgumentException e) {
            threwOutOfRange = true;
        }
        check("push('101') -> throws IllegalArgumentException", threwOutOfRange);
    }

