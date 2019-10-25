package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.Model.Banner;

import java.util.List;

public interface IbannerService {

    //查询指定形状的banner，bannerShape，lg、md、sm、cr
    List<Banner> selectBannersByShape(String bannerShape);

}
