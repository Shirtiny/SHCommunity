package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class ShResultDTO<T> {
    //状态码
    private Integer code;
    //信息
    private String message;
    //数据
    private T data;
    //错误
    String error;

    public ShResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ShResultDTO(String message, String error) {
        this.message = message;
        this.error = error;
    }
}
