package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Mapper.CommentMapper;
import cn.shirtiny.community.SHcommunity.Mapper.InvitationMapper;
import cn.shirtiny.community.SHcommunity.Model.Comment;
import cn.shirtiny.community.SHcommunity.Service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //增加一条评论
    @Override
    public boolean addOneComment(Comment comment) {
        //校验评论是否为null或是否全为空格
        if (comment==null  || comment.getCommentContent().trim().isEmpty()){
            return false;
        }
        //使对应帖子的回复数加1
        invitationMapper.incrInvitationReplyNum(comment.getTargetId());
        try {
            //向数据库插入评论
            commentMapper.insert(comment);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Comment> findAllComment(long invitationId) {
        return commentMapper.selectAllByInvitatonId(invitationId);
    }


}
