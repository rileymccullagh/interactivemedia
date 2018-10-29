<?php
$test = false;

$request = explode('/', trim($_SERVER['PATH_INFO'],'/'));
 
if($test){
    $link = mysqli_connect('localhost', 'root', 'root', 'custom');
} else {
    $link = mysqli_connect('custom-axsn.mysql.us1.frbit.com', 'custom-axsn', 'R3nRlTLvyepxIGekt2LaWds1', 'custom-axsn');
}

mysqli_set_charset($link,'utf8');
$key = preg_replace('/[^a-z0-9_]+/i','',array_shift($request));

if($key) {
    $sql = "select * from `cameras`".($key?" WHERE id=$key":'');
} else {
    $sql = "select * from `cameras`";
}
 
$result = mysqli_query($link,$sql);
 
if (!$result) {
  http_response_code(404);
  die(mysqli_error());
}

$array = mysqli_fetch_all($result);

foreach($array as $camera) {
    print_r($camera);
    $image = file_get_contents($camera[1]);
    if($image) {
        file_put_contents('images/'.$camera[0].'.jpg', $image);
    }
}

mysqli_close($link);
?>