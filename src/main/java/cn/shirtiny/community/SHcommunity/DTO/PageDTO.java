package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class PageDTO {//存储页面信息

    //每页标题
    private String title;
    //每页对应路径
    private String path;

    public PageDTO() {
    }

    public PageDTO(String title, String path) {
        this.title = title;
        this.path = path;
    }
}
