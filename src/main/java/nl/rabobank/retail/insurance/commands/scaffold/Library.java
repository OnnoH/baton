package nl.rabobank.retail.insurance.commands.scaffold;

public enum Library {
    ZEEBE("zeebe"),
    KAFKA("kafka"),
    WIREMOCK("wiremock"),
    NONE("none");

    private final String name;

    Library(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Library getLibrary(String name) {
        for (Library lib : Library.values()) {
            if (lib.getName().equals(name)) {
                return lib;
            }
        }
        return null;
    }
    public static Boolean isValid(String name) {
        return getLibrary(name) != null;
    }

}
