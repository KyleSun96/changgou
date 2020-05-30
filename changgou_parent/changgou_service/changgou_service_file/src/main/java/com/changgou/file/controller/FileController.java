package com.changgou.file.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.pojo.FastDFSFile;
import com.changgou.file.util.FastDFSClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Program: ChangGou
 * @ClassName: FileController
 * @Description:
 * @Author: KyleSun
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * @description: //TODO  FastDFS上传文件
     * @param: [file]
     * @return: com.changgou.entity.Result
     */
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) throws IOException {
        try {
            // 判断文件是否存在
            if (file == null) {
                throw new RuntimeException("文件不存在");
            }

            // 获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)) {
                throw new RuntimeException("文件不存在");
            }
            // 获取文件的扩展名称  abc.jpg   jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            // 获取文件内容
            byte[] content = file.getBytes();

            // 创建文件上传的封装实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename, content, extName);

            // 基于工具类进行文件上传,并接受返回参数  String[]
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);

            // 封装返回结果
            String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];

            return new Result(true, StatusCode.OK, "文件上传成功", url);

            // 测试图片 http://192.168.200.128:8080/group1/M00/00/00/wKjIgF7RsvmANd8DAAlMxBQ9eKc973.png

        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "文件上传失败");
        }
    }

}
