package cn.shirtiny.community.SHcommunity.DTO;

import cn.shirtiny.community.SHcommunity.Exception.ShException;
import lombok.Data;

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
    ShException error;

    public ShResultDTO() {
    }

    public ShResultDTO(Integer code, String message, Map<K, V> data, ShException error) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = error;
    }


    public ShResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ShResultDTO(String message, ShException error) {
        this.message = message;
        this.error = error;
    }
}
