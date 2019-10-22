package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import cn.shirtiny.community.SHcommunity.Service.ServiceImpl.InvitationService;
import cn.shirtiny.community.SHcommunity.Utils.ShArrayQueue;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InvitationController {


    @Autowired
    private IinvitationService invitationService;


    @PostMapping(value = "/createInvitation")
    @ResponseBody
    public ShResultDTO createInvitation(@RequestBody Invitation invitation, Model model, HttpServletRequest request){

        Long userId=((User)request.getSession().getAttribute("user")).getId();
        invitation.setAuthorId(userId);
        boolean flag = invitationService.addInvitation(invitation);
        if(flag){
            //前往帖子的最后一页zaaz
//            return "redirect:/?curPage=999999";
            return new ShResultDTO<>(200,"提交成功了哦~");
        }else {
//            return "newInvitation";
            return new ShResultDTO<>(400,"标题或内容不能为空，并且字数不能大于20和400");
        }

    }

    @GetMapping(value = "/shApi/listInvitationsByPage")
    @ResponseBody
    public ShResultDTO listInvitationsByPage(@RequestParam(value = "curPage", defaultValue = "1") Long curPage,
                                             @RequestParam(value = "orderBy", defaultValue = "1",required = false) Integer orderBy,
                                             @RequestParam(value = "sectionId",defaultValue = "1",required = false) Long sectionId){

        Page<InvitationDTO> page = new Page<>();
        page.setCurrent(curPage);
        IPage<InvitationDTO> pageInfo=null;
        if (orderBy==null||orderBy==0){
            //无或0按默认顺序，id升序
            pageInfo = invitationService.selectDtoBypage(page,sectionId);
        }else {
            //其他值按更新时间倒序排列（Controller默认）
            pageInfo = invitationService.selectDtoBypageDesc(page,sectionId);
        }

        //返回数据map集合
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("pageInfo",pageInfo);

        //总页数
        long pages = pageInfo.getPages();
        //若当前页大于总页数
        if (curPage > pages) {
            //重新调用1次自身方法，将当前页设为最后一页
            return listInvitationsByPage(pages,orderBy,sectionId);
        }
        //控制的打印前端标签
        long[] pageNumArray=null;
        long pageNum = curPage - 3;
        ShArrayQueue queue=new ShArrayQueue(7);
        for (int i=0;i<queue.getMaxSize();i++){
            if (pageNum>0 && pageNum<=pages){
                queue.add(pageNum);
            }
            pageNum++;
        }
        pageNumArray=queue.toArray();
        dataMap.put("pageNumArray", pageNumArray);

        return new ShResultDTO<>(200,"分页查询完成",dataMap,null);
    }



    //测试，返回1号 帖子、帖子的评论以及评论人
    @RequestMapping("/test/i")
    @ResponseBody
    public InvitationDTO testI(){
        return invitationService.selectOneDtoAndCs(1);
    }




}
