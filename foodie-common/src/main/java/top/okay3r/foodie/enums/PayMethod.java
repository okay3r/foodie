package top.okay3r.foodie.enums;

public enum PayMethod {
    WEIXIN(1, "微信"),
    ALIPAY(2, "支付宝");

    public final int type;

    public final String value;

    PayMethod(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
