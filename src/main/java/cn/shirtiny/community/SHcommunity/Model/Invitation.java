package cn.shirtiny.community.SHcommunity.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("invitation")
public class Invitation {//论坛帖子

    @TableId(value = "invitation_id",type = IdType.AUTO)
    private Long id;//主键id，因为数据库内是自增的，用Integer类型，默认为空
    private String title;//标题
    private String content;//内容

    private long replyNum;//回复数
    //浏览量views
    private long views;
    private long gmtCreated;//创建时间
    private long gmtModified;//更新时间
    private Long authorId;//发帖者的id
    private Long sectionId;//所属版块的id


}
