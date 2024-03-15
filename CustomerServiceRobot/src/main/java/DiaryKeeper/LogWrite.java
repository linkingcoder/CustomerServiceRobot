package DiaryKeeper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogWrite {
        public static void Write(String username,String status) throws  IOException {
            String path = "D:\\java\\CustomerServiceRobot\\src\\main\\resources\\Log.txt";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            String word =  username+ "在"+formatter.format(calendar.getTime())+status;
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path,true)));
            out.write(word+"\r\n");
            out.close();
        }
}
