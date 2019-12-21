package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.DTO.PageDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Exception.NotFoundException;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import cn.shirtiny.community.SHcommunity.Service.IjsHelperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import static cn.shirtiny.community.SHcommunity.Enum.ShErrorCode.NotFound_Error;

@Controller
public class pageController {

    @Autowired
    private IinvitationService invitationService;
    @Autowired
    private IjsHelperService jsHelperService;
    @Autowired
    private IcookieService cookieService;


    /**面包屑导航 辅助
     * @param curPage 要存储的pageDTO对象 接收json格式 ，如：
     *  {
     * 	"title":"标题1",
     * 	"path":"路径1"
     *  }
     * @param request 用于读取session
     * @return 返回处理后的数组
     */
    @PostMapping("/shApi/breadcrumbHelper")
    @ResponseBody
    public ShResultDTO<String, Object> getBreadcrumbArray(
            @RequestBody PageDTO curPage, HttpServletRequest request) {


        //读取session中的数组
        PageDTO[] breadcrumb = (PageDTO[]) request.getSession().getAttribute("breadcrumb");

        //处理和截取数组
        breadcrumb = jsHelperService.cutBreadcrumbArray(breadcrumb, curPage);

        //把处理后的数组，存入session
        request.getSession().setAttribute("breadcrumb", breadcrumb);

        //封装结果
        Map<String, Object> map = new HashMap<>();
        map.put("breadcrumb", breadcrumb);

        return new ShResultDTO<>(200, "面包屑数组已处理", map, null);
    }

    @GetMapping("/signup")//跳转到注册界面
    public String toSignUpPage() {

        return "signUp";
    }


    @GetMapping("/login")//跳转到登录界面
    public String toSignUpPage(@RequestParam(value = "shRedirect", defaultValue = "/") String shRedirect, HttpServletResponse response) {
        //设置回调cookie
        cookieService.addOneCookie(response,"shRedirectCookie",shRedirect,"/",-1);
        return "login";
    }

    @GetMapping("/")//首页
    public String toIndex() {//前往首页
        return "index";
    }

    @GetMapping("/shPub/sectionDetail")//前往版块详情页，由前端sessionStorage传递版块id
    public String toSectionDetail() {
        return "sectionDetail";
    }

    //前往版块详情首页并分页 _旧，已不用
    @GetMapping("/index_old")//首页，分页展示首页的帖子，包含对应用户信息
    public String toIndexByPage(@RequestParam(value = "curPage", defaultValue = "1") long curPage,
                                @RequestParam(value = "orderBy", defaultValue = "1", required = false) Integer orderBy
            , Model model, @RequestParam(value = "sectionId", defaultValue = "1", required = false) Long sectionId) {
        Page<InvitationDTO> page = new Page<>();
        page.setCurrent(curPage);
        IPage<InvitationDTO> pageInfo = null;
        if (orderBy == null || orderBy == 0) {
            //无或0按默认顺序，id升序
            pageInfo = invitationService.selectDtoBypage(page, sectionId);
            System.out.println("这是旧版本，已不适用");
        } else {
            //其他值按更新时间倒序排列（Controller默认）
            pageInfo = invitationService.selectDtoBypageDesc(page, sectionId);
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

    @GetMapping("/shPub/newInvitation")//前往创建帖子的页面
    public String toNewInvitation() {

        return "newInvitationMd";
    }


    //前往帖子详情页面
    @GetMapping("/shPub/invitationDetail/{invitationId}")
    public String toInvitationDetail(@PathVariable("invitationId") long invitationId) {

        return "invitationDetail";
    }


    @GetMapping("/shPub/invitationDetail_Old/{invitationId}")//前往帖子详情页面_旧，传递一个帖子id
    public String toInvitationDetail_Old(@PathVariable("invitationId") long invitationId, Model model) {

        InvitationDTO invitationDetail = invitationService.selectOneDtoAndCs(invitationId);
        if (invitationDetail == null) {
            throw new NotFoundException("未找到要请求的帖子");
        }
        model.addAttribute("invitationDetail", invitationDetail);
        return "invitationDetail_Old";
    }

    //前往消息中心
    @RequestMapping(value = "/shPub/messageCenter")
    public String toMessageCenterPage() {
        return "messageCenter";
    }

    @RequestMapping(value = "/test")
    public String toTestPage() {
        return "test";
    }

    @RequestMapping(value = "/test/ws")
    public String toTestWebSocket() {
        return "webSocket";
    }

    //错误测试
    @GetMapping(value = "/shPub/errorPage")
    public String toTestErrPage(){
        return "notExistPage";
    }

}
