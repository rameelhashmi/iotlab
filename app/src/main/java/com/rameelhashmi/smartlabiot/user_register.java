package com.rameelhashmi.smartlabiot;

import android.app.Fragment;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class user_register extends Fragment implements View.OnClickListener {


    private Button reg_btn;
    private EditText reg_email, reg_name;
    private TextView reg_login;

    public final String SiteKey = "6Lckn4kUAAAAACrVjiV21dMiYqWH_RKdXPZKInIu";
    public final String URL_VERIFY_ON_SERVER = "http://rameeltuc.000webhostapp.com/google-recaptcha-verfication.php";
    public final String SiteSecretKey = "6LdWf4kUAAAAAFgqxUpvA1BoXmrkgT8TT4RwsUIi";
    CheckBox checkbox;
    String mCaptcha = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register, container, false);
        views(view);

        return view;
    }

    private void views(View view)

    {

        reg_btn = (Button) view.findViewById(R.id.reg_btn);
        reg_login = (TextView) view.findViewById(R.id.reg_login);
        reg_name = (EditText) view.findViewById(R.id.reg_name);
        reg_email = (EditText) view.findViewById(R.id.reg_email);

        reg_btn.setOnClickListener(this);
        reg_login.setOnClickListener(this);
        checkbox = (CheckBox)view.findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked())
                SafetyNet.getClient(getContext()).verifyWithRecaptcha(SiteKey)
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                Log.d("TAG", "onSuccess");

                                if (!response.getTokenResult().isEmpty()) {

                                    // Received captcha token
                                    // This token still needs to be validated on the server
                                    // using the SECRET key
                                    verifyTokenOnServer(response.getTokenResult());
                                }
                            }
                        })
                        .addOnFailureListener(getActivity(), new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    Log.d("TAG", "Error message: " +
                                            CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                                } else {
                                    Log.d("TAG", "Unknown type of error: " + e.getMessage());
                                }
                                checkbox.setChecked(false);
                            }
                        });
            }
        });
    }


    @Override
    public void onClick(View v)

    {

        switch (v.getId())

        {
            case R.id.reg_login:

                Fragment login = new user_login();
                FragmentTransaction fragtrans = getFragmentManager().beginTransaction();
                fragtrans.replace(R.id.fragment_box, login);
                fragtrans.commit();
                break;

            case R.id.reg_btn:

                final String name = reg_name.getText().toString().trim();
                final String email = reg_email.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    // Showing reCAPTCHA dialog
                    if(mCaptcha.equals("1"))
                        registering(reg_name.getText().toString(), reg_email.getText().toString());
                    else
                        Snackbar.make(getView(), "Verify the Captcha at first !", Snackbar.LENGTH_LONG).show();

                } else {
                    Snackbar.make(getView(), "Empty Fields !", Snackbar.LENGTH_LONG).show();
                }

                break;

        }

    }

    public void verifyTokenOnServer(final String token) {
        Log.d("TAG", "Captcha Token" + token);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_VERIFY_ON_SERVER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");

                    if (success) {
                        // Congrats! captcha verified successfully on server
                        // TODO - submit the feedback to your server
                        Toast.makeText(getContext(), "captcha success", Toast.LENGTH_SHORT).show();
                        mCaptcha = "1";
                        checkbox.setChecked(true);
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        checkbox.setChecked(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Json Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    checkbox.setChecked(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error.getMessage());
                checkbox.setChecked(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("recaptcha-response", token);
                checkbox.setChecked(false);

                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    //Register new user with name and email
    private void registering(String name, String email)

    {

        //creating retrofit adapter
        Retrofit retrofit = new Retrofit.Builder().baseUrl(consts.ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();

        int_req requestInterface = retrofit.create(int_req.class);

        user client = new user();
        client.setName(name);
        client.setEmail(email);
        req_serv requesting = new req_serv();
        requesting.setprocess(consts.process_signup);
        requesting.setuser(client);
        Call<resp_serv> response = requestInterface.process(requesting);

        response.enqueue(new Callback<resp_serv>()

        {
            @Override
            public void onResponse(Call<resp_serv> call, retrofit2.Response<resp_serv> response)

            {

                resp_serv rep = response.body();
                Snackbar.make(getView(), rep.getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<resp_serv> call, Throwable t)

            {

                Log.d(consts.tag, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }
}











