package code.moneytap.di;

import code.moneytap.base.MyApplication;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by shank on 06/09/17.
 */
@Component(modules={ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(MyApplication application);
    Retrofit getRetrofit();
}
