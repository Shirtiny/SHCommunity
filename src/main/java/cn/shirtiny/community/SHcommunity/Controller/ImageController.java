package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.Md_ImageUpResultDTO;
import cn.shirtiny.community.SHcommunity.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    //md编辑器的图片上传表单的name参数值
    @Value("${Md_Editor_imageFile_name}")
    private String Md_Editor_imageFile_name;

    //是否允许服务修改上传到服务器后的文件名
    @Value("${ImageUploadService_isAllownRename}")
    private boolean ImageUploadService_isAllownRename;

    //md图片上传以及回显
    @RequestMapping(value = "/imageUpload")
    @ResponseBody
    public Md_ImageUpResultDTO uploadImage(HttpServletRequest request){
        //转换request
        MultipartHttpServletRequest multipartRequest= (MultipartHttpServletRequest) request;
        String downloadUrl="";
        try {
            //需要md图片表单提交的文件name
            MultipartFile file = multipartRequest.getFile(Md_Editor_imageFile_name);
            if (file != null) {
                InputStream inputStream = file.getInputStream();
                String contentType = file.getContentType();
                String filename = file.getOriginalFilename();
                //调用上传服务
                downloadUrl = imageService.upload(inputStream, contentType, ImageUploadService_isAllownRename, filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Md_ImageUpResultDTO(1,"上传成功!",downloadUrl);
    }

}
