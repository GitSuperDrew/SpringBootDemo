## mybatis逆向工程的两种方式
 1. 使用配置类`generatorConfig.xml`
 
 2. 安装IDEA插件：`Easy Code`
    * 使用 IDEA 自带的 Database 连接数据库；
    * 安装 IDEA 插件 `Easy Code` ：setting → plugins（search: Easy Code） → Install；
    * 打开 Database 找到对应的表，右击 → 找到`Easy Code` → `Generate Code` → 选择需要生成的内容，例如：`controller/dao/entity/service等等`；
    
  3. Oracle 数据库的类型 和 Java 的数据类型有些差异，需要相互转换([转换规则](https://www.cnblogs.com/softidea/p/7101091.html))
    ![图片规则](src\main\resources\static\oracle-java.png)
  