package staffcontrol.constants;

public enum LanguageLevel {
    A1,
    A2,
    B1,
    B2,
    C1,
    C2;

    public static LanguageLevel fromKey(String value) {
        if (value != null) {
            for (LanguageLevel englishLevel : LanguageLevel.values()) {
                if (value.equalsIgnoreCase(englishLevel.name())) {
                    return englishLevel;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }
}
