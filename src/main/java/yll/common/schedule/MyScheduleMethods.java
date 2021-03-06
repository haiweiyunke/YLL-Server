package yll.common.schedule;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.entity.Activities;
import yll.entity.CustomerRecharge;
import yll.entity.CustomerWalletDetails;
import yll.entity.RecruitEnterpriseDetails;
import yll.service.ActivitiesService;
import yll.service.CustomerRechargeService;
import yll.service.CustomerWalletDetailsService;
import yll.service.RecruitEnterpriseDetailsService;
import yll.service.model.vo.CustomerRechargeVo;
import yll.service.model.vo.RecruitEnterpriseDetailsVo;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class MyScheduleMethods {

    // ==============================Fields===========================================
    @Autowired
    private RecruitEnterpriseDetailsService recruitEnterpriseDetailsService;
    @Autowired
    private CustomerRechargeService customerRechargeService;
    @Autowired
    private CustomerWalletDetailsService customerWalletDetailsService;


    // ==============================Methods==========================================
    /**
     * 招聘信息上下架
     */
    public void recruitEnterpriseDetailsStateExecute() {
        System.out.println("=========更新招聘信息上下架启用开始=========");
        RecruitEnterpriseDetailsVo condition = new RecruitEnterpriseDetailsVo();
        condition.setState(1);
        List<RecruitEnterpriseDetailsVo> todoList = recruitEnterpriseDetailsService.findBySchedule(condition);
        for (RecruitEnterpriseDetailsVo vo :
                todoList) {
            String endTimeStr = vo.getEndTimeStr();
            if("0".equals(endTimeStr)){
                vo.setState(3);     //（0-删除，1-正常，2-下架，3-已过期）
                recruitEnterpriseDetailsService.update(vo);
            }
        }
        System.out.println("=========更新招聘信息上下架启用结束=========");
    }


    /**
     * 支付订单过期
     */
    public void customerRechargeStateExecute() {
        System.out.println("=========处理支付订单过期开始=========");
        CustomerRechargeVo condition = new CustomerRechargeVo();
        condition.setState(21);
        List<CustomerRechargeVo> todoList = customerRechargeService.findBySchedule(condition);
        for (CustomerRechargeVo vo :
                todoList) {
            String dayNum = vo.getDayNum();
            if(!"0".equals(dayNum)){
                vo.setState(23);     //（21-待支付，22-已支付，23-取消支付）
                //修改充值明细状态
                customerRechargeService.update(vo);
                //修改钱包流水明细状态
                CustomerWalletDetails customerWalletDetails = new CustomerWalletDetails();
                customerWalletDetails.setOrderNumber(vo.getOrderNumber());
                customerWalletDetails = customerWalletDetailsService.findBy(customerWalletDetails);
                if(null != customerWalletDetails){
                    customerWalletDetails.setState(23);
                    customerWalletDetailsService.update(customerWalletDetails);
                }
            }
        }
        System.out.println("=========处理支付订单过期结束=========");
    }


  /*  public static void main(String[] args) throws IOException {
        String uriAPI = "https://chs-hn-3chanl.ylbz.henan.gov.cn/localcfc-web/";

        HttpGet httpRequst = new HttpGet(uriAPI);
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
        HttpEntity httpEntity = httpResponse.getEntity();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("↓↓↓↓↓请求返回状态码↓↓↓↓↓");
        System.out.println(statusCode);
        String result = EntityUtils.toString(httpEntity);//取出应答字符串
        System.out.println("↓↓↓↓↓请求返回报文↓↓↓↓↓");
        System.out.println(result);

    }*/


}
