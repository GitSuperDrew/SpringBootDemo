## 1.生成 `jks` 文件：
要求有 `JDK` 安装好了，执行命令<br/>
`keytool -genkeypair -alias drew-jwt -validity 3650 -keyalog RSA -dname "CN=jwt,OU=jtw,O=jtw,L=zurich,S=zurich,C=CH -keypass drew123 -keystore drew-jwt.jks -storepass drew123`

## 2.获取 `jks` 文件的公钥命令如下：
`keytool -list -rfc --keystore drew-jwt.jks | openssl x509 -inform pem -pubkey` <br/>
 在计算机终端输入上面的命令，提示需要密码，本例的密码为：`drew123` , 输入即可，即可得到--公钥信息。

## 3. 新建一个 `public.cert` 文件, 将上面的公钥信息复制到 `public.cert` 文件中保存。并将 `public.cert` 文件放在资源服务的工程的 `Resource` 目录下。到此为止，`Uaa`授权服务已经搭建完毕。

## 4. 需要注意：maven在编译项目时，可能会将 jks 文件编译，导致 jks 文件乱码，最后不可用，需要在工程的 pom 文件中添加：
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-resources-plugin</artifactId>
    <configuation>
        <nonFilteredFileExtensions>
            <nonFilteredFileExtension>cert</nonFilteredFileExtension>
            <nonFilteredFileExtension>jks</nonFilteredFileExtension>
        </nonFilteredFileExtensions>
    </configuation>
</plugin>
```
