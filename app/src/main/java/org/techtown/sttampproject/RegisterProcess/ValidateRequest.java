package org.techtown.sttampproject.RegisterProcess;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest { // 아이디 중복확인을 위한 클래스이다.
//StringRequest란 Volly라이브러리가 지원하는 common request의 한 종류이다.
//URL을 지정해주고 Raw String으로 된 응답을 받는다.
//Common request의 종류 : (StringRequest, ImageRequest, JsonObjectRequest, JsonArrayRequest)


    final static private String URL = "http://13.209.15.23/UserValidate.php"; //서버컴퓨터의 아이디중복확인용 php 파일 url을 선언한다.

    private Map<String, String> parameters;


    // HashMap 의 Hashing 검색방법을 이용하여 데이터베이스에 저장된 아이디 컬렉션에 접근할 것이다?
    // key 값은 중복이 되지 않고 value 값은 중복이 허용되는데..
    //
    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);//위에서 선언한 URL에 POST방식으로 파마미터들을 전송함
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}