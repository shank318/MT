package code.moneytap.features;

import java.net.UnknownHostException;

import code.moneytap.base.BasePresenter;
import code.moneytap.networking.ConnectivityInterceptor;
import code.moneytap.pojo.SearchResult;
import code.moneytap.realmmodels.RealmPage;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shank on 06/09/17.
 */

public class Presenter extends BasePresenter<IUiView> {

    Service service;
    public Presenter(Service service){
        this.service = service;
    }

    public void getData() {
        if(!service.checkIfContactsExists()) getView().showDialog();
        addSubscription(service.fetchContactsFromInternet("Sachin")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(service::writeToRealm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadFailure));
    }

    void onLoadSuccess(SearchResult result) {
        getView().hideDialog();
        if(result.getQuery()==null) return;
        getView().onDataReceived(result.getQuery().getPages());
    }

    void onLoadFailure(Throwable throwable) {
        getView().hideDialog();
        if(throwable instanceof ConnectivityInterceptor.NoConnectivityException
                || throwable instanceof UnknownHostException){
            // No internet connection
            getView().showNoInternetError(throwable);
        }else{
            getView().showErrorMessage(throwable);
        }
    }

    // UI Thread - Can't transfer realm results to a different thread,
    // its very fast so doesn't matter
    public RealmResults<RealmPage> readCacheDataFromRealm() {
        return service.readAllContactsFromRealm();
    }

}
