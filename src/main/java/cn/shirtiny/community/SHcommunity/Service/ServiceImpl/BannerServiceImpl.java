package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Mapper.BannerMapper;
import cn.shirtiny.community.SHcommunity.Model.Banner;
import cn.shirtiny.community.SHcommunity.Service.IbannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements IbannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> selectBannersByShape(String bannerShape) {
        return bannerMapper.selectBannersByShape(bannerShape);
    }

}
