package code.boilerplate.features;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import code.boilerplate.networking.GetDataApi;
import code.boilerplate.pojo.FilmLocation;
import code.boilerplate.realmmodels.RealmFilmLocation;
import code.boilerplate.utils.GsonUtil;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import static code.boilerplate.utils.Logger.error;
/**
 * Created by shank on 06/09/17.
 */

public class Service {

    GetDataApi contactsApi;
    public Service(GetDataApi contactsApi) {
        this.contactsApi = contactsApi;
    }

    public Observable<List<FilmLocation>> fetchContactsFromInternet() {
        return contactsApi.getLocations();
    }
    public boolean checkIfContactsExists(){
        RealmFilmLocation realmContact = Realm.getDefaultInstance().where(RealmFilmLocation.class).findFirst();
        return realmContact!=null ? true : false;
    }


    public List<FilmLocation> getDataFromRealm(int limit) {
        Realm mRealm=Realm.getDefaultInstance();
        RealmResults<RealmFilmLocation> list= mRealm.where(RealmFilmLocation.class).findAll();
        List<FilmLocation> temp = new ArrayList<>();
        for(RealmFilmLocation location: list){
            FilmLocation filmLocation = new FilmLocation(location);
            temp.add(filmLocation);
        }
        return temp.size()>limit? temp.subList(0,limit): temp;
    }

    public List<FilmLocation> search(String queryString, int limit) {
        Realm mRealm=Realm.getDefaultInstance();

        RealmQuery<RealmFilmLocation> query = mRealm.where(RealmFilmLocation.class).beginGroup();

        Field[] fields = RealmFilmLocation.class.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Class<?> type = fields[i].getType();
            String fieldName = fields[i].getName();
            if (i > 0) {
                query.or();
            }
            if (type.equals(String.class)) {
                query.contains(fieldName, queryString, Case.INSENSITIVE);
            } else if (type.equals(Integer.class)) {
                query.equalTo(fieldName, Integer.parseInt(queryString));
            }
        }
        RealmResults<RealmFilmLocation> list = query.endGroup().findAll();
        List<FilmLocation> temp = new ArrayList<>();
        for(RealmFilmLocation location: list){
            FilmLocation filmLocation = new FilmLocation(location);
            temp.add(filmLocation);
        }
        return temp.size()>limit? temp.subList(0,limit): temp;
    }

    public Observable<List<FilmLocation>> writeToRealm(List<FilmLocation> contacts) {
        Realm realm = Realm.getDefaultInstance();
        JSONArray contactsJsonArray = new JSONArray();
        Gson gson = GsonUtil.getGson();
        try {
            String jsonString = gson.toJson(
                    contacts,
                    new TypeToken<ArrayList<FilmLocation>>() {}.getType());
            contactsJsonArray = new JSONArray(jsonString);
            realm.beginTransaction();
            realm.createAllFromJson(RealmFilmLocation.class, contactsJsonArray);
            realm.commitTransaction();
        } catch (JSONException e) {
            error(e.getMessage());
        }finally {
            realm.close();
        }
        return Observable.just(contacts);
    }

}
