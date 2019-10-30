package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.ChatHistoryDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {

    //根据记录名查找聊天记录
    @Select("select * from chat_history where chat_history_name=#{chatHistoryName}")
    ChatHistoryDTO selectOneHistory(@Param("chatHistoryName") String chatHistoryName);

}
