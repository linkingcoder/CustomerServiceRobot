package ScriptParser;

import CustomerData.DataService;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {
    public static ArrayList<Step> Steps = new ArrayList<>();
    public static Step s;

    public static Step SelectStep(String StepId) {
        for (Step step : Steps) {
            if (step.StepId.equals(StepId)) {
                return step;
            }
        }
        return null;
    }

    public void ParserFile(String fileName) throws IOException, SQLException {
        File f = new File("D:\\java\\CustomerServiceRobot\\src\\main\\resources\\Scripts\\" + fileName);
        InputStreamReader in = new InputStreamReader(new FileInputStream(f));
        BufferedReader reader = new BufferedReader(in);
        String line;
        while ((line = reader.readLine()) != null) {
            String res = line.replace(",", "");
            res = res.trim();
            //跳过空行和注释
            if(!res.isEmpty()&&res.charAt(0)!='#') {
                ParserLine(res);
            }
        }
        //关闭文件
        in.close();
        reader.close();
    }

    //读取一行中空白分割的每一个token：
    //遇到'#'开头的token则处理结束（忽略行尾注释）
    //获得标识符，字符串或者操作符几类token
    //将token加入到List中
    //ProcessTokens(token[])
    public void ParserLine(String line) throws SQLException, IOException {
        ArrayList<String[]> list = new ArrayList<>();
        String[] tokens = line.split(" ");
        list.add(tokens);
        ProcessTokens(list);
    }

    public static void ProcessStep(String stepName) {
        s = new Step();
        s.StepId = stepName;
    }


    public static void ProcessSpeak(String[] token) throws SQLException, IOException {
        s.Expression = ProcessExpression(token);
    }

    public static String ProcessExpression(String[] token) throws SQLException, IOException {
        String username = DataService.username;
        String res = null;
        String VarName = null;
        for (int i = 1; i < token.length; i++) {
            if (token[i].charAt(0) == '$') {
                VarName = token[i].substring(1);
                if (VarName.equals("name")) {
                    VarName = username;
                } else if (VarName.equals("amount")) {
                    System.out.println("这里是" + username);
                    DataService d = new DataService();
                    VarName = String.valueOf(d.SelectBills(username));
                } else if (VarName.equals("rest")) {
                    System.out.println("这里是" + username);
                    DataService d = new DataService();
                    VarName = String.valueOf(d.SelectMoneyAmount(username));
                }
                if (res == null) {
                    res = VarName;
                } else {
                    res = res.concat(VarName);
                }
            }
            if (token[i].charAt(0) == '"' && token[i].charAt(token[i].length() - 1) == '"') {
                String sentence = token[i].substring(1, token[i].length() - 1);
                if (res == null) {
                    res = sentence;
                } else {
                    res = res.concat(sentence);
                }
            }
        }
        return res;
    }

    public static void ProcessListen(String startTimer, String endTimer) {
        int st = Integer.parseInt(startTimer);
        int et = Integer.parseInt(endTimer);
        s.startTimer = st;
        s.stopTimer = et;
    }

    public static void ProcessSilence(String nextStepId) {
        s.Silence = nextStepId;
    }

    public static void ProcessDefault(String nextStepId) {
        s.Default = nextStepId;
    }

    public static void ProcessBranch(String answer) {
        s.Branches.add(answer);
    }

    public static void ProcessTake(String TakeAmount) {
        s.Take = Integer.parseInt(TakeAmount);
    }
    public static void ProcessSave(String SaveAmount) {
        s.Save = Integer.parseInt(SaveAmount);
    }

    public ArrayList<Step> ReturnSteps() {
        Set set = new HashSet();
        List<Step> newList = new ArrayList<>();
        for (Step e : Steps) {
            //set能添加进去就代表不是重复的元素
            if (set.add(e)) newList.add(e);
        }
        Steps.clear();
        Steps.addAll(newList);
        return Steps;
    }

    public static void ProcessTokens(ArrayList<String[]> List) throws SQLException, IOException {
        for (String[] token : List) {
            switch (token[0]) {
                case "Step" -> ProcessStep(token[1]);
                case "Speak" -> ProcessSpeak(token);
                case "Listen" -> ProcessListen(token[1], token[2]);
                case "Branch" -> ProcessBranch(token[2]);
                case "Silence" -> ProcessSilence(token[1]);
                case "Take" -> ProcessTake(token[1]);
                case "Save" -> ProcessSave(token[1]);
                case "Default" -> {
                    ProcessDefault(token[1]);
                    Steps.add(s);
                }
                case "Exit" -> Steps.add(s);
                default -> System.out.println(token[0] + "不识别");
            }
        }
    }
}
