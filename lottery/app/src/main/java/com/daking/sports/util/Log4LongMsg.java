package com.daking.sports.util;

/**
 * Created by 18 on 2017/5/31.
 * 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制
 * 所以这里使用自己分节的方式来输出足够长度的message
 */
public class Log4LongMsg {

    public static void write(String str) {
        str = str.trim();
        int index = 0;
        int maxLength = 4000;
        String sub;
        while (index < str.length()) {
            // java的字符不允许指定超过总的长度end
            if (str.length() <= index + maxLength) {
                sub = str.substring(index);
            } else {
                sub = str.substring(index, maxLength);
            }
            index += maxLength;
            LogUtil.e(sub.trim());
        }
    }
}
