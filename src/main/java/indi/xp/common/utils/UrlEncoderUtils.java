package indi.xp.common.utils;

import java.util.BitSet;

public class UrlEncoderUtils {

    private static BitSet dontNeedEncoding;

    static {
        dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }
        dontNeedEncoding.set('+');
        /**
         * 这里会有误差,比如输入一个字符串 123+456, <br>
         * 它到底是原文就是123+456还是123 456做了urlEncode后的内容呢？<br>
         * 其实问题是一样的，比如遇到123%2B456,它到底是原文即使如此，还是123+456 urlEncode后的呢？ <br>
         * 在这里，我认为只要符合urlEncode规范的，就当作已经urlEncode过了<br>
         * 毕竟这个方法的初衷就是判断string是否urlEncode过<br>
         */

        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');
    }

    /**
     * 判断str是否urlEncoder.encode过<br>
     * 经常遇到这样的情况，拿到一个URL,但是搞不清楚到底要不要encode.<Br>
     * 不做encode吧，担心出错，做encode吧，又怕重复了<Br>
     */
    public static boolean hasUrlEncoded(String str) {

        boolean needEncode = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (dontNeedEncoding.get((int) c)) {
                continue;
            }
            if (c == '%' && (i + 2) < str.length()) {
                // 判断是否符合urlEncode规范
                char c1 = str.charAt(++i);
                char c2 = str.charAt(++i);
                if (isDigit16Char(c1) && isDigit16Char(c2)) {
                    needEncode = true;
                    break;
                }

            }

        }

        return needEncode;
    }

    /**
     * 判断c是否是16进制的字符
     */
    private static boolean isDigit16Char(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

}
