package com.catchu.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catchu.util.JaxbUtil;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * 支付回调流水,将微信回调回来的xml数据格式化成json格式数据
 * <xml>
 *     <appid>
 *         <![CDATA[wxfb71b73289534a80]]>
 *     </appid>
 *     <attach>
 *         <![CDATA[184]]>
 *     </attach>
 *     <bank_type>
 *         <![CDATA[CMBC_CREDIT]]>
 *     </bank_type>
 *     <cash_fee>
 *         <![CDATA[10]]>
 *     </cash_fee>
 *     <device_info>
 *         <![CDATA[WEB]]>
 *     </device_info>
 *     <fee_type>
 *         <![CDATA[CNY]]>
 *     </fee_type>
 *     <is_subscribe>
 *         <![CDATA[N]]>
 *     </is_subscribe>
 *     <mch_id>
 *         <![CDATA[1501438571]]>
 *     </mch_id>
 *     <nonce_str>
 *         <![CDATA[EafBARxFmeLCWwMt3obDRafWiJi5tO5N]]>
 *     </nonce_str>
 *     <openid>
 *         <![CDATA[odGdO5USvnGjtJIAktSulk5oKX-Q]]>
 *     </openid>
 *     <out_trade_no>
 *         <![CDATA[W201907091045001621001]]>
 *     </out_trade_no>
 *     <result_code>
 *         <![CDATA[SUCCESS]]>
 *     </result_code>
 *     <return_code>
 *         <![CDATA[SUCCESS]]>
 *     </return_code>
 *     <sign>
 *         <![CDATA[179871FCBFB2F3925EB01F4BD20EF545]]>
 *     </sign>
 *     <time_end>
 *         <![CDATA[20190709104518]]>
 *     </time_end>
 *     <total_fee>10</total_fee>
 *     <trade_type>
 *         <![CDATA[JSAPI]]>
 *     </trade_type>
 *     <transaction_id>
 *         <![CDATA[4200000341201907093464198905]]>
 *     </transaction_id>
 * </xml>
 * @author junzhongliu
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class PayCallBackFlow {

    @XmlElement(name = "appid")
    private String appid;

    @XmlElement(name = "attach")
    private String attach;

    @XmlElement(name = "bank_type")
    private String bank_type;

    @XmlElement(name = "cash_fee")
    private Integer cash_fee;

    @XmlElement(name = "device_info")
    private String device_info;

    @XmlElement(name = "fee_type")
    private String fee_type;

    @XmlElement(name = "is_subscribe")
    private String is_subscribe;

    @XmlElement(name = "mch_id")
    private String mch_id;

    @XmlElement(name = "nonce_str")
    private String nonce_str;

    @XmlElement(name = "openid")
    private String openid;

    @XmlElement(name = "out_trade_no")
    private String out_trade_no;

    @XmlElement(name = "result_code")
    private String result_code;

    @XmlElement(name = "sign")
    private String sign;

    @XmlElement(name = "time_end")
    private String time_end;

    @XmlElement(name = "total_fee")
    private Integer total_fee;

    @XmlElement(name = "trade_type")
    private String trade_type;

    @XmlElement(name = "transaction_id")
    private String transaction_id;

    @XmlElement(name = "settlement_total_fee")
    private Integer settlement_total_fee;

    @XmlElement(name = "cash_fee_type")
    private String cash_fee_type;

    @XmlElement(name = "coupon_fee")
    private Integer coupon_fee;

    @XmlElement(name = "coupon_count")
    private Integer coupon_count;

    @XmlElement(name = "sign_type")
    private Integer sign_type;

    @XmlElement(name = "err_code")
    private String err_code;

    @XmlElement(name = "err_code_des")
    private String err_code_des;

    @XmlElement(name = "return_code")
    private String return_code;

    @XmlElement(name = "return_msg")
    private String return_msg;

    public static void main(String[] args) throws Exception{
        String str = "<xml><appid><![CDATA[wxfb71b73289534a80]]></appid><attach><![CDATA[187]]></attach><bank_type><![CDATA[CMBC_CREDIT]]></bank_type><cash_fee><![CDATA[10]]></cash_fee><device_info><![CDATA[WEB]]></device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1501438571]]></mch_id><nonce_str><![CDATA[4iyUaVvC7cCxMcL0UdE6yEtu5SA6Xp45]]></nonce_str><openid><![CDATA[odGdO5USvnGjtJIAktSulk5oKX-Q]]></openid><out_trade_no><![CDATA[W201907091244049624599]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[AADC5246AB379418CAD3073237F452D1]]></sign><time_end><![CDATA[20190709124458]]></time_end><total_fee>10</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000351201907091147515673]]></transaction_id></xml>";
        PayCallBackFlow orderInfo = JaxbUtil.converToJavaBean(str, PayCallBackFlow.class);
        System.out.println(JSONObject.toJSONString(orderInfo));

    }
}
