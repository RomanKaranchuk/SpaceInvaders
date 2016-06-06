package com.invaders;

import com.invaders.entity.Score;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.out;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class SerializationScore {
    public static void Serializate(ArrayList<Score> scores){
        try{
            FileOutputStream fileOut =
                    new FileOutputStream("D:/Java/SpaceInvaders/android/assets/scores_info.json");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(scores);
            out.close();
            fileOut.close();
//            System.out.println("Serialized data is saved in /Java.../scores_info.json");
        }catch (IOException i){
            i.printStackTrace();
        }
    }
    public static ArrayList<Score> Deserializate(){
        try{
            FileInputStream fileIn = new FileInputStream("D:/Java/SpaceInvaders/android/assets/scores_info.json");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Score> scores = (ArrayList<Score>) in.readObject();
            in.close();
            fileIn.close();
//            out.println("Deserialized data is recoveried from /Java.../scores_info.json");
            return scores;
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
