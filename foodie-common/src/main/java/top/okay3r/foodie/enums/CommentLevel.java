package top.okay3r.foodie.enums;

public enum CommentLevel {
    good(1, "好评"),
    normal(2, "中评"),
    bad(3, "差评");

    public final int type;

    public final String value;

    CommentLevel(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
