/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author pavel
 */
public class TriviaGame {
    QuestionsDataBase GameDB;
    String FileOfGameDB="GameDB.dat";

    public TriviaGame(QuestionsDataBase GameDB) {
        this.GameDB = GameDB;
        StartNewGame();
    }
    
    public TriviaGame(String FileOfGameDB) throws Exception
    {
        this.FileOfGameDB=FileOfGameDB;
        LoadQaFromDB();
        StartNewGame();
    }
    public void LoadQaFromDB() throws Exception
    {
        DAL.ConnectionString=FileOfGameDB;
        GameDB=DAL.ReadQuestionDBObject();
      
        
    }
    public ArrayList<String> GetCategories()
    {
       return  GameDB.GetCategoriesList();
    }
    public void SaveQaToDB() throws IOException
    {
        DAL.ConnectionString=FileOfGameDB;
        DAL.WriteQuestionDBObject(GameDB);
    }
    public Question GetNextQuestion(Category choice)
    {
         return GameDB.GetQuestion(choice);
    }
    public void AddQuestion(Question q)
    {
        GameDB.AddQuestion(q);
    }
    public void RemoveQuestion(int index)
    {
        GameDB.DeleteQuestion(index);
    }
    public QuestionsDataBase GetQuestionsDB()
    {
        return GameDB;
    }
    public void StartNewGame()
    {
        GameDB.initShowedStatus();
    }
    public void GameOver()
    {
        GameDB.initShowedStatus();
    }
}
