package com.silptech.kdf;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is the fragment which is also the startup fragment that displays on opening the app.
 * The text(Nepali) is Typedfaced and custom font is used to display the nepali fonts.
 * The nepali unicode is also used and taken from file.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    public final static String TAG = "HomeFragment";
    Typeface customFont;

    TextView kdfNepali;
    TextView kdfEnglish;
    TextView intro;
    TextView introDescription;
    TextView organisation;
    TextView organisationDescription;
    TextView objective;
    TextView objectiveDescription;
    TextView membershipRequirement;
    TextView membershipRequirementDescription;
    TextView general;
    TextView generalDescription;
    TextView centralBoard;
    TextView centralBoardDescription;

    String kdfNepaliText;
    String kdfEnglishText;
    String introText;
    String introDescriptionText;
    String organisationText;
    String organisationDescriptionText;
    String objectiveText;
    String objectiveDescriptionText;
    String membershipRequirementText;
    String membershipRequirementDescriptionText;
    String generalText;
    String generalDescriptionText;
    String centralBoardText;
    String centralBoardDescriptionText;

    InputStream intro_desc = null;
    InputStream org_desc = null;
    InputStream obj_desc = null;
    InputStream mem_desc = null;
    InputStream gen_desc = null;
    InputStream central_desc = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialization
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        kdfNepali = (TextView) view.findViewById(R.id.kdf_nepali);
        kdfEnglish = (TextView) view.findViewById(R.id.kdf_english);
        intro = (TextView) view.findViewById(R.id.kdf_intro);
        introDescription = (TextView) view.findViewById(R.id.kdf_intro_description);
        organisation = (TextView) view.findViewById(R.id.kdf_organisation);
        organisationDescription = (TextView) view.findViewById(R.id.kdf_organisation_description);
        objective = (TextView) view.findViewById(R.id.kdf_objectives);
        objectiveDescription = (TextView) view.findViewById(R.id.kdf_objectives_description);
        membershipRequirement = (TextView) view.findViewById(R.id.kdf_membership_requirement);
        membershipRequirementDescription = (TextView) view.findViewById(R.id.kdf_membership_requirement_description);
        general = (TextView) view.findViewById(R.id.kdf_general);
        generalDescription = (TextView) view.findViewById(R.id.kdf_general_description);
        centralBoard = (TextView) view.findViewById(R.id.kdf_central_board);
        centralBoardDescription = (TextView) view.findViewById(R.id.kdf_central_board_description);

        // using customfont and setting the fonts to respective textviews
        customFont = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/sagarmatha.TTF");
        kdfNepali.setTypeface(customFont);
        intro.setTypeface(customFont);
        introDescription.setTypeface(customFont);
        organisation.setTypeface(customFont);
        organisationDescription.setTypeface(customFont);
        objective.setTypeface(customFont);
        objectiveDescription.setTypeface(customFont);
        membershipRequirement.setTypeface(customFont);
        membershipRequirementDescription.setTypeface(customFont);
        general.setTypeface(customFont);
        generalDescription.setTypeface(customFont);
        centralBoard.setTypeface(customFont);
        centralBoardDescription.setTypeface(customFont);

        // getting strings from R.string
        kdfNepaliText = getActivity().getResources().getString(R.string.kdf_text_nepali);
        kdfEnglishText = getActivity().getResources().getString(R.string.kdf_text_english);
        introText = getActivity().getResources().getString(R.string.kdf_intro);
        organisationText = getActivity().getResources().getString(R.string.kdf_organisation);
        objectiveText = getActivity().getResources().getString(R.string.kdf_objective);
        membershipRequirementText = getActivity().getResources().getString(R.string.kdf_membership);
        generalText = getActivity().getResources().getString(R.string.kdf_general);
        centralBoardText = getActivity().getResources().getString(R.string.kdf_central_board);

//        introDescriptionText = getActivity().getResources().getString(R.string.kdf_intro_description);
//        organisationDescriptionText = getActivity().getResources().getString(R.string.kdf_organisation_description);
//        objectiveDescriptionText = getActivity().getResources().getString(R.string.kdf_objective_description);
//        membershipRequirementDescriptionText = getActivity().getResources().getString(R.string.kdf_membership_description);
//        generalDescriptionText = getActivity().getResources().getString(R.string.kdf_general_description);
//        centralBoardDescriptionText = getActivity().getResources().getString(R.string.kdf_central_board_description);

        //getting nepali unicode from files inside assets folder
        try {
            intro_desc = getActivity().getAssets().open("kdf_intro_description.txt");
            int size1 = intro_desc.available();
            byte[] buffer1 = new byte[size1];
            intro_desc.read(buffer1);
            intro_desc.close();
            // Convert the buffer into a string.
            introDescriptionText = new String(buffer1);

            org_desc = getActivity().getAssets().open("kdf_organisation_description.txt");
            int size2 = org_desc.available();
            byte[] buffer2 = new byte[size2];
            org_desc.read(buffer2);
            org_desc.close();
            // Convert the buffer into a string.
            organisationDescriptionText = new String(buffer2);

            obj_desc = getActivity().getAssets().open("kdf_objective_description.txt");
            int size3 = obj_desc.available();
            byte[] buffer3 = new byte[size3];
            obj_desc.read(buffer3);
            obj_desc.close();
            // Convert the buffer into a string.
            objectiveDescriptionText = new String(buffer3);

            mem_desc = getActivity().getAssets().open("kdf_membership_description.txt");
            int size4 = mem_desc.available();
            byte[] buffer4 = new byte[size4];
            mem_desc.read(buffer4);
            mem_desc.close();
            // Convert the buffer into a string.
            membershipRequirementDescriptionText = new String(buffer4);

            gen_desc = getActivity().getAssets().open("kdf_general_description.txt");
            int size5 = gen_desc.available();
            byte[] buffer5 = new byte[size5];
            gen_desc.read(buffer5);
            gen_desc.close();
            // Convert the buffer into a string.
            generalDescriptionText = new String(buffer5);

            central_desc = getActivity().getAssets().open("kdf_central_board_description.txt");
            int size6 = central_desc.available();
            byte[] buffer6 = new byte[size6];
            central_desc.read(buffer6);
            central_desc.close();
            // Convert the buffer into a string.
            centralBoardDescriptionText = new String(buffer6);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // setting string to the textview
        kdfNepali.setText(kdfNepaliText);
        kdfEnglish.setText(kdfEnglishText);
        intro.setText(introText);
        introDescription.setText((introDescriptionText));
        organisation.setText(organisationText);
        organisationDescription.setText(organisationDescriptionText);
        objective.setText(objectiveText);
        objectiveDescription.setText(objectiveDescriptionText);
        membershipRequirement.setText(membershipRequirementText);
        membershipRequirementDescription.setText(membershipRequirementDescriptionText);
        general.setText(generalText);
        generalDescription.setText(generalDescriptionText);
        centralBoard.setText(centralBoardText);
        centralBoardDescription.setText(centralBoardDescriptionText);
        return view;
    }
}