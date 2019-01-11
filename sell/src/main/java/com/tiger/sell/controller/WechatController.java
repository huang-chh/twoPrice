package com.tiger.sell.controller;

import com.tiger.sell.enums.ExceptionEnum;
import com.tiger.sell.handle.SellException;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Controller
@RequestMapping("/wechat")
public class WechatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);
    //这里的token要和微信测试号网页填写的token一样
    public static final String TOKEN = "hfdasf89ha80fw2ff_token";
    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/hello")
    public void hello(String signature,String timestamp,String nonce,String echostr, HttpServletResponse response){
        // 将token、timestamp、nonce三个参数进行字典序排序
        System.out.println("signature:"+signature);
        System.out.println("timestamp:"+timestamp);
        System.out.println("nonce:"+nonce);
        System.out.println("echostr:"+echostr);
        System.out.println("TOKEN:"+TOKEN);
        String[] params = new String[] { TOKEN, timestamp, nonce };
        Arrays.sort(params);
        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String clearText = params[0] + params[1] + params[2];
        String algorithm = "SHA-1";
        String sign ="";
        try{
            sign = new String(
                    org.apache.commons.codec.binary.Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(sign)) {
            try{
                response.getWriter().print(echostr);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){
        LOGGER.info("return {}",returnUrl);
        String url = "http://tiger.nat300.top/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        LOGGER.info("redirectUrl {}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,
                           @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken=wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            throw new SellException(ExceptionEnum.WXMPINFO.getCode(),"公众号授权失败");
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        LOGGER.info("【openid】,openId={}",openId);
        return "redirect:"+returnUrl+"?openid="+openId;
    }

    public WxMpService getWxMpService() {
        return wxMpService;
    }

    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }
}
