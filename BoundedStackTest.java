//6821651710 วชิรวิทย์ กตกุลบัญชร BoundedStack
//6821651728 วชิรศักดิ์ โยคี BoundedStackTest
import java.util.Arrays; 
import java.util.List; // นำเข้า interface List เพื่อใช้ประกาศตัวแปรชนิด List<String>

public class BoundedStackTest {  //ประกาศคลาสปทดสอบ

    private static int passed = 0; // ตัวนับจน.เทสที่ผ่าน เริ่มต้นที่0
    private static int failed = 0;// ตัวนับจน. เทสที่เทสไม่ผ่าน เริ่มต้นที่0

    private static void check(String name, boolean condition) { //เมดตอดช่วยเช็คผลเทส รับชื่อเทสและเงื่อนไข
        if (condition) {         //ถ้าเงื่ออใขเป็นจริง
            passed++;            //เพิ่มตัวนับ passed ขึ้น1
            System.out.println("[PASS] " + name);  /// พิมข้อความเทสว่าผ่าน
        } else {         //ถ้าเงื่อนใขเป็นเท็จ
            failed++;          // เพิ่มตัวนับ failed ขึ้น1
            failed++;          // เพิ่มตัวนับ failed ขึ้น1
            System.out.println("[FAIL] " + name); // พิมข้อความเทสว่าไม่ผ่าน
        }
    }

    public static void main(String[] args) {  //จุดเริ่มต้นการทำงานของโปรแกรม
        

        System.out.println("=== BoundedStack Test Suite ===\n");  //พิมหัวข้อชุดทดสอบ พร้อมขึ้นบรรทัดใหม่

        testCreators();  // เรียกเทสกลุ่ม constructor
        testPush();     // เรียกเทสกลุ่มการ push
        testPop();      // เรียกเทสกลุ่มการ pop
        testObservers();    // เรียกเทสกลุ่มเมธอด peek (observer)
        testProducer();    // เรียกเทสกลุ่มเมธอด copy (producer)
        testExposure();    // เรียกเทสกลุ่มการป้องกัน rep exposure
        testEdgeCases();   // เรียกเทสกลุ่ม edge case ต่างๆ

        System.out.println("\n=== Summary ===");   //พิมหัวข้อสรุปผล
        System.out.println("Passed: " + passed); // พิมจน. เทสที่ผ่าน
        System.out.println("Failed: " + failed);  //พิมจน.เทสที่ไม่ผ่าน
        System.out.println("Total : " + (passed + failed));  // พิมจน.ทั้งหมด (รวมผ่าน+ ไม่ผ่าน) 
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED"); //ถ้าfail= 0 พิมผ่านหมด ไม่งั้นพิมเทสพัง

        if (failed > 0) {  //มีเทสมากกว่า0 หรืืออย่างน้อย1 ตัว
            System.exit(1);  //จบโปรแกรมด้วย exit code (บอกว่ามีความผิดพลาด)
        }
    }
     private static void testCreators() {  //เมทอดเทส constructor ของ BoundedStack
        System.out.println("-- Creators --"); // พิมพ์หัวข้อกลุ่มเทสนี้
        BoundedStack stack = new BoundedStack(5); // สร้าง stack ใหม่ capacity = 5
        check("new BoundedStack(5) -> empty", stack.isEmpty()); // เช็คว่า stack ที่สร้างใหม่ต้องว่างเปล่า
        check("capacity is 5", stack.capacity() == 5);  // เช็คว่า capacity() คืนค่า 5 ตรงตามที่กำหนด
          boolean threwInvalidCap = false;  // ตั้ง flag เริ่มต้นเป็น false ไว้เช็คการโยน exception
        try {
            new BoundedStack(0);    // ลองสร้าง stack ด้วย capacity = 0
        } catch (IllegalArgumentException e) { // ดักจับถ้ามีการโยน IllegalArgumentException
            threwInvalidCap = true;    // ถ้าเข้า catch แปลว่าโยน exception จริง ตั้ง flag เป็น true
        }
        check("new BoundedStack(0) -> throws Exception", threwInvalidCap);  // เช็คว่ามีการโยน exception ตามคาด
    }
     private static void testPush() {  // เมธอดเทสการ push
        System.out.println("\n-- Push --");  // พิมพ์หัวข้อกลุ่มเทสนี้
        BoundedStack stack = new BoundedStack(2);   // สร้าง stack ใหม่ capacity = 2
        stack.push("1");     // push ค่า "1" เข้า stack
        check("push('1') -> size 1", stack.size() == 1);     // เช็คว่า size เป็น 1 หลัง push
        check("peek() -> '1'", stack.peek().equals("1"));     // เช็คว่า peek() คืนค่า "1" ตรงกับที่ push ไป

        stack.push("100");                  // push ค่า "100" เข้าไปอีก (ตอนนี้เต็ม capacity=2)
        check("push('100') -> isFull", stack.isFull());     // เช็คว่า isFull() คืนค่า true

        // Push ตอนเต็มต้องโดน IllegalStateException
        boolean threwFull = false;       // ตั้ง flag เริ่มต้นเป็น false
        try {
            stack.push("50");            // ลอง push ตอน stack เต็มแล้ว
        } catch (IllegalStateException e) {   // ดักจับ IllegalStateException
            threwFull = true;  // ถ้าเข้า catch ตั้ง flag เป็น true
        }
        check("push when full -> throws IllegalStateException", threwFull);   // เช็คว่าโยน exception ตามคาด

        // Push ค่าผิดเงื่อนไขต้องโดน IllegalArgumentException
        boolean threwOutOfRange = false;   // ตั้ง flag เริ่มต้นเป็น false
        try {
            BoundedStack s2 = new BoundedStack(5);    // สร้าง stack ใหม่ capacity = 5
            s2.push("101");                          // ลอง push ค่า "101" ซึ่งเกินขอบเขต (>100)
        } catch (IllegalArgumentException e) {         // ดักจับ IllegalArgumentException
            threwOutOfRange = true;                // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }
        check("push('101') -> throws IllegalArgumentException", threwOutOfRange);  // เช็คผล
    }
     private static void testPop() { // เมทอด  การสร้าง Pop
        System.out.println("\n-- Pop --"); //พิมหัวข้อ
        BoundedStack stack = new BoundedStack(5);   // สร้าง stack ใหม่ capacity = 5
        stack.push("10");                     // push "10" เข้า stack (ก้นตู้)
        stack.push("20");                     // push "20" เข้า stack (บนตู้)

        check("pop() -> returns '20' (LIFO)", stack.pop().equals("20"));  // pop ครั้งแรก ต้องได้ "20" (เข้าหลังออกก่อน)
        check("size decreases to 1", stack.size() == 1);      // เช็คว่า size ลดเหลือ 1 หลัง pop
        check("pop() -> returns '10'", stack.pop().equals("10"));   // pop อีกครั้ง ต้องได้ "10" ที่เหลืออยู่
        check("stack is empty", stack.isEmpty());                  // เช็คว่า stack ว่างเปล่าแล้ว

        // Pop ตอนว่างเปล่า
        boolean threwEmpty = false;        // ตั้ง flag เริ่มต้นเป็น false
        try {
            stack.pop();                  // ลอง pop ตอน stack ว่างเปล่าแล้ว
        } catch (IllegalStateException e) {       // ดักจับ IllegalStateException
            threwEmpty = true;                  // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }
        check("pop on empty -> throws IllegalStateException", threwEmpty);          check("pop on empty -> throws IllegalStateException", threwEmpty);  // เช็คผล

    }

    private static void testObservers() {  // เมธอดเทส observer (peek)
        System.out.println("\n-- Observers --");  // พิมพ์หัวข้อกลุ่มเทสนี้
        BoundedStack stack = new BoundedStack(3);  // สร้าง stack ใหม่ capacity = 3
        stack.push("5");                            // push "5" เข้า stack
        check("peek() doesn't remove element", stack.peek().equals("5") && stack.size() == 1);
         // เช็คสองเงื่อนไขพร้อมกันด้วย && : peek() ต้องได้ "5" และ size ต้องยังเป็น 1 (พิสูจน์ว่า peek ไม่ลบข้อมูลออก)
    }
    private static void testProducer() {  // เมธอดเทสการ copy (producer method)
        System.out.println("\n-- Producer (copy) --");   // พิมพ์หัวข้อกลุ่มเทสนี้
        BoundedStack original = new BoundedStack(5);     // สร้าง stack ต้นฉบับ capacity = 5
        original.push("1");                   // push "1" เข้า original
        original.push("2");                        // push "2" เข้า original

        BoundedStack copied = original.copy();    // เรียก copy() สร้าง stack ใหม่ที่เหมือน original
        check("copied has same size", copied.size() == original.size());    // เช็คว่า size เท่ากันทั้งคู่
        check("copied top is same", copied.peek().equals(original.peek()));    // เช็คว่าค่าบนสุดเหมือนกัน

        copied.push("3");               // push "3" เข้า copied เท่านั้น (ไม่แตะ original)
        check("mutating copy does not affect original", original.size() == 2 && copied.size() == 3);
    }    // เช็คว่า original ยัง size=2 เหมือนเดิม (ไม่ถูกกระทบ) ส่วน copied เปลี่ยนเป็น size=3 แล้ว

    private static void testExposure() {           // เมธอดเทสการป้องกัน representation exposure
        System.out.println("\n-- Representation Exposure --");    // พิมพ์หัวข้อกลุ่มเทสนี้
        BoundedStack stack = new BoundedStack(5);            // สร้าง stack ใหม่ capacity = 5
        stack.push("10");                      // push "10" เข้า stack

        List<String> list = stack.getTickets();   // เรียก getTickets() ได้ลิสต์สำเนาของข้อมูลใน stack
        list.clear();                          // ล้างข้อมูลทั้งหมดใน list ที่ได้มา (ไม่ใช่ตัวจริงใน stack)
        check("clearing result of getTickets() does not mutate stack", stack.size() == 1);
    }// เช็คว่า stack ตัวจริงยังมี size=1 อยู่ (ไม่ถูกกระทบจากการ clear ลิสต์ที่คืนออกมา)
     private static void testEdgeCases() {  // เมธอดเทสกรณีขอบเขตพิเศษต่างๆ
        System.out.println("\n-- Edge Cases --");  // พิมพ์หัวข้อกลุ่มเทสนี้

        // capacity ติดลบต้องโดน IllegalArgumentException เหมือน capacity = 0
        boolean threwNegativeCap = false;     // ตั้ง flag เริ่มต้นเป็น false
        try {
            new BoundedStack(-5);                // ลองสร้าง stack ด้วย capacity ติดลบ
        } catch (IllegalArgumentException e) {    // ดักจับ IllegalArgumentException
            threwNegativeCap = true;                // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }
        check("new BoundedStack(-5) -> throws IllegalArgumentException", threwNegativeCap);  // เช็คผล

        // boundary: capacity = 1 คือขอบเขตล่างสุดที่ยัง valid
        BoundedStack capOne = new BoundedStack(1);      // สร้าง stack capacity = 1 (ขอบเขตล่างสุดที่ valid)
        capOne.push("42");                // push "42" เข้าไป (ตอนนี้เต็มพอดี)
        boolean fullThenEmpty = capOne.isFull();    // เช็คและเก็บผลว่า isFull() เป็น true หรือไม่
        capOne.pop();                        // pop ค่าออก (ตอนนี้ว่างเปล่า)
        fullThenEmpty = fullThenEmpty && capOne.isEmpty();  // เอาผลเดิม AND กับผล isEmpty() ปัจจุบัน
        check("capacity 1 -> full after push, empty after pop", fullThenEmpty); // เช็คว่าทั้งสองเงื่อนไขเป็นจริง

        // ticket = "0" คือขอบเขตล่างที่ผิดเงื่อนไข (MIN_TICKET = 1)
        boolean threwZero = false;   // ตั้ง flag เริ่มต้นเป็น false
        try {
            new BoundedStack(5).push("0");     // สร้าง stack ใหม่แล้ว push ค่า null
        } catch (IllegalArgumentException e) {    // ดักจับ IllegalArgumentException
            threwZero = true;           // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }
        check("push('0') -> throws IllegalArgumentException", threwZero);   // เช็คผล

        // ticket = null
        boolean threwNull = false;   // ตั้ง flag เริ่มต้นเป็น false
        try {
            new BoundedStack(5).push(null);    // สร้าง stack ใหม่แล้ว push ค่า null
        } catch (IllegalArgumentException e) {   // ดักจับ IllegalArgumentException
            threwNull = true;     // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }  
        check("push(null) -> throws IllegalArgumentException", threwNull);  // เช็คผล

          // ticket ที่ไม่ใช่ตัวเลข
        boolean threwNonNumeric = false;    // ตั้ง flag เริ่มต้นเป็น false
        try {
            new BoundedStack(5).push("abc");    // สร้าง stack ใหม่แล้ว push ค่าที่ไม่ใช่ตัวเลข
        } catch (IllegalArgumentException e) {      // ดักจับ IllegalArgumentException
            threwNonNumeric = true;       // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }
        }
        check("push('abc') -> throws IllegalArgumentException", threwNonNumeric);// เช็คผล

        // capacity เกิน 100 ต้องโดน IllegalArgumentException เพราะตั๋วห้ามซ้ำ
        boolean threwOverCapacity = false; // ตั้ง flag เริ่มต้นเป็น false
        try {
            new BoundedStack(101);  // ลองสร้าง stack ด้วย capacity = 101 (เกิน 100)
        } catch (IllegalArgumentException e) {  // ดักจับ IllegalArgumentException
            threwOverCapacity = true;      // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }
        check("new BoundedStack(101) -> throws IllegalArgumentException", threwOverCapacity); //เช็ตผล

        // ตั๋วซ้ำต้องโดน IllegalArgumentException
        boolean threwDuplicate = false;   // ตั้ง flag เริ่มต้นเป็น false
        try {
            BoundedStack dupStack = new BoundedStack(5);    // สร้าง stack ใหม่ capacity = 5
            dupStack.push("7");                       // push "7" เข้าไปครั้งแรก
            dupStack.push("7");                       // push "7" ซ้ำอีกครั้ง (ควรโดน exception)
        } catch (IllegalArgumentException e) {           // ดักจับ IllegalArgumentException
            threwDuplicate = true;                        // ตั้ง flag เป็น true ถ้าโยน exception จริง
        }
        check("push duplicate ticket -> throws IllegalArgumentException", threwDuplicate);   // เช็คผล
    }
}


