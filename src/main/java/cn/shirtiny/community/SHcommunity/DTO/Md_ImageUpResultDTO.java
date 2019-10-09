package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class Md_ImageUpResultDTO {
    //表示是否上传成功
    int success;
    //提示
    String message;
    //图片地址
    String url;

    public Md_ImageUpResultDTO(int success, String message, String url) {
        this.success = success;
        this.message = message;
        this.url = url;
    }
}
