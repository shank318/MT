package code.moneytap.features;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.moneytap.networking.GetDataApi;
import code.moneytap.pojo.Page;
import code.moneytap.pojo.SearchResult;
import code.moneytap.pojo.Terms;
import code.moneytap.pojo.Thumbnail;
import code.moneytap.realmmodels.RealmPage;
import code.moneytap.utils.GsonUtil;
import code.moneytap.utils.Logger;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

import static code.moneytap.utils.Logger.error;
/**
 * Created by shank on 06/09/17.
 */

public class Service {

    GetDataApi contactsApi;
    public Service(GetDataApi contactsApi) {
        this.contactsApi = contactsApi;
    }

    public Observable<SearchResult> fetchContactsFromInternet(String query) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("action","query");
        map.put("format","json");
        map.put("prop","pageimages|pageterms");
        map.put("generator","prefixsearch");
        map.put("redirects","1");
        map.put("formatversion","2");
        map.put("piprop","thumbnail");
        map.put("pithumbsize","1");
        map.put("wbptterms","description");
        map.put("piprop","thumbnail");
        map.put("pithumbsize","1");
        map.put("gpssearch",query);
        map.put("gpslimit","10");
        map.put("pilimit","10");
        return contactsApi.getSearchData(map);
    }
    public boolean checkIfContactsExists(){
        RealmPage realmContact = Realm.getDefaultInstance().where(RealmPage.class).findFirst();
        return realmContact!=null ? true : false;
    }

    public RealmResults<RealmPage> readAllContactsFromRealm() {
        return Realm.getDefaultInstance().where(RealmPage.class)
                .findAll();
    }

    public Observable<SearchResult> writeToRealm(SearchResult result) {
        Realm realm = Realm.getDefaultInstance();
        Logger.debug("Writing data in realm::");
        JSONArray contactsJsonArray = new JSONArray();
        Gson gson = GsonUtil.getGson();
        try {
            List<RealmPage> rP = new ArrayList<>();
            List<Page> pages = result.getQuery().getPages();
            for(Page page: pages){
                RealmPage obj = new RealmPage();
                obj.setTitle(page.getTitle());
                obj.setPageid(page.getPageid());
                Thumbnail thumbnail = page.getThumbnail();
                if(thumbnail!=null) obj.setImageUrl(thumbnail.getSource());
                Terms terms = page.getTerms();
                if(terms!=null){
                    List<String> des = terms.getDescription();
                    if(des!=null && des.size()>0){
                        obj.setDescription(des.get(0));
                    }
                }
                rP.add(obj);
            }
            String jsonString = gson.toJson(
                    rP,
                    new TypeToken<ArrayList<RealmPage>>() {}.getType());
            contactsJsonArray = new JSONArray(jsonString);
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(RealmPage.class, contactsJsonArray);
            realm.commitTransaction();
            Logger.debug("Successfully written in realm::");
        } catch (JSONException e) {
            error(e.getMessage());
        }finally {
            realm.close();
        }
        return Observable.just(result);
    }

}
