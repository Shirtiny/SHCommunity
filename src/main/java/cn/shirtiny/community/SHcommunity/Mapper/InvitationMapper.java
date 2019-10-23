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

    //分页关联查询某个版块的user和invitation列表，按照帖子id升序
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID where i.section_id=#{sectionId}")
    IPage<InvitationDTO> selectDtoByPage(Page<InvitationDTO> page,@Param("sectionId") long sectionId);

    //主要
    //分页关联查询某个版块的user和invitation列表，默认按照帖子更新时间戳倒序，有新回复的时候会更新帖子, ... order by  字段  asc/desc
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID where i.section_id=#{sectionId} order by i.GMT_MODIFIED desc")
    IPage<InvitationDTO> selectDtoByPageDesc(Page<InvitationDTO> page,@Param("sectionId") long sectionId);

    //更新帖子的更新（最后一次回复）时间
    @Update("update INVITATION set GMT_MODIFIED=#{GMT_MODIFIED} where INVITATION_ID=#{invitationId}")
    void updateInviModiDate(@Param("invitationId") long id,@Param("GMT_MODIFIED") long gmtModified);//帖子浏览量+1


    //查询帖子，不查它的评论和回复
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID  where invitation_id=#{id}")
    InvitationDTO selectOneDtoById(long id);

    //主要
    //查询帖子详情及其评论
    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID  where invitation_id=#{id}")
    @Results({
            @Result(property = "invitationId",column = "invitation_id",id = true)
            ,@Result(property = "comments",column = "invitation_id",javaType = List.class,many = @Many(select = "cn.shirtiny.community.SHcommunity.Mapper.CommentMapper.selectAllByInvitatonId"))
    })
    InvitationDTO selectOneDtoAndCs(long id);


    //使帖子浏览量+1
    @Update("update INVITATION set VIEWS=VIEWS+1 where INVITATION_ID=#{invitationId}")
    void incrViews(@Param("invitationId") long id);//帖子浏览量+1

    //使invitation的replyNum回复数自增1
    @Update("update INVITATION set REPLY_NUM=REPLY_NUM+1 where INVITATION_ID=#{invitationId}")
    void incrInvitationReplyNum(@Param("invitationId") long id);


    //查询出指定版块的一个最新创建的帖子（该帖应有最大的帖子id），包含创建者、标题等
    @Select("select * from USER u join INVITATION i on u.user_id = i.AUTHOR_ID " +
            "where i.INVITATION_ID = (select max(INVITATION_ID) from INVITATION where SECTION_ID = #{sectionId})")
    InvitationDTO selectOneLatestDto(@Param("sectionId") long sectionId);
}
