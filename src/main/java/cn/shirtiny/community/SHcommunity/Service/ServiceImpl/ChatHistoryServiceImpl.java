package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.ChatHistoryDTO;
import cn.shirtiny.community.SHcommunity.Mapper.ChatHistoryMapper;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;
import cn.shirtiny.community.SHcommunity.Service.IchatHistoryService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatHistoryServiceImpl implements IchatHistoryService {

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    //增加一份聊天记录
    @Override
    public boolean addOneChatHistory(ChatHistory chatHistory) {
        try{
            ChatHistoryDTO chatHistory1 = chatHistoryMapper.selectOneHistory(chatHistory.getChatHistoryName());
            ChatHistory chatHistory2 = chatHistoryMapper.selectById(chatHistory.getChatHistoryId());
            if (chatHistory1==null && chatHistory2==null){
                chatHistoryMapper.insert(chatHistory);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
