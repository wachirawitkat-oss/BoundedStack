import java.util.ArrayList;
import java.util.List;

/**
 * BoundedStack — ADT แทนตู้สลากชิงโชคที่มีการจำกัดความจุ
 * เก็บหมายเลขสลากเป็น String ในช่วง "1" ถึง "100"
 * ทำงานแบบ Stack (LIFO)
 */
public class BoundedStack {

    public static final int MIN_TICKET = 1;
    public static final int MAX_TICKET = 100;

    // ===== representation =====
    private final List<String> tickets;
    private final int capacity;

    //   TODO1 : Abstraction Function:
    //   AF(tickets, capacity) = ตู้สลากชิงโชคที่มีความจุสูงสุด capacity ใบ 
    //                           โดยเรียงสลากจากก้นตู้ (tickets.get(0)) 
    //                           ขึ้นไปจนถึงสลากใบบนสุด (tickets.get(tickets.size() - 1))

    //   TODO2 : Representation Invariant:
    //   - tickets != null ตั๋วต้องไม่เป็นจำนวนว่างเปล่า
    //   - capacity > 0 ความจุต้องมากกว่า 0
    //   - 0 <= tickets.size() <= capacity ตั๋วต้องไม่น้อยกว่า 0 และต้องไม่มากกว่าความจุ
    //   - สลากทุกใบใน tickets ต้องไม่เป็น null และไม่เป็น String ว่าง
    //   - หมายเลขสลากทุกใบ เมื่อแปลงเป็นตัวเลขแล้ว ต้องอยู่ระหว่าง MIN_TICKET (1) ถึง MAX_TICKET (100)

    //   TODO3 : เขียน Safety from rep exposure ตรงนี้
    //   Safety from rep exposure:
    //   - ตัวแปร tickets และ capacity ถูกประกาศเป็น private และ final
    //   - คืนค่าข้อมูลออกไปด้วยการ copy ลิสต์ใหม่เสมอ ไม่คืน reference ตรงๆ (return ตัว copy แทน)
    //   - รับข้อมูลเข้าด้วยการตรวจสอบ และ copy ลิสต์ใหม่เสมอ

    /**
     * TODO 4: เขียน checkRep()
     * แปลง RI ทุกข้อเป็น assert หนึ่งบรรทัด พร้อมข้อความอธิบาย
     */
    private void checkRep() {
        assert tickets != null : "tickets list must not be null"; // ตรวจสอบว่ารายการสลากไม่เป็น null
        assert capacity > 0 : "capacity must be greater than 0"; // ตรวจสอบว่าความจุมากกว่า 0
        assert tickets.size() <= capacity : "tickets size exceeds capacity"; // ตรวจสอบว่าจำนวนสลากไม่เกินความจุ

        for (String t : tickets) { // หยิบสลากออกมาจากตู้ทีละใบ (เช็คสลากทุกใบในตู้)
            assert t != null : "ticket must not be null"; // ตรวจสอบว่าสลากไม่เป็น null
            assert !t.trim().isEmpty() : "ticket must not be empty string"; // ตรวจสอบว่าสลากไม่เป็น String ว่าง

            try { // แปลงสลากจาก String เป็น int และตรวจสอบว่าหมายเลขสลากอยู่ในช่วง 1 ถึง 100
                int num = Integer.parseInt(t);
                assert num >= MIN_TICKET && num <= MAX_TICKET : "ticket number out of range (1-100)";
            } catch (NumberFormatException e) { // ถ้าแปลงสลากจาก String เป็น int ไม่สำเร็จ โยน assert false
                assert false : "ticket must be a valid integer string";
            }
        }
    }

    // ===== Creators =====

    /**
     * สร้างตู้สลากว่างเปล่าพร้อมกำหนดความจุสูงสุด
     * @param capacity ความจุสูงสุดของตู้ (ต้องมากกว่า 0) 
     * @throws IllegalArgumentException ถ้า capacity <= 0
     */
    public BoundedStack(int capacity) {
        if (capacity <= 0) {//ถ้าขนาดความจุน้อยกว่าหรือเท่ากับ 0 ให้โยน IllegalArgumentException
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        
        this.tickets = new ArrayList<>(); // สร้าง ArrayList ว่างเปล่าเพื่อเก็บสลาก
        this.capacity = capacity; // กำหนดความจุสูงสุดของตู้
        checkRep();
    }


    // ===== Helper Method ===== 
    private void validateTicket(String ticket) {// ตรวจสอบความถูกต้องของสลากก่อนที่จะใส่ลงในตู้
        if (ticket == null || ticket.trim().isEmpty()) {// ตรวจสอบว่าสลากไม่เป็น null และไม่เป็น String ว่าง
            throw new IllegalArgumentException("Ticket cannot be null or empty");// ถ้าเป็น null หรือ String ว่าง ให้โยน IllegalArgumentException
        }
        try {//ลองแปลงสลากจาก String เป็น int และตรวจสอบว่าหมายเลขสลากอยู่ในช่วง 1 ถึง 100
            int num = Integer.parseInt(ticket);//ฟังค์ชัน Integer.parseInt(ตั๋ว) ใช้แปลง String เป็น int
            if (num < MIN_TICKET || num > MAX_TICKET) {//ถ้าเลขสลากน้อยกว่า 1 หรือมากกว่า 100 ให้โยน IllegalArgumentException
                throw new IllegalArgumentException("Ticket number must be between 1 and 100");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ticket must be a valid number");// ถ้าแปลงสลากจาก String เป็น int ไม่สำเร็จ ให้โยน IllegalArgumentException
        }
    }

    // ===== Mutators =====

    /**
     * ใส่สลากลงในตู้ (วางต่อบนสุด)
     * @param ticket หมายเลขสลาก (1-100)
     * @throws IllegalStateException ถ้าตู้เต็มแล้ว
     * @throws IllegalArgumentException ถ้าสลากผิดเงื่อนไข
     */
    public void push(String ticket) {// ใส่สลากลงในตู้ (วางต่อบนสุด)
        if (isFull()) {// ตรวจสอบว่าตู้เต็มหรือไม่ ถ้าเต็มแล้วให้โยน IllegalStateException
            throw new IllegalStateException("Stack is full");
        }
        // ถ้าไม่เต็ม ให้ตรวจสอบความถูกต้องของสลากก่อนที่จะใส่ลงในตู้
        validateTicket(ticket);// ตรวจสอบความถูกต้องของสลากก่อนที่จะใส่ลงในตู้
        tickets.add(ticket);// ใส่สลากลงในตู้ (วางต่อบนสุด)
        checkRep();// ตรวจสอบ Representation Invariant หลังจากใส่สลากลงในตู้
    }
}
