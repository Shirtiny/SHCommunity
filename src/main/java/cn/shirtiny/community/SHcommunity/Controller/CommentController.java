package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.CommentDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Exception.NoLoginException;
import cn.shirtiny.community.SHcommunity.Model.Comment;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.ICommentService;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
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

    @RequestMapping(value = "/shPri/sendComment", method = RequestMethod.POST)
    @ResponseBody
    public ShResultDTO sendComment(
            @RequestBody Comment comment
            , HttpServletRequest request) {
        Map<String, Object> calims = jwtService.parseJwtByCookie(request);
        LinkedHashMap userMap=null;
        if (calims!=null){
           userMap=(LinkedHashMap)calims.get("user");
        }
        if (userMap!=null){
            comment.setReviewerId((Long) userMap.get("userId"));
        }
        comment.setCreatedTime(System.currentTimeMillis());
        boolean flag = commentService.addOneComment(comment);

        return flag ? new ShResultDTO<>(200,"评论成功") : new ShResultDTO<>(400,"发送失败，检查是否登录，评论不能为空或全是空格或字数超限");

    }


    //测试，返回0号帖子的评论
    @RequestMapping(value="/test/c")
    @ResponseBody
    public List<CommentDTO> testComments() {
        return commentService.findAllComment(0);
    }
}
