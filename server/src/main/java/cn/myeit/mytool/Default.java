package cn.myeit.mytool;

import cn.hutool.extra.mail.MailUtil;
import cn.myeit.mytool.Util.CMD;
import cn.myeit.mytool.Util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

/**
 * 端口映射控制层
 */
@Component
public class Default {

    @Value("${frp.web.url}")
    private String webFrpUrl;
    @Value("${frp.web.start}")
    private String webFrpStart;
    @Value("${spring.mail.to}")
    private String to;
    @Autowired
    private CMD cmd;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private Config config;

    @PostConstruct
    public void init() {
        startWebFrp();
    }

    /**
     * 启动web的内网穿透
     */
    private void startWebFrp(){
        String value = cmd.start(webFrpUrl, webFrpStart);
        switch(value){
            case "已经在运行，无需再次启动":
            case "启动成功":
                break;
            default:
                //运行失败发送邮箱
                if(to != null && !"".equals(to)){
                    try {
                        MailUtil.send(to, "来自远程电脑的信息",value , false);
                    }catch (Exception e){
                        System.out.println("发送邮件失败");
                    }
                }
                break;
        }
        System.out.println(value);
    }
}
