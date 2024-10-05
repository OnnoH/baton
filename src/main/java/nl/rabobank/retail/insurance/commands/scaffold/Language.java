package nl.rabobank.retail.insurance.commands.scaffold;

public enum Language {

    JAVA("java"),
    KOTLIN("kotlin"),
    GROOVY("groovy");

    private final String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Language getLanguage(String name) {
        for (Language lang : Language.values()) {
            if (lang.getName().equals(name)) {
                return lang;
            }
        }
        return null;
    }

    public static Boolean isValid(String name) {
        return getLanguage(name) != null;
    }

}
