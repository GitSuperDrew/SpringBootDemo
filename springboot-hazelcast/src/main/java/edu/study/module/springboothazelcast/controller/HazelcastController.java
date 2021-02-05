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
 * 分布式缓存技术控制层(服务器已关闭，则hazelcast中的数据将会丢失)
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

    @PostMapping(path = "/list")
    public String saveList(String value) {
        // 创建集群 list
        IList<Object> clusterList = hazelcastInstance.getList("myList");
        clusterList.add(value);
        return "success";
    }

    @GetMapping(value = "/showList")
    public IList<Object> showList() {
        return hazelcastInstance.getList("myList");
    }

    @DeleteMapping(value = "/clearList")
    public String clearList() {
        IList<Object> clusterList = hazelcastInstance.getList("myList");
        clusterList.clear();
        return "success";
    }

    @PostMapping(value = "/queue")
    public String saveQueue(String value) {
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
