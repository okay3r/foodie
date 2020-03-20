package top.okay3r.foodie.enums;

public enum YesOrNo {

    NO(0, "否"),
    YES(1, "是");

    public final int type;

    public final String value;

    YesOrNo(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
