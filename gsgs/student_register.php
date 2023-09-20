<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'],"Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android)
    {
        $userID=$_POST['userID'];
        $userPassword=$_POST['userPassword'];
        $userNickname = $_POST['userNickname'];

        if(empty($userID)){
            $errMSG = "아이디를 입력하세요.";
        }
        else if(empty($userPassword)){
            $errMSG = "비밀번호를 입력하세요.";
        }

        else if(empty($userNickname)){
            $errMSG = "이름을 입력하세요.";
        }


        if(!isset($errMSG)) //$errMSG가 존재하지 않았을 경우
        {
            try{ //try , catch
                $stmt = $con->prepare('INSERT INTO students(userID, userPassword, userNickname)
                 VALUES(:userID, :userPassword, :userNickname )');
                $stmt->bindParam(':userID', $userID);
                $stmt->bindParam(':userPassword', $userPassword);
                $stmt->bindParam(':userNickname', $userNickname);

                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가했습니다.";
                }
                else
                {
                    $errMSG = "사용자 추가 에러";
                }
            } catch(PDOException $e) { //PHP와 MySQL 연동은 성공하였으나 DB 오류가 났을때
                die("Database error: " . $e->getMessage()); 
            }
        }

    }
?>
<html>
<body>


<?php 
        if (isset($errMSG)) { //만약 $errMSG의 변수의 값이 존재할 경우 
            // 자바 스크립트의 alert를 이용하여 팝업창에 $errMSG를 띄운다.
         echo   "<script>      
         alert('{$errMSG}');
         </script>";         
        }
        
        if (isset($successMSG)){ //만약 $$successMSG 변수의 값이 존재할 경우 
       // 자바 스크립트의 alert를 이용하여 팝업창에 $successMSG 띄운다.
        echo   "<script>      
        alert('{$successMSG}');
        </script>";
        }

        $android = strpos($_SERVER['HTTP_USER_AGENT'],"Android");
        if(!$android)
        {
        ?>
<form action="<?php $_PHP_SELF ?>" method="POST">
                <h1>회원가입 창</h1>
                  <!-- 일반적인 입력 형태 -->
                <p><input type="text" name="userID" id="userID" placeholder="ID"></p>
                <p><input type="password" name="userPassword" id="userPassword" placeholder="Password"></p>
                <p><input type="text" name="userNickname" id="userNickname" placeholder="Name"></p>


            <input type = "submit" name = "submit" />
        </form>
</body>
</html>

<?php
}
?>