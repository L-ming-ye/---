package cn.myeit.mytool.Util;

import java.util.HashMap;
import java.util.Map;

public class SendUtil extends HashMap {
    public <T> SendUtil add(String key, T value) {
        this.put(key,value);
        return this;
    }

    public static SendUtil create(){
        return new SendUtil();
    }
}
