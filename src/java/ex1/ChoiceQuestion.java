package ex1;


public class ChoiceQuestion extends Question {
private String[] Options;
private int Answer;

    public ChoiceQuestion(String[] Options, int Answer, String QA) {
        super(QA,"Choice");
        this.Options = Options;
        this.Answer = Answer;
    }

    public ChoiceQuestion(String[] Options, int Answer, String QA, int Diff, Category Cat) {
        super(QA, Diff, Cat,"Choice");
        this.Options = Options;
        this.Answer = Answer;
    }

    @Override
    public boolean checkAnswer(String ans) {
        if (Options[Answer].equals(ans)) 
        {
            Score.GetInstanc().AddPoint();
            return true;
        }
        return false;
    }
    public String[] GetOptions()
    {
        return Options;
    }
}
