package com.medic.medicvrahiapp.f_profil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.InitOsnovnActivityBottomButton;
import com.medic.medicvrahiapp.model.MetodiStatic;
import com.medic.medicvrahiapp.model.Vrahi;

public class RegistrationTelefonActivity extends InitOsnovnActivityBottomButton {
    static String mVerificationId;
    EditText etTelefonToSendCode, etTelefonProveritCod;
    ConstraintLayout conFirst, conSecond;
    static PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    static boolean mVerificationInProgress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_telefon);
        etTelefonToSendCode=findViewById(R.id.etTelefonToSendCode);
        etTelefonProveritCod=findViewById(R.id.etTelefonProveritCod);
        conFirst=findViewById(R.id.conFirst);
        conSecond=findViewById(R.id.conSecond);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                Toast.makeText(RegistrationTelefonActivity.this,"Verification Complete", Toast.LENGTH_SHORT).show();

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(RegistrationTelefonActivity.this,"Verification Failed"+e.toString(), Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(RegistrationTelefonActivity.this,"InValid Phone Number", Toast.LENGTH_SHORT).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(RegistrationTelefonActivity.this,"Verification code has been send on your number", Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
//                mResendToken = token;
//                MetodiStatic.codSMS_Otpravlen(conFirst, conSecond );
                // ...
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        final Intent intent=new Intent(this, ProfilInfoActivity.class);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(RegistrationTelefonActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "signInWithCredential:success");
//                            FirebaseHelper.userFb= FirebaseAuth.getInstance().getCurrentUser();
//                            User.user.setGoogleUID(FirebaseHelper.userFb.getUid());
//                            MetodiStatic.codSMS_Proveren( conRegFio, conRegTele);
//                            MetodiStatic.uznatEstLiUserVMySQL();
//                            Toast.makeText(this,"Verification Done",
//                                    Toast.LENGTH_SHORT).show();
                            // ...
                            System.out.println("Loggined !");

                            MetodiStatic.loadInfoVraha();
                            startActivity(intent);
                            finish();
                        } else {
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(RegistrationTelefonActivity.this,"Invalid Verification",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public  void poluhitCod(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        etTelefonToSendCode.getText().toString(),
                        60,
                        java.util.concurrent.TimeUnit.SECONDS,
                        RegistrationTelefonActivity.this,
                        mCallbacks);
        conFirst.setVisibility(View.GONE);
        conSecond.setVisibility(View.VISIBLE);
        Vrahi.vrah.setTelefon(etTelefonToSendCode.getText().toString());
        System.out.println();
    }
    public  void proveritCod(View v) {
        if(!etTelefonProveritCod.getText().toString().equals("")){
                PhoneAuthCredential credential = PhoneAuthProvider.
                        getCredential(mVerificationId,
                        etTelefonProveritCod.getText().toString());
                // [END verify_with_code]
                signInWithPhoneAuthCredential(credential);
                etTelefonProveritCod.setText("");


        }
    }
}
