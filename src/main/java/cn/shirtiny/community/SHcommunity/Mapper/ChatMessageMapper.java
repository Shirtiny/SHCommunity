package cn.shirtiny.community.SHcommunity.Mapper;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    //查询某个聊天记录的消息 ,比如查询聊天室的消息（chatHistoryId为0的消息） 创建时间顺序
    @Select("select * from chat_message where chat_history_id=#{chatHistoryId} ORDER BY gmt_created ASC")
    List<ChatMessageDTO> selectAllDTOByhistoryId(@Param("chatHistoryId") String chatHistoryId );

    //查询某个聊天记录的消息Id ,比如查询聊天室的消息（所有chatHistoryId为0的消息的id）
    @Select("select chat_message_id from chat_message where chat_history_id=#{chatHistoryId}")
    List<String> selectMessageIdsByHistoryId(@Param("chatHistoryId") String chatHistoryId);

    //根据id清空一个聊天记录的所有数据
    @Delete("delete from chat_message where chat_history_id=#{chatHistoryId}")
    void deleteMessagesByhistoryId(@Param("chatHistoryId") Long chatHistoryId);

    //根据消息记录id和消息内容 查询一个消息的自增id
    @Select("SELECT chat_message_id FROM chat_message WHERE chat_history_id=#{chatHistoryId} AND chat_message_content=#{chatMessageContent}")
    Long selectByHidAndContent(@Param("chatHistoryId") String chatHistoryId,@Param("chatMessageContent") String chatMessageContent);

    //根据消息id 更新消息的已读状态，false未读 true已读
    @Update("update chat_message set readed=#{readed} where chat_message_id=#{chatMessageId}")
    void updateReadByid(@Param("chatMessageId") String chatMessageId,@Param("readed") boolean readed);
}
