package winit.mini.endlessrecycler.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Lottery {

    @SerializedName("Id")
    int id;

    @SerializedName("HeadLine")
    String prize;

    @SerializedName("CompanyName")
    String host;

    @SerializedName("TypeId")
    int type;

    @SerializedName("ImageId")
    int imageId;

    @SerializedName("Link")
    String url;

    Date EndDate;

    String description;

    String Terms;

    int MinAge;

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getTerms() {
        return Terms;
    }

    public void setTerms(String terms) {
        Terms = terms;
    }

    public int getMinAge() {
        return MinAge;
    }

    public void setMinAge(int minAge) {
        MinAge = minAge;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
