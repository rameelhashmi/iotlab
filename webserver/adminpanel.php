<!-- Below html code is use to show registerd user to admin to send password to new users on their registered email-->



<table style="width: 500px;" border="5" >
	<tr><th colspan="4"><h1>SMART LAB USERS</h1></th></tr>

<tr>
	<td>
		Serial No
	</td>
	<td>
		Name
	</td>
<td>
	Email
</td>

<td>
	Date
</td>
<td>
	Sent
</td>

</tr>
<?php

//PHPMailer library is used to send password to registered user email
require 'PHPMailer/class.phpmailer.php';

$host='localhost';
$user='root';
$db='iotlab';
$pass='';

$con=mysqli_connect($host,$user,$pass,$db);
if (!$con) {
	echo "Failed to connect database";

}

	//To show the table activated to the panel containing user credientials and for the admin to send password to these users

	$query= "SELECT * FROM activated";
	$result=mysqli_query($con,$query);
	while ($row=mysqli_fetch_array($result)) {

		
echo "<tr>";

		echo"<td>".$row['sno']."</td>";
		echo"<td>".$row['name']."</td>";
		echo"<td>".$row['email']."</td>";
		echo"<td>".$row['created']."</td>";
		echo"<td>".$row['pass_sent']."</td>";




		echo "</tr>";
	}


	//If the admin press send button the first sql query will run which first check th database if the password is already send to this user or not 
	//if yes the password will not be send again and the message will appear password already send to this email. 

	if(isset($_POST['send']))
{
	$name=$_POST['name'];
 	$email=$_POST['email'];
  	
  	$query_e= "SELECT * FROM users WHERE email='$email'";
  	$results = mysqli_query($con, $query_e);
  	if (mysqli_num_rows($results) > 0) {
  	  echo "Password already Sent to this email- ".$email."<br>";	
  	}
  	else
  	{

  	 //Password is then hashed and stored in table users
  	//Salt is used to add extra string into hashed password to avoid decryptions
  	//Salt is random generated string
  	 $salt = sha1(rand());
     $salt = substr($salt, 0, 10);
     $chars = "abecdefghijklmnoprstuvwxyz1234567890!@#$%^&*()";
 	 $password = substr( str_shuffle( $chars ), 0, 12 );
 	 $nonhashed=$password;
     $hashing = password_hash($password.$salt, PASSWORD_DEFAULT);//PASSWORD_DEFAULT used bcrypt algo
     $hash = array("salt" => $salt, "hashing" => $hashing);
     $password = $hash["hashing"];
	 $salt = $hash["salt"];
 
 	

 	$unique_id = uniqid('',true);
 
 	$created_at= date("Y-m-d H:i:s");

  	$sql="insert into users (unique_id, name, email, user_password, salt, created_at) values('$unique_id','$name','$email','$password', '$salt', '$created_at')";


  	// this function is use to send hashed password to registered user email address
  	//PHPmailer library is use send password using email
  	//The admin email and password is defined in variable username and password
  	//$mail is the object variable use to access the functions of PHPMailer library


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
$mail->Subject = 'SMART LAB IoT USER LOGIN - PASSWORD';
$mail->msgHTML('<p>Dear '.$name.',<br/><br/><br/> Your password is: '.$nonhashed.'</p>');


if (!$mail->send()) {
$error = "Mailer Error: " . $mail->ErrorInfo;
echo '<p id="para">'.$error.'</p>';
}
else {
  	echo " Password has been sent to- ".$email."<br>" ;
  	mysqli_query($con,"UPDATE activated SET pass_sent='Yes' WHERE name='".$name."' AND email='".$email."'");

}

//Ending mail function


	if (mysqli_query($con, $sql)) {
  	echo " User added to database" ;
	} 
		else 
	{
    echo "Error: " . $sql . "<br>" . mysqli_error($con);
	}

  	}

	mysqli_close($con);

}


?>

</table>


<!--Admin panel interface to generate password and to to user email
Password generator interface is defined in style.css
style.css is then called in HTML to show on local server-->
<html>
<head>
<link type="text/css" rel="stylesheet" href="style.css"/>
</head>
<body>
<div id="wrapper">

<div id="signup_form">
<p id="signup_label">Random Password Generator</p>
<form method="post"action="adminpanel.php">
 <input type="text" name="name" id="name" placeholder="Enter Name" required>
 <br>
 <input type="text" name="email" placeholder="Enter Email" required>
 <br>
 <input type="submit" name="send" value="Send Password">
</form>
</div>
