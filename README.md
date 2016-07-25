# 天气搜索网站
天气搜索网站 / 天气预报 / Spring / Spring MVC / Hibernate / Tiles / jQuery / Bootstrap / Maven  

## 题目描述
题目: 天气搜索网站  
类别: 平台方向  
描述: 搭建一个 website, 提供搜索功能，输入城市，可以搜索出此城市的未来7天（含当天）的天气预报信息  
要求:  
  
    1. 定时从天气网（http://www.weather.com.cn/）抓取全国各主要城市的天气信息  
    2. 处理后保存至数据库（自选)  
    3. 搭建website，提供一个搜索页面，输入为城市名称（可提供输入提示功能），
       输出结果为此城市的天气预报信息

建议周期:2周  

## 进度
- 2016.07.20 周三 1.选题; 搭建环境: JDK / IDEA / MAVEN / MySql / SpringMVC;
- 2016.07.20 周三 2.查看天气网的页面结构:省份列表/具体天气页面
- 2016.07.21 周四 构思画出网站各页面的原型; 考虑数据库设计; 批量下载天气图标66个
- 2016.07.22 周五 开会 没怎么做
- 2016.07.23 周六 尝试整合 MyBatis: 失败
- 2016.07.24 周日 1.整合 Hibernate; 爬取所有城市数据并插入数据库(2563条)
- 2016.07.24 周日 2.完成输入提示; 完成爬取天气数据;
- 2016.07.24 周日 3.卡在 `@OneToMany`: 更新 `City` 对象却不会保存 `WeatherRecord` 到数据库
- 2016.07.25 周一 1.完成展示天气数据页面; 使用 `flush` 方法解决上述问题;
- 2016.07.25 周一 2.添加“热门城市”功能; 上传至 GitHub
- 2016.07.25 周一 3.修正获取天气的逻辑; 关闭第三方包的日志输出; 微调页面

注：
1. 天气网页面错误：http://m.weathercn.com/citychange.jsp?partner= 该页面的海南的超链接指向湖南  
2. `SQLException: Connection is read-only. Queries leading to data modification`异常:
   因为配置的 `get*` 方法都是只读的，换一个方法名即可写入(flush 需要写入数据库)  

## 项目结构
<pre>
 src
  ├─main
  │  ├─java
  │  │  └─com/youthlin/weather
  │  │              ├─controller  # Spring MVC 控制器
  │  │              ├─dao         # 数据库访问 DAO 包
  │  │              │  └─impl     # DAO 实现类
  │  │              ├─po          # 实体类
  │  │              ├─service     # Controller 调用服务
  │  │              │  └─impl     # Service 实现类
  │  │              └─task        # 爬取城市数据及天气数据
  │  ├─resources                  # 资源文件夹(cities.txt 爬取的城市数据; 
  │  │                              hibernate.cfg.xml Hibernate文件;
  │  │                              logback.xml 日志配置文件)
  │  └─webapp
  │      ├─static                 # 静态 Web 文件
  │      │  ├─css
  │      │  ├─fonts
  │      │  ├─images
  │      │  │  └─small            # 天气图标
  │      │  │      ├─day
  │      │  │      └─night
  │      │  └─js
  │      └─WEB-INF
  │          └─pages
  │              ├─content        # 各页面内容
  │              └─layout         # Tiles 模板
  └─test                          # Test
      └─java
          └─com/youthlin/weather
                      └─test
                          ├─jsoup
                          └─sql
</pre>
