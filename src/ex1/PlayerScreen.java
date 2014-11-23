/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author pavel
 */
public class PlayerScreen extends GameScreen{
    BufferedReader bufferRead ;

    public PlayerScreen() {
        bufferRead = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void welcomescreen()
    {
        System.out.println("Welcome to The Trivia Game Of you Life");
        
    }
    public Category showoptions(TriviaGame NewGame) throws IOException
    {
        
		System.out.println("Choose Category from The Next List");
                
                //Get the Categories List from the Questions DB
                ArrayList<String> cat= NewGame.GetCategories();
                String[] catlist=new String[cat.size()];
                int count=0;
                for(String c:cat)
                {
                    
                    System.out.println((count+1) + ". " + c);
                    catlist[count++]=c;
                }
                
                System.out.println("Enter x to exit");
                
                //read the category choice of the user
                 Category ChoosedCategory = null;
                 String s = bufferRead.readLine();
                 if (s.compareTo("x") != 0) 
                 {
                    ChoosedCategory=new Category(catlist[Integer.parseInt(s)-1]);
                    System.out.println("you choose:"+catlist[Integer.parseInt(s)-1]);
                 }
                 else
                 {
                     Score.GetInstanc().ShowScore();
                     System.out.println("Please Enter Your name");
                     String newRecordForScoreBoard = Score.GetInstanc().GetNameAndScore();
                     // WRITE SCORE BOARD TO FILE
                     AppendScoreToScoreBoard(newRecordForScoreBoard);    
                     
                     System.out.println("Thank you for playing");
                     System.exit(0);
                 }
                 
                 return ChoosedCategory;
    }
    
    public void AppendScoreToScoreBoard(String newRecordForScoreBoard)
    {
        try
        {
            String path = "scoreBoard.txt";

            File file = new File(path);
               
            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }
            
            FileWriter fileWriter = new FileWriter(file,true);

            BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
            fileWriter.append(newRecordForScoreBoard);
            bufferFileWriter.close();
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
    
    public void ShowQuestions(TriviaGame NewGame,Category ChoosedCategory) throws IOException
    {   
            String StringInput;
            String[] Options;
                //Show Questions to the user
                 Question NextQA=NewGame.GetNextQuestion(ChoosedCategory);
                 while(NextQA!=null)
                 {  
                     System.out.println("================");
                     System.out.println("You Question is:");
                     switch(NextQA.GetType()){
                     case "YesNo":
                                    System.out.println(NextQA.getQA());           
                                    System.out.println("1(YES) or 0(NO)?");
                                    
                                    StringInput = bufferRead.readLine();
                                    if (NextQA.checkAnswer(StringInput))
                                    {
                                        System.out.println("right answer!!!");
                                    }
                                    else
                                    {
                                        System.out.println("Wrong!!");
                                    }
                     break;
                     case "Choice":
                                    System.out.println(NextQA.getQA());
                                    System.out.println("Choose from one of the next Options");
                                    Options=((ChoiceQuestion)NextQA).GetOptions();
                                    int count = 1;
                                    for(String op:Options)
                                    {
                                        
                                        System.out.println(count + ". " + op);
                                        count++;
                                    }
                                    StringInput = bufferRead.readLine();
                                    if (NextQA.checkAnswer(Options[Integer.parseInt(StringInput)-1]))
                                    {
                                        System.out.println("right answer!!!");
                                    }
                                    else
                                    {
                                        System.out.println("Wrong!!");
                                    }
                     break;
                     case "Open":
                                    System.out.println(NextQA.getQA());
                                    System.out.println("write your answer");
                                    StringInput = bufferRead.readLine();
                                    if (NextQA.checkAnswer(StringInput))
                                    {
                                        System.out.println("right answer!!!");
                                    }
                                    else
                                    {
                                        System.out.println("Wrong!!");
                                    }
                     break;
                     }  
                       
                        NextQA=NewGame.GetNextQuestion(ChoosedCategory);
                 }
                 if (NextQA==null)
                 {
                     System.out.println("No Questions left in this category");
                 }
                 
    }
}
