<?php

$conexion = mysqli_connect('localhost','root','','proyecto');

if(!$conexion){
    echo "error en conexion";
}

$t_usuario = $_POST['usuario'];
$t_password = $_POST['password'];
$t_email =$_POST['email'];

$query = "INSERT INTO usuarios (usuario,password,email) values ('$t_usuario','$t_password','$t_email')";
$resultado = mysqli_query($conexion, $query);

if($resultado){
    echo "registrado correctamente";
}else{
    echo "error";
}



?>