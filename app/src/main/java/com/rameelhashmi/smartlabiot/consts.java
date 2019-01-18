package com.rameelhashmi.smartlabiot;


// Constants use in further java Clases many times
//IP of local server should be defined in ROOT_URL where the database is installed so that the application fetch data from server
public class consts {

    public static final String ROOT_URL = "http://192.168.1.102/";

    //name, email and unique id for identification
    public static final String name = "name";
    public static final String email = "email";
    public static final String unique_id = "unique_id";

    //Processes runs according to the backend PHP script
    public static final String process_signup = "signup";
    public static final String process_signin = "signin";
    public static final String process_changepassword = "changepass";

    //backend messages in case of failure and success
    public static final String success = "success";
    public static final String logged_in = "loggedin";




    public static final String tag = "SMART LAB IoT";

}



















