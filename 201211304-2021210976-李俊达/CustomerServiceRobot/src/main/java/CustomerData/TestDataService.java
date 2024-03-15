package CustomerData;

import SpeakSentence.TalkService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class TestDataService {
    @Test
    public void TestUpdate() throws SQLException, IOException {
        DataService dataService = new DataService();
        dataService.UpdateMoney("李俊达",100);
    }
    @Test
    public void TestLogin()  throws SQLException {
        if(DataService.login("李俊达","123456")) {
            TalkService.Speaking("登录成功");
            System.out.println("登陆成功");
        }
        else
            System.out.println("登录失败");
    }
    @Test
    public void TestMoneyTake() throws SQLException, IOException {

        DataService ds = new DataService();
        ds.UpdateMoney("李俊达",-40000);
    }
    @Test
    public void TestSelectMoney() throws SQLException, IOException {
        DataService ds = new DataService();
        System.out.println(ds.SelectMoneyAmount("李俊达"));
    }
    @Test
    public void TestSelectBills() throws SQLException, IOException {
        DataService ds = new DataService();
        System.out.println(ds.SelectBills("李俊达"));
    }
}
