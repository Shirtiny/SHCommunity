package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.InvitationDTO;
import cn.shirtiny.community.SHcommunity.Model.Invitation;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InvitationMapper extends BaseMapper<Invitation> {

    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID")
    IPage<InvitationDTO> selectDtoByPage(Page<InvitationDTO> page);

    @Select("select * from USER u join INVITATION i on u.user_id=i.AUTHOR_ID where invitation_id=#{id}")
    InvitationDTO selectOneDtoById(long id);
}
