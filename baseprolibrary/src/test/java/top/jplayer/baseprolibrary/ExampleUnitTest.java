package top.jplayer.baseprolibrary;

import org.junit.Test;

import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        for (int i = 1; i < 200; i++) {
            String s = String.format(Locale.CHINA, "<dimen name=\"dimen_%ddp\">%ddp</dimen>", i, i);
            System.out.println(s);
        }
    }

    @Test
    public void getSp() throws Exception {
        for (int i = 1; i < 40; i++) {
            String s = String.format(Locale.CHINA, "<dimen name=\"dimen_%dsp\">%dsp</dimen>", i, i);
            System.out.println(s);
        }
    }
}