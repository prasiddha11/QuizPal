package com.example.quizpal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizpal.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WalletFragment extends Fragment {


    public WalletFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        FragmentWalletBinding binding;
        FirebaseFirestore database;
        User user;

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment

            binding = FragmentWalletBinding.inflate(inflater, container, false);
            database = FirebaseFirestore.getInstance();

            database.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            user = documentSnapshot.toObject(User.class);
                            binding.currentCoins.setText(String.valueOf(user.getCoins()));

                        }
                    });

            binding.sendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getCoins() > 50000) {
                        String uid = FirebaseAuth.getInstance().getUid();
                        String payPal = binding.emailBox2.getText().toString();
                        WithdrawRequest request = new WithdrawRequest(uid,payPal, user.getUsernameBox());
                        database.collection("withdraws")
                                .document(uid)
                                .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast. makeText(getContext(),"Request sent succesfully",Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                    else {
                        Toast.makeText(getContext(),"You need more coins to withdraw",Toast.LENGTH_SHORT).show();

                    }
                }
            });

            return binding.getRoot();
        }
}