package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.Model.Invitation;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import cn.shirtiny.community.SHcommunity.Service.ServiceImpl.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InvitationController {


    @Autowired
    private IinvitationService invitationService;


    @PostMapping(value = "/createInvitation")
    public String createInvitation(Invitation invitation, Model model){
        boolean flag = invitationService.addInvitation(invitation);
        if(flag){
            //前往帖子的最后一页
            return "redirect:/?curPage=999999";
        }else {
            model.addAttribute("error","标题或内容不能为空");
            return "newInvitation";
        }

    }





}
