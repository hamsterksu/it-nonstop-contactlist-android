package com.gdubina.contactlist.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdubina.contactlist.ContactListApplication;
import com.gdubina.contactlist.R;
import com.gdubina.contactlist.cmd.AddContactCommand;
import com.telly.groundy.annotations.OnSuccess;

/**
 * Created by gdubina on 2/26/2015.
 */
public class CreateContactFragment extends Fragment{

    public static Fragment newInstance() {
        return new CreateContactFragment();
    }

    private ImageView img;

    private TextView name;
    private TextView phone;
    private TextView mobile;
    private TextView work;
    private TextView email;
    private TextView address;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_contact_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        name = (TextView)v.findViewById(R.id.name);
        work = (TextView)v.findViewById(R.id.work);
        mobile = (TextView)v.findViewById(R.id.mobile);
        phone = (TextView)v.findViewById(R.id.phone);

        address = (TextView)v.findViewById(R.id.address);
        email = (TextView)v.findViewById(R.id.email);

        img = (ImageView)v.findViewById(R.id.img);
        v.findViewById(R.id.btn_image).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        final View btnAdd = v.findViewById(R.id.btn_add);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                btnAdd.setEnabled(name.getText().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        btnAdd.setEnabled(false);
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactCommand.start(getActivity(), CreateContactFragment.this,
                        name.getText().toString(),
                        phone.getText().toString(),
                        mobile.getText().toString(),
                        work.getText().toString(),
                        email.getText().toString(),
                        address.getText().toString(),
                        imageUrl.toString()
                        );
            }
        });
    }

    @OnSuccess(AddContactCommand.class)
    public void onCreated(){
        getListener().onContactCreated();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    private Uri imageUrl;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            imageUrl = data.getData();
            ContactListApplication.getPicasso(getActivity()).load(imageUrl).into(img);
        }
    }

    private ICreateAccountListener getListener(){
        return (ICreateAccountListener)getActivity();
    }
    public interface ICreateAccountListener{
        void onContactCreated();
    }
}
