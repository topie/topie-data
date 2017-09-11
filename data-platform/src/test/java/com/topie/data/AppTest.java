package com.topie.data;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenguojun on 2017/2/16.
 */
public class AppTest {

    @Test
    public void reg() throws Exception {
        String u = URLDecoder.decode("/?a=b", "UTF-8");
        Pattern p = Pattern.compile("(https?://[^/]+)?(/[^?]*)\\??(.*)");
        Matcher m = p.matcher(u);
        if (m.find()) {
            String host = m.group(1);
            if (StringUtils.isNotEmpty(host)) {
                host = host.replaceAll("https?://", "");
                System.out.println(host);
            }
            String uri = m.group(2);
            System.out.println(uri);

            String[] strings = m.group(3).split("[?&]+");
            for (String string : strings) {
                System.out.println(string);
            }
        }
    }

    @Test
    public void test() throws Exception {
        String[] singleFields = StringUtils.split("a,v,c",",");
        for (String singleField : singleFields) {
            System.out.println(singleField+"---");
        }
    }

}
