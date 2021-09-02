package com.themovie.db.app.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.themovie.db.app.R;
import com.themovie.db.app.view.adapter.TopStoriesAdapter;
import com.themovie.db.app.constants.Constants;
import com.themovie.db.app.databinding.ActivityMainBinding;
import com.themovie.db.app.model.StoryDTO;
import com.themovie.db.app.room.TopStoryDB;
import com.themovie.db.app.view_model.StoryViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TopStoriesAdapter.OnItemClickListener {
    private final ArrayList<StoryDTO> storyArrayList = new ArrayList<>();
    private final int totalCount = 50; // For showing top 50 stories
    private ActivityMainBinding binding;
    private StoryViewModel viewModel;
    private TopStoriesAdapter adapter;
    private String url = "";
    boolean doubleBackToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(StoryViewModel.class);

        setUpView();
        setUpAdapter();
        // git repo
        setStoryList();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpView() {
        binding.wvArticle.setWebViewClient(new MyBrowser());
        // For store web view data in cache and fetch offline
        binding.wvArticle.getSettings().setJavaScriptEnabled(true);
        binding.wvArticle.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.wvArticle.getSettings().setDomStorageEnabled(true);
        binding.wvArticle.getSettings().setAppCacheEnabled(true);

        binding.srlArticle.setOnRefreshListener(this::setArticleView);

        binding.srlTopStory.setOnRefreshListener(() -> {
            viewModel.getStories();
            setObserver();
        });
    }

    private void setStoryList() {
        storyArrayList.clear();
        storyArrayList.addAll(TopStoryDB.getInstance(this).getTopStoryList()); // fetch stories from db
        binding.srlTopStory.setRefreshing(true);
        if (storyArrayList.size() > 0) {
            setTopFiftyStories();
        } else {
            viewModel.getStories();
            setObserver();
        }
    }

    private void setUpAdapter() { // Top story adapter
        adapter = new TopStoriesAdapter(storyArrayList, this);
        binding.rcvStories.setAdapter(adapter);
    }

    @Override
    public void onStoryClick(StoryDTO storyDTO, int position) {
        storyDTO.setRead(Constants.TRUE); // maintain "read" status.
        storyArrayList.set(position, storyDTO);
        TopStoryDB.getInstance(this).update(storyDTO.getId());
        adapter.notifyItemChanged(position);
        url = storyDTO.getUrl();
        setArticleView();
    }

    private void setArticleView() {
        binding.srlArticle.setRefreshing(true);
        binding.wvArticle.loadUrl(url); // load url in web view
    }

    private void setObserver() {
        viewModel.getStoryIdList().observe(this, storyIds -> {
            if (storyIds.size() > totalCount) {
                TopStoryDB.getInstance(this).clear();
                for (int i = 0; i < totalCount; i++) { // fetch only top 50 stories from api
                    int position = i;
                    viewModel.getStoryLiveData(storyIds.get(i)).observe(this, story -> {
                        story.setRead(Constants.FALSE);
                        TopStoryDB.getInstance(this).insert(story); // insert stories in to db
                        if (position == (totalCount - 1)) {
                            storyArrayList.clear();
                            // getting sorted stories based on "score" from db
                            storyArrayList.addAll(TopStoryDB.getInstance(this).getTopStoryList());
                            setTopFiftyStories();
                        }
                    });
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setTopFiftyStories() {
        adapter.notifyDataSetChanged();
        url = storyArrayList.get(0).getUrl();
        storyArrayList.get(0).setRead(Constants.TRUE);
        storyArrayList.set(0, storyArrayList.get(0));
        adapter.notifyItemChanged(0);
        TopStoryDB.getInstance(this).update(storyArrayList.get(0).getId());
        setArticleView();
        binding.srlTopStory.setRefreshing(false);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.srlArticle.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            finishAndRemoveTask();
            finishAffinity();
            return;
        }
        doubleBackToExit = true;
        Toast.makeText(this, getString(R.string.exit_text), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExit = false, 5000);
    }
}
