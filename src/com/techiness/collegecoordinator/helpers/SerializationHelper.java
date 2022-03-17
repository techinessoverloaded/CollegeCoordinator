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
        File file = new File(parentPath+name);
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
        fout.close();
    }

    public <T> T retrieveObject(String name) throws IOException, ClassNotFoundException
    {
        File file = new File(parentPath+name);
        if(!file.exists())
            return null;
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream objIn = new ObjectInputStream(fin);
        return (T)objIn.readObject();
    }
}
