package ScriptInterpreter;

import ScriptParser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class TestInterpreter {
    @Test
    public void PrintProcess() throws SQLException, IOException, InterruptedException {
        Interpreter c = new Interpreter();
        Parser p = new Parser();
        p.ParserFile("s5.txt");
        c.InterpreterScript(p.ReturnSteps());
        System.out.println("Success Interpreter");
    }
}
