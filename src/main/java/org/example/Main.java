package org.example;

import solution.Task1;
import solution.Task2;
import solution.Task3;

public class Main {

    public static void main(String[] args) throws Exception {

        Task1 task1 = new Task1();
        task1.run();

        System.out.println("***************************************************");
        Task2 task2 = new Task2();
        task2.run();


        System.out.println("***************************************************");
        Task3 task3 = new Task3();
        task3.run();
    }
}