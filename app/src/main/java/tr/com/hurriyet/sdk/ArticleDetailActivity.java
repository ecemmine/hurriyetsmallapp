package tr.com.hurriyet.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import tr.com.hurriyet.opensourcesdk.exceptions.UninitializedSdkException;
import tr.com.hurriyet.opensourcesdk.model.ErrorModel;
import tr.com.hurriyet.opensourcesdk.model.response.Article;
import tr.com.hurriyet.opensourcesdk.services.RequestGenerator;
import tr.com.hurriyet.sdk.presenters.ArticleDetailPresenterImpl;
import tr.com.hurriyet.sdk.presenters.Presenter;
import tr.com.hurriyet.sdk.views.ArticleDetailView;

/**
 * Created by apple on 14/08/2017.
 */

public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailView{

    private ImageView detailImage;
    private TextView detailText;
    private String articleID;
    private Presenter presenter;
    private String imageUrl;
    private String articleText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("HURRIYET MINI APP");

        detailImage = (ImageView) findViewById(R.id.article_image);
        detailText = (TextView) findViewById(R.id.article_text);

        Bundle extras = getIntent().getExtras();
        articleID = extras.getString("articleID");


        presenter = new ArticleDetailPresenterImpl(getApplicationContext(), articleID);
        presenter.initializeSdk();

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
    public void setImageView(final String url) {

            Picasso.with(detailImage.getContext()).load(url).resize(500,500).into(detailImage);
    }
    @Override
    public void setTextView(final String articleText) {

        detailText.setText(articleText);
    }

    @Subscribe
    public void onArticleDetailResponse(Article response) {

        if(null != response &&  0 != response.files.size()){
            imageUrl = response.files.get(0).fileUrl;
        }
        articleText = response.description;

        setImageView(imageUrl);
        setTextView(articleText);

    }

    @Subscribe
    public void onError(ErrorModel errorModel) {
        // TODO handle the error.
    }

}
