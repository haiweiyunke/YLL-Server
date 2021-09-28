package yll.service.model;

/**
 * 业务常量类
 * @author cc
 */
public class YllConstants {

    // ==============================通用==========================================
    /** 删除 */
    public static final Integer ZERO = 0;
    /** 正常 */
    public static final Integer ONE = 1;
    /** 隐藏 */
    public static final Integer TWO = 2;
    /** 隐藏 */
    public static final Integer LAST = 9999;
    /**  请求TOKEN */
    public static final String TOKEN_NAME = "TOKEN";
    /** +1 */
    public static final Integer ADD = 1;
    /** -1 */
    public static final Integer SUBTRACT = -1;
    /** 上传文件夹 */
    public static final String FILE_UPLOAD = "/upload/";
    // ==============================APP登录类型==========================================
    /** 验证码 */
    public static final Integer LOGIN_CODE = 1;
    /** 密码 */
    public static final Integer LOGIN_PASSWORD = 2;
    /** 微信 */
    public static final Integer LOGIN_WECHAT = 3;
    /** 支付宝 */
    public static final Integer LOGIN_ALI = 4;

    /** APP */
    public static final Integer LOGIN_FLAG_APP = 1;

    /** OAuth2.0 */
    public static final Integer LOGIN_FLAG_H5 = 2;

    // ==============================业务==========================================
    /** 收藏 */
    public static final String YLL_COLLECTS = "collects";
    /** 点赞 */
    public static final String YLL_LIKES = "likes";
    /** 分享 */
    public static final String YLL_SHARE = "share";
    /** 浏览 */
    public static final String YLL_BROWSES = "browses";
    /** 历史记录 */
    public static final String YLL_HISTORIES = "histories";
    // ==============================表==========================================
    /** 用户表 */
    public static final String TABLE_CUSTOMER = "customer";
    /** 用户表 */
    public static final String TABLE_DYNAMIC = "dynamic";


    /** 课件表 */
    public static final String TABLE_COURSEWARES = "coursewares";
    /** 烟草知识表 */
    public static final String TABLE_KNOWLEDGES = "knowledges";
    /** 微视表 */
    public static final String TABLE_MICROVIDEO = "microvideo";
    /** 长廊表 */
    public static final String TABLE_CORRIDORS = "corridors";
    /** 产品表 */
    public static final String TABLE_PRODUCTS = "products";
    /** 消费者活动 */
    public static final String TABLE_HUNDRED_TALKS = "hundredTalks";
    /** 员工文化 */
    public static final String TABLE_HUNDRED_DEDICATES = "hundredDedicates";
    /** 员工文化-视频 */
    public static final String TABLE_HUNDRED_DEDICATES_MEMORIES = "dedicatesMemories";
    /** 员工文化-书画 */
    public static final String TABLE_HUNDRED_DEDICATES_PAINTING = "dedicatesPainting";
    /** 员工文化-摄影 */
    public static final String TABLE_HUNDRED_DEDICATES_PHOTOGRAPHY = "dedicatesPhotography";
    /** 员工文化-诗歌 */
    public static final String TABLE_HUNDRED_DEDICATES_POETRY = "dedicatesPoetry";
    /** 直播 */
    public static final String TABLE_LIVE = "live";

    // ============================== 短 信 ==========================================
    /** 注册验证码 */
    public static final String REG_VERIFYCODE = "YLL:REG_VERIFY_CODE";
    /** 注册验证码失效时间 */
    public static final String REG_VERIFYCODE_INVALID_TIME = "regVerifyCodeInvalidTime";
    /** 注册验证码已发送的条数 */
    public static final String REG_VERIFYCODE_SEND_COUNT = "regVerifycodeSendCount";
    /** 注册验证码发送最大条数 */
    public static final int REG_VERIFYCODE_SEND_COUNT_MAX = 2;
    /** 验证码发送提示 */
    public static final String VERIFYCODE = "欢迎您注册，此验证码有效期为2分钟，逾期后请重新申请验证码,本次验证码为：";
    /** 短信类型--注册 */
    public static final String SMS_REGISTER = "register";


    // ============================== 支 付 ==========================================
    /** 订单流水类型--充值 */
    public static final String PAY_ORDER_TYPE_ONE = "walletOrderType-payRecharge";
    /** 支付-待支付 */
    public static final Integer PAY_STATE_ONE = 21;
    /** 支付-已支付 */
    public static final Integer PAY_STATE_TWO = 22;
    /** 支付-取消支付 */
    public static final Integer PAY_STATE_THREE = 23;
    /** 支付-商品描述 */
    public static final String PAY_DESCRIPTION = "粉条儿充值";
    /** 支付渠道-微信-APP */
    public static final String PAY_TYPE_ONE = "wechatApp";
    /** 支付渠道-微信-小程序 */
    public static final String PAY_TYPE_TWO = "wechatMini";


    // ==============================字典表==========================================
    /** 用户地址标签--其它 */
    public static final String DIC_ADDRESSES_TAG_OTHER = "addressesOther";
    /** 积分明细--活动 */
    public static final String INTEGRAL_ACTIVITY = "activityIntegral";
    /** 积分明细--答题/积分兑换比率 */
    public static final String INTEGRAL_QUESTIONS_RATE = "activityQuestionsRate";
    /** 活动类型--知识竞赛 */
    public static final String ACTIVITIES_QUIZ = "quiz";
    /** 鲁烟大事记--年代 */
    public static final String EVENTS_YEARS = "eventsYears";

}
