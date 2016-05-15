package winit.mini.endlessrecycler.Models;

import com.google.gson.annotations.SerializedName;

public class PostBody {

    @SerializedName("Countries")
    private String[] countries;

    @SerializedName("MinAge")
    private int minAge;

    @SerializedName("PageIndex")
    private int pageIndex;

    public PostBody() {
    }

    public PostBody(String[] countries, int minAge, int pageIndex) {
        this.countries = countries;
        this.minAge = minAge;
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }

}
