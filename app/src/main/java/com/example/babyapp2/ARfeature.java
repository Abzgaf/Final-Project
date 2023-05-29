package com.example.babyapp2;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class ARfeature extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arfeature);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        // The "setOnTapPlaneGlbModel" method is called on the "arFragment" variable with a file path to a .glb model file.
        // This method allows the user to tap on a plane in the AR environment and place the 3D model on that plane.
        arFragment.setOnTapPlaneGlbModel(Uri.parse("models/abc.glb").toString());

        //arFragment.setOnTapPlaneGlbModel(Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb").toString());
//                        (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)
//                .setOnTapPlaneGlbModel("model.glb")
    }
}