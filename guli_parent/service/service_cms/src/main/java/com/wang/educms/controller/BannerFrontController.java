package com.wang.educms.controller;


import com.wang.commonutils.R;
import com.wang.educms.pojo.CrmBanner;
import com.wang.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台banner显示
 *
 * @author WangMengcheng
 * @since 2020-12-30
 */
@RestController
// @CrossOrigin
@Api(description = "网站首页Banner列表")
@RequestMapping("/educms/bannerFront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "获取首页banner")
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = crmBannerService.list(null);
        return R.ok().data("bannerList", list);
    }
}

