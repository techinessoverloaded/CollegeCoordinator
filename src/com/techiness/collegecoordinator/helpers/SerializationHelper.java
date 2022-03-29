package com.techiness.collegecoordinator.helpers;

import java.io.*;


public final class SerializationHelper
{
    private static SerializationHelper instance = null;
    private static final String parentPath = System.getProperty("user.dir")+"/data/";

    private SerializationHelper()
    {

    }

    public synchronized static SerializationHelper getInstance()
    {
        if(instance == null)
            instance = new SerializationHelper();
        return instance;
    }

    public <T extends Serializable> void persistObject(T object, String name) throws IOException
    {
        File file = new File(parentPath+name);
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();
        if(!file.exists())
            file.createNewFile();
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        fout.close();
    }

    public <T extends Serializable> T retrieveObject(String name) throws IOException, ClassNotFoundException
    {
        File file = new File(parentPath+name);
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();
        if(!file.exists())
            file.createNewFile();
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream objIn = new ObjectInputStream(fin);
        T retrievedObject = (T)objIn.readObject();
        objIn.close();
        fin.close();
        return retrievedObject;
    }

    public void clearStoredData(String... fileNames) throws IOException, SecurityException
    {
        for(String fName : fileNames)
        {
            String exactPath = parentPath + fName;
            File file = new File(exactPath);
            if(!file.exists())
                continue;
            FileOutputStream fout = new FileOutputStream(file);
            fout.close();
        }
    }
}
