package com.example.babyapp2;

//  ARfeature is a class that displays a 3D model in augmented reality (AR) using Google's ARCore and Sceneform library.

// The activity sets up the AR fragment, dropdown menu (spinner), and slider (seekbar) for user interaction.
// It  loads 3D models from resources and stores them in an array called "renderable_models".
// There is only one model available(baby model) but more can be added in the future.

// The code iterates through the "glb_source" array, which contains resource IDs of 3D models, and creates a ModelRenderable for each model.
// The ModelRenderable objects are stored in the "renderable_models" array for later use.

// The activity sets an setOnTapArPlaneListener for the AR fragment.
// When the user taps on a detected plane, an Anchor and AnchorNode are created at the tapped location.
// A TransformableNode is then created and attached to the AnchorNode, and the selected 3D model is set as the renderable for the TransformableNode.

// The activity listens for changes in the slider (seekbar) and updates the size of the 3D model accordingly by setting the local scale of the AnchorNode.

// The activity listens for changes in the dropdown menu (spinner) and updates the renderable of the TransformableNode to the selected 3D model.

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ARfeature extends AppCompatActivity {

    public ArFragment arFragment;
    public AnchorNode myanchornode;
    TransformableNode mytranode = null;


    public Button info_btn;
    public SeekBar model_slider;
    public Spinner model_dropdown;
    public float mySize = 70f;

    int[] glb_source = {R.raw.baby};
    String[] arr_models = {"Baby Model"};
    public ModelRenderable[] renderable_models = new ModelRenderable[glb_source.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arfeature);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        info_btn = findViewById(R.id.info_btn);
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ARfeature.this, InfoScreen.class);
                startActivity(intent);
            }
        });

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        model_dropdown = findViewById(R.id.model_dropdown);
        model_slider = findViewById(R.id.model_slider);

        model_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mySize = progress;
                myanchornode.setLocalScale(new Vector3(progress/70f, progress/70f, progress/70f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        model_slider.setEnabled(false);

        List<AnchorNode> anchorNodes = new ArrayList<>();
        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (renderable_models[model_dropdown.getSelectedItemPosition()] == null) {
                        return;
                    }

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    anchorNodes.add(anchorNode);
                    model_slider.setEnabled(true);

                    myanchornode = anchorNode;


                    TransformableNode transformableNode;
                    if(mytranode == null)
                        transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                    else transformableNode = mytranode;

                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(renderable_models[model_dropdown.getSelectedItemPosition()]);
                    transformableNode.select();

                    mytranode = transformableNode;
                    mytranode.setLocalRotation(new Quaternion(0f, 0f, 0f, 1f));
                    myanchornode.setLocalScale(new Vector3(mySize/70f, mySize/70f, mySize/70f));
                });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ARfeature.this,
                android.R.layout.simple_spinner_dropdown_item,arr_models);

        model_dropdown.setAdapter(adapter);
        model_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mytranode != null)
                    mytranode.setRenderable(renderable_models[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        for(int i = 0 ; i < glb_source.length ; i++) {
            int finalI = i;
            ModelRenderable.builder()
                    .setSource(this, glb_source[i])
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(renderable -> renderable_models[finalI] = renderable)
                    .exceptionally(
                            throwable -> {
                                Toast toast =
                                        Toast.makeText(this, "Unable to load any renderable", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            });
        }

        Field popup = null;
        try {
            popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(model_dropdown);
            popupWindow.setModal(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}



//arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
// The "setOnTapPlaneGlbModel" method is called on the "arFragment" variable with a file path to a .glb model file.
// This method allows the user to tap on a plane in the AR environment and place the 3D model on that plane.
//arFragment.setOnTapPlaneGlbModel(Uri.parse("models/abc.glb").toString());

//arFragment.setOnTapPlaneGlbModel(Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb").toString());
//                        (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)
//                .setOnTapPlaneGlbModel("model.glb")