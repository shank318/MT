package code.moneytap.di;

import javax.inject.Singleton;

import code.moneytap.base.MyApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Created by shank on 06/09/17.
 */

@Module
public class ApplicationModule {
    MyApplication instance;
    public ApplicationModule(MyApplication application){
        this.instance = application;
    }

    @Provides
    @Singleton
    MyApplication providesApplication(){
        return instance;
    }

}
