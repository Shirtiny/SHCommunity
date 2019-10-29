package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.CommentDTO;
import cn.shirtiny.community.SHcommunity.Mapper.CommentMapper;
import cn.shirtiny.community.SHcommunity.Mapper.InvitationMapper;
import cn.shirtiny.community.SHcommunity.Model.Comment;
import cn.shirtiny.community.SHcommunity.Service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private InvitationMapper invitationMapper;

    //评论内容长度限制
    @Value("${COMMENT_CONTENT_MAX_LENGTH}")
    private Integer COMMENT_CONTENT_MAX_LENGTH;

    //增加一条评论
    @Override
    public boolean addOneComment(Comment comment) {
        //校验评论是否为null或是否全为空格
        if (comment == null || comment.getCommentContent().trim().isEmpty()) {
            return false;
        }
        //校验评论内容长度是否超限
        if (comment.getCommentContent().length() > COMMENT_CONTENT_MAX_LENGTH) {
            return false;
        }
        boolean isSuccess = false;
        try {
            //向数据库插入评论
            commentMapper.insert(comment);
            isSuccess = true;
        } catch (Exception e) {
            return false;
        } finally {
            if (isSuccess) {
                //使对应帖子的回复数加1
                invitationMapper.incrInvitationReplyNum(comment.getTargetId());
                //更新帖子的回复时间
                invitationMapper.updateInviModiDate(comment.getTargetId(), System.currentTimeMillis());
            }
            return isSuccess;
        }

    }

    @Override
    public List<CommentDTO> findAllComment(long invitationId) {
        return commentMapper.selectAllByInvitatonId(invitationId);
    }


}
