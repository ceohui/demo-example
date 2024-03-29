
## 
客户端: Robo 3T(Robomongo)

## 资料
mongodb: https://docs.mongodb.com/

nosql: http://www.runoob.com/mongodb/nosql.html

## nosql

### NoSQL的优点/缺点:
1. 优点:
- 高可扩展性
- 分布式计算
- 低成本
- 架构的灵活性，半结构化数据
- 没有复杂的关系
2. 缺点:
- 没有标准化
- 有限的查询功能（到目前为止）
- 最终一致是不直观的程序

### BASE
BASE：Basically Available, Soft-state, Eventually Consistent。 由 Eric Brewer 定义。
    CAP理论的核心是：一个分布式系统不可能同时很好的满足一致性，可用性和分区容错性这三个需求，最多只能同时较好的满足两个。
    BASE是NoSQL数据库通常对可用性及一致性的弱要求原则:
    Basically Availble --基本可用
    Soft-state --软状态/柔性事务。 "Soft state" 可以理解为"无连接"的, 而 "Hard state" 是"面向连接"的
    Eventual Consistency --最终一致性 最终一致性， 也是是 ACID 的最终目的。
    
### NoSQL 数据库分类
类型	部分代表
特点
1. 列存储	
    Hbase
    Cassandra
    Hypertable
 顾名思义，是按列存储数据的。最大的特点是方便存储结构化和半结构化数据，方便做数据压缩，对针对某一列或者某几列的查询有非常大的IO优势。
2. 文档存储
    MongoDB
    CouchDB
 文档存储一般用类似json的格式存储，存储的内容是文档型的。这样也就有有机会对某些字段建立索引，实现关系数据库的某些功能。
3. key-value存储
    Tokyo Cabinet / Tyrant
    Berkeley DB
    MemcacheDB
    Redis
 可以通过key快速查询到其value。一般来说，存储不管value的格式，照单全收。（Redis包含了其他功能）
4. 图存储
    Neo4J
    FlockDB
 图形关系的最佳存储。使用传统关系数据库来解决的话性能低下，而且设计使用不方便。
5. 对象存储
    db4o
    Versant
 通过类似面向对象语言的语法操作数据库，通过对象的方式存取数据。
6. xml数据库
    Berkeley DB XML
    BaseX
 高效的存储XML数据，并支持XML的内部查询语法，比如XQuery,Xpath。