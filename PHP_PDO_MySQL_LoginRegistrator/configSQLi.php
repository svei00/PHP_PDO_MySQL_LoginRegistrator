<?php
    // Conexión a la Base de Datos sqli
    $servername = "localhost"; // Estas lineas son presindibles podemos usar la comentada y funcionaria igual
    $username = "u773249055_webUser";
    $password = "webServer2021";
    $db = "u773249055_appDB_WebServ";
    
    $conn = mysqli_connect($servername, $username , $password, $db); // Lo Puedes usar directamente sin nombrar variables
    // $conn = mysqli_connect("localhost", "root", "", "ExcelSolutionsv"); // como esta, pero para mejores practicas se recomienda como arriba 
    
    if (!$conn) {
		die("Error al Conectar con la Base de Datos: " . mysqli_connect_error());
	}

?>