package ScriptParser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class TestParser {
    @Test
    public void TestSteps() throws SQLException, IOException {
    Parser p = new Parser();
    p.ParserFile("s1.txt");
   for(Step s : p.ReturnSteps()){
        System.out.println(s.toString());
    }

    }

}
