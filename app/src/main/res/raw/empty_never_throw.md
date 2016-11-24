这三个操作符生成的 Observable 行为非常特殊和受限，用于

- 测试
- 结合其它的 Observable
- 作为其它需要 Observable 的操作符的参数

#### `Empty`：创建一个不发射任何数据但是正常终止的Observable

```
// 只调用onCompleted
Observable.empty().subscribe(new Subscriber<Object>() {
    @Override
    public void onNext(Object item) {
        // 不被调用
    }
    @Override
    public void onError(Throwable error) {
    	// 不被调用
        
    }
    @Override
    public void onCompleted() {
        // 被调用
    }
});
```

#### `Never`：创建一个不发射数据也不终止的Observable

```
// 不会调用订阅者的任何方法
Observable.never().subscribe(new Subscriber<Object>() {
    @Override
    public void onNext(Object item) {
        // 不被调用
    }
    @Override
    public void onError(Throwable error) {
        // 不被调用
    }
    @Override
    public void onCompleted() {
        // 不被调用
    }
});
```

#### `Error` ：创建一个不发射数据以一个错误终止的Observable

```
// 只会调用onError
Observable.error(new Throwable("just call onError")).subscribe(new Subscriber<Object>() {
    @Override
    public void onNext(Object item) {
        // 不被调用
    }
    @Override
    public void onError(Throwable error) {
        // 被调用
    }
    @Override
    public void onCompleted() {
        // 不被调用
    }
});
```