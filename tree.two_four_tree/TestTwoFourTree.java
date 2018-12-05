package tree.two_four_tree;

import static tree.two_four_tree.StringSSet.NIL;

public class TestTwoFourTree {
    public static void main(String[] args) {
        TwoFourTree tft = new TwoFourTree();
//        tft.add("b");
//        tft.add("c");
//        tft.add("a");
//        tft.add("d");
//        tft.add("e");
//        tft.add("g");
//        tft.add("f");
//        tft.add("h");
//        tft.add("i");
//        tft.add("j");
//        tft.add("k");
//        tft.add("l");
//        tft.add("m");

//        tft.add("k");
//        tft.add("s");
//        tft.add("m");
//        tft.add("b");

//        tft.add("E");
//        tft.add("A");
//        tft.add("W");
//        tft.add("B");
//        tft.add("C");
//        tft.add("X");
//        tft.add("F");
//        tft.add("D");
//        tft.add("G");
//        tft.add("H");
//        tft.add("I");

//        tft.add("3");
//        tft.add("4");
//        tft.add("5");
//        tft.add("6");
//        tft.add("7");
//        tft.add("8");
//        tft.add("9");
//        tft.add("a");

//        tft.add("A");
//        tft.add("B");
//        tft.add("C");
//        tft.add("E");
//        tft.add("H");
//        tft.add("I");
//        tft.add("J");

        tft.add("F");
        tft.add("A");
        tft.add("W");
        tft.add("B");
        tft.add("C");
        tft.add("X");
        tft.add("G");
        tft.add("D");
        tft.add("E");
        tft.add("H");
        tft.add("J");
        tft.add("K");
        tft.remove("K");
        tft.remove("X");
        tft.remove("W");
        tft.remove("G"); // different test cases fork
        tft.remove("E");
        tft.remove("D");
        tft.remove("C");
        tft.remove("J"); // pitfall (resolve sib.parent child binding)
        tft.remove("A");
        tft.remove("F"); // test findPred case

//        tft.remove("B");
//        tft.remove("H");

//        System.out.println(NIL.children[0] == null); //true
//        System.out.println(NIL.children[0] == NIL); //false


//        System.out.println(tft.r == NIL);
//        System.out.println(tft.r.parent == NIL);
//        System.out.println(Arrays.toString(tft.r.data));
//        System.out.println(tft.r.children[0] == NIL);
//        System.out.println(tft.r.children[1] == NIL);
//        System.out.println(tft.r.children[2] == NIL);
//        System.out.println(tft.r.children[3] == NIL);

//        System.out.println(tft.r.children[0]);

        System.out.println(tft.levelOrder());
    }
}
