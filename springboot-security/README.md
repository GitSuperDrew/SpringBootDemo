# 工程简介
> 示例地址：[今日头条](https://www.toutiao.com/i6918976968122139143/)
> <br/>项目技术：
>   - Mybatis
>   - Spring Boot
>   - Spring Security
>   - Thymeleaf
>   - MySQL
> 
> <br/> 项目文档见 HELP.md 文档

## 项目环境搭建
1. 数据库配置
    ```properties
    # 数据库驱动：
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    # 数据源名称
    spring.datasource.name=defaultDataSource
    # 数据库连接地址
    spring.datasource.url=jdbc:mysql://localhost:3306/zero?serverTimezone=UTC
    # 数据库用户名&密码：
    spring.datasource.username=root
    spring.datasource.password=123456
    ```
2. 模板引擎配置
    ```properties
    # THYMELEAF (ThymeleafAutoConfiguration)
    # 开启模板缓存（默认值： true ）
    spring.thymeleaf.cache=false
    # 检查模板是否存在，然后再呈现
    spring.thymeleaf.check-template=true
    # 检查模板位置是否正确（默认值 :true ）
    spring.thymeleaf.check-template-location=true
    #Content-Type 的值（默认值： text/html ）
    spring.thymeleaf.content-type=text/html
    # 开启 MVC Thymeleaf 视图解析（默认值： true ）
    spring.thymeleaf.enabled=true
    # 模板编码
    spring.thymeleaf.encoding=UTF-8
    # 要被排除在解析之外的视图名称列表，⽤逗号分隔
    spring.thymeleaf.excluded-view-names=
    # 要运⽤于模板之上的模板模式。另⻅ StandardTemplate-ModeHandlers( 默认值： HTML5)
    spring.thymeleaf.mode=HTML5
    # 在构建 URL 时添加到视图名称前的前缀（默认值： classpath:/templates/ ）
    spring.thymeleaf.prefix=classpath:/templates/
    # 在构建 URL 时添加到视图名称后的后缀（默认值： .html ）
    spring.thymeleaf.suffix=.html
    ```
3. 服务端口配置
    ```properties
    # 应用名称
    spring.application.name=springboot-security
    # 应用服务 WEB 访问端口
    server.port=8080
    ```

4. ORM 框架配置
    ```properties
    #下面这些内容是为了让MyBatis映射
    #指定Mybatis的Mapper文件
    mybatis.mapper-locations=classpath:mappers/*xml
    #指定Mybatis的实体目录
    mybatis.type-aliases-package=edu.study.module.springbootsecurity.mybatis.entity
    ```

5. 导入数据库脚本
    > resources/db_script/rbac.sql

6. 创建页面 `reg.html`, `login.html`, `index.html`, `403.html`, `main.html`
    1. reg.html:
        ```html
       <form action="/reg" method="post">
           用户名<input type="text" name="username"/> <br/>
           用户名<input type="text" name="realname"/> <br/>
           密码 <input type="password" name="password"/><br/>
           <input type="submit" value="注册">
       </form> 
        ```
   2. login.html
        ```html
        <!--name值：必须为username password-->
        <form method="post" action="/login">
            用户名：<input type="text" name="username">
            密码<input type="text" name="password">
            <input type="submit" value="登录">
        </form>
        <!-- MySecurity类中 .failureUrl("/login?error=true")-->
        <label style="color: red" th:if=${param.error} th:text="账户密码错误，请重新登录"></label>
        ```
   3. index.html
        ```html
        <body>
            this is Main page!
            <a href="/main">权限页面</a>
            <a href="/logout">注销</a>
        </body>
        ```
   4. main.html
        ```html
        <body>
            本页面需要权限访问
            <a href="/logout">注销</a>
        </body>
        ```
   5. 编写安全配置了类 `SecurityConfig.java` 
        ```java
        @EnableWebSecurity
        public class SecurityConfig extends WebSecurityConfigurerAdapter {
        
            @Resource
            MyUserDetailsService myUserDetailsService;
        
            /**
             * http安全配置
             *
             * @param http
             * @throws Exception
             */
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                //所有请求拦截
                http.authorizeRequests()
                        //放在所有拦截的前面放行不需要拦截的资源
                        .antMatchers("/static/**").permitAll()
                        //放行登录
                        .antMatchers("/login", "/index","/logining").permitAll()
                        //放行注销
                        .antMatchers("/logout").permitAll()
                        //放行注册
                        .antMatchers("/reg", "/register").permitAll()
                        //此页需要权限
                        .antMatchers("/main").hasAnyAuthority("ROLE_PRODUCT_LIST")
                        //除上所有拦截需要用户认证
                        .anyRequest().authenticated()
                        .and()
                        //for login认证
                        .formLogin().loginPage("/login")
                        //登陆错误页
                        .failureUrl("/login?error=true")
                        .and()
                        //关闭csrf校验
                        .csrf().disable();
        
            }
        
            /**
             * 认证管理器配置
             *
             * @param auth
             * @throws Exception
             */
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(myUserDetailsService)
                        //查询时需要密码加密后和数据库做比较
                        .passwordEncoder(new BCryptPasswordEncoder());
            }
        }
        ```
   6. 资源映射 `WebSourceConfig.java`
        ```java
        @Configuration
        public class WebSourceConfig implements WebMvcConfigurer {
        
            /**
             * 静态资源URI和位置映射
             *
             * @param registry
             */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/static/");
            }
        
            /**
             * 视图映射
             *
             * @param registry
             */
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login").setViewName("login");
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/403").setViewName("403");
                registry.addViewController("/main").setViewName("main");
            }
        }
        ```
   7. 错误页面配置 `ErrorPageConfig.java` 
      ```java
        @Configuration
        public class WebSourceConfig implements WebMvcConfigurer {
        
            /**
             * 静态资源URI和位置映射
             *
             * @param registry
             */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/static/");
            }
        
            /**
             * 视图映射
             *
             * @param registry
             */
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login").setViewName("login");
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/403").setViewName("403");
                registry.addViewController("/main").setViewName("main");
            }
        }
      ```
   8. 首页控制层 `HomeController.java`
      ```java
      @Controller
      @RequestMapping(value = "/")
      public class HomeController {
      
          /**
           * 服务对象
           */
          @Resource
          private SysUserService sysUserService;
          @Resource
          SysUserDao sysUserDao;
      
      
          @RequestMapping(value = "/register")
          public ModelAndView register() {
              ModelAndView modelAndView = new ModelAndView();
              modelAndView.setViewName("/reg.html");
              return modelAndView;
          }
      
          @PostMapping(value = "/logining")
          public ModelAndView logining(SysUser sysUser) {
              ModelAndView modelAndView = new ModelAndView();
      
              SysUser queryUser = SysUser.builder()
                      .username(sysUser.getUsername())
                      .accountNonExpired(false)
                      .accountNonLocked(false)
                      .credentialsNonExpired(false)
                      .enabled(true)
                      .build();
              List<SysUser> exitsUsers = sysUserDao.queryAll(queryUser);
      
              queryUser.setLastLoginTime(new Date());
              int num = sysUserDao.update(queryUser);
              if (num > 0) {
                  System.out.println("更新最近登录时间成功！");
              }
              if (ObjectUtils.isEmpty(exitsUsers)) {
                  modelAndView.setViewName("redirect:/login?error=true");
                  return modelAndView;
              }
      
              String password = sysUser.getPassword();
              boolean flag = new BCryptPasswordEncoder().matches(password, exitsUsers.get(0).getPassword());
              if (flag) {
                  modelAndView.setViewName("/main");
              } else {
                  modelAndView.setViewName("redirect:/login?error=true");
              }
              return modelAndView;
          }
      
      
          @RequestMapping(value = "/index")
          public ModelAndView index() {
              ModelAndView modelAndView = new ModelAndView();
              modelAndView.setViewName("/index.html");
              return modelAndView;
          }
      
          /**
           * 用户注册 (注册用户测试URL示例：http://localhost:8080/reg?username=Drew22&password=Drew22&realName=Drew22)
           *
           * @param user 用户信息
           * @return 成功与否
           */
          @RequestMapping(value = "/reg")
          @ResponseBody
          public String reg(SysUser user) {
              System.out.println("用户信息：" + user);
      
              // 1. 处理输入的密码信息
              String pwd = user.getPassword();
              //加密，springSecurity现在默认要强制加密
              pwd = new BCryptPasswordEncoder().encode(pwd);
              user.setPassword(pwd);
              user.setCreateBy("admin");
              user.setCreateTime(new Date());
              user.setLastLoginTime(new Date());
              user.setEnabled(true);
      
              // 2.保存用户其他信息
              SysUser newUser = this.sysUserService.add(user);
      
              // 3.返回是否成功
              if (newUser.getId() != null) {
                  return "注册成功";
              }
              return "注册失败";
          }
      }
      ```


# 项目测试
    
1. 用户注册地址：`http://localhost:8080/register`;
2. 用户登录地址：`http://localhost:8080/login`;
3. 需授权访问的地址：`http://localhost:8080/main`;
4. 用户名和密码：`111/111`, `Drew/222`, `222/222`, `Drew11/Drew11`

# 延伸阅读

