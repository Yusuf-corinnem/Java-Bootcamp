package edu.school21.services;

import edu.school21.utils.OutputFormatter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ObjectManager {
    private final String packages;
    private final String[] classes;
    private Class<?> currentClass;
    private Object currentObject;

    public ObjectManager(String packages, String[] classes) {
        this.packages = packages;
        this.classes = classes;
    }

    public void getClassInfo() {
        OutputFormatter.printText("---------------------");

        OutputFormatter.printClasses(classes);

        String object = readAndValidateClassName();
        if (object == null) return;

        OutputFormatter.printText("---------------------");

        currentClass = loadClass(object);
        if (currentClass == null) throw new AssertionError();

        OutputFormatter.printClassInfo(currentClass);
    }

    public void createObject() throws InstantiationException, IllegalAccessException {
        OutputFormatter.printText("Let’s create an object.");
        currentObject = currentClass.newInstance();

        for (Field f : currentClass.getDeclaredFields()) {
            OutputFormatter.printText(f.getName() + ":");
            f.setAccessible(true);
            Object value = InputHandler.readInput(f.getType());
            f.set(currentObject, value);
        }

        OutputFormatter.printText("Object created: " + currentObject +
                "\n---------------------");
    }

    public void changeFieldObject() throws IllegalAccessException {
        OutputFormatter.printText("Enter name of the field for changing:");
        String fieldName = InputHandler.readInput(String.class).toString();
        boolean isFieldFound = false;

        if (fieldName.isEmpty()) {
            OutputFormatter.printText("Field name is empty");
            return;
        }

        for (Field f : currentClass.getDeclaredFields()) {
            if (f.getName().equals(fieldName)) {
                f.setAccessible(true);
                OutputFormatter.printText("Enter " + f.getType().getSimpleName() + " value:");
                Object value = InputHandler.readInput(f.getType());
                f.set(currentObject, value);
                isFieldFound = true;
            }
        }

        if (!isFieldFound) {
            OutputFormatter.printText("Field not found");
            return;
        }

        OutputFormatter.printText("Object updated: " + currentObject +
                "\n---------------------");
    }

    public void callMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        OutputFormatter.printText("Enter name of the method for call:");
        String methodName = InputHandler.readInput(String.class).toString();

        boolean isMethodFound = false;

        for (Method m : currentClass.getDeclaredMethods()) {
            if (methodName.equals(m.getName() + getMethodParameters(m))) {
                isMethodFound = true;

                Class<?>[] parameterTypes = m.getParameterTypes();
                Object[] arguments = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; ++i) {
                    OutputFormatter.printText("Enter " + parameterTypes[i].getSimpleName() + " value:");
                    arguments[i] = InputHandler.readInput(parameterTypes[i]);
                }

                m.setAccessible(true);
                OutputFormatter.printText("Method returned:");
                System.out.println(m.invoke(currentObject, arguments));
            }
        }

        if (!isMethodFound) {
            OutputFormatter.printText("Method not found");
        }
    }

    private String getMethodParameters(Method method) {
        StringBuilder methodParameters = new StringBuilder();
        methodParameters.append("(");
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; ++i) {
            methodParameters.append(parameters[i].getType().getSimpleName());
            if (i < parameters.length - 1) {
                methodParameters.append(", ");
            }
        }

        methodParameters.append(")");

        return methodParameters.toString();
    }

    private String readAndValidateClassName() {
        OutputFormatter.printText("Enter class name:");

        String object = InputHandler.readInput(String.class).toString();

        if (findClass(object).isEmpty()) {
            OutputFormatter.printText("Class not found");
            return null;
        }

        return object;
    }

    private String findClass(String object) {
        for (String s : classes) {
            if (object.equals(s)) {
                return s;
            }
        }

        return "";
    }

    private Class<?> loadClass(String className) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(packages + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }

}
