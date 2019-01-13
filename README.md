# Reactive-Streams
* Asynchronous Reactive-Streams framework for Java
* It provides Reactive JDBC Framework classes
* Following the standard of reactive-streams.org

<br>
<br>
<br>

## 1. Reactive-Streams Framework

This is a reactive streams protocol implementation framework 
included in the java.util.concurrent.Flow class, which is included since Java 9. 
I implemented the Publisher and Subscription that I would actually use, 
and the Subscriber used what was in the Flow class, 
and did not implement the processor interface because it was not needed. <br>

<br>
<br>

### 1.1 Usage

You can see some usage through the example below

<br>

You must first create the necessary Flow.Subscriber. but, if you are using the Spring Framework or SpringBoot, 
add the [Reactor](https://mvnrepository.com/artifact/io.projectreactor/reactor-core/3.2.5.RELEASE) 
to the your project dependency and annotate @RestController. Then Spring Framework will automatically generate it 
even if you do not create a Subscriber.

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

And you have to create a Publisher to create a flow of data. 
You can combine several operators as shown in the code below. 
The operators currently implemented in this repository are map, reduce, flatmap, next, complete, error.
<br>

map(), filter(), reduce() provide the same functionality as those included in RxJava or Java8's Stream API, <br>
next(), complete(), error() provide the same functionality as RxJava's doOnNext(), doOnComplete(), doOnError()

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

If you want to run this lfow asynchronously in the background, 
you can create a Publisher with background() instead of main().


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

## 2. Reactive JDBC Framework
