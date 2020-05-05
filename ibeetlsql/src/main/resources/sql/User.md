selectByName
===
*根据name获取user
 select * from user where name = #name#
 
selectById
===
*根据id获取user
 select * from user where id = #id#
 
selectAll
===
*得到所有的用户信息
 select * from user
 
selectByNameLike
===
*根据name模糊查询user信息
 select * from user where name like #'%'+name+'%'#