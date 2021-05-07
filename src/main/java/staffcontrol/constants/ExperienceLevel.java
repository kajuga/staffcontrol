package staffcontrol.constants;

public enum ExperienceLevel {
    J1,J2, M1,M2,M3,S1,S2;

    public static ExperienceLevel fromKey(String value) {
        if (value != null) {
            for (ExperienceLevel level : ExperienceLevel.values()) {
                if (value.equalsIgnoreCase(level.name())) {
                    return level;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }
}
