package cn.shirtiny.community.SHcommunity.Model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("banner")
public class Banner {
    //主键
    @TableId(value = "banner_id",type = IdType.AUTO)
    private Long BannerId;

    //名称
    @TableField(value = "banner_name",insertStrategy = FieldStrategy.NOT_EMPTY)
    private String bannerName;

    //地址
    @TableField(value = "banner_url",insertStrategy = FieldStrategy.NOT_EMPTY)
    private String bannerUrl;

    //形状
    @TableField(value = "banner_shape",insertStrategy = FieldStrategy.NOT_EMPTY)
    private String bannerShape;
}
