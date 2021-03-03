package com.hnucm.xinglinonlineschool.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.hnucm.xinglinonlineschool.configure.PayConfig;
import com.hnucm.xinglinonlineschool.configure.WebSocket;
import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import com.hnucm.xinglinonlineschool.pojo.AliReturnPayBean;
import com.hnucm.xinglinonlineschool.pojo.Result;
import com.hnucm.xinglinonlineschool.service.AliPayTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("pay")
public class AliPayController {
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private AliPayTradeService aliPayTradeService;

//    @RequestMapping("/uploadTrade")
//    public Result uploadTrade(@RequestBody AliPayTrade trade) {
//        //进行数据库存储。并返回订单号。
//        int id = aliPayTradeService.addTradeReturnKey(trade);
//        System.out.println("id:"+id);
//        return Result.ok().put("id", id);
//    }

    @RequestMapping("/createQR")
    @ResponseBody
    public Result send(@RequestBody AliPayTrade aliPayTrade) throws AlipayApiException {
        //进行数据库存储。并返回订单号。
        int id = aliPayTradeService.addTradeReturnKey(aliPayTrade);
        aliPayTrade.setId(id);
        System.out.println("alipayTrade:"+aliPayTrade);
        //拿到订单号在数据库中查询订单信息
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest(); //创建API对应的request类
        //notifyUrl接口需要外网可以访问的，用natapp进行外网隧道的搭建。
        request.setNotifyUrl("http://3e3g9t.natappfree.cc/pay/call");
        //同步回调地址
        //request.setReturnUrl("http://localhost:8080/call");

        //订单的内容json数据，传送订单的基本信息， 发送给支付宝的支付接口。
        request.setBizContent("{" +
                "\"out_trade_no\":\""+aliPayTrade.getId()+"\"," +
                "\"total_amount\":\""+aliPayTrade.getAmount()+"\"," +
                "\"subject\":\"杏林币充值\" " +
                "  }");

        //拿到回调， 回调数据为json， 将得到的json数据中的QrCode二维码字符串， 返回给前端， 前端将字符字符串转换为二维码。
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            return Result.ok().put("QrCode",response.getQrCode());
        } else {
            System.out.println("调用失败");
            return Result.error("调用支付失败！");
        }
    }


    /**
     * 支付宝回调函数
     * @param request
     * @param response
     * @param returnPay
     * @throws IOException
     */
    @RequestMapping("/call")
    public void call(HttpServletRequest request, HttpServletResponse response, AliReturnPayBean returnPay) throws IOException {
        response.setContentType("type=text/html;charset=UTF-8");
        System.out.println("支付宝的的回调函数被调用");
        log.info("支付宝的的回调函数被调用");
        if (!PayConfig.checkSign(request)) {
            log.info("验签失败");
            response.getWriter().write("failture");
            return;
        }
        if (returnPay == null) {
            log.info("支付宝的returnPay返回为空");
            response.getWriter().write("success");
            return;
        }
        log.info("支付宝的returnPay" + returnPay.toString());
        //表示支付成功状态下的操作
        if (returnPay.getTrade_status().equals("TRADE_SUCCESS")) {
            log.info("支付宝的支付状态为TRADE_SUCCESS");
            //支付成功修改数据库订单状态，并修改余额
            aliPayTradeService.tradeComplish(new Integer(returnPay.getOut_trade_no()), returnPay.getTrade_no());
            webSocket.sendMessage("true");
        }
        response.getWriter().write("success");
    }
}
