<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include('dbcon.php');

$userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
$userPassword = isset($_POST["userPassword"]) ? $_POST["userPassword"] : "";

$response = array();
$response["success"] = false;

if (!empty($userID) && !empty($userPassword)) {
    // SQL 쿼리
    $query = "SELECT * FROM students WHERE userID = ?";
    
    $stmt = mysqli_prepare($con, $query);
    mysqli_stmt_bind_param($stmt, "s", $userID);
    
    if (mysqli_stmt_execute($stmt)) {
        mysqli_stmt_store_result($stmt);

        if (mysqli_stmt_num_rows($stmt) > 0) {
            mysqli_stmt_bind_result($stmt, $userID, $userPassword, $userName, $userNickname);

            while (mysqli_stmt_fetch($stmt)) {
                // 비밀번호 확인 및 기타 작업 수행
                if (password_verify($userPassword, $userPassword)) {
                    $response["success"] = true;
                    $response["userID"] = $userID;
                    $response["userPassword"] = $userPassword;
                    $response["userName"] = $userName;
                    $response["userNickname"] = $userNickname;
                } else {
                    $response["error"] = "비밀번호가 일치하지 않습니다.";
                }
            }
        } else {
            $response["error"] = "사용자를 찾을 수 없습니다.";
        }
    } else {
        $response["error"] = "SQL 쿼리 실행 오류: " . mysqli_error($con);
    }

    // MySQL 연결 닫기
    mysqli_stmt_close($stmt);
    mysqli_close($con);
} else {
    $response["error"] = "유효하지 않은 입력입니다.";
}

echo json_encode($response);
?>