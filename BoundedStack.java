import java.util.*;

/**
 * BoundedStack คือ สแตกที่เก็บข้อมูลได้สูงสุดตามความจุที่กําหนดตอนสร้าง
 */

public class BoundedStack{

   public static final int MAX_TICKET = 100;
   public static final int MIn_TICKET = 1;
     private final List<String> ticket ;
     private final int capacity ;

     // AF(elements,capacity) = 
     // RI
     // -
     // -

     // TODO1 : Abstraction Function
     // AF(tickets, capacity) = ตู้สลากชิงโชคที่มีความจุสูงสุด capacity ใบ
     //โดย tickets.get(0) คือสลากก้นตู้ 
     //และ tickets.get(tickets.size() - 1) คือสลากใบบนสุด

      // TODO2 : Representation Invariant
      // - tickets != null
      // - capacity > 0
      // - 0 <= tickets.size() <= capacity
      // - สลากทุกใบใน tickets ต้องไม่เป็น null หรือ String ว่าง
      // - เลขบนสลากทุกใบ เมื่อแปลงเป็นตัวเลขแล้ว ต้องอยู่ระหว่าง 1 ถึง 100 เท่านั้น

    // TODO3 : เขียน Safety from rep exposure ตรงนี้
    // Safety from rep exposure:
    //   สร้าง elements แบบ final
    //   มีการ copy Obj ทั้งตอนสร้างและตอนส่ง


     /**
      * 
      * @param capacity
      */
     public BoundedStack(int capacity){
        this.ticket = new ArrayList<>() ;
        this.capacity = capacity ;
     }

     /**
      * 
      * @param s
      */
     public void push(String s){
        
     }
}
