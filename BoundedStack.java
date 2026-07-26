import java.util.*;

/**
 * BoundedStack คือ สแตกที่เก็บข้อมูลได้สูงสุดตามความจุที่กําหนดตอนสร้าง
 */

public class BoundedStack{

     private final List<String> elements ;
     private final int capacity ;

     // AF(elements,capacity) = 
     // RI
     // -
     // -

     // TODO1 : Abstraction Function

     // TODO2 : Representation Invariant
     // ข้อมูลและตัวแปรต้องไม่เป็น null
     // 

    // TODO3 : เขียน Safety from rep exposure ตรงนี้
    // Safety from rep exposure:
    //   สร้าง elements แบบ final
    //   มีการ copy Obj ทั้งตอนสร้างและตอนส่ง


     /**
      * 
      * @param capacity
      */
     public BoundedStack(int capacity){
        this.elements = new ArrayList<>() ;
        this.capacity = capacity ;
     }

     /**
      * 
      * @param s
      */
     public void push(String s){
        
     }
}
