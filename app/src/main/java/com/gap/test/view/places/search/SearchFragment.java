package com.gap.test.view.places.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gap.test.R;
import com.gap.test.databinding.FragmentPlacesSearchBinding;
import com.gap.test.view.common.base.BaseFragment;
import com.gap.test.view.common.utils.Constants;
import com.gap.test.view.main.MainActivity;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */

public class SearchFragment extends BaseFragment implements SearchContract.view {

    private FragmentPlacesSearchBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentPlacesSearchBinding.inflate(inflater);
        binding.setModelView(new SearchViewModel());
        binding.setPresenter(new SearchPresenter(this));

        setTitle(R.string.search_places);
        setBacArrow(false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public SearchViewModel getViewModel() {
        return binding.getModelView();
    }

    @Override
    public void setViewModel(SearchViewModel viewModel) {
        binding.setModelView(viewModel);
    }


    @Override
    public void launchView(BaseFragment baseFragment) {
        hideKeyPad();
        hideSnackBarOnActivity();
        ((MainActivity)getActivity()).loadFragment(baseFragment, Constants.FRAG_RESULT_PLACES);
    }

    @Override
    public void hideSnackBarOnActivity() {
        hideSnackBar();
    }

}
