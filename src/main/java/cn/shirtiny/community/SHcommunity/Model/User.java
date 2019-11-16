package cn.shirtiny.community.SHcommunity.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Property;

@Data
@TableName("user")
public class User {//论坛用户

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;//用户唯一主键，自增长
    private String nickName;//昵称
    private String userName;//用户名
    private String passWord;//密码
    private String email;//邮箱
    private String avatarImage;//头像
    private String description;//签名、描述、自我介绍
    private String githubId;//github的用户编号
    private long gmtCreate;//创建账户时的时间戳
    private long  gmtModified;//修改用户时的时间戳
    private String token;//cookie里的登录令牌

}
