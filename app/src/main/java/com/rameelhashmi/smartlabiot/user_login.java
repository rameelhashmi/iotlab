package com.rameelhashmi.smartlabiot;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class user_login extends Fragment implements View.OnClickListener{

    private Button login_btn;
    private EditText log_email,log_password;
    private TextView log_register;
    private SharedPreferences pref;
    private ProgressBar progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {

        View view = inflater.inflate(R.layout.login,container,false);
        views(view);
        return view;
    }// creating views

    private void views(View view)

    {
        pref = getActivity().getPreferences(0);

        login_btn = (Button)view.findViewById(R.id.login_btn);
        log_register = (TextView)view.findViewById(R.id.log_register);
        log_email = (EditText)view.findViewById(R.id.log_email);
        log_password = (EditText)view.findViewById(R.id.log_password);
        progress=(ProgressBar)view.findViewById(R.id.progress);

        login_btn.setOnClickListener(this);
        log_register.setOnClickListener(this);
    }// views function ends

    @Override
    public void onClick(View v) {

       //Switch case is used for the user to login and register
        switch (v.getId())

        {

            case R.id.log_register:

                Fragment register = new user_register();
                FragmentTransaction fragtrans1 = getFragmentManager().beginTransaction();
                fragtrans1.replace(R.id.fragment_box,register);
                fragtrans1.commit();
                break;


            //In case of user click on login page the user will be directed to the login page
            case R.id.login_btn:

                String email = log_email.getText().toString();

                String password = log_password.getText().toString();

                if(email.isEmpty() && password.isEmpty()) //Validation of name and email

                {
                    Snackbar.make(getView(), "Empty Fields !", Snackbar.LENGTH_LONG).show();

                }


                else

                    {
                        progress.setVisibility(View.VISIBLE);

                        logging(email,password);// jumps to login function

                    }
                break;


        } // SWITCH CASE ENDS
    }// button click function ends
    private void logging(String email,String password)// logging method

    {

        //retrofit library using for request and response between client and server
        //creating retrofit adapter setting base URL
        Retrofit retrofit = new Retrofit.Builder().baseUrl(consts.ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();

        int_req IntReq = retrofit.create(int_req.class);

        user client = new user();
        client.setEmail(email);
        client.setPassword(password);
        req_serv request = new req_serv();
        request.setprocess(consts.process_signin);// Getting process from request class
        request.setuser(client);
        Call<resp_serv> response = IntReq.process(request);

        response.enqueue(new Callback<resp_serv>()


        {

            @Override
            public void onResponse(Call<resp_serv> call, retrofit2.Response<resp_serv> response)// on response function


            {

                resp_serv resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(consts.success)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(consts.logged_in,true);
                    editor.putString(consts.email,resp.getUser().getEmail());
                    editor.putString(consts.name,resp.getUser().getName());
                    editor.putString(consts.unique_id,resp.getUser().getUnique_id());
                    editor.apply();

                    //Go to user profile
                    Fragment profile = new user_profile();
                    FragmentTransaction fragtrans = getFragmentManager().beginTransaction();
                    fragtrans.replace(R.id.fragment_box,profile);
                    fragtrans.commit();

                }
            }


            @Override
            public void onFailure(Call<resp_serv> call, final Throwable t)

            {

               // Log.d(consts.tag, "failed");
                // Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

                    Snackbar.make(getView(), "Invalid Email or Password please try in 10 seconds", Snackbar.LENGTH_LONG).show();
                    CountDownTimer timer = new CountDownTimer(10000, 1000) {


                        @Override
                        public void onTick(long l) {

                            progress.setVisibility(View.VISIBLE);
                            log_password.setClickable(false);
                            log_password.setEnabled(false);
                            log_email.setEnabled(false);
                            log_password.setClickable(false);



                        }

                        @Override
                        public void onFinish() {
                            progress.setVisibility(View.INVISIBLE);
                            log_password.setClickable(true);
                            log_password.setEnabled(true);
                            log_email.setEnabled(true);
                            log_password.setClickable(true);

                        }
                    };
                    timer.start();



            }//ON failure ends

        });
    }//Logging ends


}//Class ends














