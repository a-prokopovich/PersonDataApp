package com.prokopovich.persondata.util;

import java.io.*;

public class SerializationObject {

    public static <T> File serializeObject(T object) throws IOException {
        File file = new File("serializeObject.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        fileOutputStream.close();
        objectOutputStream.close();
        return file;
    }

    public static <T> T deserializeObject(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        T object = (T) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        return object;
    }
}
