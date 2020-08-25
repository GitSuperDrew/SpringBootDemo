package com.study.module.mybatis.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * 剪贴板工具类
 *
 * @author Administrator
 * @date 2020/8/25 下午 1:27
 */
public class ClipboardUtil {
    /**
     * 将内容存放到剪切板
     *
     * @param text 文本内容
     */
    public static void clipboard(String text) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(text);
        clip.setContents(tText, null);
    }

    /**
     * 获取剪贴板上的内容
     *
     * @return 内容(字符串)
     */
    public static String getClipboardText() {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clip.getContents(null);
        if (contents != null) {
            // 如果剪切板的是文本内容
            if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    return (String) contents.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "剪切板的内容不是文本，不能显示";
    }
}
