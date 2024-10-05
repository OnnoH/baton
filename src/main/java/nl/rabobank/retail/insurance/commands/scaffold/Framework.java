package nl.rabobank.retail.insurance.commands.scaffold;

public enum Framework {
    SPRING_BOOT("spring-boot"),
    QUARKUS("quarkus"),
    MICRONAUT("micronaut"),
    NONE("none");

    private final String name;

    Framework(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Framework getFramework(String name) {
        for (Framework fw : Framework.values()) {
            if (fw.getName().equals(name)) {
                return fw;
            }
        }
        return null;
    }
    public static Boolean isValid(String name) {
        return getFramework(name) != null;
    }

}
