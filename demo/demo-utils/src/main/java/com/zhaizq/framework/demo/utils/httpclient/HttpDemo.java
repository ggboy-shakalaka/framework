package com.zhaizq.framework.demo.utils.httpclient;

import com.zhaizq.framework.utils.httpclient.StringSimpleHttp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class HttpDemo {
    static AtomicLong count = new AtomicLong();         // 总次数
    static AtomicLong countTime = new AtomicLong();     // 总耗时
    static AtomicLong maxTime = new AtomicLong();       // 最大耗时
    static AtomicLong errorCount = new AtomicLong();       // 最大耗时

    public static void main(String[] args) throws IOException, InterruptedException {
        String body = "<request><deliveryOrder><arAmount>0</arAmount><buyerMessage></buyerMessage><buyerNick>云颜</buyerNick><createTime>2020-04-15 00:01:48</createTime><deliveryOrderCode>12345678901234567</deliveryOrderCode><deliveryRequirements><deliveryType>PTPS</deliveryType><notRecommendDelivery></notRecommendDelivery></deliveryRequirements><discountAmount>3.6</discountAmount><expressCode></expressCode><freight>0.0</freight><insuranceFlag>N</insuranceFlag><invoiceFlag>N</invoiceFlag><itemAmount>145.73</itemAmount><logisticsCode>OTHER</logisticsCode><operateTime>2020-04-15 00:02:18</operateTime><orderType>JYCK</orderType><payTime>2020-04-14 23:39:45</payTime><placeOrderTime>2020-04-14 23:38:50</placeOrderTime><receiverInfo><area>南岸区</area><city>重庆市</city><detailAddress>涂山镇涂山路东海长州</detailAddress><mobile>YUNJI_96975689906</mobile><name>云颜</name><province>重庆市</province><zipCode></zipCode></receiverInfo><remark></remark><senderInfo><city>郑州市</city><detailAddress>河南省郑州市航空港区郑港十一路与振兴路交叉后郑州新郑综合保税区</detailAddress><mobile>13733188183</mobile><name>季雅洁</name><province>河南省</province></senderInfo><serviceFee>0</serviceFee><shopNick>普通店铺</shopNick><sourcePlatformCode>YJ</sourcePlatformCode><sourcePlatformName>云集</sourcePlatformName><totalAmount>159.00</totalAmount><warehouseCode>CK00001</warehouseCode><extendProps><payId>2020041511082828214616063</payId><paid>159.0</paid><idCard>512324198005242128</idCard><payEntNo>ZF14021901</payEntNo><tax>13.27</tax><payEntName>支付宝（中国）网络技>术有限公司</payEntName><buyerPhone>YUNJI_96975689906</buyerPhone><idCardType>1</idCardType><insureAmount>0</insureAmount><hzPurchaserId>代理蓉</hzPurchaserId></extendProps></deliveryOrder><orderLines><orderLine><actualPrice>145.73</actualPrice><discountAmount>0</discountAmount><inventoryType>ZP</inventoryType><itemCode>8801046306420</itemCode><itemId>8801046306420</itemId><itemName>AGE 20's 精华完美遮瑕粉底 星空黑13>号</itemName><orderLineNo>5280281</orderLineNo><ownerCode>EC000046</ownerCode><planQty>1</planQty><sourceOrderCode></sourceOrderCode><subSourceOrderCode></subSourceOrderCode></orderLine></orderLines></request>";
        final String bbbb = body + body + body + body + body;
        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < 100; ++i) {
            new Thread(() -> {
                for (int j = 0; j < 10000; ++j) {
                    try {
                        long time = System.currentTimeMillis();
//                        String s = YunjiUtil.pushOrder("http://119.23.49.60:8091/outHttp/qstService", body);
//                        String s = YunjiUtil.pushOrder("http://192.168.1.157:8091/outHttp/qstService", body);
//                        String s = YunjiUtil.pushOrder("http://api.hnqst.cn/yunji", body);
                        String responseStr = StringSimpleHttp.startDefaultRequest("http://192.168.1.157:8091").doGet();
//                        String s = YunjiUtil.pushOrder("http://120.78.75.1:80", bbbb);
                        time = System.currentTimeMillis() - time;

                        count.incrementAndGet();
                        countTime.addAndGet(time);
                        maxTime.accumulateAndGet(time, Math::max);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        while (true) {
            Thread.sleep(1000L);
            if (count.get() == 0) {
                continue;
            }

            long a = (System.currentTimeMillis() - beginTime) / 1000;
            long b = count.get();
            String c = new BigDecimal(countTime.get()).divide(new BigDecimal(count.get()), 3, BigDecimal.ROUND_HALF_UP).toPlainString();
            String d = new BigDecimal(count.get()).divide(new BigDecimal(a), 3, BigDecimal.ROUND_HALF_UP).toPlainString();
            long e = maxTime.get();

            String format = String.format("时间: %s, 次数: %s, 平均耗时/次: %s, TPS: %s, 最大耗时: %s", a, b, c, d, e);
            System.out.println(format);
        }
    }
}