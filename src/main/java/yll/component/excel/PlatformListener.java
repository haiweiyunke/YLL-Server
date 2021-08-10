package yll.component.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yll.component.excel.vo.PlatformExcelVo;
import yll.service.PlatformService;

import java.util.ArrayList;
import java.util.List;

public class PlatformListener extends AnalysisEventListener<PlatformExcelVo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformListener.class);

    private PlatformService platformService;

    /** 临时参数 */
    private Object params;

    public PlatformListener() {
        super();
    }

    /**
     * service无法直接注入，需要借助构造函数传入
     * @param platformService
     */
    public PlatformListener(PlatformService platformService, Object params) {
        super();
        this.platformService = platformService;
        this.params = params;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<PlatformExcelVo> list = new ArrayList<PlatformExcelVo>();

    @Override
    public void invoke(PlatformExcelVo data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        platformService.excelSaveData(list, params);
        LOGGER.info("存储数据库成功！");
    }

}
