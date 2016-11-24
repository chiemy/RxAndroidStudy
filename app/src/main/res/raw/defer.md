`defer` 操作符作用，直到有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable，光看此描述可能并不能理解它的作用，举例说明

```
public class SomeType {  
	private String value;

	public void setValue(String value) {
  		this.value = value;
	}

	public Observable<String> valueObservable() {
  		return Observable.just(value);
	}
}
```

运行下段代码

```
SomeType instance = new SomeType();  
Observable<String> value = instance.valueObservable();  
instance.setValue("Some Value");  
value.subscribe(System.out::println);
```

结果是什么？实际结果是“null”，因为在执行这段代码时

```
Observable<String> value = instance.valueObservable(); 
```
`Observable.just(value)` 就已完成了 Observable 的创建，并已经传入 value， 此时的 value 就是 null。

怎么避免此问题？

将 `instance.valueObservable` 置于 `instance.setValue("Some Value")` 之后？可以解决，但你怎么约束所有调用者都这样处理呢？

像下边这样自定义操作符？

```
public Observable<String> valueObservable() {
  return Observable.create(new Observable.OnSubscribe<String>() {
    @Override public void call(Subscriber<? super String> subscriber) {

      subscriber.onNext(value);
      subscriber.onCompleted();
    }
  });
}
```
确实是在订阅时才获取 value 值了，但自定义操作符并不推荐（随着RxJava版本的迭代，自定义操作符可能会产生一些问题）。

使用内置的 `defer` 操作符

```
public Observable<String> valueObservable() {
	return Observable.defer(new Func0<Observable<String>>() {
		@Override public Observable<String> call() {
      		return Observable.just(value);
    	}
  	});
}
```

这种方式比 `Observable.create()` 更简单，不再需要手动调用 `onCompleted()`，而且（可能）更得到官方的肯定。

