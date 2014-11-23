package ex1;


public class OpenQuestion extends Question {
private String Answer;

    public OpenQuestion(String Answer, String QA) {
        super(QA,"Open");
        this.Answer = Answer;
        
    }

    public OpenQuestion(String Answer, String QA, int Diff, Category Cat) {
        super(QA, Diff, Cat,"Open");
        this.Answer = Answer;
    }

    @Override
    public boolean checkAnswer(String ans) {
        if (ans.equalsIgnoreCase(Answer))
        {
            Score.GetInstanc().AddPoint();
            return true;
        }
        return false;
    }
    
}
