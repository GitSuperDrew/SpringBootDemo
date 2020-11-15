package com.study.module.springbootmongodb.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.study.module.springbootmongodb.entity.GoodsEntity;
import com.study.module.springbootmongodb.utils.MongoDbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2020/11/14 下午 10:32
 */
@Component
public class GoodsDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public GoodsEntity add(GoodsEntity entity) {
        return mongoTemplate.save(entity);
    }

    /**
     * 根据ID，修改对应的文档信息
     *
     * @param entity 商品文档信息类
     * @return 修改结果类
     */
    public UpdateResult upd(GoodsEntity entity) {
        Query query = new Query(Criteria.where("id").is(entity.getId()));
        Update update = new Update().set("goodsName", entity.getGoodsName())
                .set("categoryId", entity.getCategoryId())
                .set("goodsStatus", entity.getGoodsStatus())
                .set("labels", entity.getLabels());
        return mongoTemplate.updateFirst(query, update, GoodsEntity.class);
    }

    /**
     * 删除指定的文档
     *
     * @param id 商品文档记录ID
     * @return 删除结果类
     */
    public DeleteResult delById(long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, GoodsEntity.class);
    }

    /**
     * 查看商品详情
     *
     * @param id 商品文档记录ID
     * @return 商品记录信息
     */
    public GoodsEntity getById(long id) {
        Query query = new Query(Criteria.where("id").is(id));
        GoodsEntity entity = mongoTemplate.findOne(query, GoodsEntity.class);
        return entity;
    }

    /**
     * 根据ObjectId 得到详情
     *
     * @param id ObjectId记录ID
     * @return 商品记录信息
     */
    public GoodsEntity getByObjectId(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        GoodsEntity entity = mongoTemplate.findOne(query, GoodsEntity.class);
        return entity;
    }

    /**
     * 得到所有的商品记录信息
     *
     * @return 所有的商品
     */
    public List<GoodsEntity> listAll() {
        List<GoodsEntity> entities = mongoTemplate.find(new Query(), GoodsEntity.class);
        return entities;
    }

    /**
     * 分页某字段使用正则表达式模糊查询，且显示分页，ID倒叙
     *
     * @param label      某个字段
     * @param pageNumber 当前页码
     * @param pageSize   显示条目数
     * @return 分页信息集合
     */
    public List<GoodsEntity> queryPageByLabel(String label, int pageNumber, int pageSize) {
        // 完全匹配
        // Pattern pattern = Pattern.compile("^" + label + "$", Pattern.CASE_INSENSITIVE);
        // 右匹配
        // Pattern pattern = Pattern.compile("^.*" + label + "$", Pattern.CASE_INSENSITIVE);
        // 左匹配
        // Pattern pattern = Pattern.compile("^" + label + ".*$", Pattern.CASE_INSENSITIVE);
        // 模糊匹配
        Pattern pattern = Pattern.compile("^.*" + MongoDbUtils.escapeExprSpecialWord(label) + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("labels").regex(pattern));
        // ID倒序
        query.with(Sort.by("id").descending());
        // 分页
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        query.with(pageRequest);
        return mongoTemplate.find(query, GoodsEntity.class);
    }

    /**
     * 多查询条件，分页，ID倒序
     *
     * @param entity     商品相关字段都可以输入作为筛选参数
     * @param pageNumber 当前页码
     * @param pageSize   显示条目数
     * @return 分页集合商品信息
     */
    public List<GoodsEntity> queryPage(GoodsEntity entity, int pageNumber, int pageSize) {
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(entity.getGoodsName())) {
            Pattern pattern = Pattern.compile("^.*" + entity.getGoodsName() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("goodsName").regex(pattern);
        }
        if (!StringUtils.isEmpty(entity.getLabels())) {
            Pattern pattern = Pattern.compile("^.*" + entity.getLabels() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("labels").regex(pattern);
        }
        if (entity.getCategoryId() > 0) {
            criteria.and("categoryId").is(entity.getCategoryId());
        }
        if (entity.getGoodsStatus() > 0) {
            criteria.and("goodsStatus").is(entity.getGoodsStatus());
        }

        Query query = new Query(criteria);
        // 分页 & ID倒序
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");
        query.with(pageRequest);
        return mongoTemplate.find(query, GoodsEntity.class);
    }
}
