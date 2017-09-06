package code.boilerplate.networking;

import java.util.List;


import code.boilerplate.pojo.FilmLocation;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by shank on 06/09/17.
 */

public interface GetDataApi {
    @GET("wwmu-gmzc.json")
    Observable<List<FilmLocation>> getLocations();
}
