package edu.school21.app;

import edu.school21.services.ObjectManager;

import java.lang.reflect.InvocationTargetException;

public class Program {
    public static void main(String[] args) {
        String packages = "edu.school21.models";
        String[] classes = {"User", "Car"};
        ObjectManager objectManager = new ObjectManager(packages, classes);
        objectManager.getClassInfo();

        try {
            objectManager.createObject();
            objectManager.changeFieldObject();
            objectManager.callMethod();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
