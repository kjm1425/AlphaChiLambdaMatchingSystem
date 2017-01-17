public class Pref {
//String name;
String one;
String two;
String three;
String four;

public Pref(String one, String two, String three, String four) {
//      this.name = name;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
}

public boolean contains(String s){
        return s.equals(one)||s.equals(two)||s.equals(three)||s.equals(four);
}

public int rank(String s){
        if(s.equals(one)) return 100;
        if(s.equals(two)) return 75;
        if(s.equals(three)) return 50;
        return 25;
}

public void prettyPrint(){
//      System.out.println(name + ":" + one + "|" + two + "|" + three + "|" + four + "|");
        System.out.println(one + "|" + two + "|" + three + "|" + four + "|");

}

}
