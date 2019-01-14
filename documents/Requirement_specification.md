# Requirement Specification
## Usecase Diagram
![system_user_case](https://github.com/zittygit/PaperSearch/blob/master/documents/Diagrams/system_user_case.png)
## Use Cases
### 检索
- 当关键字太少，前端不向后台发送检索请求，而是提醒用户体检关键字保证检索性能。
![](https://github.com/zittygit/PaperSearch/blob/master/documents/UI/20181228081644.png)
- 当用户是登录状态，检索后数据根据用户点击数据，对检索后数据进行过滤，采用相似度算法对数据进行排序，同时将返回排名前20条数据。
- 用户是非登录状态（游客），检索后不进行排序，仅返回前20条数据。
![](https://github.com/zittygit/PaperSearch/blob/master/documents/UI/20181228081444.png)

## Domian Model
![](https://github.com/zittygit/PaperSearch/blob/master/documents/UI/er.png)

## State Model
![](https://github.com/zittygit/PaperSearch/blob/master/documents/Diagrams/state1.png)
## System Sequence Diagram
![](https://github.com/zittygit/PaperSearch/blob/master/documents/Diagrams/sq.png)
