package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.DTO.SectionDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Model.Section;
import cn.shirtiny.community.SHcommunity.Service.IinvitationService;
import cn.shirtiny.community.SHcommunity.Service.IsectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shApi")
public class SectionController {

    @Autowired
    private IsectionService sectionService;

    @Autowired
    private IinvitationService invitationService;

    //版块列表
    @GetMapping("/listSections")
    @ResponseBody
    public ShResultDTO<String,Object> toListAllSections(){
        //查询全部版块
        List<Section> sections = sectionService.selectAllSection();
        Map<String,Object> map=new HashMap<>();

        //查询各版块的最新帖子
        List<SectionDTO> sDTOList=new ArrayList<>();
        for (Section section:sections){
            SectionDTO sectionDTO=new SectionDTO();
            BeanUtils.copyProperties(section,sectionDTO);
            sectionDTO.setLatestInvitation(invitationService.selectOneLatestDto(section.getSectionId()));
            sDTOList.add(sectionDTO);
        }

        map.put("sections",sDTOList);
        return new ShResultDTO<>(200,"版块查询完成",map,null);
    }

    //单个版块信息
    @GetMapping("/oneSection/{sectionId}")
    @ResponseBody
    public ShResultDTO<String,Object> retOneSection(@PathVariable("sectionId") Long sectionId  ){
        Section section = sectionService.selectOneSection(sectionId);
        Map<String,Object> map=new HashMap<>();
        map.put("section",section);
        return new ShResultDTO<>(200,"单个版块查询完成",map,null);

    }



}
