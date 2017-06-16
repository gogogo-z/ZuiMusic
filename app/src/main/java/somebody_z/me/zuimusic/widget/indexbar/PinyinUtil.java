package somebody_z.me.zuimusic.widget.indexbar;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.regex.Pattern;

/**
 * Created by Huanxing Zeng on 2017/3/6.
 * email : zenghuanxing123@163.com
 */
public class PinyinUtil {

    private static final String PATTERN_POLYPHONE = "^#[a-zA-Z]+#.+";
    private static final String PATTERN_LETTER = "^[a-zA-Z].*+";

    /**
     * Chinese character -> Pinyin
     */
    public static String getPingYin(String inputString) {
        char[] input = inputString.trim().toCharArray();
        String output = "";
        for (int i = 0; i < input.length; i++) {
            output += Pinyin.toPinyin(input[i]);
        }
        return output.toLowerCase();
    }

    /**
     * Are start with a letter
     *
     * @return if return false, index should be #
     */
    public static boolean matchingLetter(String inputString) {
        return Pattern.matches(PATTERN_LETTER, inputString);
    }

    public static boolean matchingPolyphone(String inputString) {
        return Pattern.matches(PATTERN_POLYPHONE, inputString);
    }

    public static String gePolyphoneInitial(String inputString) {
        return inputString.substring(1, 2);
    }

    public static String getPolyphoneRealPinyin(String inputString) {
        String[] splits = inputString.split("#");
        return splits[1];
    }

    public static String getPolyphoneRealHanzi(String inputString) {
        String[] splits = inputString.split("#");
        return splits[2];
    }

}
