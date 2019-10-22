package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Model.Section;
import cn.shirtiny.community.SHcommunity.Service.IsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shApi")
public class SectionController {

    @Autowired
    private IsectionService sectionService;

    @GetMapping("/listSections")
    @ResponseBody
    public ShResultDTO<String,Object> toListAllSections(){
        List<Section> sections = sectionService.selectAllSection();
        Map<String,Object> map=new HashMap<>();
        map.put("sections",sections);
        return new ShResultDTO<>(200,"版块查询完成",map,null);
    }

}
