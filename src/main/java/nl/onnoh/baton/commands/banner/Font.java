package nl.onnoh.baton.commands.banner;

public enum Font {
    STANDARD("standard"),
    BIG("big"),
    BANNER("banner"),
    BLOCK("block"),
    BUBBLE("bubble"),
    DIGITAL("digital"),
    IVR("ivr"),
    LEAN("lean"),
    MINI("mini"),
    SCRIPT("script"),
    SHADOW("shadow"),
    SLANT("slant"),
    SMALL("small"),
    SMSLANT("smslant"),
    SMSHADOW("smshadow"),
    SMSLANT_RELIEF("smslantrelief"),
    SMSHADOW_RELIEF("smshadowrelief"),
    TERM("term");

    private final String font;

    Font(String font) {
        this.font = font;
    }

    public String getFont() {
        return font;
    }

    public static boolean isValid(String font) {
        for (Font f : Font.values()) {
            if (f.font.equals(font)) {
                return true;
            }
        }
        return false;
    }
}
