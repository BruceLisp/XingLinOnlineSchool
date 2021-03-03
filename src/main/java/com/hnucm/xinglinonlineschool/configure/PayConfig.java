package com.hnucm.xinglinonlineschool.configure;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝接口配置类
 */
@Configuration
public class PayConfig {
    // 请填写您的AppId，例如：2019091767145019（必填）
    private static final String appID = "2016103100782454";
    //应用私钥，这里修改生成的私钥即可（必填）
    private static final String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTOBGAobbI5hArMzBFbin88l8HJr06FWMC2hXLaNKPAmdFFsWEQV/fBefyxKVY7wmpBmaXsD04wRmBj/kQ6qbmIXWviKJDpdIqhs6fxk7bzyKMrweQ6Luu8954RUlGfCBj0vFXA9GXszUJmP8p2lDlOlcaJ7puV5hYiyRjrWxaYhz8XE0bhCk9KE5FzVKuLzT1ESVEGy3xpDLWhbW7M4qgbtNwL3oHynsAe6CzXBziA+oKiHrYmHe9dMo+hZQb4nD+mUgUgoem+s8BqOyuMSW8Dp6oMHCILyXE+e6mwpniumJX2pMXU9rUreD5I6sZL1LDSk2gAEZVN/Idwh7Jjq9dAgMBAAECggEBAMHK9bQRq8VT4I3iX220j+226x2Cm6eJK9jYA5snx9jSmbG4HO0lljLoFlKVvieE2lYsu0B9wQ4rjZ1kXV76DLulA3dUY5yxrcy69yV9K6lduRNfHBAGmP0CX6H14C89f/JW/nuZ01neYNE3gRYDaqmHB5fFz7WhxPOoZnDWE04wgDcwYYDYhBQKWpCJuPDNDUOiNbitXw9K4CsbnNnjqtHH3+PW5FFhGlf/fpBgxD90Q4fWyr7250q9UWydb9TAet2dwKfLVl3f3d4hg+uIxbCs6IUA73ozhAwSpBnVrxlEU08u0+eRDAWTpp6dm+dgG4IcpEyuf9CUGIk4xpNnjjkCgYEA6NMteIg2ZVn1n9br+a5ccjggUTrkRnw5VGUthMlHVJsMekujvwSADSwykuyVBdIKjVyHTAC9Z+wF6SSpR+iZWk5P7a5CXqPXi0zU7qygcdlIHaVeTLhMoAu4ot/UPNKh2UpQxcJpRJwzwUYm0ZTAkc30BjMYcxRszTVxdZpDBHsCgYEA6D5U1FCSke2k1n3qlHtoiYg6MfxGlMa8IPYKmGCfXHWmZF3ZAJ7x/p7C8SZk6SqOLCcnLIUIbZGI6shgwNhNebUybg2/N/e+bH4fYf3vWVLp9HBtQOTg2jXIRfE+E249JZwRf8j0AYogrsGxIhO0Oz+I+798741PBJitpO9fsAcCgYBdJRc88GmqtInCz64KbspHW9L6DHCFDm+1JuFGDWx/GaStTJOO+RLtsi/tfb7E3FoiYdEzA9zCtbdLpcIfpZuseEYb1apxyGSUM0HA1sk9ourqCV2nndfx856Dcr6rMq1TjBqyc2u/6in+eb1P/Y3pBxdIX6q5Z4N7uZTRssx6/wKBgBquNn6htwTuwTYnBxEB0HOkY9MkQVOMNUawHW1+BDDSD0iOg4SPvgtbX9fmakdjLSwjX1u8+MflpU4bsQnWj6gAygdcqvAaFRVPs/lE+/9OCDSdyd9fq/lFnnlyioge7QVqFZSRmH6oyLJq7BeXHeiElw8c45R9rms/wwskuOB3AoGBAI0IsD5uQl2QBbC7aYbKeyywUClfs1FQ/Ct6hVimQxCfe0RQ7YYlwjKaE3xej+fTHGL+c7Ki+Dawm92Ia/9QDLvLSKQukoQMxwMWSY1bFBcl3C7+cUEJX4P8viqgHOJXnuuDubkkr41/r5PmUjGi3mJoZbrM+qSynngjwJm0E+ue";
    //支付宝公钥，而非应用公钥（必填）
    private static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArh5w7JLCx9o7rjPN0NTyQ6zEqTsaNPzdlE5MafNnhuefsJaht77tNv/6Q3wbmbk/2vz8qE/+B/KsQ5N1RcAIpBCE4K9eJAMcofVEH/2uAZtMrYEUfkeMJg+RrIsi1i485ueO+RAc95NbsAeXg05h6ZvhEt3q+GuDmwje3r40CR2oehkC0F01wcvFUNtDcauiKRBG8NEJNQxOIx9Ppt/yDU8KSYMwCn5s6ZT+J3pQ3E45DYrjhnOEBRHtK0soWE+ayCzu+R/S0D0/Sg5pf3CyGqWiR6H2bsmQ7rlcsWkItnggpWkvOR4UaungKd+DfysLO5FPwyEqFUZYSEsoScdMvQIDAQAB";
    //默认即可（必填）
    private static final String charset = "utf-8";
	//默认即可（必填）
    private static final String signType = "RSA2";
    // 支付宝网关
    private static final String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    @Bean
    public AlipayClient alipayClient(){
    	//沙箱环境使用https://openapi.alipaydev.com/gateway.do，线上环境使用https://openapi.alipay.com/gateway.do
        return new DefaultAlipayClient(gatewayUrl, appID, privateKey, "json", charset, publicKey, signType);
    }
	/**
	 * 验签，是否正确
	 */
    public static boolean checkSign(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for(String value : values) {
                strs = strs + value;
            }
            System.out.println(key +"===>"+strs);
            paramsMap.put(key, strs);
        });
        System.out.println();
        //调用SDK验证签名
        try {
            return  AlipaySignature.rsaCheckV1(paramsMap, PayConfig.publicKey, PayConfig.charset, PayConfig.signType);
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("*********************验签失败********************");
            return false;
        }

    }
}

