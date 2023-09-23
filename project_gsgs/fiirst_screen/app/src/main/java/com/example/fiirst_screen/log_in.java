/**
 * 이 페이지는 로그인을 진행하는 페이지 입니다.
 */

package com.example.fiirst_screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class log_in extends AppCompatActivity {
    char result2;
    private static String IP_ADDRESS = "192.168.0.2";
    private static String TAG = "phptest";
    private Button btn_login;
    private EditText et_email, et_pw;
    private TextView tv_signup,tv_femail,tv_fpassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        btn_login = findViewById(R.id.btn_login);
        tv_signup = findViewById(R.id.tv_signup);
        tv_femail = findViewById(R.id.tv_femail);
        tv_fpassword = findViewById(R.id.tv_fpassword);
        et_email = findViewById(R.id.edit_email);
        et_pw = findViewById(R.id.edit_pw);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = et_email.getText().toString();
                String Password = et_pw.getText().toString();

                //회원가입을 할 때 예외 처리를 해준 것이다.
                if (ID.equals("") || Password.equals("")) {
                    Toast.makeText(getApplicationContext(), "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    InsertData task = new InsertData();
                    task.execute("http://" + IP_ADDRESS + "/gsgs/student_login.php", ID, Password);

                }

            }
        });
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(log_in.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.d("qq","\n\n"+result);
            progressDialog.dismiss();
            result2 = result.toString().charAt(0);
            //Log.d("qq","\n\n"+result2);
            if(result2=='1')
            {
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(log_in.this, user_home.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 다릅니다. ", Toast.LENGTH_LONG).show();
            }
            //Log.d(TAG, "POST response  - " + result);
        }
        @Override
        protected String doInBackground(String... params) {
            String serverURL = (String)params[0];

            String userID = (String)params[1];
            String userPassword = (String)params[2];

            String postParameters ="userID="+userID+"&userPassword="+ userPassword;

            try{ // HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송한다.
                URL url = new URL(serverURL); //주소가 저장된 변수를 이곳에 입력한다.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생한다.

                httpURLConnection.setConnectTimeout(5000); //5초안에 연결이 안되면 예외가 발생한다.

                httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST로 한다.

                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();

                //전송할 데이터가 저장된 변수를 이곳에 입력한다. 인코딩을 고려해줘야 하기 때문에 UTF-8 형식으로 넣어준다.
                outputStream.write(postParameters.getBytes("UTF-8"));

                outputStream.flush();//현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비운다.
                outputStream.close(); //객체를 닫음으로써 자원을 반납한다.

                int responseStatusCode = httpURLConnection.getResponseCode(); //응답을 읽는다.

                InputStream inputStream;

                if(responseStatusCode == httpURLConnection.HTTP_OK){ //만약 정상적인 응답 데이터 라면
                    inputStream=httpURLConnection.getInputStream();
                }
                else {
                    inputStream = httpURLConnection.getErrorStream(); //만약 에러가 발생한다면
                }
                // StringBuilder를 사용하여 수신되는 데이터를 저장한다.
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) !=null ) {
                    sb.append(line);
                }
                bufferedReader.close();
                //저장된 데이터를 스트링으로 변환하여 리턴값으로 받는다.
                return  sb.toString();
            }
            catch (Exception e) {
                return  new String("Error " + e.getMessage());
            }
        }
    }
}
