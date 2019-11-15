package cn.shirtiny.community.SHcommunity.Controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

//用于帮助处理一些从Vcb爬取的资源
@Controller
public class VcbHelperController {

    //代理访问vcb的图片
    @GetMapping("/shApi/vcb/imageHelper")
    @ResponseBody
    public void vcbImageHelper(String aid,HttpServletResponse servletResponse) throws IOException {

        OkHttpClient client = new OkHttpClient();

        String url="http://bbs.vcb-s.com/forum.php?mod=attachment&noupdate=yes&aid="+aid;

        Request request = new Request.Builder()
                .url(url).addHeader("Host","bbs.vcb-s.com")
                .build();
        try (Response response = client.newCall(request).execute()) {
            okhttp3.ResponseBody body = response.body();
            servletResponse.setContentType("image/jpeg");
            OutputStream os = servletResponse.getOutputStream();
            os.write(Objects.requireNonNull(body).bytes());
            os.flush();
            os.close();
        }
    }


    //代理下载vcb的文件
    @GetMapping("/shApi/vcb/dFileHelper")
    @ResponseBody
    public void vcbDFileHelper(String aid,HttpServletResponse servletResponse) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url="http://bbs.vcb-s.com/forum.php?mod=attachment&aid="+aid;

        Request request = new Request.Builder()
                .url(url).addHeader("Host","bbs.vcb-s.com")
                .build();
        Response response = client.newCall(request).execute();
        String ContentDisposition = response.header("Content-Disposition");
        servletResponse.setHeader("Content-Disposition", ContentDisposition);
        OutputStream os = servletResponse.getOutputStream();
        os.write(Objects.requireNonNull(response.body()).bytes());
        os.flush();
        os.close();
    }
}
