package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;
import cn.shirtiny.community.SHcommunity.Model.Banner;
import cn.shirtiny.community.SHcommunity.Service.IbannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BannerController {

    @Autowired
    private IbannerService bannerService;

    //bannerShape的值列表
    private static HashMap<String,String> bannerShapes=new HashMap<String,String>(){
        {
            put("lg","large");
            put("md","medium");
            put("sm","small");
            put("cr","circle");
        }
    };

    //获取指定类型的banner
    @GetMapping("/shApi/getBannersByShape/{bannerShape}")
    @ResponseBody
    public ShResultDTO<String, Object> retBannersByShape(@PathVariable(value = "bannerShape") String bannerShape) {
//        bannerShape=lg/md/sm/cr

        if (bannerShapes.get(bannerShape)==null){
            return new ShResultDTO<>(ShErrorCode.BannerShape_Args_Error.getCode(),ShErrorCode.BannerShape_Args_Error.getMessage(),null, null);
        }
        Map<String, Object> map = new HashMap<>();
        List<Banner> banners = bannerService.selectBannersByShape(bannerShape);
        map.put("banners",banners);
        return new ShResultDTO<>(200,"banner查询完成",map,null);
    }

}
