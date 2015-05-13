package ru.ifmo.morozov;

import ru.ifmo.morozov.classes.controller.Controller;

/**
 * Created by vks on 13/05/15.
 */
public class Checkers implements Runnable{

    private static Controller controller;

    public static void main(String[] args) {
        Thread thread = new Thread(new Checkers());
        thread.start();
        controller = new Controller(thread);
    }

    public void run() {
        controller.start();
    }
}
