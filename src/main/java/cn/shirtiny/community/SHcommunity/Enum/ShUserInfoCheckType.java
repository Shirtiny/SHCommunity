package cn.shirtiny.community.SHcommunity.Enum;

public enum  ShUserInfoCheckType {
    UserName(1),
    PassWord(2),
    Email(3),
    UserName_OR_Email(13),
    ;
    private final int code;

    ShUserInfoCheckType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
