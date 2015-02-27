package com.gdubina.contactlist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gdubina.contactlist.cmd.LoadDataCommand;
import com.gdubina.contactlist.ui.ContactListFragment;
import com.gdubina.contactlist.ui.ContactListFragment.IContactListListener;
import com.gdubina.contactlist.ui.CreateContactFragment;
import com.gdubina.contactlist.ui.CreateContactFragment.ICreateAccountListener;


public class MainActivity extends ActionBarActivity implements IContactListListener, ICreateAccountListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadDataCommand.start(this, null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ContactListFragment.newInstance())
                .commit();
    }

    @Override
    public void onCreateContact(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CreateContactFragment.newInstance())
                .addToBackStack("create")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onContactCreated() {
        getSupportFragmentManager().popBackStack();
    }
}
