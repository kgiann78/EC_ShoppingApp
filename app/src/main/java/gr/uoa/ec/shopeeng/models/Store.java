package gr.uoa.ec.shopeeng.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Store implements Parcelable, Serializable {
    private String storeId;
    private String name;
    private String address;
    private String country;
    private String site;
    private String logo;
    private String price;
    private String rating;

    public Store() {
    }

    ;

    public Store(String storeId, String name, String address, String country, String site, String logo, String price, String rating) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.country = country;
        this.site = site;
        this.logo = logo;
        this.price = price;
        this.rating = rating;
    }

    // Parcelling part
    public Store(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        this.storeId = data[0];
        this.name = data[1];
        this.address = data[2];
        this.country = data[3];
        this.logo = data[4];
        this.price = data[5];
        this.rating = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.storeId, this.name, this.address, this.country, this.logo, this.price, this.rating});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeId='" + storeId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", site='" + site + '\'' +
                ", logo='" + logo + '\'' +
                ", price='" + price + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
