package cn.shirtiny.community.SHcommunity.Service;

import java.io.InputStream;

public interface ImageService {
    //图片文件上传
    String upload(InputStream inputStream, String mimeType, boolean allownRename, String clientFileName);
    //生成随机文件名
    String createRandomName(String clientFileName);
}
