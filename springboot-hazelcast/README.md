# 工程简介
> 使用 spring boot 整合 分布式缓存技术 hazelcast 技术。

# 搭建步骤
> 知识来源：[头条](https://www.toutiao.com/a6827999056736813579/)

1. 技术架构
    - Spring Boot web框架
    - Hazelcast 分布式技术

## 一、引入依赖
```xml
<!--hazelcast 核心依赖包-->
<dependency>
   <groupId>com.hazelcast</groupId>
   <artifactId>hazelcast</artifactId>
</dependency>
<dependency>
   <groupId>com.hazelcast</groupId>
   <artifactId>hazelcast-spring</artifactId>
</dependency>
```

## 二、核心代码
1. 添加应用配置；(非核心)
```properties
# 应用名称
spring.application.name=springboot-hazelcast
# 应用服务 WEB 访问端口
server.port=8080
```
2. 书写 hazelcast 配置类: HazelcastConfiguration.java
```java
@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        // 解决同网段下，不同库项目
        GroupConfig gc = new GroupConfig("hazelGroup");
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("configuration")
                        // Map 中存储条目的最大值。[0 ~ Integer.MAX_VALUE] 默认值为0
                        .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        // 数据释放策[NONE/LRU/LFU] 这是 Map作为缓存的一个参数，用于指定数据的回收算法。默认为 NONE，LRU：最近最少使用策略。
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        // 数据留存时间（0~Integer.MAX_VALUE,缓存相关参数，单位秒，默认为0）
                        .setTimeToLiveSeconds(-1)).setGroupConfig(gc);
        return config;
    }
}
```

3. 添加 hazelcast 控制层，便于测试。
```java
package edu.study.module.springboothazelcast.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Queue;

/**
 * 分布式缓存技术控制层
 *
 * @author drew
 * @date 2021/2/5 15:06
 **/
@Slf4j
@RestController
@RequestMapping(path = "/hazelcast")
public class HazelcastController {
    @Autowired
    @Qualifier("hazelcastInstance")
    private HazelcastInstance hazelcastInstance;

    @PostMapping(value = "/save")
    public String saveMapData(@RequestParam String key, @RequestParam String value) {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("hazelcastMap");
        hazelcastMap.put(key, value);
        return "success";
    }

    @GetMapping(path = "/get")
    public String getMapData(@RequestParam String key) {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("hazelcastMap");
        return hazelcastMap.get(key);
    }

    @GetMapping(path = "/all")
    public Map<String, String> readAllDataFromHazelcast() {
        return hazelcastInstance.getMap("hazelcastMap");
    }

    @GetMapping(path = "/list")
    public String saveList(@RequestParam(required = false) String value) {
        // 创建集群 list
        IList<Object> clusterList = hazelcastInstance.getList("myList");
        clusterList.add(value);
        return "success";
    }

    @DeleteMapping(value = "/showList")
    public IList<Object> showList() {
        return hazelcastInstance.getList("myList");
    }

    @GetMapping(value = "/clearList")
    public String clearList() {
        IList<Object> clusterList = hazelcastInstance.getList("myList");
        clusterList.clear();
        return "success";
    }

    @GetMapping(value = "/queue")
    public String saveQueue(@RequestParam String value) {
        // 创建集群Queue
        Queue<String> clusterQueue = hazelcastInstance.getQueue("myQueue");
        clusterQueue.offer(value);
        return "success";
    }

    @GetMapping(value = "/showQueue")
    public Queue<String> showQueue() {
        Queue<String> clusterQueue = hazelcastInstance.getQueue("myQueue");
        for (String obj : clusterQueue) {
            log.warn("value=" + obj);
        }
        return clusterQueue;
    }

    @DeleteMapping(value = "/clearQueue")
    public String clearQueue() {
        Queue<String> clusterQueue = hazelcastInstance.getQueue("myQueue");
        clusterQueue.clear();
        return "success";
    }
}

```

4. 调用接口汇总
   1. map 类型的数据结构
      1. 存储元素到指定的map（hazelcastMap）中；
         ```text
         请求方式：POST
         请求地址：http://localhost:8080/hazelcast/save
         数据:
           key:phone
           value:13798765425
         ```
      2. 查询指定元素；
         ```text
         请求方式：GET
         请求地址：http://localhost:8080/hazelcast/get?key=name
         ```
      3. 查询所有元素；
         ```text
         请求方式：GET
         请求地址：http://localhost:8080/hazelcast/all
         ```
   2. list 类型的数据结构
      1. 添加元素到指定集合;
         ```text
         请求方式：GET
         请求地址：http://localhost:8080/hazelcast/list?value=Fish
         ```
      2. 查询所有 list 元素;
         ```text
         请求方式：GET
         请求地址：http://localhost:8080/hazelcast/showList
         ```
      3. 清空指定集合;
         ```text
         请求方式：DELETE
         请求地址：http://localhost:8080/hazelcast/clearList
         ```
   3. queue 类型的数据结构
      1. 添加元素;
      2. 查询元素;
      3. 删除元素;
   


# 延伸阅读

