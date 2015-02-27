package com.gdubina.contactlist.store;

import android.support.annotation.NonNull;

import com.annotatedsql.annotation.provider.Provider;
import com.annotatedsql.annotation.provider.URI;
import com.annotatedsql.annotation.sql.Autoincrement;
import com.annotatedsql.annotation.sql.Column;
import com.annotatedsql.annotation.sql.Column.Type;
import com.annotatedsql.annotation.sql.PrimaryKey;
import com.annotatedsql.annotation.sql.Schema;
import com.annotatedsql.annotation.sql.Table;
import com.annotatedsql.annotation.sql.Unique;
import com.hamsterksu.asql.projections.Projection;

/**
 * Created by gdubina on 2/26/2015.
 */
@Provider(name = "ContactListProvider", schemaClass = "ContactListSchema", authority = "com.gdubina.contactlist.AUTHORITY")
@Schema(className = "ContactListSchema", dbName = "contacts.db", dbVersion = 1)
public class ContactStore {

    @Table(Contact.TABLE_NAME)
    public interface Contact{

        String TABLE_NAME = "contact";

        @URI
        String CONTENT_URI = "uri";

        @Autoincrement
        @PrimaryKey
        @Column(type = Type.INTEGER)
        String ID = "_id";

        @Unique
        @Column(type = Type.INTEGER)
        String SERVER_ID = "server_id";

        @NonNull
        @Column
        String NAME = "title";

        @Column
        String PHONE = "phone";

        @Column
        String MOBILE = "mobile";

        @Column
        String WORK = "work";

        @Column
        String ADDRESS = "address";

        @Column
        String PHOTO = "photo";

        @Column
        String EMAIL = "email";

        @Projection
        String PROJECTION = "Contacts";
    }

}
