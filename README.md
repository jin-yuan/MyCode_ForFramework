# 框架介绍

基于WebMagic基本理念而自行开发的一个JAVA爬虫框架项目,目的是为了易实现定制化

## 框架组件or主要部分

framework-common所属文件夹

```markdown
cookie:针对部分URL的cookie处理,属于需求定制化的一部分,若爬取的网址不需要cookie限制,则可以不进行定制

decrpyt:签名解密,属于需求定制化的一部分

httpclient(核心):自己定制化的一个httpclient

slector:自己选择的一个xpth提取器以及其他的选择,这里用JXDocument

util:自己定义的工具类

```

framework-core所属文件夹

```markdown

base:爬虫生命体所具备的一系列成员变量或组成部分

download:自定义的一个下载器,通过上面自定义的httpclient进行携带一些自己想要的信息

enums:一些自定义辨别的枚举类

Exception：自定义异常捕获

```

For more details see [GitHub Flavored Markdown](https://guides.github.com/features/mastering-markdown/).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/jin-yuan/MyCode_ForFramework/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://docs.github.com/categories/github-pages-basics/) or [contact support](https://github.com/contact) and we’ll help you sort it out.
 
