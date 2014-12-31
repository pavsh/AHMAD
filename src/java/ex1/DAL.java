/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.*;

/**
 *
 * @author pavel
 */
public class DAL {
   //FileInputStream fis;
   //FileInputStream fos;
   // ObjectInputStream ois ;
    //ObjectOutputStream oos;
    static String ConnectionString;
    private DAL(String FileOfGameDB) throws FileNotFoundException, IOException {
        //this.fis = new FileInputStream(FileOfGameDB);
      // this.ois = new ObjectInputStream(fis);
        ConnectionString=FileOfGameDB;
        
    }
    public static QuestionsDataBase ReadQuestionDBObject() throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(ConnectionString);
        ObjectInputStream ois = new ObjectInputStream(fis);
        QuestionsDataBase Qdb=(QuestionsDataBase)ois.readObject();
        ois.close();
        return Qdb;
    }
    public static void WriteQuestionDBObject(QuestionsDataBase Qdb) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(ConnectionString);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(Qdb);
        oos.close();
        
    }
    public static String GetConnectionString()
    {
        return ConnectionString;
    }
    
}
