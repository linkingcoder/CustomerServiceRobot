package ScriptParser;

import java.util.ArrayList;

public class Step {
    public String StepId;
    public String Expression;
    public int startTimer;
    public int stopTimer;
    public int Take;
    public int Save;
    public String Silence;
    public String Default;
    public ArrayList<String> Branches = new ArrayList<>();


    @Override
    public String toString() {
        return "Step{" +
                "StepId='" + StepId + '\'' +
                ", Expression='" + Expression + '\'' +
                ", startTimer=" + startTimer +
                ", stopTimer=" + stopTimer +
                ", Take=" + Take +
                ", Save=" + Save +
                ", Silence='" + Silence + '\'' +
                ", Default='" + Default + '\'' +
                ", Branches=" + Branches +
                '}';
    }
}
