package com.example.databasesample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.databasesample.model.Grocery;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class AddGroceryDialogFragment extends DialogFragment {
    private TextInputEditText inputItemName;
    private TextInputEditText inputAmount;
    private Spinner spinnerUnit;
    private Button buttonAddItem;

    private OnGroceryAddedListener groceryAddedListener;

    public AddGroceryDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof OnGroceryAddedListener)) {
            throw new IllegalStateException(getActivity().getClass().getSimpleName()
                    + " must implement " + OnGroceryAddedListener.class.getSimpleName());
        }
        groceryAddedListener = (OnGroceryAddedListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_grocery, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputItemName = view.findViewById(R.id.input_item_name);
        inputAmount = view.findViewById(R.id.input_amount);
        spinnerUnit = view.findViewById(R.id.spinner_unit);
        buttonAddItem = view.findViewById(R.id.button_add_item);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.unit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerUnit.setAdapter(adapter);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGrocery();
                dismiss();
            }
        });

        Dialog dialog = getDialog();
        Window window = dialog.getWindow();

        dialog.setTitle("Add Grocery");
        inputItemName.requestFocus();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    void addGrocery() {
        String itemName = inputItemName.getText().toString();
        int amount = Integer.parseInt(inputAmount.getText().toString());
        String unit = spinnerUnit.getSelectedItem().toString();

        SQLiteDatabase db = ((MyApp) getActivity().getApplication()).getDb();
        Grocery grocery = new Grocery(itemName, amount, unit, false);

        long id = cupboard().withDatabase(db).put(grocery);
        groceryAddedListener.onGroceryAdded(id);
        Log.d("DBTEST", String.valueOf(id));
    }

    public interface OnGroceryAddedListener {
        void onGroceryAdded(long id);
    }
}
