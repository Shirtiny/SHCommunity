package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select * from comment c join user u on c.reviewer_id=u.user_id where target_id=#{invitationId}")
    @Results({
            @Result(property = "userDTO",column ="reviewer_id",javaType = UserDTO.class,one = @One(select = "cn.shirtiny.community.SHcommunity.Mapper.UserMapper.selectDTObyid"))
    })
    List<Comment> selectAllByInvitatonId(long invitationId);

}
