package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.CommentDTO;
import cn.shirtiny.community.SHcommunity.Model.Comment;

import java.util.List;

public interface ICommentService {

    boolean addOneComment(Comment comment);

    List<CommentDTO> findAllComment(long invitationId);

    //通过commentId查找评论者id
    Long selectReviewerIdByCommentId(long commentId);

    //通过id查出一条评论的关联内容
    CommentDTO selectCommentById(long commentId);

}
