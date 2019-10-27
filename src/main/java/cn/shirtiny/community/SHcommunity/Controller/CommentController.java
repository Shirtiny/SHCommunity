package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.CommentDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.Comment;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/shApi/sendComment", method = RequestMethod.POST)
    @ResponseBody
    public ShResultDTO sendComment(
            @RequestBody Comment comment
            , HttpServletRequest request) {
        //从session中取用户id，以后改
        Long userId = ((UserDTO) request.getSession().getAttribute("user")).getUserId();
        comment.setReviewerId(userId);
        comment.setCreatedTime(System.currentTimeMillis());
        boolean flag = commentService.addOneComment(comment);

        return flag ? new ShResultDTO<>(200,"评论成功") : new ShResultDTO<>(400,"发送失败，评论不能为空或全是空格或字数超限");

    }


    //测试，返回0号帖子的评论
    @RequestMapping(value="/test/c")
    @ResponseBody
    public List<CommentDTO> testComments() {
        return commentService.findAllComment(0);
    }
}
