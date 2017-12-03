package com.qf58.androidnote;

import java.util.List;

/**
 * Created by linSir
 * date at 2017/12/2.
 * describe:
 */

public class NoteModel {

    private long noteId;
    private String noteName;
    private List<String> content;
    private String createTime;

    public NoteModel(long noteId, String noteName, List<String> content, String createTime) {
        this.noteId = noteId;
        this.noteName = noteName;
        this.content = content;
        this.createTime = createTime;
    }


    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
