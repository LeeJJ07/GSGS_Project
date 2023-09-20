/**
 * 회원가입에서 학생 토글을 눌렸을 때 수행되는 화면
 */

package com.example.fiirst_screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class fragment_student extends Fragment implements AdapterView.OnItemSelectedListener{
    private EditText et_id, et_pw, et_nickname;
    private Button btn_register;

    private static String IP_ADDRESS = "192.168.193.48";
    //192.168.0.16
    //192.168.0.2
    private static String TAG = "phptest"; //phptest log 찍으려는 용도
    private View view;
    String myJSON;
    public fragment_student(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student,container,false);

        et_id = view.findViewById(R.id.edit_email);
        et_pw = view.findViewById(R.id.edit_pw);
        et_nickname = view.findViewById(R.id.edit_nickname);
        btn_register = view.findViewById(R.id.btn_st_next);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_id.getText().toString();
                String userPassword = et_pw.getText().toString();
                String userNickname = et_nickname.getText().toString();


                //회원가입을 할 때 예외 처리를 해준 것이다.
                if (userID.equals("")  || userPassword.equals("") ||  userNickname.equals("") )
                {
                    Toast.makeText(getActivity(), "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    InsertData task = new InsertData(); //PHP 통신을 위한 InsertData 클래스의 task 객체 생성
                    //본인이 접속할 PHP 주소와 보낼 데이터를 입력해준다.
                    task.execute("http://"+IP_ADDRESS+"/gsgs/student_register.php",userID,userPassword, userNickname);
                    Toast.makeText(getActivity().getApplicationContext(), "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });



        return view;
    }

    class InsertData extends AsyncTask<String,Void,String> { // 통신을 위한 InsertData 생성
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //진행 다이얼로그 생성
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss(); //onPostExcute 에 오게되면 진행 다이얼로그 취소
            myJSON = result;
        }


        @Override
        protected String doInBackground(String... params) {
            /*
            PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터를 준비한다.
            POST 방식으로 데이터 전달시에는 데이터가 주소에 직접 입력되지 않는다.
            이 값들은 InsertData 객체.excute 에서 매개변수로 준 값들이 배열 개념으로 차례대로 들어가
            값을 받아오는 개념이다.
             */
            String serverURL = (String) params[0];

            String userID = (String)params[1];
            String userPassword = (String)params[2];
            String userNickname = (String)params[3];
            /*
            HTTP 메세지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야한다.
            전송할 데이터는 "이름=값" 형식이며 여러 개를 보내야 할 경우에 항목 사이에 &를 추가해준다.
            여기에 적어준 이름들은 나중에 PHP에서 사용하여 값을 얻게 된다.
             */
            String postParameters ="userID="+userID+"&userPassword="+ userPassword
                    +"&userNickname="+userNickname;

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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
