package gr.uoa.ec.shopeeng.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class ShoppingItem implements Parcelable, Serializable {

    private Product product;
    private Store store;

    public ShoppingItem(Product product, Store store) {
        this.product = product;
        this.store = store;
    }

    public ShoppingItem() {

    }

    // Parcelling part
    public ShoppingItem(Parcel in) {
        in.readArray(ShoppingItem.class.getClassLoader());
        Object[] data = new Object[2];
        this.product = (Product) data[0];
        this.store = (Store) data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeArray(new Object[]{this.product,
                this.store});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ShoppingItem createFromParcel(Parcel in) {
            return new ShoppingItem(in);
        }

        public ShoppingItem[] newArray(int size) {
            return new ShoppingItem[size];
        }
    };

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "product=" + product +
                ", store=" + store +
                '}';
    }
}
