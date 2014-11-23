/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author pavel
 */
public class presentation {
    public static void main(String[] args) throws Exception {
                TriviaGame NewGame;
                if(!new File("GameDB.dat").exists())
                {
                //Questions DB Init
                ArrayList<Question> Questions=new ArrayList<>();
                Question q1=new YesNoQuestion(true,"Pavel is the king?",2,new Category("History"));
                Questions.add(q1);
                Question q2=new OpenQuestion("Pavel is the king","Who is The King",2,new Category("Math"));
                Questions.add(q2);
                String[] Options={"Pavel","Ben","San"};
                Question q3=new ChoiceQuestion(Options,1,"Who is the king?",3,new Category("Computer"));
                Questions.add(q3);
                QuestionsDataBase Qdb=new QuestionsDataBase(Questions);
                 NewGame=new TriviaGame(Qdb);
                  NewGame.SaveQaToDB();
                }
                else
                {
                     NewGame=new TriviaGame("GameDB.dat");
                    
                }
                //Create New Game of Trivia
                
                System.out.println("================");
                System.out.println("Welcome");
                System.out.println("Who Are you?(CTRL+C for Exist)");
                System.out.println("1 Admin");
                System.out.println("2 Player");
                System.out.println("3 Show Score Board");
                BufferedReader bufferRead =new BufferedReader(new InputStreamReader(System.in));;
                String s=bufferRead.readLine();
                switch(Integer.parseInt(s))
                {
                    case 1:
                        //Admin Screen
                        while(true)
                        {
                        AdminScreen AdmnScreen=new AdminScreen();
                        AdmnScreen.welcomescreen(NewGame);
                        }
                        
                    case 2:
                        while(true)
                        {
                            PlayerScreen PlScreen=new PlayerScreen();

                            //Show the Categories to the Player
                            Category ChoosedCategory= PlScreen.showoptions(NewGame);

                            //Show Questions to the player
                            PlScreen.ShowQuestions(NewGame, ChoosedCategory);
                        }
                    case 3:
                        Score.GetInstanc().ShowScoreBoard();
                }

                
                
                
                
                      
                 
		}
}
