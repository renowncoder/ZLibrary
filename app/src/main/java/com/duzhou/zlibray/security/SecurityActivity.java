package com.duzhou.zlibray.security;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.duzhou.zlibray.BaseActivity;
import com.duzhou.zlibray.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhou on 16-4-23.
 */
public class SecurityActivity extends BaseActivity {

    @Bind(R.id.ed_des_data)
    EditText desData;
    @Bind(R.id.tv_des_key)
    TextView desKeyTv;
    @Bind(R.id.tv_des_encode_result)
    TextView desEncodeResultTv;
    @Bind(R.id.tv_des_decode_result)
    TextView desDecodeResultTv;

    DESUtil desUtil;
    String desKey ;
    String desEncodeResult , desDecodeResult;



    @Bind(R.id.ed_aes_data)
    EditText aesData;
    @Bind(R.id.tv_aes_key)
    TextView aesKeyTv;
    @Bind(R.id.tv_aes_encode_result)
    TextView aesEncodeResultTv;
    @Bind(R.id.tv_aes_decode_result)
    TextView aesDecodeResultTv;

    AESUtil aesUtil;
    String aesKey ;
    String aesEncodeResult , aesDecodeResult;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        desUtil = new DESUtil();
        aesUtil = new AESUtil();
        initData();
    }

    private void initData() {
        desKey = aesKey = desUtil.getDEDKey();
        desKeyTv.setText(getString(R.string.security_des_key) + desKey);
        aesKeyTv.setText(getString(R.string.security_des_key) + desKey);
    }

    @OnClick(R.id.btn_des_encode)
    void btnDesEncodeClick(View view){
        String data = desData.getText().toString().trim();
        desEncodeResult = desUtil.encode(desKey,data);
        desEncodeResultTv.setText(getString(R.string.security_des_encode_result) + desEncodeResult);
    }

    @OnClick(R.id.btn_des_decode)
    void btnDesDecodeClick(View view){
        desDecodeResult = desUtil.decode(desKey,desEncodeResult);
        desDecodeResultTv.setText(getString(R.string.security_des_decode_result) + desDecodeResult);
    }


    @OnClick(R.id.btn_aes_encode)
    void btnAesEncodeClick(View view){
        String data = aesData.getText().toString().trim();
        try {
            aesEncodeResult = aesUtil.encode(aesKey,data);
            aesEncodeResultTv.setText(getString(R.string.security_aes_encode_result) + aesEncodeResult);
        }catch (Exception e){
            aesEncodeResultTv.setText(getString(R.string.security_aes_encode_result) + "error");
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_aes_decode)
    void btnAesDecodeClick(View view){
        try{
            aesDecodeResult = aesUtil.decode(aesKey,aesEncodeResult);
            aesDecodeResultTv.setText(getString(R.string.security_aes_decode_result) + aesDecodeResult);
        }catch (Exception e){
            aesDecodeResultTv.setText(getString(R.string.security_aes_decode_result) + "error");
            e.printStackTrace();
        }
    }



}
