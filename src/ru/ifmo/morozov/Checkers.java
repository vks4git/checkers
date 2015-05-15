package ru.ifmo.morozov;

import ru.ifmo.morozov.classes.controller.Controller;

/**
 * Created by vks on 13/05/15.
 */
public class Checkers implements Runnable{

    private static Controller controller;

    public static void main(String[] args) {
        Thread thread = new Thread(new Checkers());
        controller = new Controller(thread);
        thread.start();
    }

    public void run() {
        controller.start();
    }
}
