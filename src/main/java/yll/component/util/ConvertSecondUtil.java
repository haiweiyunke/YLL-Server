package yll.component.util;

/**
 * 时分秒格式00:00:00转换秒数
 */
public class ConvertSecondUtil {    //TODO 看看提交过来的直播时长是否转换秤秒，然后开始构思图表

    /**时分秒格式00:00:00转换秒数
     * @param time 			//时分秒格式00:00:00
     * @return  秒数
     */
    public static long getSecond(String time){
        long s = 0;
        if(time.length()==7 || time.length()==8 ){ //时分秒格式00:00:00 或 0:00:00
            int index1=time.indexOf(":");
            int index2=time.indexOf(":",index1+1);
            s = Integer.parseInt(time.substring(0,index1))*3600;//小时
            s+=Integer.parseInt(time.substring(index1+1,index2))*60;//分钟
            s+=Integer.parseInt(time.substring(index2+1));//秒
        }
        if(time.length()==5){//分秒格式00:00
            s = Integer.parseInt(time.substring(time.length()-2)); //秒  后两位肯定是秒
            s+=Integer.parseInt(time.substring(0,2))*60;    //分钟
        }
        return s;
    }

    /** 秒转分钟
     * @param secondsInput 			//秒数
     * @return  分钟数
     */
    public static long getMinutes(long secondsInput){
        long minutes = secondsInput / 60;
        long seconds = secondsInput % 60;
        String format = String.format("%d", minutes, seconds);
        long m = Long.parseLong(format);
        return m;
    }

    /** 秒转分钟
     * @param time 			//时分秒格式00:00:00
     * @return  分钟数
     */
    public static long getMinutes(String time){
        long second = getSecond(time);
        long minutes = getMinutes(second);
        return minutes;
    }

    public static void main(String[] args) {
        int secondsInput = 120;
        long minutes = getMinutes(secondsInput);

        String test = "2:02:36";
        long m = getMinutes(test);
        System.out.println(m);
    }


}
