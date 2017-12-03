package com.qf58.androidnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.CacheUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 声明变量
     */
    private RecyclerView recyclerView;
    private ImageView add;
    NoteAdapter adapter = new NoteAdapter();
    private List<NoteModel> retList;
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * 绑定控件
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add = (ImageView) findViewById(R.id.add);


        /*
         * 添加点击事件
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("noteId", -1l);
                startActivity(intent);
            }
        });

        /*
         * 添加长按事件
         */
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("noteId", retList.get(position).getNoteId());
                startActivity(intent);
            }
        });

        /*
         * 添加点击事件
         */
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {
                showRemoveDialog(position);
                return false;
            }
        });
    }


    /**
     * 弹出删除对话框
     */
    private void showRemoveDialog(final int position) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("确认删除吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONArray array = CacheUtils.getInstance().getJSONArray("note");
                        List<NoteModel> retList = gson.fromJson(array.toString(),
                                new TypeToken<List<NoteModel>>() {
                                }.getType());

                        for (int i = 0; i < retList.size(); i++) {
                            if (retList.get(i).getNoteId() == retList.get(position).getNoteId()) {

                                retList.remove(position);
                                adapter.setNewData(retList);
                            }

                        }
                        CacheUtils.getInstance().put("note", new Gson().toJson(retList));

                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     * 当activity刷新时调用
     */
    @Override
    protected void onResume() {
        super.onResume();
        JSONArray array = CacheUtils.getInstance().getJSONArray("note");
        if (array == null) {
            return;
        }

        retList = gson.fromJson(array.toString(),
                new TypeToken<List<NoteModel>>() {
                }.getType());

        adapter.setNewData(retList);
    }
}
