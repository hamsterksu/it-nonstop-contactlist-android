package com.gdubina.contactlist.cmd;

import android.content.Context;
import android.os.Bundle;

import com.gdubina.contactlist.api.ContactDto;
import com.gdubina.contactlist.store.Projections.ContactQuery;
import com.gdubina.contactlist.ui.CreateContactFragment;
import com.telly.groundy.Groundy;
import com.telly.groundy.TaskResult;

/**
 * Created by gdubina on 2/26/2015.
 */
public class AddContactCommand extends BaseCommand{

    public static final String ARG_NAME = "ARG_NAME";
    public static final String ARG_PHONE = "ARG_PHONE";
    public static final String ARG_MOBILE = "ARG_MOBILE";
    public static final String ARG_WORK = "ARG_WORK";
    public static final String ARG_EMAIL = "ARG_EMAIL";
    public static final String ARG_ADRESS = "ARG_ADRESS";
    public static final String ARG_IMG = "ARG_IMG";

    @Override
    protected TaskResult doInBackground() {
        Bundle arg = getArgs();

        ContactDto dto = new ContactDto();
        dto.name = arg.getString(ARG_NAME);
        dto.work = arg.getString(ARG_WORK);
        dto.mobile = arg.getString(ARG_MOBILE);
        dto.phone = arg.getString(ARG_PHONE);
        dto.email = arg.getString(ARG_EMAIL);
        dto.address = arg.getString(ARG_ADRESS);

        ContactDto newDto = getApi().create(dto);
        if(newDto != null) {
            newDto.photo = arg.getString(ARG_IMG);

            getContext().getContentResolver()
                    .insert(ContactQuery.CONTENT_URI, newDto.toValues());
            return succeeded();
        }
        return failed();
    }

    public static void start(Context context, Object callback,
                             String name,
                             String phone,
                             String mobile,
                             String work,
                             String email,
                             String address,
                             String img) {
        Groundy.create(AddContactCommand.class)
                .arg(ARG_NAME, name)
                .arg(ARG_PHONE, phone)
                .arg(ARG_MOBILE, mobile)
                .arg(ARG_WORK, work)
                .arg(ARG_EMAIL, email)
                .arg(ARG_ADRESS, address)
                .arg(ARG_IMG, img)
                .callback(callback)
                .queueUsing(context);
    }
}
