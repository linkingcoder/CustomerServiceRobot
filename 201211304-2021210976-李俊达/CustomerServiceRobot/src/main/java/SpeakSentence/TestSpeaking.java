package SpeakSentence;

import org.junit.jupiter.api.Test;

public class TestSpeaking {
    @Test
    public void Speaking(){
        TalkService.Speaking("你好，我能为你做什么？");
    }
}
