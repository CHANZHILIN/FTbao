package com.example.jinbiao.ftbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;
import com.nostra13.universalimageloader.core.ImageLoader;


public class PublishActivity extends Activity {

    private static final String TAG = "PublishActivity";

    private FrameLayout frameLayout;
    private ImageView imageView;
    private ImageView delete;
    private Button publish;
    private ImageView addImage;
    private String imagePath;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        setTitle("发表");
        initView();
        initEvent();
    }


    private void initEvent() {


        addImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);

            }
        });

        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.GONE);
                addImage.setVisibility(View.VISIBLE);
                publish.setClickable(false);
            }
        });



    }


    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        imageView = (ImageView) findViewById(R.id.imageview);
        delete = (ImageView) findViewById(R.id.delete);
        addImage = (ImageView) findViewById(R.id.addImage);
        publish = (Button) findViewById(R.id.publish);
    }

    // 回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imagePath = picturePath;
            Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            frameLayout.setVisibility(View.VISIBLE);
            addImage.setVisibility(View.INVISIBLE);
        }
    }


}
