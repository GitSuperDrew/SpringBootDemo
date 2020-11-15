package com.study.module.springbootmongodb.controller;

import com.mongodb.client.result.UpdateResult;
import com.study.module.springbootmongodb.dao.GoodsDao;
import com.study.module.springbootmongodb.entity.GoodsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品相关控制层
 *
 * @author Administrator
 * @date 2020/11/15 上午 11:58
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 查询所有商品
     *
     * @return 商品集合
     */
    @GetMapping(value = "/mongo/findAll")
    public List<GoodsEntity> findAll() {
        return goodsDao.listAll();
    }

    /**
     * 根据ObjectId，得到商品详情
     *
     * @param id ObjectId(MongoDB自动生成的ID)
     * @return 商品详情
     */
    @GetMapping(value = "/mongo/findOne")
    public GoodsEntity findOne(@RequestParam String id) {
        return goodsDao.getByObjectId(id);
    }

    /**
     * 根据标签模糊分页查询
     *
     * @param label      商品标签
     * @param pageNumber 页码
     * @param pageSize   数目
     * @return 商品集合
     */
    @GetMapping(value = "/mongo/pageLike")
    public List<GoodsEntity> pageLike(@RequestParam(value = "label", required = false) String label,
                                      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return goodsDao.queryPageByLabel(label, pageNumber, pageSize);
    }

    /**
     * 添加商品
     *
     * @param goodsEntity 商品信息
     * @return 是否成功
     */
    @PostMapping(value = "/mongo/saveOne")
    public String saveOne(@RequestBody GoodsEntity goodsEntity) {
        GoodsEntity entity = goodsDao.add(goodsEntity);
        if (!ObjectUtils.isEmpty(entity)) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 更新商品
     *
     * @param entity 商品信息
     * @return
     */
    @PutMapping(value = "/mongo/update")
    public String update(@RequestBody GoodsEntity entity) {
        UpdateResult result = goodsDao.upd(entity);
        if (!ObjectUtils.isEmpty(result)) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 删除指定的商品
     *
     * @param objectId 商品ID
     * @return 是否成功
     */
    @DeleteMapping(value = "/mongo/delete")
    public String delete(@RequestParam(value = "id") String objectId) {
        if (goodsDao.delByObjectId(objectId)) {
            return "success";
        } else {
            return "fail";
        }
    }

}
