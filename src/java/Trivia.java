/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ex1.AdminScreen;
import ex1.Category;
import ex1.ChoiceQuestion;
import ex1.GameScreen;
import ex1.PlayerScreen;
import ex1.Question;
import ex1.TriviaGame;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pavel
 */
public class Trivia extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //to do make this in the session according to user
    TriviaGame NewGame;
    PlayerScreen PlScreen;
    AdminScreen AdmScreen;
    Category ChoosedCategory = null;
    final String COOKIE_NAME = "TriviaUsername";
    String Cookie_user = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String path = this.getServletContext().getRealPath("/");
        File file = new File(path + "\\index_question.html");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            HttpSession session = request.getSession();
            //First Entry to servlet
            if (session.getAttribute("Phase") == null) {
                session.setAttribute("Phase", "Login");
                
                //Get the User Cookie if have
                Cookie[] Cookies = request.getCookies();
                for (Cookie cookie : Cookies) {
                    if (cookie.getName().equals(COOKIE_NAME)) {
                        Cookie_user = cookie.getValue();
                        
                        //End the Login Phase
                        session.setAttribute("username", Cookie_user);
                        session.setAttribute("Login", "True");
                    }
                }
                WelcomeScreen(reader, out);
            }
            
            if (((String) session.getAttribute("Login"))==null){
               //If the user entered His User name
               if (request.getParameter("username") != null) {
                String value = (String) request.getParameter("username");
                session.setAttribute("username", value);
                session.setAttribute("Login", "True");
                
                //If The user doesn't have cookie
                if (request.getParameter("remember")!=null) {
                    Cookie UserCookie
                            = new Cookie(COOKIE_NAME, value);
                    response.addCookie(UserCookie);
                }
                else
                {
                    
                }
                
            } 
               //The user Left the USername field empty
               else {
                

                    }
            }

            //===============================
            //Alredy have a session id
            //===============================
            if( session.getAttribute("username")!=null) {
                
                //New Game Pressed
                if (request.getParameter("new_Game") != null) {
                    //session.setAttribute("phase", "new game");
                    session.setAttribute("PlayerType", "Player");
                    session.setAttribute((String) session.getAttribute("username") + "TriviaGame",
                            NewGame(path, reader, session, request, out));
                } else //==================================
                //Get The user Selection from Menue
                //===============================
                if (session.getAttribute("PlayerType") == null) {
                    if (request.getParameter("r1") != null) {
                        switch (request.getParameter("r1")) {
                            case "1":
                                session.setAttribute("PlayerType", "Admin");
                                AdminMenu(reader, out, session);

                                break;
                            case "3":
                                session.setAttribute("PlayerType", "Player");
                                session.setAttribute((String) session.getAttribute("username") + "TriviaGame",
                                        NewGame(path, reader, session, request, out));
                                break;
                            case "2":
                                session.setAttribute("PlayerType", "ScoreBoard");
                                ShowScoreBoard(reader, out);
                                break;
                        }
                    }
                } //====================================
                //Alredy picked from the Menu
                //===============================
                else {
                    switch ((String) session.getAttribute("PlayerType")) {
                        case "Player":
                            PlayGame(reader, session, request, out);
                            break;
                        case "Admin":
                            AdminScreens(reader, out, session, request);
                            break;
                    }
                }
            }

        } catch (Exception e) {
            out.println(e.toString());
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    void WelcomeScreen(BufferedReader reader, PrintWriter out) throws IOException {

        String[] HtmlStr = new String[15];
        
        if (Cookie_user != null) {
        HtmlStr[0] = "<form id=\"open\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">";
        HtmlStr[1] = "<h1>Welcome "+Cookie_user+"</h1>";
        HtmlStr[2] = "<h2>Who Are You?</h2>";
        
        
        HtmlStr[4] = "<ul>";
        HtmlStr[5] = "<li><input id=\"r1\" name=\"r1\" value=\"1\" type=\"radio\"><label for=\"r1\">Admin</label></li>";
        HtmlStr[6] = "<li><input id=\"r1\" name=\"r1\" value=\"3\" type=\"radio\"><label for=\"r1\">Player</label></li>";
        HtmlStr[7] = "<li><input id=\"r1\" name=\"r1\" value=\"2\" type=\"radio\"><label for=\"r1\">Show Score Board</label></li>";
        HtmlStr[8] = "</ul>";
        HtmlStr[9] = "<button type='submit'>Go</button>";
        HtmlStr[10] = "</form>";
        }
        else
        {
        HtmlStr[0] = "<form id=\"open\" class=\"ac-custom ac-radio ac-fill\" autocomplete=\"off\">";
        HtmlStr[1] = "<h1>Welcome</h1>";
        HtmlStr[2] = "<h2>Who Are You?</h2>";
        HtmlStr[3] = "<h3> Please type your username (no spaces and special charecters)";
        
        HtmlStr[4] = "<input id=\"username\" name=\"username\" type=\"text\" size=25/>";
        HtmlStr[5] = "<ul><li><input id=\"remember\" name=\"remember\" value=\"remember\" type=\"radio\"><label for=\"remember\">Remeber Me</label></li></ul>";       
        
        HtmlStr[6] = "<ul>";
        HtmlStr[7] = "<li><input id=\"r1\" name=\"r1\" value=\"1\" type=\"radio\"><label for=\"r1\">Admin</label></li>";
        HtmlStr[8] = "<li><input id=\"r1\" name=\"r1\" value=\"3\" type=\"radio\"><label for=\"r1\">Player</label></li>";
        HtmlStr[11] = "<li><input id=\"r1\" name=\"r1\" value=\"2\" type=\"radio\"><label for=\"r1\">Show Score Board</label></li>";
        HtmlStr[12] = "</ul>";
        HtmlStr[13] = "<button type='submit'>Go</button>";
        HtmlStr[14] = "</form>";
        }
        GameScreen.Printer(HtmlStr, reader, out);

    }

    TriviaGame NewGame(String path, BufferedReader reader, HttpSession session, HttpServletRequest request, PrintWriter out) throws FileNotFoundException, Exception {
        session.setAttribute("phase", "category_choose");

        //Show Categories to the user
        NewGame = new TriviaGame(path + "\\GameDB.dat");
        session.setAttribute((String) session.getAttribute("username") + "PlayerScreen",
                new PlayerScreen(reader));
        PlayerScreen CurrPlscreen = (PlayerScreen) session.getAttribute((String) session.getAttribute("username") + "PlayerScreen");
        CurrPlscreen.showoptions(NewGame, out,session);
        return NewGame;
    }

    void AdminMenu(BufferedReader reader, PrintWriter out, HttpSession session) throws IOException, Exception {
        String path = this.getServletContext().getRealPath("/");
        File file = new File(path + "\\index_question.html");
        session.setAttribute((String) session.getAttribute("username") + "AdminScreen", new AdminScreen(reader));
        AdminScreen currAdminScreen = (AdminScreen) session.getAttribute((String) session.getAttribute("username") + "AdminScreen");
        NewGame = new TriviaGame(path + "\\GameDB.dat");
        session.setAttribute("phase", "Admin_Menu");
        currAdminScreen.welcomescreen(out);

    }

    @SuppressWarnings("StringEquality")
    void AdminScreens(BufferedReader reader, PrintWriter out, HttpSession session, HttpServletRequest request) throws IOException {
        AdminScreen currAdminScreen = (AdminScreen) session.getAttribute((String) session.getAttribute("username") + "AdminScreen");
        currAdminScreen.setBufferRead(reader);
        switch ((String) session.getAttribute("phase")) {
            case "Admin_Menu":
                //The Admin Choosed To add a New Question
                if (request.getParameter("r1").equals("1")) {
                    session.setAttribute("phase", "Add_Question");
                    currAdminScreen.addQuestionsMenu(out);
                } //Choosed to Delete Question
                else {
                    session.setAttribute("phase", "Delete_Question");
                    currAdminScreen.RemoveQuestionsMenu((TriviaGame) session.getAttribute((String) session.getAttribute("username") + "TriviaGame"), reader, out);
                }
                break;
            case "Add_Question":
                session.setAttribute("Question_Type", request.getParameter("r1"));
                session.setAttribute("Question_Diff", request.getParameter("Diff"));
                session.setAttribute("Question_Category", request.getParameter("Category"));
                session.setAttribute("Question", request.getParameter("Question"));
                session.setAttribute("phase", "Save_Question");
                currAdminScreen.GetQuestionAnswers(out, request.getParameter("r1"), request.getParameter("Question"));
                break;
            case "Save_Question":

                currAdminScreen.CreateQuestion((TriviaGame) session.getAttribute((String) session.getAttribute("username") + "TriviaGame"), (String) session.getAttribute("Question"),
                        (String) session.getAttribute("Question_Type"),
                        (String) session.getAttribute("Question_Diff"), (String) session.getAttribute("Question_Category"),
                        request.getParameter("Answer"), request.getParameter("Options"));
                session.setAttribute("phase", "Admin_Menu");
                currAdminScreen.welcomescreen(out);
                break;
            case "Delete_Question":
                session.setAttribute("phase", "Admin_Menu");
                currAdminScreen.RemoveQuestion((TriviaGame) session.getAttribute((String) session.getAttribute("username") + "TriviaGame"), request.getParameter("Question"));
                currAdminScreen.welcomescreen(out);
                break;
        }
    }

    void PlayerScreens(BufferedReader reader, PrintWriter out) throws FileNotFoundException, IOException {

        String line;
        line = reader.readLine();
        while (line != null) {

            out.println(line);
            line = reader.readLine();
        }
    }

    void PlayGame(BufferedReader reader, HttpSession session, HttpServletRequest request, PrintWriter out) throws IOException {

        try{
            
        
        PlayerScreen CurrPlscreen = (PlayerScreen) session.getAttribute((String) session.getAttribute("username") + "PlayerScreen");
        CurrPlscreen.setBufferRead(reader);
        //Check on which phase the Game is
        switch ((String) session.getAttribute("phase")) {
            case "category_choose":
                if(request.getParameter("Category")!=null){
                ChoosedCategory = new Category(request.getParameter("Category"));
                CurrPlscreen.ShowQuestions((TriviaGame) session.getAttribute((String) session.getAttribute("username") + "TriviaGame"), ChoosedCategory, out, session);
                session.setAttribute("phase", "show_Questions");
                }
                else
                {
                    

                    
                }
                break;
            case "show_Questions":

                //Check is the Answer right 
                if (CurrPlscreen.CheckAnswer(request.getParameter("r1"), out, session)) //Show next Question
                {
                    CurrPlscreen.ShowQuestions((TriviaGame) session.getAttribute((String) session.getAttribute("username") + "TriviaGame"), ChoosedCategory, out, session);
                }

                break;
            // If returned from The WRONG Answer Page than show new question
            case "Wrong_answer":
                session.setAttribute("phase", "show_Questions");
                CurrPlscreen.ShowQuestions((TriviaGame) session.getAttribute((String) session.getAttribute("username") + "TriviaGame"), ChoosedCategory, out, session);
                break;

            //Check if New category is needed
            case "Empty_category":

                session.setAttribute("phase", "category_choose");
                CurrPlscreen.showoptions((TriviaGame) session.getAttribute((String) session.getAttribute("username") + "TriviaGame"), out,session);
                break;
        }
        }
        catch(Exception e)
        {
            System.err.println(e);
        }

    }

    private void ShowScoreBoard(BufferedReader reader, PrintWriter out) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
