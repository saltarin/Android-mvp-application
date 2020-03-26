package com.example.mvpapplication.activity.editor;

public interface EditorView {
    void showProgress();
    void hideProgress();
    void onAddSucess(String message);
    void onAddError(String message);
}
