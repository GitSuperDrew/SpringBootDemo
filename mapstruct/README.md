## 实践步骤

### 1.引入相关依赖
```xml
<!--引入mapStruct注解包-->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${org.mapstruct.version}</version>
</dependency>
<!-- mapStruct的annotation processors（下面说，可以理解为编译时生成实现类代码的类）-->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>${org.mapstruct.version}</version>
    <scope>provided</scope>
</dependency>
<!--谷歌的一个spi包，看作是工具包就好（下面说spi）-->
<dependency>
    <groupId>com.google.auto.service</groupId>
    <artifactId>auto-service</artifactId>
    <version>1.0-rc5</version>
</dependency>
```
指定 `mapstruct` 版本号
```xml
<properties>
    <org.mapstruct.version>1.3.0.Final</org.mapstruct.version>
</properties>
``` 

### 2.指定maven-compiler-plugin插件
```xml
<!--maven编译默认5.0，使用maven-compiler-plugin插件指定jdk版本，1.6以上-->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.5.1</version> <!-- or newer version -->
    <configuration>
        <source>1.8</source> <!-- depending on your project -->
        <target>1.8</target> <!-- depending on your project -->
        <annotationProcessorPaths>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

### 3.定义转换接口`StudentConverter`
```java
package com.study.module.mapstruct.service;

import com.study.module.mapstruct.dto.StudentDTO;
import com.study.module.mapstruct.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Administrator
 * @date 2020/7/26 上午 10:24
 */
@Mapper
public interface StudentConverter {
    StudentConverter INSTANCE = Mappers.getMapper(StudentConverter.class);

    @Mappings({
            @Mapping(source = "stuId", target = "id"),
            @Mapping(source = "stuName", target = "name"),
            @Mapping(source = "stuAge", target = "age"),
            @Mapping(source = "stuSex", target = "sex")
    })
    StudentDTO demain2DTO(Student student);

}
```

### 4.maven 编译
`mvn compiler` 会得到一个`StudentConverter`的实现类。




### 附录
1. 学习网址：[https://blog.csdn.net/wangxueqing52/article/details/87928948?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-4&spm=1001.2101.3001.4242](https://blog.csdn.net/wangxueqing52/article/details/87928948?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-4&spm=1001.2101.3001.4242)


