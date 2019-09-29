package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Exception.InvitationNotFoundException;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class pageController {

    @Autowired
    private IinvitationService invitationService;


    @GetMapping("/loginPage")//跳转到登录界面
    public String toSignUpPage() {
        return "loginPage";
    }

    //    @GetMapping("/")
    public String toIndex(Model model) {//前往首页，并展示全部帖子
        //分页的在下面
        List<Invitation> invitations = invitationService.selectAll();
        model.addAttribute("invitations", invitations);
        return "index";
    }


    @GetMapping("/")//首页，分页展示首页的帖子，包含对应用户信息
    public String toIndexByPage(@RequestParam(value = "curPage", defaultValue = "1") long curPage, Model model) {//前往首页并分页
        Page<InvitationDTO> page = new Page<>();
        page.setCurrent(curPage);
        IPage<InvitationDTO> pageInfo = invitationService.selectDtoBypage(page);
        model.addAttribute("pageInfo", pageInfo);
        //总页数
        long pages = pageInfo.getPages();
        //若当前页大于总页数
        if (curPage > pages) {
            return "redirect:/?curPage=" + pages;
        }
        //若已到最后一页
        if (curPage == pages) {
            model.addAttribute("pageError", "已经到最后一页喽~");
        }
        //控制的打印前端标签
        long[] pageNumArray = new long[7];
        long pageNum = curPage - 3;

        for (int i = 0; i < curPage + 4; i++) {
            if (i >= pageNumArray.length) {
                break;
            }
            pageNumArray[i] = pageNum;
            pageNum++;
        }
        model.addAttribute("pageNumArray", pageNumArray);

        return "index";
    }


    @GetMapping("/page")//前往page页面
    public String toPage(String username, Model model) {
        model.addAttribute("username", username);
        return "page";
    }

    @GetMapping("/newInvitation")//前往创建帖子的页面
    public String toNewInvitation() {

        return "newInvitation";
    }


    @GetMapping("/invitationDetail/{invitationId}")//前往帖子详情页面，传递一个帖子id
    public String toInvitationDetail(@PathVariable("invitationId") long invitationId, Model model) {
        InvitationDTO invitationDetail = invitationService.selectOneInvitationDetailsById(invitationId);
        if (invitationDetail==null){
            throw new InvitationNotFoundException("找不到该帖，请确认是否存在",404);
        }
        model.addAttribute("invitationDetail", invitationDetail);
        return "invitationDetail";
    }


    //测试
    @RequestMapping(value = "signup")
    public String signup(Model model) {

        model.addAttribute(new User());

        return "index :: signupForm";

    }
}
