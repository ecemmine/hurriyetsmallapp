package tr.com.hurriyet.sdk.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import tr.com.hurriyet.opensourcesdk.exceptions.UninitializedSdkException;
import tr.com.hurriyet.opensourcesdk.model.ErrorModel;
import tr.com.hurriyet.opensourcesdk.model.response.Article;
import tr.com.hurriyet.opensourcesdk.services.RequestGenerator;
import tr.com.hurriyet.sdk.ArticleDetailActivity;
import tr.com.hurriyet.sdk.R;
import tr.com.hurriyet.sdk.adapters.ArticlesRecyclerViewAdapter;
import tr.com.hurriyet.sdk.presenters.ArticlePresenterImpl;
import tr.com.hurriyet.sdk.presenters.Presenter;
import tr.com.hurriyet.sdk.views.ArticleView;

import static tr.com.hurriyet.sdk.R.id.articles;


/**
 * Created by apple on 12/08/2017.
 */

public class ArticlesFragment extends Fragment implements ArticleView {

    private ArticlesRecyclerViewAdapter adapter;
    private ArrayList<Article> arrayList = new ArrayList<Article>();
    private RecyclerView recyclerView;
    private Presenter presenter;

    public ArticlesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.articles_fragment, container, false);



        recyclerView = (RecyclerView) view.findViewById (articles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(new ArticlesRecyclerViewAdapter(getActivity(), arrayList, new ArticlesRecyclerViewAdapter.ItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                openDetail(position);
            }

        }));

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        presenter = new ArticlePresenterImpl(getActivity().getApplicationContext());
        presenter.initializeSdk();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            RequestGenerator.get().registerAsResponseHandler(this);
        } catch (UninitializedSdkException e) {
            e.printStackTrace();
        }
        presenter.launchRequests();
    }

    @Override
    public void onPause() {
       try {
            RequestGenerator.get().unregisterAsResponseHandler(this);
        } catch (UninitializedSdkException e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    public void setData(ArrayList<Article> arrayList) {

        recyclerView.setAdapter(new ArticlesRecyclerViewAdapter(getActivity(), arrayList, new ArticlesRecyclerViewAdapter.ItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                  openDetail(position);
            }

        }));
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void openDetail(int position){

        Intent intent = new Intent(ArticlesFragment.this.getActivity(), ArticleDetailActivity.class);
        intent.putExtra("articleID", arrayList.get(position).id);
        startActivity(intent);


    }

    @Subscribe
    public void onArticleListResponse(Article[] response) {

        for(int i= 0; i<response.length; i++){
                arrayList.add(response[i]);
        }

        setData(arrayList);
    }

    @Subscribe
    public void onError(ErrorModel errorModel) {
        // TODO handle the error.
    }

}