package com.android.visva.allinonesdksample.provider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.visva.allinonesdksample.R;
import com.visva.android.visvasdklibrary.network.HttpMethod;
import com.visva.android.visvasdklibrary.provider.IReponseListener;
import com.visva.android.visvasdklibrary.provider.VolleyProvider;

public class VolleyProviderSampleActivity extends Activity {
    private TextView mTxtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_provider_sample);
        mTxtResult = (TextView) findViewById(R.id.result);
    }

    public void onClickGetSample(View v) {
        String url = "http://httpbin.org/get";
        VolleyProvider.getInstance(this).requestDataFromServerAPI(HttpMethod.GET, url, new IReponseListener() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(response);
            }

            @Override
            public void onErrorResponse(int errorType, String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(errorType + response);
            }
        }, null);
    }

    public void onClickPostSample(View v) {
        String url = "http://httpbin.org/post";
        VolleyProvider.getInstance(this).requestDataFromServerAPI(HttpMethod.POST, url, new IReponseListener() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(response);
            }

            @Override
            public void onErrorResponse(int errorType, String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(errorType + response);
            }
        }, null);
    }

    public void onClickPutSample(View v) {

        String url = "http://httpbin.org/put";
        VolleyProvider.getInstance(this).requestDataFromServerAPI(HttpMethod.PUT, url, new IReponseListener() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(response);
            }

            @Override
            public void onErrorResponse(int errorType, String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(errorType + response);
            }
        }, null);

    }

    public void onClickDeleteSample(View v) {

        String url = "http://httpbin.org/delete";
        VolleyProvider.getInstance(this).requestDataFromServerAPI(HttpMethod.DELETE, url, new IReponseListener() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(response);
            }

            @Override
            public void onErrorResponse(int errorType, String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(errorType + response);
            }
        }, null);
    }

    public void onClickGetIPSample(View v) {
        String url = "http://httpbin.org/ip";
        VolleyProvider.getInstance(this).requestDataFromServerAPI(HttpMethod.GET, url, new IReponseListener() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(response);
            }

            @Override
            public void onErrorResponse(int errorType, String response) {
                // TODO Auto-generated method stub
                mTxtResult.setText(errorType + response);
            }
        }, null);
    }
}
