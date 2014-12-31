/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pavel
 */
public class PlayerScreen extends GameScreen {

    BufferedReader bufferRead ;

    public void setBufferRead(BufferedReader bufferRead) {
        this.bufferRead = bufferRead;
    }

    Question LastQuestion = null;
    File HtmlFile;

    public PlayerScreen() {
        // bufferRead = new BufferedReader(new InputStreamReader(System.in));
        HtmlFile = null;
    }

    public PlayerScreen(BufferedReader reader) {
        bufferRead = reader;
    }

    public void welcomescreen() {
        System.out.println("Welcome to The Trivia Game Of you Life");

    }

    public void showoptions(TriviaGame NewGame, PrintWriter out) throws IOException {
        String line;
        //BufferedReader bufferRead = new BufferedReader(new FileReader(HtmlFile));
        line = bufferRead.readLine();

        while (line != null) {
            out.println(line);
            if (line.contains("id=\"question_sec\"")) {
                //Get the Categories List from the Questions DB
                ArrayList<String> cat = NewGame.GetCategories();
                String[] catlist = new String[cat.size()];
                int count = 1;

                out.println("<form id=\"multi\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">");
                out.println("        <h2>Choose category</h2>");
                out.println("        <ul>");
                for (String c : cat) {
                    out.println("                <li><input id=\"r" + (count) + "\" name=\"Category\" value=\"" + c + "\" type=\"radio\"><label for=\"r1\">" + c + "</label></li>");
                    count++;
                }
                out.println("        </ul>");
                out.println("        <button type='submit'>Next</button>");
                out.println("        </form>");
                        //catlist[count++]=c;

            }
            line = bufferRead.readLine();
            
        }
    }

    public void AppendScoreToScoreBoard(String newRecordForScoreBoard) {
        try {
            String path = "scoreBoard.txt";

            File file = new File(path);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, true);

            BufferedWriter bufferFileWriter = new BufferedWriter(fileWriter);
            fileWriter.append(newRecordForScoreBoard);
            bufferFileWriter.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ShowQuestions(TriviaGame NewGame, Category ChoosedCategory, PrintWriter out,HttpSession session) throws IOException {
        String StringInput;
        String[] Options;
        //BufferedReader bufferRead = new BufferedReader(new FileReader(HtmlFile));
            //String line=bufferRead.readLine();
        //Show Questions to the user
        LastQuestion = NewGame.GetNextQuestion(ChoosedCategory);
        
        //If now Questions Left Show Message to Screen
        if (LastQuestion == null) {
            session.setAttribute("phase", "Empty_category");
            String line;
            line = bufferRead.readLine();
            while (line != null) {
                out.println(line);
                if (line.contains("id=\"question_sec\"")) {
                    out.println("<form id=\"New_Category\" class=\"ac-custom\" autocomplete=\"off\">");
                    out.println("<h2>No Questions Left In this Category</h2><BR>");
                    out.println("<button type='submit'>New category</button>");
                    out.println("</form>");

                }
                line = bufferRead.readLine();
              
            }

        }
        //Show the Next Question in choosed Category
        else {
            switch (LastQuestion.GetType()) {
                case "YesNo":
                    YesNoquestionOut(bufferRead, out, LastQuestion);
                    break;
                case "Choice":
                    MultiquestionOut(bufferRead, out, LastQuestion);
                    break;
                case "Open":
                    OpenquestionOut(bufferRead, out, LastQuestion);
                    break;
            }

        }

    }

    public void MultiquestionOut(BufferedReader reader, PrintWriter out, Question qa) throws IOException {
        String line;
        line = reader.readLine();
        String[] opt = ((ChoiceQuestion) qa).GetOptions();
        while (line != null) {
             out.println(line);
            if (line.contains("id=\"question_sec\"")) {
                out.println("<form id=\"multi\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">");
                out.println("        <h2>" + qa.getQA() + "</h2>");
                out.println("        <ul>");
                for (int i = 0; i < opt.length; i++) {
                    out.println("                <li><input id=\"r" + (i + 1) + "\" name=\"r1\" value=\"" + (i + 1) + "\" type=\"radio\"><label for=\"r1\">" + opt[i] + "</label></li>");
                }
                  
                out.println("        </ul>");
                out.println("        <button type='submit'>Next</button>");
                out.println("        </form>");

            }
            line = reader.readLine();
           
        }

    }

    void OpenquestionOut(BufferedReader reader, PrintWriter out, Question qa) throws IOException {
        String line;
        line = reader.readLine();
        while (line != null) {
            out.println(line);
            if (line.contains("id=\"question_sec\"")) {
                out.println("<form id=\"open\" class=\"ac-custom\" autocomplete=\"off\">");
                out.println("<h2>" + qa.getQA() + "</h2>");
                out.println("<input name=\"r1\" type=\"text\"/>");
                out.println("<button type='submit'>Next</button>");
                out.println("</form>");

            }
            line = reader.readLine();
            
        }
    }

    void YesNoquestionOut(BufferedReader reader, PrintWriter out, Question qa) throws IOException {
        String line;
        line = reader.readLine();
        while (line != null) {
            out.println(line);  
            if (line.contains("id=\"question_sec\"")) {
                out.println("<form id=\"yesno\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">");
                out.println("<h2>" + qa.getQA() + "</h2>");
                out.println("<ul>");
                out.println("<li><input id=\"r1\" name=\"r1\" value=\"Yes\" type=\"radio\"><label for=\"r1\">True</label></li>");
                out.println("<li><input id=\"r2\" name=\"r1\" value=\"No\" type=\"radio\"><label for=\"r2\">False</label></li>");
                out.println("</ul>");

                out.println("<button type='submit'>Next</button>");
                out.println("</form>");

            }
            line = reader.readLine();
            
        }
    }

    public boolean CheckAnswer(String Ans, PrintWriter out, HttpSession session) throws IOException {
        if (LastQuestion.getClass() == ChoiceQuestion.class) {
            String[] Opt = ((ChoiceQuestion) LastQuestion).GetOptions();
            if( LastQuestion.checkAnswer(Opt[Integer.parseInt(Ans) - 1]))
                return true;
        }
        if (LastQuestion.getClass() == YesNoQuestion.class) {
            if( LastQuestion.checkAnswer(Ans))
                return true;
        }
        if (LastQuestion.getClass() == OpenQuestion.class) {
            if( LastQuestion.checkAnswer(Ans))
                return true;
        }
        WrongAnswerMesssage(out, session);
        return false;
    }

    void WrongAnswerMesssage(PrintWriter out, HttpSession session) throws FileNotFoundException, IOException {

        //Init the "Showed Status of the Question
        LastQuestion.setShowed(false);

        //Show The "Wrong Answer" Page
       // BufferedReader bufferRead = new BufferedReader(new FileReader(HtmlFile));

        String line;
        line = bufferRead.readLine();
        while (line != null) {
            out.println(line);
            if (line.contains("id=\"question_sec\"")) {
                out.println("<form id=\"New_Category\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">");
                out.println("<h2>Wrong Answer!!</h2>");
                out.println("<ul>");
                out.println("<input type=\"hidden\" name=\"Wrong\" value=\"true\"/>");
                out.println("</ul>");
                out.println("<button type='submit'>Try Again</button>");
                out.println("</form>");

            }
            line = bufferRead.readLine();
           
        }
        //Set the Session Phase to "Wrong answer"
        session.setAttribute("phase", "Wrong_answer");

    }

}
