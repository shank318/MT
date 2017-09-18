package code.moneytap.realmmodels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by shank on 18/09/17.
 */

public class RealmPage extends RealmObject {
    private String imageUrl;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    private String description;
    @PrimaryKey
    private Integer pageid;

    public Integer getPageid() {
        return pageid;
    }
    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    private String title;

}
