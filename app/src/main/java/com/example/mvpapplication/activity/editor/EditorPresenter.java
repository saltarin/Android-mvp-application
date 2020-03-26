package com.example.mvpapplication.activity.editor;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mvpapplication.api.ApiClient;
import com.example.mvpapplication.api.ApiInterface;
import com.example.mvpapplication.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {
    private EditorView view;
    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    public void saveNote(final String title, final String note, final int color) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title, note, color);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NonNull Call<Note> call, @NonNull Response<Note> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().isSuccess();
                    if(success) {
                        view.onAddSucess(response.body().getMessage());
//                        Toast.makeText(EditorActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        finish();
                    } else {
                        //Toast.makeText(EditorActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        view.onAddError(response.body().getMessage());

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Note> call, @NonNull Throwable t) {
                view.hideProgress();
//                Toast.makeText(EditorActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                finish();
                view.onAddError(t.getLocalizedMessage());
            }
        });
    }
}
