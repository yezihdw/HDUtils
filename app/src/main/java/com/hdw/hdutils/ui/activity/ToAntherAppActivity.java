package com.hdw.hdutils.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hdw.hdutils.R;

/**
 * Created by hdw on 2017/3/3.
 */

public class ToAntherAppActivity extends AppCompatActivity{

    private String userName;
    private String passWord;
    private String idno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_to_anther_app);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.elinkdeyuan.children_hangtianhy");
//                Intent intent = new Intent();
//                ComponentName cn = new ComponentName("com.elink.housekeeper","com.elink.housekeeper.ui.LoginActivity");
//                intent.setComponent(cn);
//                Uri uri=Uri.parse("housekeeper://housekeeper");//此处应与B程序中Data中标签一致
//                intent.setData(uri);
                startActivity(intent);

//                Intent intent = getPackageManager().getLaunchIntentForPackage("com.elink.housekeeper");
//                Intent intent = new Intent();
//                ComponentName componentName = new ComponentName("com.elink.housekeeper","housekeeper");
//                Uri uri = Uri.parse("housekeeper");
//                intent.setData(uri);
//                intent.setComponent(componentName);
//                startActivity(intent);
//                // 这里如果intent为空，就说名没有安装要跳转的应用嘛
//                if (intent != null) {
//                    // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
//                    if (!"".equals(userName) && userName !=null) {
//                        if (!"".equals(passWord) && passWord !=null) {
////							intent.putExtra("userid", userName);
////						    intent.putExtra("password", passWord);
//
//                            intent.putExtra("idno", userName);
//                            intent.putExtra("username", passWord);
//                            Toast.makeText(getApplicationContext(), "toAntherAPP userid: "+userName+"password: "+passWord , Toast.LENGTH_LONG).show();
//
//                        }
//                    }
////                    startActivity(intent);
////                    intent.putExtra("name", "Liu xiang");
////                    intent.putExtra("birthday", "1983-7-13");
//
//                } else {
//                    // 没有安装要跳转的app应用，提醒一下
//                    Toast.makeText(getApplicationContext(), "哟，赶紧下载安装这个APP吧", Toast.LENGTH_LONG).show();
//                }
            }
        });

    }
}
