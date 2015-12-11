package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.graphics.Bitmap;

public class ItemObjects {
    private String price;
    private String desc;
    private Bitmap photo;

    public ItemObjects(String desc, Bitmap photo,String price) {//get price
        this.desc = desc;
        this.photo = photo;
        this.price=price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
