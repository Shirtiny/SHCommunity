package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.Model.Section;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SectionMapper extends BaseMapper<Section> {

    //查询全部版块，按id顺序排列
    @Select("select * from section order by section_id ")
    List<Section> listAllSection();

    //帖子总计数+1
    @Update("update Section set INVITATION_TOTAL_NUM=INVITATION_TOTAL_NUM+1 where SECTION_ID=#{sectionId}")
    void incrInvitationTotalNUm(@Param("sectionId") Long sectionId);

}
