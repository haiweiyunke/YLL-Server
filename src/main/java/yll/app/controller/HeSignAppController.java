package yll.app.controller;

import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.service.CommonService;
import yll.service.model.YllConstants;
import yll.service.model.vo.TrainVo;


/**
 * 和签处理类
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/sign")
public class HeSignAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommonService commonService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/sign/list <br>
     * 查询
     */
    @PostMapping(value = "/sign/list")
    public Result<?> slideList() {


        return Result.ok();
    }

}
