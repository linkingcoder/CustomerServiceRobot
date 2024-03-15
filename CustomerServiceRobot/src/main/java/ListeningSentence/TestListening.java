package ListeningSentence;

import org.junit.jupiter.api.Test;

public class TestListening {
    @Test
    public  void  Listening(){
        ListeningService recorder = new ListeningService();
        System.out.println("开始录音");
        int recordTimeInSeconds = 60; // 设置录音时间（以秒为单位）
        recorder.startRecording(recordTimeInSeconds);
        System.out.println("录音成功");
    }
}
