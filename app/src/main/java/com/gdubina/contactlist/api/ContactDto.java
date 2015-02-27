package com.gdubina.contactlist.api;

import android.content.ContentValues;

import com.gdubina.contactlist.store.ContactStore.Contact;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gdubina on 2/26/2015.
 */
public class ContactDto {
    @SerializedName("Id")
    public long id;

    @SerializedName("Name")
    public String name;

    @SerializedName("Phone")
    public String phone;

    @SerializedName("Mobile")
    public String mobile;

    @SerializedName("Work")
    public String work;

    @SerializedName("Address")
    public String address;

    @SerializedName("Photo")
    public String photo;

    @SerializedName("Email")
    public String email;


    public ContentValues toValues() {
        ContentValues v = new ContentValues();
        v.put(Contact.SERVER_ID, id);
        v.put(Contact.NAME, name);
        v.put(Contact.PHONE, phone);
        v.put(Contact.MOBILE, mobile);
        v.put(Contact.WORK, work);
        v.put(Contact.ADDRESS, address);
        v.put(Contact.EMAIL, email);
        v.put(Contact.PHOTO, photo);
        return v;
    }
}
