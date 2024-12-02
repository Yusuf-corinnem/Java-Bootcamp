package edu.school21.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class OutputFormatter {
    public static void printClasses(String[] classes) {
        System.out.println("Classes:");

        for (String s : classes) {
            System.out.println("- " + s);
        }

        System.out.println("---------------------");
    }

    public static void printClassInfo(Class<?> aClass) {
        printClassFields(aClass);
        printClassMethods(aClass);
        System.out.println("---------------------");
    }

    public static void printText(String text) {
        System.out.println(text);
    }

    private static void printClassFields(Class<?> aClass) {
        System.out.println("Fields:");

        for (Field f : aClass.getDeclaredFields()) {
            System.out.println("    " + f.getType().getSimpleName() + " " + f.getName());
        }
    }

    private static void printClassMethods(Class<?> aClass) {
        System.out.println("Methods:");

        for (Method m : aClass.getDeclaredMethods()) {
            StringBuilder methodSignature = new StringBuilder();

            if (m.getReturnType().getSimpleName().equals("void"))
                methodSignature.append("   ").append(" ").append(m.getName()).append("(");
            else
                methodSignature.append("    ").append(m.getReturnType().getSimpleName()).append(" ").append(m.getName()).append("(");

            Parameter[] parameters = m.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                methodSignature.append(parameters[i].getType().getSimpleName());
                if (i < parameters.length - 1) {
                    methodSignature.append(", ");
                }
            }

            methodSignature.append(")");
            System.out.println(methodSignature);
        }
    }
}
