package com.example.tccadoteumaarvore.activity.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tccadoteumaarvore.R;
import com.example.tccadoteumaarvore.activity.RegisterActivity;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentProfileBinding;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private Usuario user = new Usuario();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Recuperar dados do usuário logado
        returnUserInfo();
    }

    public void returnUserInfo(){

        FirebaseAuth userAuth = ConfigFirebase.getFirebaseAuth();
        String uuiUser = Base64Custom.encodeBase64(userAuth.getCurrentUser().getEmail());
        DatabaseReference databaseref = ConfigFirebase.getFirebaseRef();

        databaseref.child("usuarios").child(uuiUser).get().addOnCompleteListener(
            new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){
                        if (task.getResult().exists()){
                            DataSnapshot dataSnapshot = task.getResult();
                            user = dataSnapshot.getValue(Usuario.class);

                        } else {
                            System.out.println("Else");
                        }
                    }
                }
            }
        );
    }
}