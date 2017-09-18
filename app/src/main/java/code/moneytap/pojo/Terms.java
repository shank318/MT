package code.moneytap.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shank on 18/09/17.
 */

public class Terms {

    @SerializedName("description")
    private List<String> description;

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

}
