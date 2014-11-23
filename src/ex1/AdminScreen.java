/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author pavel
 */
public class AdminScreen extends GameScreen{
    BufferedReader bufferRead ;

    public AdminScreen(BufferedReader bufferRead) {
        this.bufferRead = bufferRead;
    }
    public AdminScreen() {
         bufferRead = new BufferedReader(new InputStreamReader(System.in));
    }
    public void welcomescreen(TriviaGame NewGame) throws IOException
    {
        System.out.println("==============================");
        System.out.println("hello Admin what do you want?");
        System.out.println("1. Add new Question?");
        System.out.println("2. remove Question?");
        String s = bufferRead.readLine();
                 switch(Integer.parseInt(s))
                 {
                     case 1:addQuestionsMenu(NewGame);
                            break;
                     case 2:RemoveQuestionsMenu(NewGame);
                         break;
                 }
                 NewGame.SaveQaToDB();
    }             

public Question addQuestionsMenu(TriviaGame NewGame) throws IOException
{
    System.out.println("Enter the Category");
     String Cat = bufferRead.readLine();
     System.out.println("Enter the Difficalty");
     String Diff = bufferRead.readLine();
     System.out.println("Enter the Question");
     String Qa = bufferRead.readLine();
     Question NewQa=null;
     System.out.println("What kind of Question?");
     System.out.println("1. YesNo?");
     System.out.println("2. Open?");
     System.out.println("3. Choice?");
      String Type = bufferRead.readLine();
                 switch(Integer.parseInt(Type))
                 {
                     case 1:
                            
                            System.out.println("Enter the Answer(1(YES)/0(NO)");
                            String Ans = bufferRead.readLine();
                            NewQa=new YesNoQuestion(Boolean.parseBoolean(Ans),Qa,Integer.parseInt(Diff),new Category(Cat));
                            break;
                     case 2:
                            System.out.println("Enter the Answer:");
                            Ans = bufferRead.readLine();
                            NewQa=new OpenQuestion(Ans,Qa,Integer.parseInt(Diff),new Category(Cat));
                            break;
                     case 3:
                            System.out.println("Enter the Options for the answer[op1 op2 ...]:");
                            String Options = bufferRead.readLine();
                            System.out.println("Enter the right answer number from the Options:");
                            Ans = bufferRead.readLine();
                            NewQa=new ChoiceQuestion(Options.split(" "),Integer.parseInt(Ans)-1,Qa,Integer.parseInt(Diff),new Category(Cat));   
                         break;
                 }
                 NewGame.AddQuestion(NewQa);
                 return NewQa;
}
public void RemoveQuestionsMenu(TriviaGame NewGame) throws IOException
{
    System.out.println("Those are the Questions in the DB, Choose which to delete:");
    QuestionsDataBase Qdb=NewGame.GetQuestionsDB();
    int count=0;
    for(Question q:Qdb.GetQaDB() )
    {
        System.out.println((count+1)+q.getQA());
        count++;
    }
    String QA = bufferRead.readLine();
    Qdb.DeleteQuestion(Integer.parseInt(QA)-1);
}
                  
                 
                 
                 
}
