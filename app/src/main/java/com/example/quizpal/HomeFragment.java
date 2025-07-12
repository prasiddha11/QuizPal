package com.example.quizpal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizpal.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentHomeBinding binding;
    FirebaseFirestore database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentHomeBinding.inflate(inflater,container,false);
       database= FirebaseFirestore.getInstance();

        final ArrayList<CategoryModel> categoryModels = new ArrayList<>();

        final CategoryAdapter adapter = new  CategoryAdapter(getContext(), categoryModels);

        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categoryModels.clear();
                        {

                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                CategoryModel model = snapshot.toObject(CategoryModel.class);
                                model.setCategoryId(snapshot.getId());
                                categoryModels.add(model);

                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.categoryList.setAdapter(adapter);
        binding.spinwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),SpinnerActivity.class));
            }
        });
        binding.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Visit this link and download this fantastic quiz application \n\n https://play.google.com/store/apps/details?id=com.example.quizpal");
                startActivity(Intent.createChooser(sendIntent,"Choose One"));
            }
        });



        return  binding.getRoot();
    }
}