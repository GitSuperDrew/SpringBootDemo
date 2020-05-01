# SpringBootDemo

学习Spring Boot项目的一些小案列，用来深入理解Spring Boot相关知识。
1. mybatis : mybatis + Spring Boot + Freemarker + MySQL 搭建组成


## 项目规范
1. 包名规范（Package）
    * 推荐使用公司或机构的顶级域名作为报名的前缀，保证所使用的报名的唯一性。
    * 包名必须全部为小写字母，且具有实际区分意义。
    * 结合应用分层：
        - Dao 层（数据库访问层）： 使用 `Dao` 做后缀；
        - Service 层（业务处理曾）：使用 `Service` 做后缀；
        - Web 层（页面控制层）：使用 `action` 或 `controller` 做后缀；
                    
2. 类名的命名规范（Class）
    * 类名必须使用驼峰式命名，且具有实际的区分意义。
    * Dao层和Service层：
        - Dao 接口类采用 `实体类 + Dao`，实现类采用：`实体类 + ImplDao`。
        - Service 接口类采用 `模块名 + Service`，实现类采用：`模块名 + ImplService`。 
    * Web层：
        - 一般采用 "模块+操作+Action" 的命名形式。
        
3. 实体类的命令规范（Class）
    1. 分层领域模型规约
        - DO(Data Object)：与数据库表结构对应，通过DAO层向上传输数据源对象。
        - DTO(Data Transfer Object)：数据传输对象，Service或Manager向外传输的对象。
        - BO(Business Object)：业务对象，由Service层输出的封装业务逻辑的对象。
        - AO(Application Object)：应用对象，在Web层与Service层之间抽象的复用对象模型，极为贴近展示层，复用度很低。
        - VO(View Object)：显示层对象，通常是Web向模板渲染引擎层传输的对象。
        - Query：数据查询对象，各层接收上层的查询请求，超过2个参数的查询封装，禁止使用Map类传输。
    2. 领域模型类名命名规约
        - 数据对象：xxxxDO，xxxx为数据表名。
        - 数据传输对象：xxxxDTO，xxxx为业务领域相关的名称。
        - 展示对象：xxxxVO，xxxx一般为网页名称。
    3. 注意：`POJO 是DO/DTO/BO/VO的统称，禁止命名成 xxxxPOJO。`
4. Service/DAO层的方法命名规约
    1. 插入的方法命名使用 insert或save 做前缀。
    2. 删除的方法命名使用 delete或remove 做前缀。
    3. 修改的方法命名使用 update 做前缀。
    4. 获取单个对象的方法命名使用 get 做前缀。
    5. 获取多个对象的方法命名使用 list 做前缀，复数结尾。
    6. 获取统计值的方法命名使用 count 做前缀。
<hr>
