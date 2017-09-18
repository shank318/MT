package code.moneytap.pojo;

import com.google.gson.annotations.SerializedName;


/**
 * Created by shank on 05/09/17.
 */
public class SearchResult {


    @SerializedName("query")
    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

}
