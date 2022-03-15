package com.techiness.collegecoordinator.helpers;

import java.io.*;

public class SerializationHelper
{
    private static SerializationHelper instance = null;
    private static String parentPath = "/Users/arun-pt5167/IdeaProjects/CollegeCoordinator/src/com/techiness/collegecoordinator/data/";
    private SerializationHelper()
    {

    }

    public synchronized static SerializationHelper getInstance()
    {
        if(instance == null)
            instance = new SerializationHelper();
        return instance;
    }

    public void persistObject(Object o, String name) throws IOException
    {
        FileOutputStream fout = new FileOutputStream(parentPath+name);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
        fout.close();
    }

    public <T> T retrieveObject(String name) throws IOException, ClassNotFoundException
    {
        FileInputStream fin = new FileInputStream(parentPath+name);
        ObjectInputStream objIn = new ObjectInputStream(fin);
        T obj = (T)objIn.readObject();
        return obj;
    }
}
