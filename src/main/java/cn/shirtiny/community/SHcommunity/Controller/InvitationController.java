package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import cn.shirtiny.community.SHcommunity.Service.ServiceImpl.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InvitationController {


    @Autowired
    private IinvitationService invitationService;


    @PostMapping(value = "/createInvitation")
    public String createInvitation(Invitation invitation, Model model, HttpServletRequest request){

        Long userId=((User)request.getSession().getAttribute("user")).getId();
        invitation.setAuthorId(userId);
        boolean flag = invitationService.addInvitation(invitation);
        if(flag){
            //前往帖子的最后一页zaaz
            return "redirect:/?curPage=999999";
        }else {
            model.addAttribute("error","标题或内容不能为空，并且字数不能大于20和400");
            model.addAttribute("invitation",invitation);
            return "newInvitation";
        }

    }

    //测试，返回1号 帖子、帖子的评论以及评论人
    @RequestMapping("/test/i")
    @ResponseBody
    public InvitationDTO testI(){
        return invitationService.selectOneDtoAndCs(1);
    }




}
