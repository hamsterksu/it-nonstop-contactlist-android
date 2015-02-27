package com.gdubina.contactlist.cmd;

import com.gdubina.contactlist.R;
import com.google.gson.GsonBuilder;
import com.telly.groundy.GroundyTask;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by gdubina on 2/26/2015.
 */
public abstract class BaseCommand extends GroundyTask{

    protected ContactListApi getApi(){
        String HOST = getContext().getResources().getString(R.string.api_host);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(HOST)
                .setConverter(new GsonConverter(new GsonBuilder().create()))
                .build();
        return restAdapter.create(ContactListApi.class);
    }

}
