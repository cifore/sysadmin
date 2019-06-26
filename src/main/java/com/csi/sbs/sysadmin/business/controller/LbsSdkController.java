package com.csi.sbs.sysadmin.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin")
public class LbsSdkController {

    @Value("${sdkpath}")
    private String sdkpath;

    @RequestMapping(value = "/downloadsdk",method = RequestMethod.GET)
    public ResultUtil downloadcsv(HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
        File file = new File(sdkpath);
        String fileName = file.getName(); // 文件的默认保存名
        ResultUtil<JSONObject> responseMsg = new ResultUtil<>();
        if (!file.exists()){
            responseMsg.setCode("404");
            responseMsg.setMsg("file not found!");
            return responseMsg;
        }
        // 读到流中
        InputStream inStream = new FileInputStream(sdkpath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
            responseMsg.setCode("0");
            responseMsg.setMsg("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

}
