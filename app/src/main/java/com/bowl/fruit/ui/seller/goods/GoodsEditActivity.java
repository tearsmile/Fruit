package com.bowl.fruit.ui.seller.goods;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.ui.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by CJ on 2018/2/14.
 */

public class GoodsEditActivity extends BaseActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_CAMERA_IMAGE = 2;

    private RecyclerView mPicList;
    private String mCurrentPhotoPath;
    private GoodsPictureAdapter mAdapter;

    private RelativeLayout mSelect;
    private TextView mCancel, mCamera, mAlbum;
    private EditText mName, mDesc, mStock, mPrice, mDiscount;
    private EditText mStandard, mWeight, mLife, mStore;
    private EditText mDetail;
    private Button mSave;

    private Fruit fruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_edit);
        initIntent();
        initViews();
    }

    private void initIntent(){
        fruit = (Fruit) getIntent().getSerializableExtra("fruit");
        if(fruit == null){
            fruit = new Fruit();
        }
    }

    private void initViews(){
        mName = findViewById(R.id.et_name);
        mDesc = findViewById(R.id.et_desc);
        mStock = findViewById(R.id.et_stock);
        mPrice = findViewById(R.id.et_price);
        mDiscount = findViewById(R.id.et_discount);
        mStandard = findViewById(R.id.et_standard);
        mWeight = findViewById(R.id.et_weight);
        mLife = findViewById(R.id.et_life);
        mStore = findViewById(R.id.et_store);
        mDetail = findViewById(R.id.et_detail_desc);

        if(!fruit.getName().equals("")) {
            mName.setText(fruit.getName());
            mDesc.setText(fruit.getDesc());
            mStock.setText(fruit.getStock() + "");
            mPrice.setText("" + fruit.getPrice());
            mDiscount.setText(fruit.getDiscount() + "");
            mStandard.setText(fruit.getStandard());
            mWeight.setText(fruit.getWeight() + "");
            mLife.setText(fruit.getLife());
            mStore.setText(fruit.getStore());
            mDetail.setText(fruit.getDetailDesc());
        }


        mPicList = findViewById(R.id.rv_pic);
        mSelect = findViewById(R.id.rl_select);
        mCancel = findViewById(R.id.tv_cancel);
        mCamera = findViewById(R.id.tv_camera);
        mAlbum = findViewById(R.id.tv_album);
        
        mSave = findViewById(R.id.btn_save);

        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });
        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
                pickPhoto();
            }
        });
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
                takeCamera();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mPicList.setLayoutManager(mLayoutManager);
        mAdapter = new GoodsPictureAdapter(this);
        mPicList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GoodsPictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
                if(index == mAdapter.getItemCount() - 1){
                    mSelect.setVisibility(View.VISIBLE);
                }
            }
        });
        mAdapter.update(new LinkedList<String>());
        
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/2/21 上传图片及水果信息

            }
        });

    }

    private void takeCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
            }
        }

        startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);//跳转界面传回拍照所得数据
    }
    private File createImageFile() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = null;
        try {
            image = File.createTempFile(
                    System.currentTimeMillis() + "",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ) {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImage = generateUri(data.getData(),data.getType());
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
                final String picturePath = cursor.getString(columnIndex);
                mAdapter.add(picturePath);
                cursor.close();
            }else if (requestCode == RESULT_CAMERA_IMAGE){
                mAdapter.add(mCurrentPhotoPath);
            }
        }
    }

    private Uri generateUri(Uri uri, String type){
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
}
