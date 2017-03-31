package com.example.alex.qtapandroid.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class EventInfoFragment extends Fragment {
        @Nullable

        String data, data2, date;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            Bundle bundle = this.getArguments();

        String actionTitle = "";
        Bitmap imageBitmap = null;
        String transText = "";
        String transitionName = "";

        if (bundle != null) {
            transitionName = bundle.getString("TRANS_NAME");
            actionTitle = bundle.getString("ACTION");
            imageBitmap = bundle.getParcelable("IMAGE");
            transText = bundle.getString("TRANS_TEXT");
            data = bundle.getString("data1", "");
                data2 = bundle.getString("data2", "");
                date = bundle.getString("date", "");
            }

        getActivity().setTitle(actionTitle);
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            view.findViewById(R.id.card_view).setTransitionName(transitionName);
//            view.findViewById(R.id.card_view_event).setTransitionName(transText);
//        }

//        ((CardView) view.findViewById(R.id.card_view_event)).set(actionTitle);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView eventName = (TextView) view.findViewById(R.id.EventName);
        eventName.setText(data);
        TextView eventLoc = (TextView) view.findViewById(R.id.EventLoc);
        eventLoc.setText(data2);
        TextView eventDate = (TextView) view.findViewById(R.id.EventDate);
        eventDate.setText(data);
        TextView eventTime = (TextView) view.findViewById(R.id.EventTime);
        eventTime.setText(data2.substring(0,data2.indexOf(" ")));
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    DayFragment nextFrag=  new DayFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(date,"");
                    nextFrag.setArguments(bundle);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        setSharedElementReturnTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(R.transition.card_transistion));
                        setExitTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(android.R.transition.explode));

                        nextFrag.setSharedElementEnterTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(R.transition.card_transistion));
                        nextFrag.setEnterTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(android.R.transition.explode));
                    }
                    CardView card = (CardView) v.findViewById(R.id.card_view_event);
                    String cardName = card.getTransitionName();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, nextFrag)
                            .addToBackStack("EventInfoFragment")
                            .addSharedElement(card, cardName)
                            .commit();
                    return true;

                }

                return false;
            }
        });
    }
}
