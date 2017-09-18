package code.moneytap.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shank on 18/09/17.
 */

public class Thumbnail  {

    @SerializedName("height")
    private Integer height;
    @SerializedName("source")
    private String source;
    @SerializedName("width")
    private Integer width;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}
