package com.rameelhashmi.smartlabiot;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class user_profile extends Fragment implements View.OnClickListener

{

    private TextView prof_name,prof_email,chg_message;
    private SharedPreferences pref;
    private Button btn_change_password,btn_logout;
    private EditText chg_old_password,chg_new_password;
    private AlertDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {

        View view = inflater.inflate(R.layout.profile,container,false);
        setview(view);
        return view;
    }

    private void setview(View view)

    {

        prof_name = (TextView)view.findViewById(R.id.prof_name);
        prof_email = (TextView)view.findViewById(R.id.prof_email);
        btn_change_password = (Button) view.findViewById(R.id.btn_chg_password);
        btn_logout = (Button)view.findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)

    {

        pref = getActivity().getPreferences(0);
        prof_name.setText("USER NAME : "+pref.getString(consts.name,""));
        prof_email.setText("EMAIL : "+pref.getString(consts.email,""));

    }




    @Override
    public void onClick(View v)

    {
        switch (v.getId())

        {

            case R.id.btn_chg_password:

                dialogboxtochangepassword();
                break;
            case R.id.btn_logout:

                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(consts.logged_in,false);
                editor.putString(consts.email,"");
                editor.putString(consts.name,"");
                editor.putString(consts.unique_id,"");
                editor.apply();

                Fragment login = new user_login();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_box,login);
                ft.commit();                break;
        } //SWITCH CASE ENDS HERE
    }



    //Changing password functions
    private void changingpassword(String email,String old_password,String new_password)

    {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(consts.ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();

        int_req requestInterface = retrofit.create(int_req.class);

        user client = new user();
        client.setEmail(email);
        client.setOld_password(old_password);
        client.setNew_password(new_password);
        req_serv request = new req_serv();
        request.setprocess(consts.process_changepassword);//Calling changepassword process from PHP backend
        request.setuser(client);
        Call<resp_serv> response = requestInterface.process(request);

        response.enqueue(new Callback<resp_serv>()





        {




            @Override
            public void onResponse(Call<resp_serv> call, retrofit2.Response<resp_serv> response)

            {

                resp_serv resp = response.body();
                if(resp.getResult().equals(consts.success)){


                    chg_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();



                }

                else

                {

                    chg_message.setVisibility(View.VISIBLE);

                    chg_message.setText(resp.getMessage());

                }
            }


            @Override
            public void onFailure(Call<resp_serv> call, Throwable t)

            {

                Log.d(consts.tag,"failed");
                chg_message.setVisibility(View.VISIBLE);
                chg_message.setText(t.getLocalizedMessage());


            }
        });
    }


    //Dialog box open to change password

    private void dialogboxtochangepassword()

    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.changepassword, null);
        chg_old_password = (EditText)view.findViewById(R.id.chg_old_password);
        chg_new_password = (EditText)view.findViewById(R.id.chg_new_password);
        chg_message = (TextView)view.findViewById(R.id.chg_message);

        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)

            {
                //override function, keep empty does not need here
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = chg_old_password.getText().toString();
                String new_password = chg_new_password.getText().toString();
                if(old_password.isEmpty() && new_password.isEmpty()){
                    chg_message.setVisibility(View.VISIBLE);


                }else if ( new_password.length()<=11)
                {
                    Toast.makeText(getActivity(),"Password should be minimum 12 characters long",Toast.LENGTH_LONG).show();
                    chg_message.setVisibility(View.INVISIBLE);


                }
                else

                {
                    changingpassword(pref.getString(consts.email,""),old_password,new_password);



                }
            }
        });
    }
}









