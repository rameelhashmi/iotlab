<?php

require_once 'dboperation.php';//Calling file dboperation.php to use functions in control.php

class control{

private $db;

//This function is used to create object of dboperation.php
//db variable is use to call all the function made in dboperations.php
public function __construct() {

      $this -> db = new dboperation();

}

//This function is made to register new user with name an email
//The sql query will save data as in dboperation.php function enter_data
//In this function JSON webservice is use for response from the server
//because in android application if wrong name and email enter the response will be generated through JSON
//First it will check if this user with this email already exist if exist then the response will be "user already registered"
//If the user is new the response will be user successfully registered and the data will be saved to database by using function enter_data
//PHP json_encode() function converts a PHP value into a JSON value so that adroid application can use it further
public function signup_new_user($name, $email) {

	$db = $this -> db;

	if (!empty($name) && !empty($email)) {

  		if ($db -> check_user_exist_android($email)) {

  			$response["result"] = "failure";
  			$response["message"] = "User Already Registered !";
  			return json_encode($response);

  		} else {

  			$result = $db -> enter_data($name, $email);

  			if ($result) {

				  $response["result"] = "success";
  				$response["message"] = "Please check email to verify email address!";
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "failure";
  				$response["message"] = "Registration Failure";
  				return json_encode($response);

  			}
  		}					
  	} else {

      //If nothing enter in the fields the function empty_param_msg will be called
  		return $this -> empty_param_msg();

  	}
}

//This function is used to allow user login into the system
//The email and password received by the administrator will be use to login into system
//If users email is not present in the database the JSON response will be arise "Invalid email or password"
//In case if password is wrong the JSON response will be from server will be the same
//If user successfully enter correct email and password then the user will access the system as allowed.
public function user_login($email, $password) {

  $db = $this -> db;

  if (!empty($email) && !empty($password)) {

    if ($db -> check_user_exist($email)) {

       $result =  $db -> login_check($email, $password);


       if(!$result) {


        // $response["result"] = "failure";
        // $response["message"] = "Invaild Password";
        // return json_encode($response);



       } else {

        $response["result"] = "success";
        $response["message"] = "Login Successful";
        $response["user"] = $result;
        return json_encode($response);

       }

    } 
    else 

    {

       // $response["result"] = "failure";
       // $response["message"] = "Email is not registered with us";
       // return json_encode($response);

    }
  } 
  else 

  {

      return $this -> empty_param_msg(); //If nothing entered in the fields function empty_param_msg called.
    }

}//Function user_login ends


//This function is used to change users password
//the user has to first login into system with correct email and password using login_check function
//Then the user will be able to change password
// The user has to enter old password first and then new desired password if the old password is entered wrong the JSON response will
//be wrong old password the system first verify the old password the user will be able to change password
//If the old password is correct the password will be change successfully and will be updated to table name 'users' also

public function password_change($email, $old_password, $new_password) {

  $db = $this -> db;

  if (!empty($email) && !empty($old_password) && !empty($new_password)) {

    if(!$db -> login_check($email, $old_password)){

      $response["result"] = "failure";
      $response["message"] = 'Wrong Old Password';
      return json_encode($response);

    } else {


    $result = $db -> password_change($email, $new_password);

      if($result) {

        $response["result"] = "success";
        $response["message"] = "Password Changed Successfully";
        return json_encode($response);

      } else {

        $response["result"] = "failure";
        $response["message"] = 'Error Updating Password';
        return json_encode($response);

      }

    } 
  } else {

      return $this -> empty_param_msg();//If the fields are empty empty_param_msg function will be called
  }

}

//This function is used to check the correct pattern of email like xyz@xyz.com
//FILTER_VALIDATE_EMAIL is library predefined in php
//This function will be use in index.php
public function valid_email_pattern($email)
{

  return filter_var($email, FILTER_VALIDATE_EMAIL);
}



//This function is use to check whether user enter into fields or not
public function empty_param_msg(){


  $response["result"] = "failure";
  $response["message"] = "Parameters should not be empty !";
  return json_encode($response);

}

//This function is use to check whether user enter correct parameters into fields like 
//email to the email field and password to the password field
public function invalid_param_msg(){

  $response["result"] = "failure";
  $response["message"] = "Invalid Parameters";
  return json_encode($response);

}

//This function is use to check wheteher user enter correct email address which is available in database
public function invalid_email_msg(){

  $response["result"] = "failure";
  $response["message"] = "Invalid Email";
  return json_encode($response);

}


}
