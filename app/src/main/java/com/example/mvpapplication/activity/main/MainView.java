package com.example.mvpapplication.activity.main;

import com.example.mvpapplication.model.Note;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Note> notes);
    void onErrorLoad(String message);
}
