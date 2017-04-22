package com.gap.test.view.places.details;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.gap.test.R;
import com.gap.test.data.model.Venue;
import com.gap.test.databinding.DialogPlaceDetailsBinding;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
public class DetailsDialog extends DialogFragment implements DetailsContract.View {

    private static final String HTML_HREF = "<a href=\"tel:%s\">";
    private static final String HTML_CLOSE_HREF = "</a>";

    private static final String PLACE_SELECTED = "placeSelected";

    private AlertDialog alertDialog;
    private DialogPlaceDetailsBinding binding;


    public static DetailsDialog newInstance(Venue placeInfo) {
        DetailsDialog instance = new DetailsDialog();
        Bundle args = new Bundle();
        args.putParcelable(PLACE_SELECTED, placeInfo);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        Venue placeInfo = getArguments().getParcelable(PLACE_SELECTED);

        binding = DialogPlaceDetailsBinding.inflate(inflater);
        binding.setModelView (new DetailsViewModel());
        alertDialog.setView(binding.getRoot());

        setCancelable(false);

        if (placeInfo != null) {
            getViewModel().placeName.set(placeInfo.getName());

            //Create a linkable text with phone number, in order to open keypad phone to call
           String linkPhone = String.format(HTML_HREF + placeInfo.getContact().getFormattedPhone() + HTML_CLOSE_HREF,
                    placeInfo.getContact().getPhone());

            getViewModel().contactPone.set(linkPhone);
            getViewModel().address.set(placeInfo.getLocation().getAddress());
            getViewModel().cityState.set(String.format("%s, %s ", placeInfo.getLocation().getCity(),
                    placeInfo.getLocation().getState()));
        }

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext())
                .setTitle(R.string.place_details)
                .setIcon(null)
                .setNegativeButton(R.string.close_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        DetailsDialog.this.dismiss();
                    }
                });

        alertDialog = dialog.create();
        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog alertDialog = (AlertDialog) getDialog();

        if (alertDialog != null) {
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public DetailsViewModel getViewModel() {
        return binding.getModelView();
    }

    @Override
    public void setViewModel(DetailsViewModel viewModel) {
        binding.setModelView(viewModel);
    }
}
