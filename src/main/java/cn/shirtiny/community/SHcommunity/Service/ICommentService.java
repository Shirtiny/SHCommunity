package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.CommentDTO;
import cn.shirtiny.community.SHcommunity.Model.Comment;

import java.util.List;

public interface ICommentService {

    boolean addOneComment(Comment comment);

    List<CommentDTO> findAllComment(long invitationId);

}
