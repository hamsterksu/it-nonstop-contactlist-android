package com.gdubina.contactlist.cmd;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.gdubina.contactlist.api.ContactDto;
import com.gdubina.contactlist.store.Projections.ContactQuery;
import com.telly.groundy.Groundy;
import com.telly.groundy.TaskResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gdubina on 2/26/2015.
 */
public class LoadDataCommand extends BaseCommand{

    @Override
    protected TaskResult doInBackground() {
        List<ContactDto> contacts = getApi().loadData();

        ArrayList<ContentValues> valueses = new ArrayList<>(contacts.size());
        for(ContactDto c : contacts){
            valueses.add(c.toValues());
        }
        ContentResolver cr = getContext().getContentResolver();
        cr.delete(ContactQuery.CONTENT_URI, null, null);
        cr.bulkInsert(ContactQuery.CONTENT_URI, valueses.toArray(new ContentValues[valueses.size()]));

        return succeeded();
    }

    public static void start(Context context, Object callback){
        Groundy.create(LoadDataCommand.class).callback(callback).queueUsing(context);
    }
}
