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
import com.alibaba.fastjson.JSONObject;
import com.baidu.fsg.uid.service.UidGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
        if (userMap != null) {
            Long userId = (Long) userMap.get("userId");
            comment.setReviewerId(userId);
        } else {
            return new ShResultDTO<>(400, "发送失败，检查是否登录，评论不能为空或全是空格或字数超限");
        }
        comment.setCreatedTime(System.currentTimeMillis());
        boolean flag = commentService.addOneComment(comment);
        //向被回复者发送通知
        if (flag) {
            //消息的收信人
            Long recipientId = null;
            if (comment.getCitedCommentId() != null) {
                long commentId = comment.getCitedCommentId();
                recipientId = commentService.selectReviewerIdByCommentId(commentId);
            } else {
                Long invitationId = comment.getTargetId();
                recipientId = invitationService.selectAuthorIdByInvitationId(invitationId);
            }
            //尝试创建消息历史记录
            chatHistoryService.addOneSystemChatHistory(recipientId);
            //消息id
            Long messageId = uidGenerateService.generateUid();
            //消息内容 存取评论的json字符串
            String messageContent = JSONObject.toJSONString(comment);
            //消息入库
            boolean isNotifySuccess = false;
            if (recipientId != null) {
                //注意发信人id是0 看板娘
                isNotifySuccess = chatMessageService.addChatMessage(messageId, messageContent, 0L, recipientId, true);
            }
            return new ShResultDTO<>(200, "评论成功，是否成功通知收信人：" + isNotifySuccess);
        } else {
            return new ShResultDTO<>(400, "发送失败，检查是否登录，评论不能为空或全是空格或字数超限");
        }
    }

    //返回一个评论的关联内容
    @GetMapping(value = "/shApi/retOneComment")
    @ResponseBody
    public ShResultDTO<String,Object> retOneComment(Long commentId){
        CommentDTO commentDTO = commentService.selectCommentById(commentId);
        Map<String,Object> data = new HashMap<>();
        data.put("comment",commentDTO);
        return new ShResultDTO<>(200, "返回评论的详细信息",data,null);
    }

    //测试，返回0号帖子的评论
    @RequestMapping(value = "/test/c")
    @ResponseBody
    public List<CommentDTO> testComments() {
        return commentService.findAllComment(0);
    }


}
