package ListeningSentence;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ListeningService {
    private TargetDataLine targetDataLine;
    private AudioFileFormat.Type fileType;
    private File audioFile;

    public void startRecording(int recordTimeInSeconds) {
        try {
            // 设置音频格式
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

            // 获取音频数据行信息
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);

            // 打开数据行并开始录音
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            // 创建音频文件
            audioFile = new File("D:\\java\\CustomerServiceRobot\\src\\main\\resources\\Voice\\recorded.wav");
            fileType = AudioFileFormat.Type.WAVE;

            // 创建计时器以停止录音
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    stopRecording();
                }
            }, recordTimeInSeconds * 1000); // 将录音时间从秒转为毫秒

            // 创建音频输入流并将录音数据写入文件
            AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
            AudioSystem.write(audioInputStream, fileType, audioFile);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (targetDataLine != null && targetDataLine.isOpen()) {
            targetDataLine.stop();
            targetDataLine.close();
            System.out.println("录音结束");
        }
    }
    public static void Listening(int recordTime){
            ListeningService recorder = new ListeningService();
            System.out.println("开始录音");
            recorder.startRecording(recordTime);
            System.out.println("录音成功");
    }

}
