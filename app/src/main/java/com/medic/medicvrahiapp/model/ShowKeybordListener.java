package com.medic.medicvrahiapp.model;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.medic.medicvrahiapp.R;

public class ShowKeybordListener extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_CALL = 100;


    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(ShowKeybordListener.this);

            if(heightDiff <= contentViewTop){
                onHideKeyboard();

                Intent intent = new Intent("KeyboardWillHide");
                broadcastManager.sendBroadcast(intent);
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                onShowKeyboard(keyboardHeight);

                Intent intent = new Intent("KeyboardWillShow");
                intent.putExtra("KeyboardHeight", keyboardHeight);
                broadcastManager.sendBroadcast(intent);
            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard(int keyboardHeight) {
//        System.out.println("show keyb "+Integer.toString(keyboardHeight));

//        if(keyboardHeight>300){
//            FinishForResult.this.findViewById(R.id.conBottom).setVisibility(View.GONE);
//        }else{
//            FinishForResult.this.findViewById(R.id.conBottom).setVisibility(View.VISIBLE);
//        }
    }
    protected void onHideKeyboard() {}

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = findViewById(R.id.container);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(resultCode + "result !!!!!!!!!!!!!!!     cod in main");
//        if (resultCode == FinishForResult.forResultBottomButtonMenu) {
//            InitOsnovnActivityBottomButton.chatButtonClick();
//        } else if (resultCode == FinishForResult.forResultBottomButtonZapis) {
//            MainActivity.ibZapis.performClick();
//        } else if (resultCode == FinishForResult.forResultBottomButtonVopros) {
//            MainActivity.ibVopros.performClick();
//        } else if (resultCode == FinishForResult.forResultBottomButtonAkcii) {
//            MainActivity.ibAkcii.performClick();
//        } else if (resultCode == FinishForResult.forResultBottomButtonProfil) {
//            MainActivity.ibProfil.performClick();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }
}
