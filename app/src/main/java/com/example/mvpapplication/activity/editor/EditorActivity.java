package com.example.mvpapplication.activity.editor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvpapplication.R;
import com.thebluealliance.spectrum.SpectrumPalette;

public class EditorActivity extends AppCompatActivity implements EditorView{

    EditText edTitle;
    EditText edNote;
    ProgressDialog progressDialog;
    SpectrumPalette palette;
    int color;

    EditorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        edTitle = findViewById(R.id.title);
        edNote = findViewById(R.id.note);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        palette = findViewById(R.id.palette);
        int defaultColor = getResources().getColor(R.color.white );
        palette.setSelectedColor(defaultColor);
        color = defaultColor;
        palette.setOnColorSelectedListener( color -> this.color = color);
        presenter = new EditorPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                String title = edTitle.getText().toString().trim();
                String note = edNote.getText().toString().trim();
                int color = this.color;
                if(title.isEmpty()) {
                    edTitle.setError("Required!");
                } else if(note.isEmpty()) {
                    edNote.setError("Required!");
                } else {
                    presenter.saveNote(title, note, color);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onAddSucess(String message) {
        Toast.makeText(EditorActivity.this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(EditorActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
