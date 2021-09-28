package yll.component.pay.enums;


/**
 * 订单操作枚举类
 * @author yll
 */
public enum OrdersOperateEnum {
    SUBMIT(1,"订单提交"),
    PAYMENT(2,"订单付款"),
    CANCEL(3,"订单取消"),
    DELETE(4,"订单删除"),
    ABOLISH(5,"订单废除"),
    REFUND(6,"订单退款");
    public int code;

    public String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    OrdersOperateEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据code获得msg
     * @param code
     * @return
     */
    @SuppressWarnings("unlikely-arg-type")
    public static String getValueByCode(Integer code){
        if(null == code) {
            return "未知";
        }
        for(OrdersOperateEnum platformFree:OrdersOperateEnum.values()){
            if(code.equals(platformFree.getCode())){
                return platformFree.getMsg();
            }
        }
        return "未知";
    }

    public static OrdersOperateEnum getByCode(Integer code){
        for(OrdersOperateEnum transactType : values()){
            if (code.equals(transactType.getCode())) {
                //获取指定的枚举
                return transactType;
            }
        }
        return null;
    }
}
