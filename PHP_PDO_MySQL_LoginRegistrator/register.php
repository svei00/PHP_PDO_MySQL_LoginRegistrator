<?php
include 'config2.php';

// Verificamos si los datos en POST no estan vacíos
if (!empty($_POST)) {
    // Sino esta Vacio incertamos el registro
    // Definimos las Variables
    $user = isset($_POST['user']) ? $_POST['user']: '';
    $email = isset($_POST['email']) ? $_POST['email']: '';
    $pwd = isset($_POST['pwd']) ? $_POST['pwd']: '';

    // Encriptamos el Password antes de enviarlo
    $pwd = md5($pwd);
    
    // Insertamos el Registro en la Base de datos
    $sql = "INSERT INTO users (user, email, pwd) VALUES (?, ?, ?)";

    // Preparamos la consulta
    $query = $conn -> prepare ($sql);

    // Vinculamos los Parametros
    $query -> bindParam(1, $user, PDO::PARAM_STR);
    $query -> bindParam(2, $email, PDO::PARAM_STR);
    $query -> bindParam(3, $pwd, PDO::PARAM_STR);

    // Se ejecuta la acción
    $query -> execute ();

    // Verificamos que haya funcionado la inserción
    $lastInserted = $conn -> $lastInserted();

    if ($lastInserted) {
        // Mensaje de Salida Exitoso
        echo "Record Inserted Successfully";
    
    } else {
        echo "Something went Wrong!";
    }

}
?>