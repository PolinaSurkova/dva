package com.company;

public class Main {
    public static void main(String[] args) {
        String str = "7+11*(4-2*2+8)/2";
        System.out.print("Результат вычисления номер 1: ");
        System.out.println(Operations.result(str));

        String str1 = ")7+11*(4-2*2+8)/2(";
        System.out.print("Результат вычисления номер 2: ");
        System.out.println(Operations.result(str1));
    }
}
