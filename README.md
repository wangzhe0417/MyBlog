# **项目背景**
因为自学了Java开发和一些框架，虽然大致了解工作流程，但是没有具体实现过还是不行，所以就想着做个东西出来练练手，
刚好在学习java期间一直在CSDN上记录学习过程，
所以就想着做一个小型的博客平台，实现基本的用户登录、文章编写与发布、文章分类归档等功能。
# **项目介绍**
## **使用的工具与技术**
  使用MySQL数据库存储数据，Eclipse为开发工具，JDK使用的是1.7版本，Tomcat使用的是8.11版本。
  后端使用S2SH框架组合进行开发，页面使用EL表达式和OGNL表达式进行数据渲染。
  部分异步请求的页面使用jQuery进行ajax请求，Vue.js进行数据渲染，Bootstrap框架搭建前端页面。 
  其中编写文章使用的是自制的一个简易markdown编辑器，用marked.js进行markdown语法解析，然后用highlight.js进行语法高亮。
## **各模块展示**
### **首页**
![这里写图片描述](http://img.blog.csdn.net/20170323192237900?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTmljb3J1aQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### **我的博客**
![这里写图片描述](http://img.blog.csdn.net/20170323192311229?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTmljb3J1aQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![这里写图片描述](http://img.blog.csdn.net/20170323192329161?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTmljb3J1aQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### **个人中心**
 ![这里写图片描述](http://img.blog.csdn.net/20170323192356370?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTmljb3J1aQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### **博客管理**
 ![这里写图片描述](http://img.blog.csdn.net/20170323192407756?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTmljb3J1aQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### **文章编写**
![这里写图片描述](http://img.blog.csdn.net/20170323192420714?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTmljb3J1aQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
# **后记**
学了框架之后花了三周把这个东西做了出来，总结出了以下一些东西：
1. 需求分析很重要。在项目还没开始的时候一定要做个文档仔细分析清楚各个模块有哪些功能，这些功能有哪些实现方法，各种实现方法有哪些弊端，我应该选择哪种比较好。
2. 框架技术选择很重要。在项目开始之初就要想清楚各个部分用什么框架用什么技术，比如说UI用什么写，数据刷新是异步还是同步，为什么异步为什么同步，异步的话用什么js框架渲染页面，后端用什么框架组合，为什么用这种组合，是因为自己用的爽还是项目需要。明确各个功能该怎么实现，这样实现有什么好处。
3. 代码要规范。这个东西是自己的想法，不是说什么应该规范，而是因为不规范自己写得不爽，或者说就是自己看着都不满意。说的代码规范也不仅仅是编码的规范，还有代码设计的规范。
  比较印象的一点是有好多地方有分页，每个分页要取数据和去数目两个数据库请求，除了两个sql语句不一样，其它地方都一样，所以就想着提取一个公共方法，传入参数就是两个sql语句，但是这样sql语句就要在service层写了，为了省事我就这样写了。不知道这里该如何规范处理。
  还有一点是异常信息与验证的处理，也不知道该如何规范的写。
4. 写东西并没有那么难，用学过的东西做做过的东西最简单，用没学过的东西做没做过的东西是难。而且一般难是体现在学上面的，做东西是为了运用学习的知识，无非熟悉不熟悉，踩坑多不多罢了，体现的是熟练，对知识的熟练运用。

由于是练手项目，所以就跟做题打草稿一样，就当它是草稿纸，所有的想法无论是思考过的还是即兴的都去试着实现一下，而且也并没有做很多测试，所以项目就跟草稿一样，也就是并没有当它是项目了，因为根本称不上，就是一草稿。草稿可以想怎么写就怎么写，什么想法都可以塞，项目就是要规范。
