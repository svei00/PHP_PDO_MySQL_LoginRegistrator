<?php
include 'config3.php';

if(!empty($_POST)){
    // Si no esta vacío:
    // Creamos las Variables
    $user = isset($_POST['user']) ? $_POST['user']: "";
    $email = isset($_POST['email']) ? $_POST['email']: "";
    $pwd = isset($_POST['pwd']) ? $_POST['pwd']: "";

    // Validamos que el Usuario no este registrado
    // Usuario y Email deben de ser únicos
    $doubleRegister = "SELECT * FROM users WHERE user = '$user' OR email = '$email' LIMIT 1";

    // Guardamos el Resultado en una variable
    $result = mysqli_query($conn, $doubleRegister);
    $user = mysqli_fetch_assoc($result);

    // Si el usuario ya esta registrado
    if ($user){
        if ($user['user'] === $user){
            array_push($errors, "Username its already taken" );
        }
        if ($user['email'] === $email) {
            array_push($errors, "Email has been registered already");
        }
    }

    // Sino existen errores registramos al usuario y cerramos la conexción
    if (count($errors) == 0) {
        // Encriptamos el Password antes de enviarlo
        $pwd = md5($pwd);

        // Ejecutamos la Instrucción SQL
        $query = "INSERT INTO users (user, email, pwd) VALUES ('$user', '$email', '$pwd')";
        mysqli_query($conn, $query);

        // Cerramos la sesión
        exit(0);

        /*
        También puede ser como:
        $conn = null;

        */

    }
    
}

?>