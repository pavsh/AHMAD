package ex1;


public class YesNoQuestion extends Question {
    
    private boolean Answer;

    public YesNoQuestion(boolean Answer, String QA) {
        super(QA,"YesNo");
        this.Answer = Answer;
    }

    public YesNoQuestion(boolean Answer, String QA, int Diff, Category Cat) {
        super(QA, Diff, Cat,"YesNo");
        this.Answer = Answer;
    }
    
    
    @Override
    public boolean checkAnswer(String ans) {
        if(ans.equalsIgnoreCase("YES")&&Answer==true)
        {
            Score.GetInstanc().AddPoint();
            return true;
        }
        if(ans.equalsIgnoreCase("NO")&&Answer==false)
        {
                return true;
        }
         return false;   
    }

}
