

### limit offset 优化
MySQL的limit给分页带来了极大的方便，但数据量一大的时候，limit的性能就急剧下降。
```
为什么limit大，时间会变慢
http://www.cnblogs.com/hustzzl/p/7067830.html


【优化方向一】：根据某有序列，设置where x>x，来跳过前面不关心的数据

优化1：如果是按id从大到小排序的，可以改成
原：select * from user order by id limit 30000,10;
新：select * from user where id > last_page_id order by id limit 10;

优化2：如果是按时间从大到小排序的

【优化方向二】：根据mysql读取数据页的方式去优化
验证执行sql加载了多少的数据页和索引页
select index_name,count(1) from information_schema.INNODB_BUFFER_PAGE group  by index_name;

优化3：
原：select * from user order by id limit 30000,10;
新：select a.* from user a inner join (select id from user limit 3000,10) b on a.id=b.id;



```