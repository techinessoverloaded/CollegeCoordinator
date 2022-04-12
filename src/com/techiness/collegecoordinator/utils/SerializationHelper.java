package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.enums.OSType;
import java.io.*;
import static com.techiness.collegecoordinator.utils.IOUtils.hideFile;

public final class SerializationHelper
{
    private static SerializationHelper instance = null;
    private static final String parentPath = System.getProperty("user.dir")+"/.data/";
    private static final String lettersPath = parentPath+".letters/";

    private SerializationHelper()
    {
        File dataDir = new File(parentPath);
        if(!dataDir.exists())
            dataDir.mkdir();
        File lettersDir = new File(lettersPath);
        if(!lettersDir.exists())
            lettersDir.mkdir();

        // Implementation of Cross-Platform functionality. The folders will be hidden no matter what OS the JVM is running on
        if(OSDetector.getCurrentOS() == OSType.WINDOWS)
        {
            hideFile(dataDir.toPath());
            hideFile(lettersDir.toPath());
        }
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
        if(!file.exists())
            file.createNewFile();
        if(!file.canWrite())
            file.setWritable(true);
        if(!file.canRead())
            file.setReadable(true);
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        file.setWritable(false);
        file.setReadable(false);
        fout.close();
    }

    public <T extends Serializable> T retrieveObject(String name) throws IOException, ClassNotFoundException
    {
        File file = new File(parentPath+name);
        if(!file.exists())
            file.createNewFile();
        if(!file.canWrite())
            file.setWritable(true);
        if(!file.canRead())
            file.setReadable(true);
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream objIn = new ObjectInputStream(fin);
        T retrievedObject = (T)objIn.readObject();
        objIn.close();
        file.setReadable(false);
        file.setWritable(false);
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
            if(!file.canWrite())
                file.setWritable(true);
            if(!file.canRead())
                file.setReadable(true);
            FileOutputStream fout = new FileOutputStream(file);
            fout.close();
            file.setReadable(false);
            file.setWritable(false);
        }
    }
}
