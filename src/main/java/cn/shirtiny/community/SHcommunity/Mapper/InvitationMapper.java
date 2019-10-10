package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InvitationMapper extends BaseMapper<Invitation> {

    //分页关联查询user和invitation
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID")
    IPage<InvitationDTO> selectDtoByPage(Page<InvitationDTO> page);


    //查询帖子，不查它的评论和回复
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID  where invitation_id=#{id}")
    InvitationDTO selectOneDtoById(long id);

    //查询帖子及其评论
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID  where invitation_id=#{id}")
    @Results({
            @Result(property = "invitationId",column = "invitation_id",id = true)
            ,@Result(property = "comments",column = "invitation_id",javaType = List.class,many = @Many(select = "cn.shirtiny.community.SHcommunity.Mapper.CommentMapper.selectAllByInvitatonId"))
    })
    InvitationDTO selectOneDtoAndCs(long id);

    //使invitation的replyNum回复数自增1
    @Select("update INVITATION set REPLY_NUM=REPLY_NUM+1 where INVITATION_ID=#{invitationId}")
    void incrInvitationReplyNum(@Param("invitationId") long id);

}
