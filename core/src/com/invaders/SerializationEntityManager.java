package com.invaders;

import com.badlogic.gdx.Gdx;
import com.invaders.entity.EntityManager;
import com.invaders.entity.Score;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by NotePad.by on 03.06.2016.
 */
public class SerializationEntityManager {
    public static void Serializate(ArrayList<EntityManager> entityManagers){
        try{
            FileOutputStream fileOut =
                    new FileOutputStream("entity_manager_info.json");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(entityManagers);
            out.close();
            fileOut.close();
//            System.out.println("Serialized data is saved in /Java.../entity_manager_info.json");
        }catch (IOException i){
            i.printStackTrace();
        }
    }
    public static ArrayList<EntityManager> Deserializate(){
        try{
//            FileInputStream fileIn = new FileInputStream("entity_manager_info.json");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
            InputStream is = Gdx.files.internal("entity_manager_info.json").read();
            ObjectInputStream ois = new ObjectInputStream(is);
            ArrayList<EntityManager> entityManager = (ArrayList<EntityManager>) ois.readObject();
            ois.close();
            is.close();
//            in.close();
//            fileIn.close();
//            System.out.println("Deserialized data is recoveried from /Java.../entity_manager_info.json");
            return entityManager;
        } catch(IOException i){
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c){
//            System.out.println("Array<Score> not found");
            c.printStackTrace();
            return null;
        }
    }
}
