## 简介
* 这是一个基于jetpack、mvvm架构，用组件化方式编写的快速开发框架，对常用功能进行了简单封装，提供了非常友好的api，尽量做到小而美而不是大而全。
* fully written in Kotlin.

## 提供的一些功能组件
* CommonPage 封装了空页面、错误页面、和加载中页面，提供全局的默认配置，也可以在具体页面进行自定义配置，并且在activity和fragment中懒加载，不使用则不注入。
* [XBus](https://github.com/yu1tiao/EventBus) 采用编译时注解技术实现的轻量级消息总线，使用方式和EventBus一致，但是效率更高
* [LiteAdapter](https://github.com/yu1tiao/LiteAdapter) 非常好用的adapter
* PermissionManager
* DataBinding 可选，集成BaseDataBindActivity或者BaseDataBindFragment
* RxJava 可选，默认使用Retrofit+协程请求数据，加载lib-base-rx即可切换到rxjava
* WMRouter 可选，美团路由方案

## 全局配置
* 对项目中需要配置的项目进行统一管理，配置项参考GlobalConfiguration，只需要在BaseApplication中简单的初始化，就可以快速开始一个项目。
```kotlin
    // 参考，一般只需配置一下网络请求，其他的采用默认配置
    override fun initGlobalConfiguration(): GlobalConfiguration {
        return GlobalConfiguration.create {
            okHttpConfigCallback = {
                it.connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(CommonHeaderInterceptor())
                    .addInterceptor(
                        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                            override fun log(message: String) {
                                Log.d("okHttp_", message)
                            }
                        }).apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
            }
            retrofitConfigCallback = {
                it.addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            }
        }
    }

```
## 模块介绍
* lib-base 封装的基类，mvvm架构，可选DataBinding
* lib-base-rx rxjava的支持
* lib-router 路由库，放置各模块对外提供的服务接口、拦截器、实体对象和常量
* module-user 用户模块，提供了登录注册功能，对外提供用户管理服务
* module-wandroid demo模块，本来准备用wandroid的api写一个组件化的app，时间太紧也没写，就简单写了组件间跳转、登录拦截器和服务使用的示例
* app app入口，空壳app，只放置启动页
