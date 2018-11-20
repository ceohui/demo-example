

https://neo4j.com/developer/java/

http://bboyjing.github.io/categories/Neo4j/


## 
bolt://localhost:32768
neo4j
123456

##

```
// 创建设备节点
CREATE (d:Device{deviceId:"1001"})
// 创建用户节点
CREATE (u:User{userId:"123"})
// 创建登录关系
MATCH (d:Device),(u:User) 
where d.deviceId='1001' and u.userId='123'
CREATE (u)-[r:login ]->(d)


// 创建唯一约束
CREATE CONSTRAINT ON Device
ASSERT deviceId IS UNIQUE


```