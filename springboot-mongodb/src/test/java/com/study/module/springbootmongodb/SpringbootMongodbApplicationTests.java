package com.study.module.springbootmongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.study.module.springbootmongodb.dao.GoodsDao;
import com.study.module.springbootmongodb.entity.GoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@SpringBootTest
class SpringbootMongodbApplicationTests {

    @Autowired
    private GoodsDao goodsDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void add() {
        GoodsEntity entity = new GoodsEntity();
        // entity.setId("1"); // 如果使用 ObjectId，就不需要额外处理ID字段了(注释此行即可)
        entity.setCategoryId(3);
        entity.setGoodsName("测试商品C");
        entity.setGoodsStatus(2);
        entity.setLabels("a,b,d");
        GoodsEntity newEntity = goodsDao.add(entity);
        ObjectId id = new ObjectId(newEntity.getId());
        System.out.println("添加记录的时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(id.getDate()));
    }

    @Test
    public void upd() {
        GoodsEntity entity = goodsDao.getById(1);
        entity.setLabels("a,b,c,d");
        UpdateResult updateResult = goodsDao.upd(entity);
        System.out.println("更新记录为：" + updateResult);
        log.info("更新后的记录为[updated]:{}", updateResult);
    }

    @Test
    public void del() {
        DeleteResult deleteResult = goodsDao.delById(1);
        log.info("更新后的记录为[deleted]:{}", deleteResult);
    }

    /**
     * 注意：如果系统使用的是传统的ID，进行程序中自动设置 “_id” 为自动增长/UUID等等其他的策略，请及时处理好 GoodEntity.id 的@Id(注释掉)
     *
     * @see SpringbootMongodbApplicationTests#getByObjectId()
     */
    @Test
    public void getById() {
        GoodsEntity entity = goodsDao.getById(1);
        System.out.println("根据传统的程序设置唯一ID查询：\n" + entity);
        log.info("记录为[detail]:{}", entity);
    }

    /**
     * 使用的是MongoDB自带的ObjectId作为唯一主键，进行查询（推荐，对分布式系统友好 → 因为可以根据ObjectId得到记录的添加时间）
     *
     * @see SpringbootMongodbApplicationTests#getById()
     */
    @Test
    public void getByObjectId() {
        String objectId = "5fb0784e2cbbe8e869ad05f7";
        GoodsEntity entity = goodsDao.getByObjectId(objectId);
        System.out.println("详细商品信息如下：\n" + entity);

        ObjectId id = new ObjectId(entity.getId());
        System.out.println("详细商品插入的日期为：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(id.getDate()));
    }

    @Test
    public void listAll() {
        List<GoodsEntity> entities = goodsDao.listAll();
        System.out.println("所有记录：listAll \n" + entities);
        log.info("所有的记录为[all-listAll]:{}", entities);
    }

    @Test
    public void queryByLabel() {
        List<GoodsEntity> entities = goodsDao.queryPageByLabel("a,b,c,d", 0, 2);
        System.out.println("记录为：\n" + entities);
        log.info("更新后的记录为[queryByLabel]:{}", entities);
    }

    @Test
    public void queryPage() {
        GoodsEntity entity = new GoodsEntity();
        // entity.setCategoryId(1);
        entity.setGoodsName("测试商品B");
        // entity.setGoodsStatus(1);
        // entity.setLabels("a,b,c,*,d");
        List<GoodsEntity> entities = goodsDao.queryPage(entity, 0, 10);
        System.out.println("记录为：\n" + entities.get(0).toString());
        log.info("更新后的记录为[queryByLabel]:{}", entities);
    }

}
