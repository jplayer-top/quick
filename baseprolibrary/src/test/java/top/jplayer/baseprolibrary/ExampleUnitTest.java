package top.jplayer.baseprolibrary;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public void getTime() {
        String input = "12:20";
        int iInput = Integer.parseInt(input.replace(":", ""));
        ArrayList<String> list = new ArrayList<>();
        for (int i = iInput; i <= 2400; i += 10) {
            String str;
            if (i % 100 >= 60) {
                continue;
            }
            if (i < 10) {
                str = "00:0" + i;
            } else if (i < 100) {
                str = "00:" + i;
            } else if (i < 1000) {
                int i1 = i % 100;
                if (i1 == 0) {
                    str = "0" + i / 100 + ":0" + i1;
                } else {
                    str = "0" + i / 100 + ":" + i1;
                }
            } else {
                int i1 = i % 100;
                if (i1 == 0) {
                    str = i / 100 + ":0" + i1;
                } else {
                    str = i / 100 + ":" + i1;
                }
            }
            list.add(str);
        }
        System.out.println(list.toString());
    }

    @Test
    public void getSp() throws Exception {
        for (int i = 1; i < 40; i++) {
            String s = String.format(Locale.CHINA, "<dimen name=\"dimen_%dsp\">%dsp</dimen>", i, i);
            System.out.println(s);
        }
    }
}