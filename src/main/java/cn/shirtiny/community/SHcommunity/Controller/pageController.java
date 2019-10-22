package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Exception.NotFoundException;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static cn.shirtiny.community.SHcommunity.Enum.ShErrorCode.NotFound_Error;

@Controller
public class pageController {

    @Autowired
    private IinvitationService invitationService;





    @GetMapping("/loginPage")//跳转到登录界面
    public String toSignUpPage(@RequestParam(value = "shRedirect",defaultValue = "/") String shRedirect, HttpServletResponse response) {
        //设置回调cookie
        Cookie redirectCookie=new Cookie("shRedirectCookie",shRedirect);
        //位于本站根目录下
        redirectCookie.setPath("/");
        response.addCookie(redirectCookie);
        return "loginPage";
    }

    @GetMapping("/")//首页
    public String toIndex() {//前往首页
        return "index";
    }

    @GetMapping("/shPub/sectionDetail")//前往版块详情页，由前端sessionStorage传递版块id
    public String toSectionDetail() {
        return "sectionDetail";
    }

    //前往版块详情首页并分页 _旧
    @GetMapping("/index_old")//首页，分页展示首页的帖子，包含对应用户信息
    public String toIndexByPage(@RequestParam(value = "curPage", defaultValue = "1") long curPage,
                                @RequestParam(value = "orderBy", defaultValue = "1",required = false) Integer orderBy
            ,Model model,@RequestParam(value = "sectionId",defaultValue = "1",required = false) Long sectionId) {
        Page<InvitationDTO> page = new Page<>();
        page.setCurrent(curPage);
        IPage<InvitationDTO> pageInfo=null;
        if (orderBy==null||orderBy==0){
            //无或0按默认顺序，id升序
            pageInfo = invitationService.selectDtoBypage(page,sectionId);
            System.out.println("这是旧版本，已不适用");
        }else {
            //其他值按更新时间倒序排列（Controller默认）
            pageInfo = invitationService.selectDtoBypageDesc(page,sectionId);
            System.out.println("这是旧版本，已不适用");
        }

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

        return "sectionDetail_old";
    }


    @GetMapping("/page")//前往page页面
    public String toPage(String username, Model model) {
        model.addAttribute("username", username);
        return "page";
    }

    @GetMapping("/newInvitation")//前往创建帖子的页面
    public String toNewInvitation() {

        return "newInvitationMd";
    }


    @GetMapping("/shPub/invitationDetail/{invitationId}")//前往帖子详情页面，传递一个帖子id
    public String toInvitationDetail(@PathVariable("invitationId") long invitationId, Model model) {

        InvitationDTO invitationDetail = invitationService.selectOneDtoAndCs(invitationId);
        if (invitationDetail==null){
            throw new NotFoundException(NotFound_Error);
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

    @RequestMapping(value = "/test")
    public String toTestPage(){
        return "test";
    }
}
