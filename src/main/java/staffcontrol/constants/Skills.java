package staffcontrol.constants;

public enum Skills {
    SKILLS_FIRST,
    SKILLS_SECOND,
    SKILLS_THIRD;


    public static Skills fromKey(String value) {
        if (value != null) {
            for (Skills skill : Skills.values()) {
                if (value.equalsIgnoreCase(skill.name())) {
                    return skill;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }
}