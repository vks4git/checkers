package ru.ifmo.morozov.interfaces;

/**
 * Created by vks on 15/04/15.
 */
public interface Notifier {
    void addListener(Listener listener);
    void update();
}
