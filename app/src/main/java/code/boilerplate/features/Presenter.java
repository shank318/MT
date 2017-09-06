package code.boilerplate.features;

import java.net.UnknownHostException;
import java.util.List;

import code.boilerplate.base.BasePresenter;
import code.boilerplate.networking.ConnectivityInterceptor;
import code.boilerplate.pojo.FilmLocation;
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
        getView().showDialog();
        addSubscription(service.fetchContactsFromInternet()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(service::writeToRealm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadFailure));
    }

    void onLoadSuccess(List<FilmLocation> contacts) {

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

//    /**
//     * @Param List of Observable of Film Locations
//     * Get the lat lng of every address parallely
//     * and merge the outcome of each observable via zip
//     */
//    public void getLatLngOfFilmsLocations(List<Observable<FilmLocation>> observables){
//        addSubscription(Observable.zip(observables, new FuncN(){
//            public List<FilmLocation> call(java.lang.Object... args){
//                List<FilmLocation> result=new ArrayList<FilmLocation>(); //to be made
//                //preparatory code for using the args
//                for (Object obj : args){
//                    FilmLocation retObj = (FilmLocation)obj;
//                    Logger.debug(retObj.getLocations()+" "+retObj.getLat()+" "+retObj.getLng());
//                    result.add(retObj);
//                    //code to use the arg once at a time to combine N of them into one.
//                }
//                return result;
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::onLoadGeoCodes, this::onFailureGeoCodes));
//    }
}
