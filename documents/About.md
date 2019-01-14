# 项目规划
## About Project
- 背景：
 - 目前同学们一般都在google scholar 或者在一些paper数据库上检索，但一般不能针对paper内容进行检索。每个同学会针对个人研究方向收藏很多solid paper 但没有很好的共享途径，目前共享文件的工具有seafile,gitlab等，但不能针对内容检索，不能隔离。

- 问题：
  - 1. 无法针对paper内容进行检索
  - 2. 同学间无法很好的共享paper

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
  - 定制化检索
    - 根据领域选择，检索相关领域paper
  - 搜索排名
    - 根据点击率进行搜索排名调整
  - 文档解析
    - 对PDF.DOCX等文件进行解析
  - 文档权限
    - 针对用户实现文件的共享和私有

## Iterations
### Iteration one
 - 评估和选择前后端框架，定义接口和数据类
### Week1
 - 经过评估前端框架使用Reactjs+AntDesign,实现前后端分离
 - 后端使用SpringMVC框架
 - 检索框架使用Solr

### Week2
 - 定义数据模型
 - 熟悉Reactjs和AntDesign框架使用和编码

### Week3
 - 定义数据接口
 - 熟悉SpringMVC框架

### Week4
 - 熟悉Solr框架
 - 针对数据模型完成Solr配置

---
### Iteration two
 - 实现搜索页面和后端服务框架搭建
### goals
 - 实现搜索页面布局，搜索前端数据分页，搜索内容展示
 - 实现solr分词配置，solr索引配置，solr API封装，搜索内容高亮匹配
### Week 5
 - 实现框架搭建，创建github库，push代码
 - 后端：搭建配置SpringMVC，实现数据类，定义接口，配置swagger
 - 前端：使用ReactJs实现搜索页面

### Week 6
 - 后端：配置solr索引字段，配置分词，编码封装solr API
 - 前端：实现前端数据分页和数据展示样式设计

### Week 7
 - 后端：搜索高亮处理，solr API封装接口调整
 - 前端：修改样式和修改数据处理bugs
---
### Iteration three
 - goals
 - 针对用户设计文件权限和文件隔离
 - 实现上传页面布局，PDF，DOC,DOCX文件解析
 - 英文分词优化
### week 8
 - 前端：实现前端上传paper页面
 - 后端：实现文件上传功能，实现解析PDF,DOC,DOCX类型文件。

### week 9
 - 前端：优化和修复前端上传细节问题
 - 后端：实现文件权限，针对用户选择实现文件隔离和共享

### week 10
 - 后端：搭建FTP文件系统，实现FTPclent管理文件上传和下载，实现文件自动压缩备份定时脚本
---
### Iteration three
 - 实现基于用户搜索记录和下载记录进行搜索推荐功能和paper top10功能
### week11
 - 搜索推荐数据设计
### week12
 - 推荐算法选择，选择一个适合当前场景的推荐算法，调研协同过滤算法
### week13
 - 完成用户搜索推荐功能
### week14
 - 设计和完成paper top 10 功能
### week15
 -  对功能进行测试

### Final
