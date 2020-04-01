package com.example.mvpapplication.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mvpapplication.R;
import com.example.mvpapplication.activity.editor.EditorActivity;
import com.example.mvpapplication.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView{

    FloatingActionButton tab;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MainPresenter presenter;
    MainAdapter mainAdapter;
    MainAdapter.ItemClickListener itemClickListener;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab = findViewById(R.id.add);
        tab.setOnClickListener(view -> {
            startActivity(new Intent(this, EditorActivity.class));
        });
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener( () -> {
            presenter.getData();
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemClickListener = (view, position) -> {
            String title = notes.get(position).getTitle();
            String note = notes.get(position).getNote();
            int color = notes.get(position).getColor();
            int id = notes.get(position).getId();
            String date = notes.get(position).getDate();
            Toast.makeText(this, title + ": " + note, Toast.LENGTH_SHORT).show();
        };
        presenter = new MainPresenter(this);
        presenter.getData();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Note> notes) {
        mainAdapter = new MainAdapter(this, notes, itemClickListener);
        mainAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mainAdapter);
        this.notes = notes;
    }

    @Override
    public void onErrorLoad(String message) {
        Log.i("foo", message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
