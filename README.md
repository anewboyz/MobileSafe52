# MobileSafe52
黑马手机卫士练习

# 手机卫士52期笔记 #

## 代码组织结构 ##

- 根据业务逻辑划分

	- 办公软件

		- 出差 com.itheima.travel
		- 工资 com.itheima.money
		- 会议 com.itheima.meeting

	- 网盘

		- 上传 com.vdisk.upload
		- 下载 com.vdisk.download
		- 分享 com.vdisk.share

- 根据功能模块划分(Android开发推荐此方法)

	- Activity com.itheima.mobilesafe.activty
	- 后台服务  com.itheima.mobilesafe.service
	- 广播接受者 com.itheima.mobilesafe.receiver
	- 数据库 com.itheima.mobilesafe.db.dao
	- 对象(java bean) com.itheima.mobilesafe.domain/bean
	- 自定义控件 com.itheima.mobilesafe.view
	- 工具类 com.itheima.mobilesafe.utils
	- 业务逻辑 com.itheima.mobilesafe.engine

## 项目创建 ##

- minimum SDK 要求最低的安装版本, 安装apk前,系统会判断当前版本是否高于(包含)此版本, 是的话才允许安装

- maxSdkVersion 要求最高的安装版本(一般不用)

- Target SDK 目标SDK, 一般设置为开发时使用的手机版本, 这样的话,系统在运行我的apk时,就认为我已经在该做了充分的测试, 系统就不会做过多的兼容性判断, 从而提高运行效率

- Compile With 编译程序时使用的版本


## 闪屏页面(Splash) ##

- 展示logo,公司品牌
- 项目初始化
- 检测版本更新
- 校验程序合法性(比如:判断是否有网络,有的话才运行)

## 什么是奋斗 ##

> 奋斗就是,你每天都很辛苦, 但是,你一年一年会越来越轻松
> 不奋斗就是, 你每天都很轻松, 但是,你一年一年会越来越辛苦


## 签名冲突 ##

> 如果两个应用程序, 包名相同, 但是签名不同, 就无法覆盖安装

> 正式签名

	1. 有效期比较长,一般大于25年
	2. 需要设置密码
	3. 正式发布应用时,必须用正式签名来打包

> 测试签名(debug.keystore)

	1. 有效期是1年,很短
	2. 有默认的别名,密码, alias=android, 密码是androiddebugkey
	3. 在eclipse中直接运行项目是,系统默认采用此签名文件

> 如果正式签名丢失了怎么办?

	1. 修改包名, 发布, 会发现有两个手机卫士, 用户会比较纠结
	2. 请用户先删掉原来的版本,再进行安装, 用户会流失
	3. 作为一名有经验的开发人员,请不要犯这种低级错误

## 常用快捷键 ##

- ctrl + shift + o 导包
- ctrl + shift + t 快速查找某个类
- 先按ctrl + 2 ,再点L, 创建变量并命名
- ctrl + o , 在当前类中,快速查找某个方法
- ctrl + k, 向下查找某个字符串
- ctrl + shift + k, 向上查找某个字符串
- alt + 左方向键 跳转上一个页面

## 子类和父类 ##

> 子类拥有父类的所有方法, 而且可以有更多自己的方法

> Activity(token), Context(没有token)
> 平时,要获取context对象的话, 优先选择Activity, 避免bug出现, 尽量不用getApplicationContext()




	
