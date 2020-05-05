## [IBeelSQL](http://ibeetl.com/guide/)
### ⚙ 简介
1. 是区别于 `hibernate` 和 `Mybatis` 的另一个 `ORM`框架。

## 🕷 注意的地方：
1. 实体类和sql文件夹下的Markdown文件名必须要对应（例如:`entity/user` 和 `sql/user.md`）
   由此可知，ibeetlsql的重点集中在这两个文件当中（即相当于mybatis的mapper和*.xml文件）。
2. `ibeetlsql` 的模糊查询-值得注意：
    ```sql
   原来的sql为：
       select * from user where name like '%D%'
   使用ibeetlsql之后：
       select * from user where name like %D%  --（即，再拼SQL时，注意不要匹配上单引号"'"）
   另外，再指定的sql/User.md中，我们可以这样来拼接模糊查询
       select * from user where name like #'%'+name+'%'# 
   ```
3. 根据主键查询，如果主键的超过了主句库表实际的记录数，将出现异常，（而不是像其他框架那样，自己处理异常，返回个空值）
4. 支持逻辑表达式
    `user.gender==1?’女’:’男’   #支持 逻辑与，或，非，以及三元表达式`

## 🕊 本项目学习需要注意的地方：
1. TODO 引入静态资源未成功！