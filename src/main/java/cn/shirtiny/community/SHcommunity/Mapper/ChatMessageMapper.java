package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    //查询某个聊天记录的消息 ,比如查询聊天室的消息（chatHistoryId为0的消息）
    @Select("select * from chat_message where chat_history_id=#{chatHistoryId}")
    List<ChatMessageDTO> selectAllDTOByhistoryId(@Param("chatHistoryId") String chatHistoryId );

    //根据id清空一个聊天记录的所有数据
    @Delete("delete from chat_message where chat_history_id=#{chatHistoryId}")
    void deleteMessagesByhistoryId(@Param("chatHistoryId") Long chatHistoryId);


}
