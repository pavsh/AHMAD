/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex1;

import static ex1.DAL.ConnectionString;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Sun
 */
public class Score 
{
    private static int score = 0;
    private static String name = "";
    private static Score instance = null;
    BufferedReader bufferRead = null;
    private Score()
    {
        bufferRead = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public static Score GetInstanc()
    {
        if (instance == null) 
        {
            instance = new Score();
        }
        
        return instance;
    }
    
    public String GetName()
    {
        return name;
    }
    
    public int GetScore()
    {
        return score;
    }
    
    public  void SetName(String Name)
    {
        name = Name;
    }
    
    public void SetScore(int Score)
    {
       score = Score;
    }
    
    public void AddPoint()
    {
       score++;
    }
    
    public void ShowScore()
    {
        System.out.println("Your Score is: " + score);
    }
    
    public String GetNameAndScore() throws IOException
    {
        SetName(bufferRead.readLine());
        return (GetName() + " : " + GetScore());
    }
    
    public void ShowScoreBoard() throws IOException
    {
        // TO DO read score board from file and print
        System.out.println("********** Score Board **********");
        try{
            BufferedReader br = new BufferedReader(new FileReader("scoreBoard.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
              System.out.println(line);
            }
             br.close();
             System.out.println("*********************************");
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
