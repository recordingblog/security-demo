
	zhushan-security:
		项目父模块
		
	zhushan-security-browser:
		浏览器相关配置,见com.zhushan.security.browser.BrowserSecurityConfig.java代码注释
	
	zhushan-security-core:
		核心包,其中包含了Security认证成功,失败处理,短信验证码，图片验证码,QQ,微信登录等相关功能,以及各种配置的抽取,单机，集群环境下的Session管理,失效时间
		当前登录用户的最大连接数,并发处理策略等，配置详见com.zhushan.security.core.properties包
	
	zhushan-secueity-oauth2
		新增了OAuth2协议,调用服务,通过向服务提供商申请code,携带code申请access_token,携带access_token 调用demo下的Controller服务,	oauth2
		模式下不支持集群Session管理,只支持browser模式,并且启动前需要启动redis
	
	zhushan-security-demo
		该模块为测试模块,pom文件下的两个依赖,browser与oauth2两个依赖来决定是否使用OAuth2协议的方式去调用Demo Controller
	            社交登陆的默认端口 为80，修改为80   并修改本地 hosts下的回调地址 通过回调地址 去访问 请求登录页即可
	    
	QQ:自己申请的,给你们测试用的
		回调地址:www.chinazhushan.com/qqLogin/callable.do
	    app-i:101509209
	    app-secret:ece4b17b6c3c085af1f3f8399148c6d4
	   
	微信：网上找的
		回调地址:www.pinzhi365.com/qqLogin/weixin
		app-id:wxd99431bbff8305a0
		app-secret:60f78681d063590a469f1b297feff3c4
		
	OAuth2
		获取code的地址:localhost:port/oauth/authorize
		获取accesstoken的地址:localhost:port/oauth/token
	
	数据库
		记住密码功能使用默认实现: JdbcTokenRepositoryImpl SQL脚本cpoy出来用就行
		记住密码的方式:向本地cookie存储token,携带token访问数据库用户信息
		微信,QQ相关绑定也使用默认实现:JdbcUsersConnectionRepository
		sql脚本:org.springframework.social.connect.jdbc 下的 JdbcUsersConnectionRepository.sql
		
	jdk版本 1.8
		
	