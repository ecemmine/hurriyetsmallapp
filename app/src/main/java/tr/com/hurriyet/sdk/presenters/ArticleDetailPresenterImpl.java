package tr.com.hurriyet.sdk.presenters;

import android.content.Context;

import tr.com.hurriyet.opensourcesdk.exceptions.InvalidApiKeyException;
import tr.com.hurriyet.opensourcesdk.exceptions.UninitializedSdkException;
import tr.com.hurriyet.opensourcesdk.services.RequestGenerator;
import tr.com.hurriyet.sdk.Constants;

/**
 * Created by apple on 14/08/2017.
 */

public class ArticleDetailPresenterImpl implements Presenter{

    private Context context;
    private String rid;

    public ArticleDetailPresenterImpl(Context context,String id){

        this.context = context;
        this.rid = id;
    }


    @Override
    public void initializeSdk(){
        try {
            RequestGenerator.initialize(this.context, Constants.API_KEY);
        } catch (InvalidApiKeyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void launchRequests() {
        try {
            RequestGenerator requestBuilder = RequestGenerator.get();

            requestBuilder.articleDetailRequest(this.rid,null);

        } catch (UninitializedSdkException e) {
            e.printStackTrace();
        }
    }

}
