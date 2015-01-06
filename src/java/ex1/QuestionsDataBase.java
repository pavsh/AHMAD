/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author pavel
 */
public class QuestionsDataBase implements Serializable{
    private ArrayList<Question> Qdb;
    private Map<String, ArrayList<Question>> MapOfCategory;
    //private int nextQa=0;
    public QuestionsDataBase(ArrayList<Question> Qdb) {
        this.Qdb = Qdb;
        mapQuestions();
    }
    public void AddQuestion(Question qa)
    {
        Qdb.add(qa);
        ArrayList<Question> newqa=MapOfCategory.get(qa.getCat().getCategoryName());
        if(newqa!=null)
        newqa.add(qa);
        else
        {
            newqa=new ArrayList<Question>();
            newqa.add(qa);
            MapOfCategory.put(qa.getCat().getCategoryName(),newqa);
        }
    }
    public void DeleteQuestion(Question qa)
    {
        Qdb.remove(qa);
        ArrayList<Question> newqa=MapOfCategory.get(qa.getCat().getCategoryName());
        newqa.remove(qa);
        //Delete the Category if no questions left
        if(newqa.size()==0)
            MapOfCategory.remove(qa.getCat().getCategoryName());
    }
    public void DeleteQuestion(int index)
    {
      Question  qa=Qdb.get(index);
        ArrayList<Question> newqa=MapOfCategory.get(qa.getCat().getCategoryName());
        newqa.remove(qa);
        Qdb.remove(index);
        
        //Delete the Category if no questions left
        if(newqa.size()==0)
            MapOfCategory.remove(qa.getCat().getCategoryName());
    }
    
    public void mapQuestions()
    {
        MapOfCategory=new HashMap<String, ArrayList<Question>>();
        ArrayList<Question> qa;
        for(Question q:Qdb)
        {
            if(MapOfCategory.get(q.getCat())!=null)
            {
                //Add qa to category if category exists
                qa=MapOfCategory.get(q.getCat());
                qa.add(q);
            }
            else
            {
                qa=new ArrayList<Question>();
                qa.add(q);
                MapOfCategory.put(q.getCat().getCategoryName(),qa);
            }
            //MapOfCategory.put(q.getCat(), Qdb);
           
        }
    }
    public Question GetQuestion(Category cat)
    {   ArrayList<Question> QainCat=MapOfCategory.get(cat.getCategoryName());
        //Question nextqa= QainCat.get(nextQa++);
        for(Question q:QainCat)
        {
         
         if(!q.isShowed())
            {
                 q.setShowed(true);
                 return q;
            }
        }
        MapOfCategory.remove(cat.getCategoryName());
        return null;
        
    }
    public ArrayList<String> GetCategoriesList()
    {
       return new ArrayList<String>( MapOfCategory.keySet());
    }
    public ArrayList<Question> GetQaDB()
    {
        return Qdb;
    }
    public void initShowedStatus()
    {
        for(Question q:Qdb)
        {
            q.setShowed(false);
        }
    }
}
