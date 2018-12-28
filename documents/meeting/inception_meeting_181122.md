### 第二次会议纪要
- 会议目标：讨论产品功能详细设计,接口定义,数据索引定义
此次会议主要讨论产品主体功能的详细设计，针对功能点进行详细分析，同时规范和定义接口。

- 文件上传功能：
  - 多文件上传
  - 文件隔离：公有和私有选择
  - 文件所属者
  - 文件属性更改
- 用户功能
  - 角色：普通用户，管理员用户，游客
  - 游客：无需登陆，可检索公有文件
  - 用户：需登陆，可检索公有文件和所属文件，可进行检索推荐和检索排名。
  - 管理员：需登陆，可检索所有文件，管理所有文件，管理普通用户。
- 检索功能
  - 记录用户检索记录作为推荐和过滤数据
  - 定义检索规则

- 主要接口
接口名|接口定义
---|---
上传|upload(MultipartFile[] file, String userid, String author, String year, String name, String isPrivate)
文件转换|String fileConvertText(InputStream fileStream, String fileType)
检索|query(String keyWord, String userid, String type, int start, int rows)

- 数据索引定义
在solr索引中，我们配置了一下索引字段
```
  <field name="name" type="text_general" indexed="true" stored="true" multiValued="true"/>
   <field name="authors" type="text_general" indexed="true" stored="true" multiValued="true"/>
   <field name="content_type" type="string" indexed="false" stored="true" multiValued="true"/>
   <field name="content" type="text_general" indexed="true" stored="true" multiValued="true"/>
   <field name="year" type="string" indexed="false" stored="true" multiValued="false"/>
   <field name="userid" type="string" indexed="false" stored="true" multiValued="false"/>
   <field name="isprivate" type="string" indexed="false" stored="true" multiValued="false"/>
   <field name="file_url" type="string" indexed="false" stored="true" multiValued="true"/>
   <field name="paper_keyword" type="text_general" indexed="true" stored="true" multiValued="true"/>
```
