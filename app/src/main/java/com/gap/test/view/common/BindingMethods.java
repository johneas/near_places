package com.gap.test.view.common;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gap.test.R;


/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
public class BindingMethods {

    /**
     * Display a view according boolean field declared on vieModel
     *
     * @param view view to show or hide
     * @param isShown is visible or not
     */
    @BindingAdapter("android:onShow")
    public static void setOnShow(View view, boolean isShown) {
        if (isShown)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }

    /**
     * Check if a mandatory field, have information before continue
     * @param view Edit text
     * @param validate if require validation
     */
    @BindingAdapter("app:validate")
    public static void setMandatory(View view, Boolean validate) {

        if (validate && view != null) {
            if (view instanceof EditText) {
                if (((EditText) view).getText().length() > 0)
                    ((EditText) view).setError(null);
                else
                    ((EditText) view).setError(view.getResources().getString(R.string.edit_text_error_message));
            }

            if (view instanceof TextInputLayout) {
                EditText edittext = ((TextInputLayout) view).getEditText();

                if (edittext != null) {
                    if (TextUtils.isEmpty(edittext.getText())) {
                        ((TextInputLayout) view).setError(view.getResources().getString(R.string.edit_text_error_message));
                        ((TextInputLayout) view).setErrorEnabled(true);
                    } else {
                        ((TextInputLayout) view).setError("");
                        ((TextInputLayout) view).setErrorEnabled(false);
                    }
                } else {
                    ((TextInputLayout) view).setError("");
                    ((TextInputLayout) view).setErrorEnabled(false);
                }
            }
        }
    }

    /**
     * Convert a text to htm format
     *
     * @param textView Text container
     * @param html htm text
     */
    @BindingAdapter("bind:spanned")
    @SuppressWarnings("deprecation")
    public static void setHtmlContent(TextView textView, String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        textView.setText(result.toString().equals("null") ? textView.getContext().getText(R.string.na) : result);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Replace null value for n/a
     *
     * @param textView Text container
     * @param text text to check
     */
    @BindingAdapter("bind:availableContent")
    public static void setAvailable(TextView textView, String text) {
        String naText =  textView.getContext().getText(R.string.na).toString();
        textView.setText(text == null ? naText : text.replace("null", naText));
    }
}