package com.ecom.testCases;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Testing {

    public static void main(String[] args) {
        System.out.println("king");
        List<Integer> king = new ArrayList<>();
        king.add(45);
        king.add(25);
        king.add(65);
        king.add(55);
        king.sort((a, b) -> b-a);
        System.out.println(king);
    }
}
