package tr.com.hurriyet.sdk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import tr.com.hurriyet.opensourcesdk.exceptions.UninitializedSdkException;
import tr.com.hurriyet.opensourcesdk.model.ErrorModel;
import tr.com.hurriyet.opensourcesdk.model.response.Gallery;
import tr.com.hurriyet.opensourcesdk.services.RequestGenerator;
import tr.com.hurriyet.sdk.R;
import tr.com.hurriyet.sdk.adapters.GalleryRecyclerViewAdapter;
import tr.com.hurriyet.sdk.presenters.GalleryPresenterImpl;
import tr.com.hurriyet.sdk.presenters.Presenter;
import tr.com.hurriyet.sdk.views.GalleryView;

/**
 * Created by apple on 12/08/2017.
 */

public class GalleryFragment extends Fragment implements GalleryView{

    private Presenter presenter;
    private GalleryRecyclerViewAdapter adapter;
    private ArrayList<Gallery> arrayList = new ArrayList<Gallery>();
    private RecyclerView recyclerView;

    public GalleryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.gallery_fragment, container, false);

       recyclerView = (RecyclerView) view.findViewById (R.id.gallery);

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        presenter = new GalleryPresenterImpl(getActivity().getApplicationContext());
        presenter.initializeSdk();

        return view;
    }

    @Override
    public void setData(ArrayList<Gallery> arrayList) {

        recyclerView.setAdapter(new GalleryRecyclerViewAdapter(getActivity(), arrayList));
        recyclerView.getAdapter().notifyDataSetChanged();
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

    @Subscribe
    public void onGalleryListResponse(Gallery[] response) {

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
