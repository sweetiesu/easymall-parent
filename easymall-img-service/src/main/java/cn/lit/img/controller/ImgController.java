package cn.lit.img.controller;

import cn.lit.img.service.ImgService;
import com.jt.common.vo.PicUploadResult;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImgController {
    @Autowired
    private ImgService is;
    //图片上传
    @RequestMapping("pic/uploadImg")
    public PicUploadResult picUp(MultipartFile pic){
        return is.picUp(pic);
    }
}
