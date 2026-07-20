package TZJanosi.probaFeladat.model;

public enum CarCondition {

    POOR(1),NORMAL(2),GOOD(3),EXCELLENT(4);
    private final int value;

    CarCondition(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
