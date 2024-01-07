package cn.myeit.mytool.Util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class CMD {
    public String start(String command,String param){
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(command,param);
            Process process = processBuilder.start();

            // 读取脚本输出
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GB2312"));
            String value = reader.readLine();
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
