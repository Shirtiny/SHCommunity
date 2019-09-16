package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.Model.Invitation;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class pageController {

    @Autowired
    private IinvitationService invitationService;

//    @GetMapping("/")
    public String toIndex(Model model){//前往首页，并展示全部帖子
        //分页的在下面
        List<Invitation> invitations = invitationService.selectAll();
        model.addAttribute("invitations",invitations);
        return "index";
    }

    //分页展示首页的帖子
    @GetMapping("/")
    public String toIndexByPage(@RequestParam(value = "curPage",defaultValue = "1") long curPage ,Model model){//前往首页并分页
        Page<Invitation> page=new Page<>();
        page.setCurrent(curPage);
        IPage<Invitation> pageInfo = invitationService.selectBypage(page);
        model.addAttribute("pageInfo",pageInfo);
        //总页数
        long pages = pageInfo.getPages();
        //若当前页大于总页数
        if (curPage>pages){
            return "redirect:/?curPage="+pages;
        }
        //若已到最后一页
        if (curPage==pages){
            model.addAttribute("pageError","已经到最后一页喽~，后面的页数是装饰品O(∩_∩)O");
        }
        //控制的打印前端标签
        long[] pageNumArray=new long[7];
        long pageNum=curPage-3;

        for (int i=0;i<curPage+4;i++){
            if (i>=pageNumArray.length){
                break;
            }
            pageNumArray[i]=pageNum;
            pageNum++;
        }
        model.addAttribute("pageNumArray",pageNumArray);

        return "index";
    }

    @GetMapping("/page")
    public String toPage(String username, Model model){//前往page页面
        model.addAttribute("username",username);
        return "page";
    }

    @GetMapping("/newInvitation")
    public String toNewInvitation(){//前往创建帖子的页面

        return "newInvitation";
    }
}
