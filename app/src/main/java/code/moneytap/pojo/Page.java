package code.moneytap.pojo;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

/**
 * Created by shank on 18/09/17.
 */

public class Page {

    @SerializedName("index")
    private Integer index;
    @SerializedName("ns")
    private Integer ns;

    @PrimaryKey
    @SerializedName("pageid")
    private Integer pageid;

    @SerializedName("thumbnail")
    private Thumbnail thumbnail;
    @SerializedName("title")
    private String title;
    @SerializedName("terms")
    private Terms terms;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }

    public Integer getPageid() {
        return pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }



    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public Page(RealmPage page) {
//        title = page.getTitle();
//        index = page.getIndex();
//        pageid = page.getPageid();
//        thumbnail = page.getThumbnail();
//        terms = page.getTerms();
//    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

}
