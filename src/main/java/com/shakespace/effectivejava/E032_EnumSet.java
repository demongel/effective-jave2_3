package com.shakespace.effectivejava;

import java.util.EnumSet;
import java.util.Set;

public class E032_EnumSet {
    public static void main(String[] args) {
        new Text().applyStyles(EnumSet.of(Text.Style.BOLD, Text.Style.ITALIC));

        // 可以使用 EnumSet 替换这种使用方式
        new TextOri().applyStyles(TextOri.STYLE_BOLD | TextOri.STYLE_ITALIC);
    }
}

class Text {
    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
    }

    public void applyStyles(Set<Style> styles) {

    }
}

class TextOri {
    public static final int STYLE_BOLD = 1 << 0;
    public static final int STYLE_ITALIC = 1 << 0;
    public static final int STYLE_UNDERLINE = 1 << 0;
    public static final int STYLE_ordinal = 1 << 0;

    public void applyStyles(int style) {

    }
}
