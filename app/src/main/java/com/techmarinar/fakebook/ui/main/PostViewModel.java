package com.techmarinar.fakebook.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techmarinar.fakebook.data.PostsClient;
import com.techmarinar.fakebook.pojo.PostModel;

import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;



public class PostViewModel extends ViewModel {

    private static final String TAG = "PostViewModel";

    MutableLiveData<List<PostModel>> postsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> posts = new MutableLiveData<>();

    public void getPosts() {
        //initiating the observable

        io.reactivex.rxjava3.core.Observable observable = PostsClient.getINSTANCE().getPosts()
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());



        //create the observer
        io.reactivex.rxjava3.core.Observer <List<PostModel>> listObserver =
        new io.reactivex.rxjava3.core.Observer<List<PostModel>>() {
            @Override
            public void onSubscribe(io.reactivex.rxjava3.disposables.@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<PostModel> postModels) {
                postsMutableLiveData.postValue(postModels);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        //subscribe the observer with observable
        observable.subscribe(listObserver);

    }
}
