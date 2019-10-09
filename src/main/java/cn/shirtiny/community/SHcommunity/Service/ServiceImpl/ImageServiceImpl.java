package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Exception.FileUploadFailedException;
import cn.shirtiny.community.SHcommunity.Exception.Md_ImageUploadFailedException;
import cn.shirtiny.community.SHcommunity.Service.ImageService;
import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class ImageServiceImpl implements ImageService {
    //公钥
    @Value("${ucloud_uFile_SHtoken_PublicKey}")
    private String myPublicKey;
    //私钥
    @Value("${ucloud_uFile_SHtoken_PrivateKey}")
    private String myPrivateKey;
    //bucket地域
    @Value("${ucloud_uFile_bucket_region}")
    private String region;
    //域名后缀
    @Value("${ucloud_uFile_bucket_proxySuffix}")
    private String proxySuffix;
    //名字
    @Value("${ucloud_uFile_bucket_name}")
    private String bucketName;

    //临时下载地址的过期时间
    //  2 * 60秒 --> 2分钟后过期，315360000 --> 10 * 365 * 24 * 60 * 60 = 10年
    @Value("${ucloud_uFile_downloadURL_expiresDuration}")
    private int expiresDuration;


    /**上传文件
     * @param inputStream    文件的流
     * @param mimeType       文件的ContentType
     * @param allownRename   是否需要、允许修改文件上传到服务器后的名字
     * @param clientFileName 初始文件名
     * @return downLoadUrl 返回刚刚上传文件的临时地址
     */
    @Override
    public String upload(InputStream inputStream, String mimeType, boolean allownRename, String clientFileName) {
        //文件上传到服务器后的名字
        String serverFileName = clientFileName;
        //临时下载地址
        String downloadUrl = "";
        //当允许重命名文件时，命名文件
        if (allownRename) {
            System.out.println("暂时先不重命名，到时候看一下id生成工具"+"emm百度开源的那个雪花算法的uidGenerator要用到数据库");
            //暂时用以前自己写的,传到服务器后的文件名
            serverFileName=createRandomName(clientFileName);
        }
        // 对象相关API的授权器
        ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(myPublicKey, myPrivateKey);
        // 对象操作需要ObjectConfig来配置您的地区和域名后缀
        ObjectConfig config = new ObjectConfig(region, proxySuffix);

        //待上传文件
        //File file = new File("your file path");
        PutObjectResultBean response;
        {
            try {
                response = UfileClient.object(OBJECT_AUTHORIZER, config)
                        //可以使用文件的方式
                        .putObject(inputStream, mimeType)
                        .nameAs(serverFileName)
                        //我命名空间的名字
                        .toBucket(bucketName)
                        /**
                         * 是否上传校验MD5, Default = true
                         */
                        //  .withVerifyMd5(false)
                        /**
                         * 指定progress callback的间隔, Default = 每秒回调
                         */
                        //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                        /**
                         * 配置进度监听
                         */
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(long bytesWritten, long contentLength) {
                                //已上传/总长度
                                System.out.println(bytesWritten + "/" + contentLength + "进度：" + (bytesWritten * 100) / contentLength + "%");
                            }
                        }).execute();
                //上传完成后，查看response，然后获得刚刚上传图片的临时地址
                    //上传成功RetCode是0，错误时的response：{"ResponseCode":400,"RetCode":-30010,"ErrMsg":"bucket not exist","X-SessionId":"0e9df91b-5d69-4e9b-bfeb-5d9b8c182869"}
                    if (response.getRetCode() == 0) {
                        //获取刚刚上传的文件地址，设置过期时间
                        downloadUrl = UfileClient.object(OBJECT_AUTHORIZER, config)
                                .getDownloadUrlFromPrivateBucket(serverFileName, bucketName, expiresDuration)
                                .createUrl();
                        return downloadUrl;
                }
            } catch (UfileClientException | UfileServerException e) {
                throw new Md_ImageUploadFailedException(e.getMessage());
            }
        }
        return downloadUrl;
    }

    @Override
    public String createRandomName(String clientFileName) {
        //拿到文件后缀名
        String suffix = clientFileName.substring(clientFileName.lastIndexOf("."));
        //根据当前日期，伪建一个文件夹
        String directory = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //生成新的文件名
        //时间戳
        long currentTimeMillis = System.currentTimeMillis();
        //随机数
        Random random = new Random();
        int randomInt = random.nextInt(999);
        //%X 获得数字，把它转为16进制，大写字母
        //%04X 增加的04，意思是，转化后的字符串占4个字符，不够用0填充
        String fileId=currentTimeMillis+String.format("%04X",randomInt);
        //组合为传到服务器后的文件名
        return directory+"_"+fileId+suffix;
    }
}
