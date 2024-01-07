package cn.myeit.mytool.controller;

import cn.myeit.mytool.Config;
import cn.myeit.mytool.Util.CMD;
import cn.myeit.mytool.Util.SendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping()
@CrossOrigin()
public class PortController {
    @Value("${frp.password}")
    private String password;

    @Autowired
    private CMD cmd;

    @Autowired
    private Config config;

    /**
     * 启动3306端口映射的bat
     * @param password
     * @return
     */
    @RequestMapping(value = "/{name}/start/{password}")
    public SendUtil start(@PathVariable String name, @PathVariable String password){
        if(password == null || !password.equals(this.password)){
            return SendUtil.create().add("message","error");
        }
        //密码正确开始获取脚本路径以及参数
        Map<String, String> client = config.getClient().get(name);
        if(client == null){
            return SendUtil.create().add("message","error");
        }
        String url = client.get("url");
        String start = client.get("start");
        String value = cmd.start(url, start);
        if(value == null){
            return SendUtil.create().add("message","error");
        }
        return SendUtil.create().add("message", value);
    }

    /**
     * 关闭3306端口映射的bat
     */
    @RequestMapping(value = "/{name}/shutdown/{password}")
    public SendUtil shutdown(@PathVariable String name, @PathVariable String password){
        if(password == null || !password.equals(this.password)){
            return SendUtil.create().add("message","error");
        }
        //密码正确开始获取脚本路径以及参数
        Map<String, String> client = config.getClient().get(name);
        if(client == null){
            return SendUtil.create().add("message","error");
        }
        String url = client.get("url");
        String start = client.get("shutdown");
        String value = cmd.start(url, start);
        if(value == null){
            return SendUtil.create().add("message","error");
        }
        return SendUtil.create().add("message", value);
    }

    /**
     * 检查端口映射状态
     */
    @RequestMapping(value ="/{name}/check/{password}")
    public SendUtil check(@PathVariable String name, @PathVariable String password, HttpServletResponse response){
        if(password == null || !password.equals(this.password)){
            return SendUtil.create().add("message","error");
        }
        //密码正确开始获取脚本路径以及参数
        Map<String, String> client = config.getClient().get(name);
        if(client == null){
            return SendUtil.create().add("message","error");
        }
        String url = client.get("url");
        String start = client.get("check");
        String value = cmd.start(url, start);
        if(value == null){
            return SendUtil.create().add("message","error");
        }
        return SendUtil.create().add("message", value);
    }
}
