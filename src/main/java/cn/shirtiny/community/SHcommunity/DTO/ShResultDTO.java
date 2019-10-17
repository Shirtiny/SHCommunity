package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ShResultDTO<K,V> {
    //状态码
    private Integer code;
    //信息
    private String message;
    //数据
    private Map<K,V> data;
    //错误
    String error;

    public ShResultDTO() {
    }

    public ShResultDTO(Integer code, String message, Map<K, V> data, String error) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = error;
    }


    public ShResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ShResultDTO(String message, String error) {
        this.message = message;
        this.error = error;
    }
}
