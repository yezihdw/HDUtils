package com.hdw.hdutils.util;

import android.util.Log;

import com.hdw.hdutils.BuildConfig;
import com.hdw.hdutils.api.Api;
import com.hdw.hdutils.api.RequestParams;
import com.hdw.hdutils.api.Urls;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by hdw on 2017/6/1.
 */

public class RxjavaDemo {

    private static final String TAG = "RxjavaDemo";

    public static Subscription mSubscription;

    public static void request(){
        mSubscription = new Subscription() {
            @Override
            public void request(long n) {

            }

            @Override
            public void cancel() {

            }
        };

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 1000; i++) {
                    Log.d(TAG, "emit " + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public static void req(){
        mSubscription.request(128);
    }

    public static void observeRequest(){
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "subscribe: "+Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                e.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.d(TAG, "onsubscriber: "+Thread.currentThread().getName());
                Log.d(TAG, "onNext: "+integer);
            }
        };

//        observable.subscribe(consumer);

        observable.subscribeOn(Schedulers.newThread())//发送事件（上游）
                .observeOn(AndroidSchedulers.mainThread())//接受事件（下游）
                .subscribe(consumer);

    }

    private static void create(){
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5,TimeUnit.SECONDS);

        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.HOST)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        RequestParams params = new RequestParams();
        params.put("loginName","ye");
        params.put("password","123456");
        api.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>(){
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        ToastUtil.show("登录成功");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtil.show("登录失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //map是RxJava中最简单的一个变换操作符了, 它的作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化
    public static void createMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "this is result "+integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG,s);

            }
        });
    }

    //FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里
    public static void creatFlatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<String>();
                for (int i=0 ; i<3; i++){
                    list.add(" value : "+integer);
                }
                return Observable.fromIterable(list).delay(2,TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG,s);
            }
        });
    }

    //这里也简单说一下concatMap吧, 它和flatMap的作用几乎一模一样, 只是它的结果是严格按照上游发送的顺序来发送的
    public static void concatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i=0; i<3;i++){
                    list.add("this is value: "+integer);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG,s);
            }
        });

    }


    public static void createZip(){

        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                Log.d(TAG,"A");
//                Thread.sleep(1000);

                e.onNext("B");
                Log.d(TAG,"B");
//                Thread.sleep(1000);

                e.onNext("C");
                Log.d(TAG,"C");
//                Thread.sleep(1000);

                Log.d(TAG,"onComplete");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.d(TAG,"1");
//                Thread.sleep(1000);

                e.onNext(2);
                Log.d(TAG,"2");
//                Thread.sleep(1000);


                e.onNext(3);
                Log.d(TAG,"3");
//                Thread.sleep(1000);

                Log.d(TAG,"onComplete");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable2, observable1, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer s, @NonNull String s2) throws Exception {
                return s + s2;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG,"onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG,"onNext: "+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete: ");
            }
        });

    }

}
