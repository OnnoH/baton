package nl.rabobank.retail.insurance.commands.scaffold;

public enum BuildTool {
    MAVEN("maven"),
    GRADLE("gradle"),
    NONE("none");

    private final String name;

    BuildTool(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BuildTool getBuildTool(String name) {
        for (BuildTool bt : BuildTool.values()) {
            if (bt.getName().equals(name)) {
                return bt;
            }
        }
        return null;
    }

    public static Boolean isValid(String name) {
        return getBuildTool(name) != null;
    }
}
