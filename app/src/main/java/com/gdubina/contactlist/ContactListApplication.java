package com.gdubina.contactlist;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

/**
 * Created by gdubina on 2/26/2015.
 */
public class ContactListApplication extends Application{

    Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        picasso = Picasso.with(this);
    }

    public static Picasso getPicasso(Context context) {
        ContactListApplication app = (ContactListApplication)context.getApplicationContext();
        return app.picasso;
    }
}
