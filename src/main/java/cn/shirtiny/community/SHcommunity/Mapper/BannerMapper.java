package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.Model.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BannerMapper extends BaseMapper<Banner> {

    //查询指定形状的banner，bannerShape，lg、md、sm、cr
    @Select("select * from banner where banner_shape=#{bannerShape}")
    List<Banner> selectBannersByShape(@Param("bannerShape") String bannerShape);

}
