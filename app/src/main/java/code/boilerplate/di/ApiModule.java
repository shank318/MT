package code.boilerplate.di;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import code.boilerplate.base.MyApplication;
import code.boilerplate.networking.ConnectivityInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shank on 06/09/17.
 */
@Module
public class ApiModule {
    private String baseUrl;
    public ApiModule(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Provides
    public Gson providesGson(){
        return new Gson();
    }

    @Provides
    public Retrofit providesRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(MyApplication.getInstance()))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
