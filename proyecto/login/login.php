<?php

$conexion = mysqli_connect('localhost','root','','proyecto');

if(!$conexion){
    echo "error en conexion";
}

$t_usuario = $_POST['usuario'];
$t_password = $_POST['password'];

$query = "SELECT * FROM usuarios WHERE email= '$t_usuario' AND password ='$t_password'";
$resultado = mysqli_query($conexion,$query);

if($resultado->num_rows >0){
    echo "ingreso correctamente";
}else{
    echo"no pudo ingresar";
}

?>