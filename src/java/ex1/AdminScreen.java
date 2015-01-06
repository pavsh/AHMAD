/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author pavel
 */

public class AdminScreen extends GameScreen{
    BufferedReader bufferRead ;

    public void setBufferRead(BufferedReader bufferRead) {
        this.bufferRead = bufferRead;
    }
    File HtmlFile;
    String NewQuestionString = null;
    Question NewQuestion=null;
    public AdminScreen(BufferedReader bufferRead) {
        this.bufferRead = bufferRead;
    }
    public AdminScreen(File file) {
         //bufferRead = new BufferedReader(new InputStreamReader(System.in));
        HtmlFile=file;
    }
    public void welcomescreen(PrintWriter out) throws IOException
    {
         //Show The "Wrong Answer" Page
       // BufferedReader bufferRead = new BufferedReader(new FileReader(HtmlFile));

        String line;
        line = bufferRead.readLine();
        while (line != null) {

            if (line.contains("id=\"question_sec\"")) {
                out.println("<form id=\"New_Category\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">");
                out.println("<h2>hello Admin what do you want?</h2>");
                out.println("<ul>");
                out.println("<li><input id=\"r1\" name=\"r1\" value=\"1\" type=\"radio\"><label for=\"r1\">Add new Question?</label></li>");
                out.println("<li><input id=\"r1\" name=\"r1\" value=\"2\" type=\"radio\"><label for=\"r1\">remove Question?</label></li>");
                out.println("</ul>");
                out.println("<button type='submit'>Next</button>");
                out.println("</form>");

            }
            line = bufferRead.readLine();
            out.println(line);
        }

    }             

public void addQuestionsMenu(PrintWriter out) throws IOException
{
    //BufferedReader bufferRead = new BufferedReader(new FileReader(HtmlFile));
    
    String line;
        line = bufferRead.readLine();
        while (line != null) {

            if (line.contains("id=\"question_sec\"")) {
                out.println("<form id=\"open\" class=\"ac-custom\" autocomplete=\"off\">");
                out.println("<h4>Enter the Category</h4>");
                out.println("<input name=\"Category\" type=\"text\"/>");
                out.println("<h4>Enter the Difficalty</h4>");
                out.println("<input name=\"Diff\" type=\"text\"/>");
                out.println("<h4>Enter the Question</h4>");
                out.println("<input name=\"Question\" type=\"text\"/>");
                out.println("<h4>What kind of Question?</h4>");
                out.println("<li><input id=\"r1\" name=\"r1\" value=\"1\" type=\"radio\"><label for=\"r1\">YesNo</label></li>");
                out.println("<li><input id=\"r1\" name=\"r1\" value=\"3\" type=\"radio\"><label for=\"r1\">Choice</label></li>");
                out.println("<li><input id=\"r1\" name=\"r1\" value=\"2\" type=\"radio\"><label for=\"r1\">Open</label></li>");
                out.println("<button type='submit'>Next</button>");
                out.println("</form>");

            }
            line = bufferRead.readLine();
            out.println(line);
        }
                
}
public void RemoveQuestionsMenu(TriviaGame NewGame,BufferedReader reader, PrintWriter out) throws IOException
{
    String[] HtmlStr;
    
    QuestionsDataBase Qdb=NewGame.GetQuestionsDB();
    int QdbSize=Qdb.GetQaDB().size();
    HtmlStr=new String[QdbSize+4];
    HtmlStr[0]="<form id=\"open\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">";
    HtmlStr[1]="<h2>Those Are The Questions for Deletion</h2>";
    int TagCounter=2;
    int Qacount=1;
    for(Question q:Qdb.GetQaDB() )
    {
        HtmlStr[TagCounter++]="<li><input id=\"r1\" name=\"Question\" value=\""+Qacount+"\" type=\"radio\"><label for=\"r1\">"+q.getQA()+"</label></li>";
        Qacount++;
    }
    
                HtmlStr[QdbSize+2]="<button type='submit'>Next</button>";
                HtmlStr[QdbSize+3]="</form>";
     GameScreen.Printer(HtmlStr, reader, out);
    //String QA = bufferRead.readLine();
   // Qdb.DeleteQuestion(Integer.parseInt(QA)-1);
}
public void RemoveQuestion(TriviaGame NewGame,String QaIndx) throws IOException
{
    QuestionsDataBase Qdb=NewGame.GetQuestionsDB();
    Qdb.DeleteQuestion(Integer.parseInt(QaIndx)-1);
    NewGame.SaveQaToDB();
}
public void CreateQuestion(TriviaGame NewGame,String Qa,String Type,String Diff,String Cat,String Ans,String Options) throws IOException
{
    switch(Integer.parseInt(Type))
                 {
                     case 1:
                            
                            
                            NewQuestion=new YesNoQuestion(Boolean.parseBoolean(Ans),Qa,Integer.parseInt(Diff),new Category(Cat));
                            break;
                     case 2:
                           
                            NewQuestion=new OpenQuestion(Ans,Qa,Integer.parseInt(Diff),new Category(Cat));
                            break;
                     case 3:
                            
                            NewQuestion=new ChoiceQuestion(Options.split(" "),Integer.parseInt(Ans)-1,Qa,Integer.parseInt(Diff),new Category(Cat));   
                         break;
                 }
                 NewGame.AddQuestion(NewQuestion);
                 NewGame.SaveQaToDB();
               
}
public void GetQuestionAnswers(PrintWriter out,String Type,String Qa) throws FileNotFoundException, IOException
{
  // BufferedReader bufferRead = new BufferedReader(new FileReader(HtmlFile));
    
    String line;
        line = bufferRead.readLine();
        while (line != null) {
            out.println(line);
        if (line.contains("id=\"question_sec\"")) {
                switch(Integer.parseInt(Type))
                {
                    case 1: 
                            out.println("<form id=\"open\" class=\"ac-custom\" autocomplete=\"off\">");
                            out.println("<h1>"+Qa+"</h2>");
                            out.println("<h2>Enter The Answer (Yes/No)</h2>");
                            out.println("<input name=\"r1\" type=\"text\"/>");
                            out.println("<button type='submit'>Next</button>");
                            out.println("</form>");
                        break;
                    case 2: 
                            out.println("<form id=\"open\" class=\"ac-custom\" autocomplete=\"off\">");
                            out.println("<h1>"+Qa+"</h2>");
                            out.println("<h2Enter The Answer</h2>");
                            out.println("<input name=\"r1\" type=\"text\"/>");
                            out.println("<button type='submit'>Next</button>");
                            out.println("</form>");
                        break;
                    case 3:
                           out.println("<form id=\"open\" class=\"ac-custom\" autocomplete=\"off\">");
                           out.println("<h1>"+Qa+"</h2>");
                           out.println("<h2>Enter the Optional answers[ans1 ans2 ...]</h2>");
                           out.println("<input name=\"Options\" type=\"text\"/>");
                           out.println("<h2>Enter the right Answer index</h2>");
                           out.println("<input name=\"Answer\" type=\"text\"/>");
                           out.println("<button type='submit'>Next</button>");
                           out.println("</form>"); 
                        break;
                }
        }
        line = bufferRead.readLine();
            
        }
}
           
}
