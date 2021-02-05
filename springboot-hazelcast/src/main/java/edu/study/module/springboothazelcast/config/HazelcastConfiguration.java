package edu.study.module.springboothazelcast.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * hazelcast 配置类
 *
 * @author drew
 * @date 2021/2/5 14:50
 **/
@Configuration
public class HazelcastConfiguration {

    /**
     * <pre>
     * 1. NONE:当设置为 NONE 时，不会发生数据回收，同时 max-size 会失效。但是仍然可以使用 time-to-live-seconds 和 max-idle-seconds 参数来控制数据存留时间。
     * 2. LRU: 最近最少使用 策略。
     * 3. LFU： 最不常用的使用 策略。
     * </pre>
     * <pre>
     * time-to-live-seconds(TTL)
     * 1.数据留存时间（0~Integer.MAX_VALUE），缓存相关参数，单位秒，默认为0.这个参数决定了一条数据在 map 中的停留时间。
     * 2.当数据在 MAP中留存超过这个时间并且没有被更新时，他会根据指定的回收策略从 MAP 中移除。值为0时，意味着无穷大。
     * </pre>
     * @return 配置信息类
     */
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
