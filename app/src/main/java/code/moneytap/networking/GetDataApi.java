package code.moneytap.networking;

import java.util.Map;

import code.moneytap.pojo.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by shank on 06/09/17.
 */

public interface GetDataApi {
    @GET("api.php")
    Observable<SearchResult> getSearchData( @QueryMap Map<String, String> options);
}
