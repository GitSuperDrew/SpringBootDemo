# MapStruct 示例步骤演示
学习网址：[https://blog.csdn.net/wangxueqing52/article/details/87928948?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-4&spm=1001.2101.3001.4242](https://blog.csdn.net/wangxueqing52/article/details/87928948?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-4&spm=1001.2101.3001.4242)


## (一) 实践步骤
### 1.引入相关依赖
```xml
<dependencies>
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
</dependencies>
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
`mvn compiler` 会得到一个`StudentConverter`的实现类。**尽量保证编译无警告/错误**

### 5.编写测试类 `StudentConverterTest.java`
```java
package com.study.module.mapstruct.service;

import com.study.module.mapstruct.converter.StudentConverter;import com.study.module.mapstruct.dao.StudentDao;
import com.study.module.mapstruct.dto.StudentDTO;
import com.study.module.mapstruct.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @date 2020/7/26 上午 10:30
 */
@SpringBootTest
class StudentConverterTest {
    @Resource
    private StudentDao studentDao;

    @Test
    void demain2DTO() {
        Student student = Student.builder()
                .stuId(1)
                .stuName("Drew")
                .stuAge(12)
                .stuSex("female")
                .build();
        StudentDTO studentDTO = StudentConverter.INSTANCE.demain2DTO(student);
        System.out.println(studentDTO);
    }

    @Test
    void toStudentDTO() {
        Student student = studentDao.queryById(2);
        System.out.println(StudentConverter.INSTANCE.demain2DTO(student));
    }
}
```
⚠注意：查看`pom.xml`文件是否有 junit测试依赖：如无可加入以下依赖：
```xml
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-launcher</artifactId>
    <version>1.6.2</version>
    <scope>test</scope>
</dependency>
```


## (二)多对一
### 1.新建演示的DO和DTO
> 例如：Sku 和 Item 两个DO，一个 SkuDTO 
>
```java
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private Long id;
    private String title;
}
 
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sku {
    private Long id;
    private String code;
    private Integer price;
}
 
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SkuDTO {
    private Long skuId;
    private String skuCode;
    private Integer skuPrice;
    private Long itemId;
    private String itemName;
}
```
### 2.创建ItemConverter(映射)接口
> MapStruct就会自动实现该接口
```java
@Mapper
public interface ItemConverter {
    ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);    @Mappings({
            @Mapping(source = "sku.id",target = "skuId"),
            @Mapping(source = "sku.code",target = "skuCode"),
            @Mapping(source = "sku.price",target = "skuPrice"),
            @Mapping(source = "item.id",target = "itemId"),
            @Mapping(source = "item.title",target = "itemName")
    })
    SkuDTO domain2dto(Item item, Sku sku);
}
```
### 3.创建测试类
> 将Item 和 Sku 两个 DO对象，映射成一个 DTO 对象 SkuDTO
```java
public class ItemConverterTest {
    @Test
    public void test() {
        Item item = new Item(1L, "iPhone X");
        Sku sku = new Sku(2L, "phone12345", 1000000);
        SkuDTO skuDTO = ItemConverter.INSTANCE.domain2dto(item, sku);
        assertNotNull(skuDTO);
        assertEquals(skuDTO.getSkuId(),sku.getId());
        assertEquals(skuDTO.getSkuCode(),sku.getCode());
        assertEquals(skuDTO.getSkuPrice(),sku.getPrice());
        assertEquals(skuDTO.getItemId(),item.getId());
        assertEquals(skuDTO.getItemName(),item.getTitle());
    }
}
```


### 附录
1. 学习网址：[https://blog.csdn.net/wangxueqing52/article/details/87928948?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-4&spm=1001.2101.3001.4242](https://blog.csdn.net/wangxueqing52/article/details/87928948?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-4&spm=1001.2101.3001.4242)


