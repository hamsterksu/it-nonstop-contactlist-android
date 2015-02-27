package com.gdubina.contactlist.cmd;

import com.gdubina.contactlist.api.ContactDto;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by gdubina on 2/26/2015.
 */
public interface ContactListApi {

    @GET("/api/find")
    List<ContactDto> loadData();

    @POST("/api/delete")
    String delete(@Body ContactDto contact);

    @POST("/api/create")
    ContactDto create(@Body ContactDto dto);
}
