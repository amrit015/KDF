package com.silptech.kdf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.silptech.kdf.Utils.CallDial;
import com.silptech.kdf.Utils.MailTo;

/**
 * This fragment displays the corresponding layout. The organisation info are displayed here.
 */
public class AboutUsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    ImageView callCC1;
    ImageView callCC2;
    ImageView callCC3;
    ImageView mailCC2;
    ImageView callRC1;
    ImageView callRC2;
    ImageView callRC3;
    String phoneCC1;
    String phoneCC2;
    String phoneCC3;
    String phoneRC1;
    String phoneRC2;
    String phoneRC3;
    String mailtoCC2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_about_us, container, false);
        callCC1 = (ImageView) view.findViewById(R.id.call_cc_one);
        callCC2 = (ImageView) view.findViewById(R.id.call_cc_two);
        callCC3 = (ImageView) view.findViewById(R.id.call_cc_three);
        callRC1 = (ImageView) view.findViewById(R.id.call_rc_one);
        callRC2 = (ImageView) view.findViewById(R.id.call_rc_two);
        callRC3 = (ImageView) view.findViewById(R.id.call_rc_three);
        mailCC2 = (ImageView) view.findViewById(R.id.mail_cc_two);

        //getting string from R.string....
        phoneCC1 = getActivity().getResources().getString(R.string.centralContactOneNumber);
        phoneCC2 = getActivity().getResources().getString(R.string.centralContactTwoNumber);
        phoneCC3 = getActivity().getResources().getString(R.string.centralContactThreeNumber);
        phoneRC1 = getActivity().getResources().getString(R.string.regionalContactOneNumber);
        phoneRC2 = getActivity().getResources().getString(R.string.regionalContactTwoNumber);
        phoneRC3 = getActivity().getResources().getString(R.string.regionalContactThreeNumber);
        mailtoCC2 = getActivity().getResources().getString(R.string.centralContactTwoEmail);
        callCC1.setOnClickListener(this);
        callCC2.setOnClickListener(this);
        callCC3.setOnClickListener(this);
        callRC1.setOnClickListener(this);
        callRC2.setOnClickListener(this);
        mailCC2.setOnClickListener(this);
        callRC3.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_cc_one:
                CallDial.PhoneDialer(getActivity(), phoneCC1);
                break;
            case R.id.call_cc_two:
                CallDial.PhoneDialer(getActivity(), phoneCC2);
                break;
            case R.id.call_cc_three:
                CallDial.PhoneDialer(getActivity(), phoneCC3);
                break;
            case R.id.call_rc_one:
                CallDial.PhoneDialer(getActivity(), phoneRC1);
                break;
            case R.id.call_rc_two:
                CallDial.PhoneDialer(getActivity(), phoneRC2);
                break;
            case R.id.call_rc_three:
                CallDial.PhoneDialer(getActivity(), phoneRC3);
                break;
            case R.id.mail_cc_two:
                MailTo.SendEmail(getActivity(), mailtoCC2);
                break;
            default:
                break;
        }
    }
}
