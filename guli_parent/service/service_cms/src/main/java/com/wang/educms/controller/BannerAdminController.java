package com.wang.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.R;
import com.wang.educms.pojo.CrmBanner;
import com.wang.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台banner增删改查
 *
 * @author WangMengcheng
 * @since 2020-12-30
 */
@RestController
// @CrossOrigin
@Api(description = "后台banner管理")
@RequestMapping("/educms/bannerAdmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{current}/{limit}")
    public R pageBanner(@PathVariable Long current, @PathVariable Long limit) {

        Page<CrmBanner> pageParam = new Page<>(current, limit);
        crmBannerService.page(pageParam,null);
        return R.ok().data("rows", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner) {
        crmBannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("updateBanner")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        boolean b = crmBannerService.removeById(id);
        if(!b){
            return R.error();
        }else {
            return R.ok();
        }
    }
}

