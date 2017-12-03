package com.qf58.androidnote;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by linSir
 * date at 2017/12/2.
 * describe:
 */

public class NoteAdapter extends BaseQuickAdapter<NoteModel, BaseViewHolder> {

    //首页的适配器
    public NoteAdapter() {
        super(R.layout.item_note);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoteModel item) {

        helper.setText(R.id.title, item.getNoteName())
                .setText(R.id.time, item.getCreateTime());

    }
}
