# JNIDev
JNI开发，C加密和视频等

###  代码安全
代码安全主要是指AndroidApk容易被反编译，从而面临软件破解，内购破解，软件逻辑修改，插入恶意代码，替换广告商ID等风险。我们可以采用以下方法对apk进行保护
##### 1、代码混淆
　　代码混淆可以在一定程度上增加apk逆向分析的难度。Android SDK从2.3开始就加入了ProGuard代码混淆功能，开发者只需进行简单的配置就可以实现对代码的混淆。
##### 2、Apk签名校验
　　每一个软件在发布时都需要开发人员对其进行签名，而签名使用的密钥文件时开发人员所独有的，破解者通常不可能拥有相同的密钥文件，因此可以使用签名校验的方法保护apk。Android SDK中PackageManager类的getPackageInfo()方法就可以进行软件签名检测。
**JAVA**
**代码中获取签名方法：**

```
/**
 * 获取应用的签名
 *
 * @return
 */
public String getCertificateSHA1Fingerprint() {
    //获取包管理器
    PackageManager pm = context.getPackageManager();
    //获取当前要获取 SHA1 值的包名，也可以用其他的包名，但需要注意，
    //在用其他包名的前提是，此方法传递的参数 Context 应该是对应包的上下文。
    String packageName = context.getPackageName();
    //返回包括在包中的签名信息
    int flags = PackageManager.GET_SIGNATURES;
    PackageInfo packageInfo = null;
    try {
        //获得包的所有内容信息类
        packageInfo = pm.getPackageInfo(packageName, flags);
    } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
    }
    //签名信息
    Signature[] signatures = packageInfo.signatures;
    byte[] cert = signatures[0].toByteArray();
    //将签名转换为字节数组流
    InputStream input = new ByteArrayInputStream(cert);
    //证书工厂类，这个类实现了出厂合格证算法的功能
    CertificateFactory cf = null;
    //X509 证书，X.509 是一种非常通用的证书格式
    X509Certificate c = null;
    String hexString = null;
    try {
        cf = CertificateFactory.getInstance("X509");
        c = (X509Certificate) cf.generateCertificate(input);
        //加密算法的类，这里的参数可以使 MD4,MD5 等加密算法
        MessageDigest md = MessageDigest.getInstance("SHA1");
        //获得公钥
        byte[] publicKey = md.digest(c.getEncoded());
        //字节到十六进制的格式转换
        hexString = byte2HexFormatted(publicKey);
    } catch (NoSuchAlgorithmException e1) {
        e1.printStackTrace();
    } catch (CertificateEncodingException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return hexString;
}
```
获取apk 签名的SHA1:

方法一：keytool -list -v -keystore *.jks    *.jks 为本应用签名

方法二：
1. 先把apk解压
2. 在META_INF目录下找到xxx.RSA文件
3. 确保安装了jdk并且正确配置了环境变量
4. cmd中执行keytool -printcert -file xxxx.RSA目录
如下图所示：

方法三：
1. 确保安装了jdk并且正确配置了环境变量。  
2. cmd中执行keytool -printcert -jarfile xxx.apk  



##### 3、DEX文件校验  
检测安装后classes.dex文件的hash值来判断apk是否被重打包过。

 （1）读取应用安装目录下/data/app/xxx.apk中的classes.dex文件并计算其哈希值，将该值与软件发布时的classes.dex哈希值做比较来判断客户端是否被篡改。
 
（2）读取应用安装目录下/data/app/xxx.apk中的META-INF目录下的MANIFEST.MF文件，该文件详细记录了apk包中所有文件的哈希值，因此可以读取该文件获取到classes.dex文件对应的哈希值，将该值与软件发布时的classes.dex哈希值做比较就可以判断客户端是否被篡改。  
　　为了防止被破解，软件发布时的classes.dex和a哈希值应该存放在服务器端。  
　　另外由于逆向c/c++代码要比逆向Java代码困难很多，所以关键代码部位应该使用Native C/C++来编写。  
　　
获取apk的sha值: shasum *.apk   * 代表任意文件， Shasum 命令默认检测 SHA1 值，而通过参数 -a 可以修改为 224、256、384 或 512。
获取apk文件的MD5值: md5 app.apk. 

##### 4、完整性校验
使用反编译工具可以修改smail文件和替换资源文件，为了防止apk文件的改动，获取dex文件或apk文件的crc值来判断文件是否修改。和上面同是完整性校验
文件crc值获取方法：crc 文件。
##### 5、逆向工具对抗
* Apktool 工具调用
　　对apk进行重打包常用的工具是apktool，apktool对于后缀为PNG的文件，会按照PNG格式进行处理，如果我们将一个非PNG格式文件的文件后缀改为PNG，再使用apktool重打包则会报错。可以将jpg 文件改为png格式，在每次启动时候调用。
* Jd-GUI
    利用这两个工具将apk首先反编译成classes.dex然后再将classes.dex反编译成jar文件或者将apk直接反编译成jar文件；得到jar文件以后就可以利用JD-GUI将得到的jar文件打开就可以直接查看apk的java源码了。在程序中加入不可能的特殊分支语句，如下所示
```
 switch(0)
       {
       case 1:
           JSONObject jsoObj;
           String date=null;
           String second=null;
           try
           {
              jsoObj=new JSONObject();
              date=jsoObj.getString("date");
               second=jsoObj.getString("second");
           }
           catch(JSONException e)
           {
              e.printStackTrace();
           }
           test.settime(date,second);
           break;
       }
```
用JD-GUI打开会看到提示error错误
##### 6、调试器检测
　　为了防止apk被动态调试，可以检测是否有调试器连接。在Application类中提供了Debug.isDebuggerConnected()方法用于检测是否有调试器连接，如果发现有调试器连接，可以直接退出程序。
　　
分析android自带调试检测函数isDebuggerConnected()在native的实现， 尝试在native使用。

（1）dalvik模式下:  
找到进程中libdvm.so中的dvmDbgIsDebuggerConnected()函数，
调用他就能得知程序是否被调试。
dlopen(/system/lib/libdvm.so)
dlsym(_Z25dvmDbgIsDebuggerConnectedv)

（2）art模式下：  
art模式下，结果存放在libart.so中的全局变量gDebuggerActive中，符号名为_ZN3art3Dbg15gDebuggerActiveE。但是貌似新版本android不允许使用非ndk原生库，dlopen(libart.so)会失败。 所以无法用dalvik那样的方法了。

##### 1.7、加壳保护
　　使用加壳程序防止apk逆向是一种非常有效的方式，也是一个趋势。Jack_Jia在《Android APK加壳技术方案》一文中详细阐述了Androidapk加壳原理以及几种加壳方案的具体实现。我们可以利用这几种方案对apk进行加壳。

不过这种加壳方式是在Java层实现的，被反编译的风险仍然很大。为了克服这个缺点，今后可以研究采用如下思路来进行保护：  
　
将核心业务逻辑代码放入加密的.jar或者.apk文件中，在需要调用时使用Native C/C++代码进行解密，同时完成对解密后文件的完整性校验。如果需要更加安全的保护方法，可以考虑对so文件（Native C/C++代码编译得到的文件）进行加壳。

Android so加壳主要需要解决两个问题：

（1）对ELF文件加壳；  
（2）对Android SO的加载、调用机制做特殊处理。

　　其实不管是Linux还是Windows，加壳的思路基本是一致的，简单点无非加密+拆解+混淆，复杂点如Stolen Code + VM等。所以确定好一个加壳方案之后，剩下的就是了解elf的文件结构和加载机制，然后自己写一套壳+loader。
　　Android上的loader是/system/bin/linker，跟linux上的ld有一些区别。但主要过程还是一致的：map + relocate + init。
　　Android so主要充当的角色是通过JNI与java交互，所以主要是作为一个库存在（也有一些so是可执行的），然后被Android runtime加载，并能被java层调用。所以对elf加完壳之后，还要对Android so做一些特殊处理：
　　
1. Android so被System.LoadLibrary()加载之后，会将so的信息存储在一个全局链表里，所以要保证脱壳后的so能被这个链表访问到。
2. Android so库函数被java调用有两种方式：一种是通过registerNative注册，另一种是通过javah命名规则命名（参考:[http://blog.csdn.net/sno_guo/article/details/7688227](https://note.youdao.com/)）。所以一个通用的加壳方案要保证所有的库函数都能被调用，前者好解决，后者需要花点功夫。

　　解决掉这两个问题之后，基本上一套AndroidSO加壳框架就成形了，后续就可以增加各种Anti tricks来完善壳的强度。
参考文档：http://www.colordancer.net/blog/2014/01/05/android-native-so%E5%8A%A0%E5%A3%B3%E6%8A%80%E6%9C%AF/
以上代码安全主要在Java层使用，易于反编译，可在native层进行判断，加强反编译的难度。
