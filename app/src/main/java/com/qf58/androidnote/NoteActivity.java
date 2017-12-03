package com.qf58.androidnote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linSir
 * date at 2017/12/2.
 * describe:
 */

public class NoteActivity extends AppCompatActivity {

    private EditText noteName;
    private PictureAndTextEditorView noteContent;
    private ImageView addImage;
    private ImageView save;
    private long mNoteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        noteName = (EditText) findViewById(R.id.note_name);
        noteContent = (PictureAndTextEditorView) findViewById(R.id.note_content);
        addImage = (ImageView) findViewById(R.id.add_img);
        save = (ImageView) findViewById(R.id.save);
        mNoteId = getIntent().getExtras().getLong("noteId");

        if (mNoteId != -1) {//如果不是-1，那就代表已经存在，存在就去初始化
            Gson gson = new Gson();
            JSONArray array = CacheUtils.getInstance().getJSONArray("note");
            List<NoteModel> retList = gson.fromJson(array.toString(),
                    new TypeToken<List<NoteModel>>() {
                    }.getType());
            for (int i = 0; i < retList.size(); i++) {
                if (retList.get(i).getNoteId() == mNoteId) {
                    noteContent.setmContentList(retList.get(i).getContent());
                    noteName.setText(retList.get(i).getNoteName());
                }
            }

        }
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.image(NoteActivity.this) // 选择图片。
                        .multipleChoice()
                        .requestCode(200)
                        .camera(true)
                        .columnCount(3)
                        .selectCount(1)
                        .onResult(new Action<ArrayList<AlbumFile>>() {
                            @Override
                            public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                                noteContent.insertBitmap(result.get(0).getPath());
                            }
                        })
                        .onCancel(new Action<String>() {
                            @Override
                            public void onAction(int requestCode, @NonNull String result) {

                            }
                        })
                        .start();

            }
        });


        /**
         * 保存按钮的点击事件，noteId为-1 代表不存在的订单，否则是已存在订单
         * 不存在的话，就去新建缓存，然后写入
         * 如在的话，就找到指定的条目，然后修改
         */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNoteId == -1) {
                    JSONArray array = CacheUtils.getInstance().getJSONArray("note");
                    if (array == null) {
                        List<NoteModel> retList = new ArrayList<NoteModel>();
                        retList.add(new NoteModel(TimeUtils.getNowMills(), noteName.getText().toString(), noteContent.getmContentList(), TimeUtils.getNowString()));
                        CacheUtils.getInstance().put("note", new Gson().toJson(retList));
                    } else {
                        Gson gson = new Gson();
                        List<NoteModel> retList = gson.fromJson(array.toString(),
                                new TypeToken<List<NoteModel>>() {
                                }.getType());
                        retList.add(new NoteModel(TimeUtils.getNowMills(), noteName.getText().toString(), noteContent.getmContentList(), TimeUtils.getNowString()));
                        CacheUtils.getInstance().put("note", new Gson().toJson(retList));


                    }


                } else {
                    JSONArray array = CacheUtils.getInstance().getJSONArray("note");
                    Gson gson = new Gson();
                    List<NoteModel> retList = gson.fromJson(array.toString(),
                            new TypeToken<List<NoteModel>>() {
                            }.getType());

                    for (int i = 0; i < retList.size(); i++) {
                        if (retList.get(i).getNoteId() == mNoteId) {
                            retList.get(i).setContent(noteContent.getmContentList());
                            retList.get(i).setNoteName(noteName.getText().toString());
                        }

                    }
                    CacheUtils.getInstance().put("note", new Gson().toJson(retList));
                }


                finish();
            }
        });

    }
}
