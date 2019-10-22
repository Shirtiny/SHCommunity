package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.Model.Section;
import cn.shirtiny.community.SHcommunity.Model.User;

import java.util.List;

public interface IsectionService {
    //查询全部版块
    List<Section> selectAllSection();
}
