package com.example.databasesample;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class AddGroceryDialogFragment extends DialogFragment {
    private TextInputEditText inputItemName;
    private TextInputEditText inputAmount;
    private Spinner spinnerUnit;
    private Button buttonAddItem;

    public AddGroceryDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_grocery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputItemName = view.findViewById(R.id.input_item_name);
        inputAmount = view.findViewById(R.id.input_amount);
        spinnerUnit = view.findViewById(R.id.spinner_unit);
        buttonAddItem = view.findViewById(R.id.button_add_item);

        getDialog().setTitle("Add Grocery");

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.unit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerUnit.setAdapter(adapter);
    }

    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}