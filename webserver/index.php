

<?php
//This file index.php is use by by android application to connect with database installed into server

require_once 'control.php';//Calling file control.php

//action is the object variable to call all the functions from the file control.php
$action = new control();


//If the server request method is POST like user wants to register or login 
//POST function is used in php to connect with server
if ($_SERVER['REQUEST_METHOD'] == 'POST')
{
  $data = json_decode(file_get_contents("php://input"));

  if(isset($data -> process)){

  	$process = $data -> process;

  	//If the user wants to signup to the system then the process will be signup
    //This process will be called in android app and do the function as defined in it
    //It will receive user name and email
    //The email first verify through valid_email_pattern to meet the email pattern requirements
    //If the email is not valid pattern the function invalid_email_msg will be called
    //If the email is correct the will be signup to the system successfully
    if(!empty($process)){

  		//
      if($process == 'signup'){

  			if(isset($data -> user ) //The isset function is used to check whether the variable is set or not
          && !empty($data -> user) 
          && isset($data -> user -> name) 
  				&& isset($data -> user -> email) )
        {

  				$user = $data -> user;
  				$name = $user -> name;
  				$email = $user -> email;
  	

          if ($action -> valid_email_pattern($email)) {

            
            
            echo $action -> signup_new_user($name, $email);



          }

          else{

            echo $action -> invalid_email_msg();
          }

  			} else {

  				echo $action -> invalid_param_msg();

  			}




  		}

      //If the user wants to signin to the system then the process will be signin
    //This process will be called in android app and do the function as defined in it
    //The user enter email and password if it is already registered and present in database the function user_login checks this scenerio
      //If the parameters are not valid the function invalid_param_msg will be called

      else if ($process == 'signin') {

        if(isset($data -> user ) 
          && !empty($data -> user) 
          && isset($data -> user -> email) 
          && isset($data -> user -> password))
        {

          $user = $data -> user;
          $email = $user -> email;
          $password = $user -> password;

          echo $action -> user_login($email, $password);

        } else {

          echo $action -> invalid_param_msg();

        }

       

        //If the user wants to change password the process changepass will be called
        //User has to enter old password and new password the function password_change from control.php will be called
        //and will do the function as defined
        //If the parameters is invalid function invalid_param_msg will be called
      } else if ($process == 'changepass') {

        if(isset($data -> user ) 
          && !empty($data -> user) 
          && isset($data -> user -> email) 
          && isset($data -> user -> old_password) 
          && isset($data -> user -> new_password)){

          $user = $data -> user;
          $email = $user -> email;
          $old_password = $user -> old_password;
          $new_password = $user -> new_password;

          echo $action -> password_change($email, $old_password, $new_password);

        } else {

          echo $action -> invalid_param_msg();

        }
      }

  	}else{

  		
  		echo $action -> empty_param_msg();

  	}
  } 

 //If server request is the get method just to check connectivivity with local server
} else if ($_SERVER['REQUEST_METHOD'] == 'GET'){


  echo "android API";

}

