

<!DOCTYPE html>
<html>
<head>
  <td>EMAIL VERIFICATION</td>
</head>
<body>

</body>
</html>

<?php

$host='localhost';
$user='root';
$db='iotlab';
$pass='';

$con=mysqli_connect($host,$user,$pass,$db);
if (!$con) {
  echo "Failed to connect database";

}
            
if(isset($_GET['name']) && !empty($_GET['name']) AND isset($_GET['email']) && !empty($_GET['email']) AND isset($_GET['code']) && !empty($_GET['code'])){
    // Verify data
    $email = $_GET['email']; // Set email variable
    $code = $_GET['code']; // Set code for verify the email variable
    $name=$_GET['name']; //Set name of the user 

        $created= date("Y-m-d H:i:s");//updating time 


    $search = mysqli_query($con,"SELECT name, email, code, status FROM androidusers WHERE name='".$name."' AND email='".$email."' AND code='".$code."' AND status='0'");
    $match  = mysqli_num_rows($search);
                 
    //If both code matched then upate the table android users and set the status 1 which means that the user verified the email account 

    if($match > 0){
        mysqli_query($con,"UPDATE androidusers SET status='1' WHERE name='".$name."' AND email='".$email."' AND code='".$code."' AND status='0'");
        echo '<div class="statusmsg">Your account has been activated, You will receive password from admin within 48 hours.</div>';

        $sql="insert into activated (name, email, created) values('$name','$email','$created')";

        (mysqli_query($con, $sql));

    }else{
        // If the user again click the link or the link is broken
        echo '<div class="statusmsg">The url is either invalid or you already have activated your account.</div>';
    }
                 
}else{
    //If the link is broken or the user try to edit the link then this message appears
    echo '<div class="statusmsg">Invalid approach, please use the link that has been send to your email.</div>';
}
 