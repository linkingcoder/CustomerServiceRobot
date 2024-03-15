package CustomerData;

import DiaryKeeper.LogWrite;
import Druid.JDBCUtils;
import SpeakSentence.TalkService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataService {
    public static String username;
    //创建用户
    public static void CreateAccount(String name, String password) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        //sql
        String sql = "INSERT INTO customers(name,password,MoneyAmount ,Bills)"
                + "values(?,?,?,?)";
        //预编译
        PreparedStatement preparedStatement = conn.prepareStatement(sql); //预编译SQL，减少sql执行
        //传参
       preparedStatement.setObject(1,name);
       preparedStatement.setObject(2,password);
       preparedStatement.setObject(3,0);
       preparedStatement.setObject(4,0);
        //执行
        preparedStatement.execute();
    }
    //存钱取钱
    public void UpdateMoney(String name, int money) throws SQLException, IOException {
        Connection connection = JDBCUtils.getConnection();
        try {
            connection.setAutoCommit(false); // 禁用自动提交
            int balance = SelectMoneyAmount(name);

            String sql1 = "UPDATE customers SET MoneyAmount = MoneyAmount + ? WHERE name = ?";
            String sql2 = "UPDATE customers SET Bills = Bills + ? WHERE name = ?";

            if (money > 0) {
                // 增加金额
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1, money);
                preparedStatement1.setString(2, name);
                preparedStatement1.executeUpdate();
                preparedStatement1.close();
                LogWrite.Write(name, "存了" + money + "元");
                System.out.println(name + "在河南村镇银行存了" + money + "元");
            } else if (money + balance >= 0) {
                int bill = Math.abs(money);
                // 更新金额和账单
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

                preparedStatement1.setInt(1, money);
                preparedStatement1.setString(2, name);
                preparedStatement2.setInt(1, bill);
                preparedStatement2.setString(2, name);

                preparedStatement1.executeUpdate();
                preparedStatement2.executeUpdate();

                preparedStatement1.close();
                preparedStatement2.close();
                LogWrite.Write(name, "取了" + bill + "元");
                System.out.println(name + "在河南村镇银行取了" + bill + "元");
            } else {
                LogWrite.Write(name, "取钱失败，余额不足");
                TalkService.Speaking("余额不足");
                System.out.println("余额不足");
            }

            connection.commit(); // 手动提交更改
        } catch (SQLException e) {
            connection.rollback(); // 发生异常时回滚事务
            throw e;
        } finally {
            connection.setAutoCommit(true); // 恢复自动提交模式
            JDBCUtils.close(connection);
        }
    }


    //判断用户是否已存在
    public static boolean  isExist(String name) throws SQLException {
        String sql = "select MoneyAmount from customers where name = ?;";
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            preparedStatement.close();
            JDBCUtils.close(connection);
            return true;
        }
        preparedStatement.close();
        JDBCUtils.close(connection);
        return false;
    }
    public int SelectMoneyAmount(String name) throws SQLException, IOException {
        LogWrite.Write(name,"查询余额");
        int money=0;
        String sql = "select MoneyAmount from customers where name = ?;";
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            //TalkService.Speaking("查询成功");
            money = resultSet.getInt("MoneyAmount");
            //TalkService.Speaking(name + "您的余额是" + money + "元");
        } else {
           // TalkService.Speaking("查询失败");
            System.out.println("ERROR");
        }
        preparedStatement.close();
        JDBCUtils.close(connection);
        return money;
    }

    public int  SelectBills(String name) throws SQLException, IOException {
        LogWrite.Write(name,"查询账单");
        int money=0;
        String sql = "select Bills from customers where name = ?;";
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
             money = resultSet.getInt("Bills");
            System.out.println(name + "您的账单是" + money + "元");
           // TalkService.Speaking(name + "您的账单是" + money + "元");
        }
        preparedStatement.close();
        JDBCUtils.close(connection);
        return money;
    }
     public static   boolean login(String name, String password) throws SQLException {
         String sql = "select password from customers where name = ?;";
         Connection connection = JDBCUtils.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setObject(1, name);
         ResultSet resultSet = preparedStatement.executeQuery();
         if(resultSet.next()){
             String mypwd = resultSet.getString("password");
             if(mypwd.equals(password)){
                 preparedStatement.close();
                 JDBCUtils.close(connection);
                 username = name;
                 return true;
             }
         }
         preparedStatement.close();
         JDBCUtils.close(connection);
         return false;
     }
}