package ex1;

import java.io.Serializable;


public abstract class Question implements Serializable  {
 private String QA;
 private int Diff;
 private Category Cat;
 private boolean showed;
 private String type;
 //private String QAType;
    public Question(String QA,String type) {
        this.QA = QA;
       Diff=1;
       Cat=new Category("History");
       this.type=type;
    }

    public Question(String QA, int Diff, Category Cat,String type) {
        this.QA = QA;
        this.Diff = Diff;
        this.Cat = Cat;
        this.type=type;
    }

    public String getQA() {
        return QA;
    }

    public void setQA(String QA) {
        this.QA = QA;
    }
    
   

    public int getDiff() {
        return Diff;
    }

    public void setDiff(int Diff) {
        this.Diff = Diff;
    }

    public Category getCat() {
        return Cat;
    }

    public void setCat(Category Cat) {
        this.Cat = Cat;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }
    
    public abstract boolean checkAnswer(String ans);
    //public abstract boolean getAnswers(String ans);
    public String GetType()
    {
        return type;
    }
}
