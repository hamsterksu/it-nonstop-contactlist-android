package com.gdubina.contactlist.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdubina.contactlist.ContactListApplication;
import com.gdubina.contactlist.R;
import com.gdubina.contactlist.cmd.DeleteCommand;
import com.gdubina.contactlist.cmd.LoadDataCommand;
import com.gdubina.contactlist.store.Projections.ContactQuery;
import com.gdubina.contactlist.store.Projections.ContactQuery.Contacts;
import com.squareup.picasso.Picasso;
import com.telly.groundy.annotations.OnFailure;
import com.telly.groundy.annotations.OnSuccess;

/**
 * Created by gdubina on 2/26/2015.
 */
public class ContactListFragment extends Fragment{

    public static Fragment newInstance() {
        ContactListFragment f = new ContactListFragment();
        return f;
    }

    private ContactListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String imageHost;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contactlist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        View v = getView();
        imageHost = getString(R.string.api_host);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadDataCommand.start(getActivity(), ContactListFragment.this);
            }
        });

        adapter = new ContactListAdapter();
        RecyclerView list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, loader);
    }

    @OnSuccess(LoadDataCommand.class)
    @OnFailure(LoadDataCommand.class)
    public void onDataLoaded(){
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contactlist_actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                getListener().onCreateContact();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private LoaderCallbacks<Cursor> loader = new LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(),
                    ContactQuery.CONTENT_URI,
                    Contacts.PROJECTION,
                    null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            adapter.changeCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private class ContactListAdapter extends Adapter<UIHolder>{

        private Cursor c;

        @Override
        public UIHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new UIHolder(LayoutInflater.from(getActivity()).inflate(R.layout.contact_list_item_view, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(UIHolder uiHolder, int i) {
            c.moveToPosition(i);

            uiHolder.btnDelete.setTag(c.getLong(Contacts.INDEX_SERVER_ID));
            uiHolder.name.setText(c.getString(Contacts.INDEX_NAME));

            setText(uiHolder.phoneLabel, uiHolder.phone, c.getString(Contacts.INDEX_PHONE));
            setText(uiHolder.workLabel, uiHolder.work, c.getString(Contacts.INDEX_WORK));
            setText(uiHolder.mobileLabel, uiHolder.mobile, c.getString(Contacts.INDEX_MOBILE));

            setText(uiHolder.addressLabel, uiHolder.address, c.getString(Contacts.INDEX_ADDRESS));
            setText(uiHolder.emailLabel, uiHolder.email, c.getString(Contacts.INDEX_EMAIL));

            String photo = c.getString(Contacts.INDEX_PHOTO);
            Log.d("TAG", "name = " + c.getString(Contacts.INDEX_NAME) + "; photo = " + photo);
            if(TextUtils.isEmpty(photo)){
                uiHolder.img.setImageResource(R.drawable.ic_avatar_placeholder);
            }else{
                Picasso picasso = ContactListApplication.getPicasso(getActivity());
                if(!photo.startsWith("file:")) {
                    picasso.load(imageHost + photo).placeholder(R.drawable.ic_avatar_placeholder).into(uiHolder.img);
                }else{
                    picasso.load(photo).placeholder(R.drawable.ic_avatar_placeholder).into(uiHolder.img);
                }
            }
        }

        @Override
        public int getItemCount() {
            return c == null ? 0 : c.getCount();
        }

        public void changeCursor(Cursor c){
            this.c = c;
            this.notifyDataSetChanged();
        }
    }

    private static void setText(View label, TextView value, String text){
        if(TextUtils.isEmpty(text)){
            label.setVisibility(View.GONE);
            value.setVisibility(View.GONE);
        }else{
            label.setVisibility(View.VISIBLE);
            value.setVisibility(View.VISIBLE);
            value.setText(text);
        }
    }

    private class UIHolder extends ViewHolder{

        ImageView img;
        TextView name;

        View phoneLabel;
        TextView phone;

        View mobileLabel;
        TextView mobile;

        View workLabel;
        TextView work;

        View emailLabel;
        TextView email;

        View addressLabel;
        TextView address;

        View btnDelete;

        public UIHolder(View v) {
            super(v);
            img = (ImageView)v.findViewById(R.id.img);
            name = (TextView)v.findViewById(R.id.name);

            phoneLabel = v.findViewById(R.id.phone_label);
            phone = (TextView)v.findViewById(R.id.phone);

            workLabel = v.findViewById(R.id.work_label);
            work = (TextView)v.findViewById(R.id.work);

            mobileLabel = v.findViewById(R.id.mobile_label);
            mobile = (TextView)v.findViewById(R.id.mobile);

            emailLabel = v.findViewById(R.id.email_label);
            email = (TextView)v.findViewById(R.id.email);

            addressLabel = v.findViewById(R.id.address_label);
            address = (TextView)v.findViewById(R.id.address);
            btnDelete = v.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    long itemId = (Long) view.getTag();
                    DeleteCommand.start(getActivity(), ContactListFragment.this, itemId);
                }
            });
        }

    }

    private IContactListListener getListener(){
        return (IContactListListener)getActivity();
    }

    public interface IContactListListener{
        void onCreateContact();
    }
}
