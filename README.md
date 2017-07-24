# Tool_kit
工程描述：该工程的创建，旨在成为一个可以提供Android快速开发的工具。
## 数据库工具使用
1、工具简介
 > 该数据库工具以面向对象的思想编写，数据库的增删改查都是通过操作对象完成。
 所以，工具使用的重点，基本上都在javabean的编写上。从一个符合标准的数据原型中
 我们完全可以看出数据库的所有结构信息。
 
2、数据原型及其注解。
> 数据原型必须是标准的具有get/set方法的java bean。  
 有两个注解：Table和Column。  
 只有被Table注解的类，被Column注解的字段会被识别为数据库中的内容。  
 Table 注解包含databases和table两个字段。  
 databases 表示将要操作的数据库名，如果用户没有指定默认为类名。  
 table 表示操作的表名，如果没有指定默认为类名。  
 Column 注解包含name和isPrimary两个字段。  
 name 表示注解所在字段在数据库中的列名，如果没有指定，默认使用字段名称
 isPrimary 表示注解所在字段在数据库中是否为主键，如未指定默认不是。
```
@Table(databases = "kivens.db", table = "user")
public class User {
    @Column(name = "_id", isPrimary = true)
    public int id;
    @Column(name = "_name")
    public String name;
    @Column(name = "_age")
    public int age;

    public User(){}

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "[ id = " + id + " : name = " + name + " : age = " + age + " ]";
    }
}
```
2、增删改查的接口的使用。

1. 添加和修改。  
    ```
    User user = new User( 9, "kiven", 20);
    DBUtils.add(this, user);
    ```
    > 添加和修改都可以调用add接口，如果数据库中不包含user的记录，则添加记录。
    如果user记录已存在则更新该记录。
2. 查询
    ```
    List<User> query = DBUtils.query(this, User.class);
    ```
    > query为数据库中所有记录的集合，该接口也适用于条件查询。
    ```
    List<User> query = DBUtils.query(this, User.class, "_id > 9");
    ```
    > 返回的是符合"_id > 9"条件的集合。当然，更多的条件也是支持的。如：
    ```
    DBUtils.query(this, User.class, "_id > 9", "_name = kiven, "_age > 12);
    ```
3. 删除

    ```
    DBUtils.delete(this, User.class, "_id = 1");
    ```
    > “_id = 1"为删除条件，同样也支持多条件选择删除
    ```
    DBUtils.delete(this, User.class, "_id = 1", "_name = kiven");
    ```
## TODO-List
1、数据库工具。（已完成）  
2、网络工具。  
3、二维码生成与识别。
