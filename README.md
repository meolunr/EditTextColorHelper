# EditColorHelper

一个为 EditText 设置主题色的帮助类

### 如何使用
1. 在 **项目** 的 build.gradle 文件中添加
```
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
```
2. 配置依赖
```
dependencies {
    implementation 'com.github.meolunr:EditTextColorHelper:v1.2'
}
```
3. 尽情使用吧
```
EditTextColorHelper.setColor(EditText, Color);
```

### 如果还想了解
可以看这儿 [Android 多主题之 EditText](https://iacn.me/2017/02/14/modify-edittext-color)

### 样例
![](https://ooo.0o0.ooo/2017/02/12/58a045b784300.png)  
你可以在 [Releases](https://github.com/meolunr/EditTextColorHelper/releases) 页面下载到 Sample 的安装包

### License
[Apache License, version 2.0](LICENSE)
