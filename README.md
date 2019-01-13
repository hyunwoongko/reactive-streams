# Reactive-Streams

![image](https://user-images.githubusercontent.com/38183241/51087829-5f6f2500-179b-11e9-8cb6-ef0d4f16a07f.png)


* Asynchronous Reactive-Streams framework for Java
* It provides Reactive JDBC Framework classes
* Following the standard of reactive-streams.org

<br>
<br>
<br>
<br>

## 0. Getting Statred
<br>

### [Download Jar file Here](https://github.com/gusdnd852/Reactive-Streams/raw/master/out/artifacts/Reactive_Streams_jar/Reactive-Streams.jar)
<br>

Add the jar file to your project dependency. It is not a high-quality framework, <br> 
and I don't have any plan to distribute it as maven or gradle.

<br>
<br>
<br>
<br>


## 1. Reactive-Streams Framework

This is a reactive streams protocol implementation framework <br>
included in the java.util.concurrent.Flow class, which is included since Java 9. <br>
<br>
I implemented the Publisher and Subscription that I would actually use, <br> 
and the Subscriber used what was in the Flow class, <br>
and did not implement the processor interface because it was not needed. <br>

<br>
<br>

### 1.1 Create Subscriber
You must first create the necessary Flow.Subscriber. but, if you are using the Spring Framework or SpringBoot, <br>
add the [Reactor](https://mvnrepository.com/artifact/io.projectreactor/reactor-core/3.2.5.RELEASE) 
to the your project dependency and annotate @RestController. Then Spring Framework will automatically generate it 
even if you do not create a Subscriber.

<br>

```java
// Create Subscriber

Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {subscription.request(Long.MAX_VALUE);}
            @Override public void onNext(Integer item) {System.out.println(item + " : " + Thread.currentThread().getName());}
            @Override public void onError(Throwable throwable) {}
            @Override public void onComplete() {}};
        
```

<br>
<br>

### 1.2 Create Publisher
And you have to create a Publisher to create a flow of data. You can combine several operators as shown in the code below. <br> 
The operators currently implemented in this repository are map, reduce, flatmap, next, complete, error.
<br>

map(), filter(), reduce() provide the same functionality as those included in RxJava or Java8's Stream API, <br>
next(), complete(), error() provide the same functionality as RxJava's doOnNext(), doOnComplete(), doOnError()

<br>

```java
// Create Publisher

    Publisher.main(1, 2, 3, 4, 5)
                .filter(number -> number > 2)
                .map(number -> number * 2)
                .subscribe(subscriber);

// Ouput : 
// 6 : main
// 8 : main
// 10 : main
```

<br>
<br>

### 1.3. Background thread scheduling
If you want to run this lfow asynchronously in the background, you can create a Publisher with background() instead of main(). <br> 
This provides similar functionality to SubscribeOn() provided by RxJava, and observeOn() is not currently implemented in the repository. <br>


```java
// Create Publisher

    Publisher.background(1, 2, 3, 4, 5)
                .filter(number -> number > 2)
                .map(number -> number * 2)
                .subscribe(subscriber);

// Ouput : 
// 6 : pool-1-thread-1
// 8 : pool-1-thread-1
// 10 : pool-1-thread-1
```

Publisher created with the background() method selects one of 16 random thread pools and proceeds to work

<br>
<br>
<br>
<br>

## 2. Reactive JDBC Framework

The JDBC framework provided by the existing JDK is very good, <br>
but it requires a lot of boilerplate code to be written, not a pure function, so it is not suitable for reactive solutions. <br>
Because of this, I create and provide a JDBC wrapper framework that is suitable for reactive solutions and very easy to use.<br>

<br>
<br>

### 2.1 Create Info Class

You have to create the Information class. The Information class is a VO class that contains your database Url, Id, and Password.

<br>

```java
class Info {
    public static final String url = "yourDatabaseUrl";
    public static final String id = "yourDatabaseId";
    public static final String pw = "yourDatabasePw";
}
```

<br>
<br>

### 2.2 Create DTO Class

You must create a DTO class that is a specification of one table included in the database. <br>
DTO-DAO System a very popular for web development. Therefore, a description thereof will be omitted in this section. <br>
I used the lombok library to remove boilerplate codes from a number of getters, setters, and so on. <br>


```java
@Data @Builder
class ExamplesDto {
    private String primaryKey;
    private String property;
}
```

<br>
<br>

### 2.3 Create DAO Class

Then you create a DAO class. <U>This DAO class must inherit the Accessor class.</U> <br>
The following is the procedure for creating DAO class methods.
<br>

0. Since all methods are pure functions, the return clause must be included at the beginning of the method.
1. You must enter your database information by calling the install() before starting all of these flows. 
2. Use the query() to write the required sql syntax.
3. Accessor use PreparedStatement. If you need to include Java syntax between sql, you can use the param().
4. Use the map() to decide how to receive the data. If you don't return data, you don't need to call it.
5. The flow is terminated by calling one of the methods below.
   * getOnce() is used when you receive single data. You need to specify type that will be returned.
   * getList() is used when you receive return multiplr data. You need to specify type that will be returned.
   * set() is used when you are entering data into the database.
  
  
<br>
There are some examples in the code below.
<br>

```java
    public ExamplesDto select(ExamplesDto dto) {
        return install(Info.url, Info.id, Info.pw)
                .quary("select * from table where primaryKey=?")
                .param(statement -> statement.setString(1, dto.getPrimaryKey()))
                .map(set -> ExamplesDto.builder()
                        .primaryKey(set.getString("primaryKey"))
                        .property(set.getString("property"))
                        .build())
                .getOnce(ExamplesDto.class);
    }

    public List<ExamplesDto> selectAll() {
        return install(Info.url, Info.id, Info.pw)
                .quary("select * from table")
                .map(set -> ExamplesDto.builder()
                        .primaryKey(set.getString("primaryKey"))
                        .property(set.getString("property"))
                        .build())
                .getList(ExamplesDto.class);
    }

    public boolean insert(ExamplesDto dto) {
        return install(Info.url, Info.id, Info.pw)
                .quary("insert into table values (?,?)")
                .param(statement -> statement.setString(1, dto.getPrimaryKey()))
                .param(statement -> statement.setString(2, dto.getProperty()))
                .set();
    }

    public boolean update(ExamplesDto dto, final String property, final String value) {
        return install(Info.url, Info.id, Info.pw)
                .quary("update table set " + property + " = ?" + "where  primaryKey = ?")
                .param(statement -> statement.setString(1, value))
                .param(statement -> statement.setString(2, dto.getPrimaryKey()))
                .set();
    }

    public boolean delete(ExamplesDto dto) {
        return install(Info.url, Info.id, Info.pw)
                .quary("delete from table where + ?")
                .param(statement -> statement.setString(1, dto.getPrimaryKey()))
                .set();
    }

```

