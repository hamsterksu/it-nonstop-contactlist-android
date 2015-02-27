package com.gdubina.contactlist.cmd;

import android.content.Context;

import com.gdubina.contactlist.api.ContactDto;
import com.gdubina.contactlist.store.ContactStore.Contact;
import com.gdubina.contactlist.store.Projections.ContactQuery;
import com.telly.groundy.Groundy;
import com.telly.groundy.GroundyTask;
import com.telly.groundy.TaskResult;

/**
 * Created by gdubina on 2/26/2015.
 */
public class DeleteCommand extends BaseCommand{

    private static final String ARG_SERVER_ITEM_ID = "ARG_SERVER_ITEM_ID";

    @Override
    protected TaskResult doInBackground() {
        long serverId = getArgs().getLong(ARG_SERVER_ITEM_ID);
        ContactDto dto = new ContactDto();
        dto.id = serverId;
        getApi().delete(dto);

        getContext().getContentResolver().delete(
                ContactQuery.CONTENT_URI,
                Contact.SERVER_ID + " = ?",
                new String[]{String.valueOf(serverId)}
        );
        return succeeded();
    }

    public static void start(Context context, Object callback, long itemId){
        Groundy.create(DeleteCommand.class)
                .arg(ARG_SERVER_ITEM_ID, itemId)
                .callback(callback).queueUsing(context);
    }
}
