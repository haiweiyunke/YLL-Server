package yll.common.constants;


/**
 * 应用业务常量类
 * @author cc
 */
public class ProjectConstants {

    /** 任务流程 */
    public static class Task {

        /** 客服审核 */
        public static final String A1 = "a1";

        /** 任务公布执行 */
        public static final String A2 = "a2";

        /** 任务超时结束 */
        public static final String A3_1 = "a3-1";

        /** 任务订单结束 */
        public static final String A3_2 = "a3-2";

        /** 审核失败 */
        public static final String A3_3 = "a3-3";

        /** 任务取消 */
        public static final String A3_4 = "a3-4";

    }

    /** 任务订单流程 */
    public static class TaskOrder {

        /** 主播接任务 */
        public static final String B1_1 = "b1-1";

        /** MCN确认 */
        public static final String B2_1 = "b2-1";

        /** 企业主审核 */
        public static final String B2_2 = "b2-2";

        /** 订单取消结束 */
        public static final String B5_1 = "b5-1";

        /** 主播待接单 */
        public static final String B1_2 = "b1-2";

        /** 订单取消结束 */
        public static final String B5_2 = "b5-2";

        /** MCN确认 */
        public static final String B2_3 = "b2-3";

        /** 直播带货 */
        public static final String B3 = "b3";

        /** 超时未提交 */
        public static final String B5_3 = "b5-3";

        /** 主播提交订单 */
        public static final String B4 = "b4";

        /** 订单完成 */
        public static final String B5_4 = "b5-4";

        /** 企业主申诉 */
        public static final String B6 = "b6";

        /** 订单取消 */
        public static final String B5_5= "b5-5";

    }

    /** 任务执行中订单流程 */
    public static class TaskLive {

        /** 签合同（电子签约） */
        public static final String C1 = "c1";

        /**  支付  */
        public static final String C2 = "c2";

        /** 发样品 */
        public static final String C3 = "c3";

        /** 主播试用 */
        public static final String C4= "c4";

        /** 脚本编写 */
        public static final String C5 = "c5";

        /** 安排直播 */
        public static final String C6 = "c6";

    }

}
