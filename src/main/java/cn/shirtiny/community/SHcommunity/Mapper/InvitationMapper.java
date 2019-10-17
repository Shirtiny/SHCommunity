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

    //分页关联查询user和invitation，按照帖子更新时间戳倒序，有新回复的时候会更新帖子, ... order by  字段  asc/desc
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID order by i.GMT_MODIFIED desc")
    IPage<InvitationDTO> selectDtoByPageDesc(Page<InvitationDTO> page);

    //更新帖子的更新（最后一次回复）时间
    @Select("update INVITATION set GMT_MODIFIED=#{GMT_MODIFIED} where INVITATION_ID=#{invitationId}")
    void updateInviModiDate(@Param("invitationId") long id,@Param("GMT_MODIFIED") long gmtModified);//帖子浏览量+1


    //查询帖子，不查它的评论和回复
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID  where invitation_id=#{id}")
    InvitationDTO selectOneDtoById(long id);

    //查询帖子及其评论，默认id升序
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID  where invitation_id=#{id}")
    @Results({
            @Result(property = "invitationId",column = "invitation_id",id = true)
            ,@Result(property = "comments",column = "invitation_id",javaType = List.class,many = @Many(select = "cn.shirtiny.community.SHcommunity.Mapper.CommentMapper.selectAllByInvitatonId"))
    })
    InvitationDTO selectOneDtoAndCs(long id);


    //使帖子浏览量+1
    @Select("update INVITATION set VIEWS=VIEWS+1 where INVITATION_ID=#{invitationId}")
    void incrViews(@Param("invitationId") long id);//帖子浏览量+1

    //使invitation的replyNum回复数自增1
    @Select("update INVITATION set REPLY_NUM=REPLY_NUM+1 where INVITATION_ID=#{invitationId}")
    void incrInvitationReplyNum(@Param("invitationId") long id);

}
