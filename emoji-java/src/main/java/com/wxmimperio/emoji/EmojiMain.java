package com.wxmimperio.emoji;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.wxmimperio.emoji.utils.CommonUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className EmojiMain.java
 * @description This is the description of EmojiMain.java
 * @createTime 2020-12-25 12:29:00
 */
public class EmojiMain {

    public static void main(String[] args) {
        String emoji = "\uD83D\uDE02 \uD83D\uDE0F test";

        String result = CommonUtils.filterEmoji(emoji);
        System.out.println(result);

        EmojiConverter converter = EmojiConverter.getInstance();
        String alias = converter.toAlias(emoji);
        System.out.println(alias);

        String html = converter.toHtml(emoji);
        System.out.println(html);

        String unicode = converter.toUnicode(emoji);
        System.out.println(unicode);

    }
}
