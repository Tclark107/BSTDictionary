//-----------------------------------------------------------------------------------------
// DictionaryTest.java
// This will be used to test the Dictionary ADT.
//-----------------------------------------------------------------------------------------

public class DictionaryTest {
    public static void main(String args[]){
        Dictionary A = new Dictionary();
        A.insert("key", "value");
        System.out.println(A);
        A.insert("some", "else");
        System.out.println(A);
        A.insert("what", "now");
        System.out.println(A);
        A.insert("this", "works");
        System.out.println(A);
        A.insert("alright", "then");
        System.out.println(A);
        System.out.println(A.lookup("what"));
        A.delete("this");
        System.out.println(A);
        A.delete("what");
        System.out.println(A);
        A.delete("alright");
        System.out.println(A);
        A.delete("key");
        System.out.println(A);
        A.delete("some");
        System.out.println(A);
        System.out.println(A);


    }
}