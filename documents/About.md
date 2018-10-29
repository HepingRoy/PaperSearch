# 项目规划
## About Project
PaperSearch是一个B/S架构的Paper内容检索服务。把实验室收录比较solid的paper集中起来提供内容检索和论文推荐，避免各位同学去搜索引擎上盲目检索论文。该项目主要提供Paper上传，内容检索，定制化搜索，paper隔离，paper搜索推荐等功能。
## Core Artifacts
- PaperSearch 技术框架
  - Solr
    - 提供倒排索引
  - SpringMVC
    - 提供业务服务
  - AntDesign+ReactJS
    - 提供前端展示
- 关键技术
  - 分词
    - 英文分词，这里主要针对计算机类词类
  - 搜索推荐
    - 根据关键字搜索率和其他特征推荐搜索关键字
  - 搜索排名
    - 根据点击率进行搜索排名调整
  - 文档解析
    - 对PDF等文件进行解析    
## Iterations
### Iteration one
 - 实现搜索页面和后端服务框架搭建
### goals
 - 实现搜索页面布局，搜索前端数据分页，搜索内容展示。
 - 实现solr分词配置，solr索引配置，solr API封装，搜索内容高亮匹配。
### Week 1
 - 实现框架搭建，创建github库，push代码。
 - 后端：搭建配置SpringMVC
 - 前端：使用ReactJs实现搜索页面

### Week 2
 - 后端：配置solr索引字段，配置分词，编码封装solr API。
 - 前端：实现前端数据分页和数据展示样式设计。

### Week 3
 - 后端：搜索高亮处理，solr API封装接口调整
 - 前端：修改样式和修改数据bugs
---
### Iteration two
 - goals
 - 实现上传页面布局，PDF，DOC,DOCX文件解析。
 - 英文分词优化。
### week

---
### Final
