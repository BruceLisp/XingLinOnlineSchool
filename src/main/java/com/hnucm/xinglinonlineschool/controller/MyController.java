package com.hnucm.xinglinonlineschool.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hnucm.xinglinonlineschool.configure.PayConfig;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@CrossOrigin
public class MyController {

    @Autowired
    UserService userService;
    @RequestMapping("/getUsers")
    public List<User> getMess() {
        List<User> users = userService.findAllUser();
        return users;
    }

    @RequestMapping("/getUser")
    public User getUser() {
        User user = new User();
        user.setId(111);
        user.setName("傻逼");
        return user;
    }

    /*@ResponseBody
    @PostMapping("/AliPay")
    //在这里接收前端传来的数据payInfo
    public Object goPay(@RequestBody JSONObject payInfo, HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        //首先在这里
        JSONObject jsonObject = new JSONObject();
        try {
            //这里是解析前端传来的数据
            String WIDout_trade_no = payInfo.get("WIDout_trade_no").toString();
            String WIDtotal_amount = payInfo.get("WIDtotal_amount").toString();
            String WIDsubject = payInfo.get("WIDsubject").toString();
            String WIDbody = payInfo.get("WIDbody").toString();
//        System.out.println(WIDout_trade_no);System.out.println(WIDtotal_amount);System.out.println(WIDsubject);System.out.println(WIDbody);
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(PayConfig.gatewayUrl, PayConfig.app_id, PayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

//        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //付款金额，必填
//        String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
//        //订单名称，必填
//        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
//        //商品描述，可空
//        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

            String out_trade_no = WIDout_trade_no;
            //付款金额，必填
            String total_amount = WIDtotal_amount;
            //订单名称，必填
            String subject = WIDsubject;
            //商品描述，可空
            String body = WIDbody;

            // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
            String timeout_express = "10m";

            //例子去官方api找
            alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"timeout_express\":\"" + timeout_express + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


//        //请求

            String result = alipayClient.pageExecute(alipayRequest).getBody() ;
            //这里以上都是支付宝的，接下来是我的
            //接下来是一系列的字符串操作，总之就是给支付宝返回的result页面的按钮属性设置为非hidden，并且添加了一些好看的属性，然后取出来<script>标签（因为前端用v-html不能显示<script>）最后将整个改造的result发给前端，就有了上面的前端将接收的内容写入sessionStorage的操作
            String befAction = result;
            StringBuffer aftAction = new StringBuffer(befAction);
            aftAction = aftAction.reverse();
            String midAction = aftAction.substring(68);
            aftAction = new StringBuffer(midAction).reverse();
            aftAction=aftAction.append(" width: 200px;  padding:8px;  background-color: #428bca;  border-color: #357ebd; color: #fff;  -moz-border-radius: 10px;  -webkit-border-radius: 10px;  border-radius: 10px;  -khtml-border-radius: 10px;text-align: center;  vertical-align: middle;  border: 1px solid transparent;  font-weight: 900;  font-size:125% \"> </form>");
            jsonObject.put("formaction", aftAction);
            jsonObject.put("message", StateCode.SUCCESS.getMessage());
            jsonObject.put("code", StateCode.SUCCESS.getCode());
            return jsonObject;
        }catch (Exception e)
        {
            jsonObject.put("message", StateCode.SERVER_FAILED.getMessage());
            jsonObject.put("code", StateCode.SERVER_FAILED.getCode());
            return jsonObject;
        }
    }*/


    /**
     * 实现文件上传
     * */
    @RequestMapping(value = "fileUpload",method= RequestMethod.POST)
    /*public String fileUpload(@RequestParam("fileName") MultipartFile file){*/
    public String fileUpload(HttpServletRequest request){
        /*List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFile("fileName");*/
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "D:\\privateData\\video\\other\\" ;
        File dest = new File(path + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }


    /**
     * 实现多文件上传
     * */
   @RequestMapping(value="multifileUpload",method= RequestMethod.POST)
   /* public @ResponseBody String multifileUpload(@RequestParam("fileName") List<MultipartFile> files) {*/
    public String multifileUpload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        System.out.println("文件上传开始");
        if(files.size()<=0){
            return "false";
        }

        String path = "D:\\privateData\\video\\other\\" ;

        for(MultipartFile file:files){
            String fileName = Long.toString(System.currentTimeMillis());
            System.out.println("fileName --> "+fileName);
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return "false";
            }else{
                File dest = new File(path + fileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }


    @RequestMapping("/download")
    public String downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename="1606823048053.mp4";
        String filePath = "D:\\privateData\\video\\other\\" ;
        File file = new File(filePath + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }



}
