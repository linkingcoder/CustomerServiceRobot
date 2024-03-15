package ScriptInterpreter;
import CustomerData.DataService;
import ListeningSentence.ListeningService;
import ScriptParser.Parser;
import ScriptParser.Step;
import SpeakSentence.TalkService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Interpreter {
    public void executeStep(Step s) throws InterruptedException, SQLException, IOException {//处理step
        //执行Speak（输出到标准输出）
        if (s.Expression!=null){
            TalkService.Speaking(s.Expression);
            System.out.println(s.Expression);
            Thread.sleep(5000);
        }
        //执行Listen（直接从标准输入读入用户意愿）
        if (s.stopTimer - s.startTimer != 0) {
            int recordTime = s.stopTimer - s.startTimer;
            ListeningService.Listening(recordTime);
            System.out.println("录音" + recordTime+"s");
            Thread.sleep(5000);
        }
        //执行存钱和取钱，操作数据库
        if(s.Save!=0){
            DataService dataService = new DataService();
            dataService.UpdateMoney(DataService.username,s.Save);
        }
        if(s.Take!=0){
            DataService dataService = new DataService();
            dataService.UpdateMoney(DataService.username,-s.Take);
        }
    }

    public void executeBranch(Step entryStep) throws InterruptedException, SQLException, IOException {//处理branch中存储的子step
        executeStep(entryStep);
        if (entryStep.Silence != null) {
            executeStep(Parser.SelectStep(entryStep.Silence));
        }
        if (entryStep.Default != null) {
            executeStep(Parser.SelectStep(entryStep.Default));
        }
    }
    //获取脚本语法树，创建执行环境
    public void InterpreterScript(ArrayList<Step> steps) throws InterruptedException, SQLException, IOException {
        String username = DataService.username;
        Step startStep = steps.get(0);
        //当前Step置为entryStep
        Step entryStep = startStep;
        executeStep(startStep);
        ArrayList<String> childSteps = startStep.Branches;//子进程
        //获得下一个StepId
        //根据用户意向查找，获得StepId
        for (String stepId : childSteps){
            entryStep = Parser.SelectStep(stepId);
            executeBranch(Parser.SelectStep(stepId));
        }
        //如果用户沉默，则获得Silence的StepId
        if (startStep.Silence != null) {
            executeStep(Parser.SelectStep(startStep.Silence));
        }
        //如果查不到则获得Default的StepId
        if (startStep.Default != null) {
            executeStep(Parser.SelectStep(startStep.Default));
        }
    }
}

