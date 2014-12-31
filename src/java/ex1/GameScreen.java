/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author pavel
 */
public  class GameScreen {
   public static void Printer(String[] HtmlString,BufferedReader reader,PrintWriter out) throws IOException
    {
        
            //BufferedReader bufferRead = new BufferedReader(new FileReader(HtmlFile));
    
            String line;
            line = reader.readLine();
            while (line != null) {
                
                out.println(line);
                if (line.contains("id=\"question_sec\"")) {
                    for(int i=0;i<HtmlString.length;i++)
                    {
                            out.println(HtmlString[i]);
                    }
                }
                line = reader.readLine();
            
        }
    }
}
