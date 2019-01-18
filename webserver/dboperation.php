<?php
require 'PHPMailer/class.phpmailer.php';//For email purposes import mail library

class dboperation{

	 private $host = 'localhost';
	 private $user = 'root';
	 private $db = 'iotlab';
	 private $pass = '';
	 private $conn;


//This function is used to connect with database Msql, 
//PDO is used to avoi SQL injections.

public function __construct() {

    //connecting to the databse using php data objects 
	$this -> conn = new PDO("mysql:host=".$this -> host.";dbname=".$this -> db, $this -> user, $this -> pass);

}


//This function is use to encrypt password using BCRYPT algorithm.
//Salt is use to add extra random string with encrypted password to avoid decrypting password.
//Salt is random 10 character string
 public function hash_user_pass($password) {

     $salt = sha1(rand());
     $salt = substr($salt, 0, 10);

//PASSWORD_DEFAULT is a default function in PHP it contains BCRYPT algo at backend PHP is using BCRYPT currently
//as it is most secure password hashing algorithm using today the term DEFAULT means that if another hashing algorithm is introduced in
//future it will automatically updated to the library ny PHP so the developer has no need to update in the code.
     $hashing = password_hash($password.$salt, PASSWORD_DEFAULT);
     $hash = array("salt" => $salt, "hashing" => $hashing);

     return $hash;

}

//This function is used when password hashing is need to do this function verify that the password which is hashed by the
//bcrypt is correct or not.
public function verify_hash_pass($password, $hash) {

    return password_verify ($password, $hash);
}

//This function is used to enter the user data into table name androidusers in database
//User will enter his name and correct email in order to verify email
//A automation generated link will be send to users email to confirm email address 
//An SQL inser query is used to enter data into table name androidusers.
//The email containing code will be send using PHP mailer library to users email
 
 public function enter_data($name,$email){


    $status=0;//status is 0 initially
    $code=md5(rand(0,1000));
 	$query = $this ->conn ->prepare('INSERT INTO androidusers SET name =:name,
    email =:email, status =:status, code=:code');
 	$query->execute(array('name' => $name, ':email' => $email, 'status'=>$status, 'code'=>$code));

        //Send verification link to registered email after clicking the link the confirmed email address after that admin sends password to 
        //that email.
        $mail = new PHPMailer;
        $mail->isSMTP();//SMTP-Simple mail transfer protocol(TCP/IP)protocol used in sending and receiving email.
        $mail->Host = 'smtp.mail.yahoo.com';
        $mail->Port = 587;//TLS port number in case of SSL 465.
        $mail->SMTPSecure = 'tls';// TLS- Transport layer security is a cryptographic protocol that provide authentication and data encryptions between the servers
        //e.g client connection to webserver
        $mail->SMTPAuth = true;
        $mail->Username = '';//Admin can replace email alotted to them
        $mail->Password = '';//Password can also be replaced according to the email
        $mail->From       = "rameelhashmi@yahoo.com";
        $mail->FromName   = "admin@smartlab-iot.com";
        $mail->AddAddress($email);
        $mail->Subject = 'SMART LAB IoT USER LOGIN - EMAIL VERIFICATION';
        
        $mail->msgHTML('<p>Dear '.$name.',<br/><br/><br/> Your verification link is: http://192.168.1.101/android/server/verify.php?name='.$name.'&email='.$email.'&code='.$code.'</p>');
        
        if (!$mail->send()) {
        $error = "Mailer Error: " . $mail->ErrorInfo;
        echo '<p id="para">'.$error.'</p>';
        }
        else {
        return true;
        }
        //Ending mail function



    if ($query) {
        
        return true;

    } else {

        return false;

    }






 }



//User will user email and password to login in the app using password provided by the administrator.
 //Email and password will be selected from users table
 //see adminpanel.php for further reference 
 //unique id is set with every users to identify the identity
 //The users credientials is fetch from the table users as this table contain users name,email and hashed password
 //The password is send by the administrator and saved into this table 
 public function login_check($email, $password) {

    $query = $this -> conn -> prepare('SELECT * FROM users WHERE email = :email');
    $query -> execute(array(':email' => $email));
    $data = $query -> fetchObject();
    $salt = $data -> salt;
    $db_user_password = $data -> user_password;

    if ($this -> verify_hash_pass($password.$salt,$db_user_password) ) {


        $user["name"] = $data -> name;
        $user["email"] = $data -> email;
        $user["unique_id"] = $data -> unique_id;
        return $user;

    } else {

        return false;
    }

 }

//This function is used to change password by the user
 //As the automatic generated password is hard to remember
 //SQL update query is used to change the password into database also
 //The passwod is then rehashed and hashed again as user change the new password
 public function password_change($email, $password){


    $hash = $this -> hash_user_pass($password);
    $user_password = $hash["hashing"];
    $salt = $hash["salt"];

    $query = $this -> conn -> prepare('UPDATE users SET user_password = :user_password, salt = :salt WHERE email = :email');
    $query -> execute(array(':email' => $email, ':user_password' => $user_password, ':salt' => $salt));

    if ($query) {
        
        return true;

    } else {

        return false;

    }

 }

//This function is used to check whether the user already registed to the table name users.
 //Users table is used by the administrator after sending password to new user to his/her email address
 //To avoid multiple entries of the same user
 //email address is use to identify users
 public function check_user_exist($email){

    $query = $this -> conn -> prepare('SELECT COUNT(*) from users WHERE email =:email');
    $query -> execute(array('email' => $email));

    if($query){

        $row_count = $query -> fetchColumn();

        if ($row_count == 0){

            return false;

        } else {

            return true;

        }
    } else {

        return false;
    }
 }
 ///This function is used to check whether the user already registed to the table name android users.
 //Android table is made for the administrator to send password after code verification
 //Admin will receive new user after email verification to send password via email
 //To avoid multiple entries of the same user
 //email address is use to identify users
 public function check_user_exist_android($email){

    $query = $this -> conn -> prepare('SELECT COUNT(*) from androidusers WHERE email =:email');
    $query -> execute(array('email' => $email));

    if($query){

        $row_count = $query -> fetchColumn();

        if ($row_count == 0){

            return false;

        } else {

            return true;

        }
    } else {

        return false;
    }
 }

}




