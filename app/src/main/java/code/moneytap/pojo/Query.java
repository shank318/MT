package code.moneytap.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shank on 18/09/17.
 */
public class Query {

    @SerializedName("pages")
    private List<Page> pages = null;

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }



}
