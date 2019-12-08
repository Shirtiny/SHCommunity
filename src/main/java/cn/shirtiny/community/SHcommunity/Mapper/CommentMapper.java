package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.CommentDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    //查询出一个帖子的所有评论
    @Select("select * from comment c join user u on c.reviewer_id=u.user_id where target_id=#{invitationId}")
    @Results({
            @Result(property = "reviewer",column ="reviewer_id",javaType = UserDTO.class,one = @One(select = "cn.shirtiny.community.SHcommunity.Mapper.UserMapper.selectDTOById")),
            @Result(property = "reviewerId",column ="reviewer_id"),
            @Result(property = "citedCommentId",column ="cited_comment_id"),
            @Result(property = "citedComment",column ="cited_comment_id",javaType = CommentDTO.class,one = @One(select = "cn.shirtiny.community.SHcommunity.Mapper.CommentMapper.selectOneByCitedCommentId"))
    })
    List<CommentDTO> selectAllByInvitatonId(long invitationId);

    //查询一条评论所引用的评论，查出除了cited_comment_content以外的所有字段 ，动态sql或正则表达式？select * where field regexp '[abd-xz].*'
    @Select("select * from comment where comment_id=#{citedCommentId}")
    @Results({
            @Result(property = "reviewer",column ="reviewer_id",javaType = UserDTO.class,one = @One(select = "cn.shirtiny.community.SHcommunity.Mapper.UserMapper.selectDTOById")),
            @Result(property = "reviewerId",column ="reviewer_id"),
            @Result(property = "citedCommentId",column ="cited_comment_id"),
    })
    CommentDTO selectOneByCitedCommentId(@Param("citedCommentId") Long citedCommentId);

    //通过commentId查找评论者id
    @Select("select reviewer_id from comment where comment_id=#{commentId}")
    Long selectReviewerIdByCid(@Param("commentId") long commentId);

    //根据id查出一条评论关联的内容 评论者、被评论的评论内容
    @Select("select * from comment where comment_id=#{commentId}")
    @Results({
            @Result(property = "reviewer",column ="reviewer_id",javaType = UserDTO.class,one = @One(select = "cn.shirtiny.community.SHcommunity.Mapper.UserMapper.selectDTOById")),
            @Result(property = "reviewerId",column ="reviewer_id"),
            @Result(property = "citedComment",column ="cited_comment_id",javaType = CommentDTO.class,one = @One(select = "cn.shirtiny.community.SHcommunity.Mapper.CommentMapper.selectOneByCitedCommentId")),
    })
    CommentDTO selectOneByCommentId(@Param("commentId") Long commentId);
}
