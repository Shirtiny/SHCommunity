package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Mapper.SectionMapper;
import cn.shirtiny.community.SHcommunity.Model.Section;
import cn.shirtiny.community.SHcommunity.Service.IsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SectionServiceImpl implements IsectionService {

    @Autowired
    private SectionMapper sectionMapper;

    //查询全部版块
    @Override
    public List<Section> selectAllSection() {
        return sectionMapper.selectList(null);
    }


}
