package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.CommentDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Exception.NoLoginException;
import cn.shirtiny.community.SHcommunity.Model.Comment;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.ICommentService;
import cn.shirtiny.community.SHcommunity.Service.IchatHistoryService;
import cn.shirtiny.community.SHcommunity.Service.IchatMessageService;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import cn.shirtiny.community.SHcommunity.Service.ServiceImpl.InvitationService;
import com.baidu.fsg.uid.service.UidGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private ICommentService commentService;
    @Autowired
    private IjwtService jwtService;
    @Autowired
    private IchatMessageService chatMessageService;
    @Autowired
    private IchatHistoryService chatHistoryService;
    @Autowired
    private UidGenerateService uidGenerateService;
    @Autowired
    private InvitationService invitationService;

    @RequestMapping(value = "/shPri/sendComment", method = RequestMethod.POST)
    @ResponseBody
    public ShResultDTO sendComment(
            @RequestBody Comment comment
            , HttpServletRequest request) {
        Map<String, Object> calims = jwtService.parseJwtByCookie(request);
        LinkedHashMap userMap = null;
        if (calims != null) {
            userMap = (LinkedHashMap) calims.get("user");
        }
        Long userId = null;
        if (userMap != null) {
            userId = (Long) userMap.get("userId");
            comment.setReviewerId(userId);
        }else {
            return new ShResultDTO<>(400, "发送失败，检查是否登录，评论不能为空或全是空格或字数超限");
        }
        comment.setCreatedTime(System.currentTimeMillis());
        boolean flag = commentService.addOneComment(comment);
        //向被回复者发送通知
        if (flag) {
            //消息的收信人
            Long recipientId = null;
            if (comment.getCitedCommentId() != null) {
                recipientId = comment.getCitedCommentId();
            } else {
                Long invitationId = comment.getTargetId();
                recipientId = invitationService.selectAuthorIdByInvitationId(invitationId);
            }
            //尝试创建消息历史记录
            chatHistoryService.addOneSystemChatHistory(recipientId);
            //消息id
            Long messageId = uidGenerateService.generateUid();
            //消息内容
            String messageContent = comment.getCommentContent();
            if (messageContent.trim().length() > 30) {
                messageContent = messageContent.substring(0, 31)+"...";
            }
            chatMessageService.addChatMessage(messageId, messageContent, userId, recipientId, true);
            return new ShResultDTO<>(200, "评论成功");
        } else {
            return new ShResultDTO<>(400, "发送失败，检查是否登录，评论不能为空或全是空格或字数超限");
        }
    }


    //测试，返回0号帖子的评论
    @RequestMapping(value = "/test/c")
    @ResponseBody
    public List<CommentDTO> testComments() {
        return commentService.findAllComment(0);
    }
}
