package tr.com.hurriyet.sdk.views;

import java.util.ArrayList;

import tr.com.hurriyet.opensourcesdk.model.response.Article;

/**
 * Created by apple on 14/08/2017.
 */

public interface ArticleView {

    void setData(ArrayList<Article> arrayList);
    void openDetail(int position);
}