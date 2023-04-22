package com.alreyada.app.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alreyada.app.R;
import com.alreyada.app.model.commons.BillingCommon;
import com.alreyada.app.data.preference.SessionManager;
import com.alreyada.app.databinding.DialogEditAddressBinding;
import com.alreyada.app.model.User;
import com.google.gson.Gson;

public class EditAddressDialog extends AppCompatDialogFragment {
    private Context context;
    private Activity activity;
    private DialogEditAddressBinding binding;
    private AlertDialog dialog;
    private Button addBtn;
    private EditAddressDialogListener listener;
    private SessionManager sessionManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        binding = DialogEditAddressBinding.inflate(inflater);
        View view = binding.getRoot();
        builder.setView(view)
                .setTitle("تعديل عنوان الشحن")
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("اضافة", null);

        dialog = builder.create();

        return dialog;
    }


    private void getData() {
        BillingCommon shipping = sessionManager.getAddress();
        if (!TextUtils.isEmpty(shipping.getFirstName()) && !TextUtils.isEmpty(shipping.getLastName())) {
            binding.textInputFirstName.getEditText().setText("" + shipping.getFirstName());
            binding.textInputLastName.getEditText().setText("" + shipping.getLastName());
        } else if (sessionManager.isLoggedIn()) {
            User user = sessionManager.getUser();
            binding.textInputFirstName.getEditText().setText("" + user.getFirstName());
            binding.textInputLastName.getEditText().setText("" + user.getLastName());
        } else {
            binding.textInputFirstName.getEditText().setText("");
            binding.textInputLastName.getEditText().setText("");
        }
        binding.textInputCompanyName.getEditText().setText("" + shipping.getCompany());
        binding.textInputAddress.getEditText().setText("" + shipping.getAddressOne() + " " + shipping.getAddressTow());
        binding.textInputCity.getEditText().setText("" + shipping.getCity());
        binding.textInputPhoneNumber.getEditText().setText("" + shipping.getPhone());
    }

    private void saveAddress(BillingCommon shipping) {
        sessionManager.setAddress(shipping);
    }

    private void initVar() {
        sessionManager = new SessionManager(context);
    }

    @Override
    public void onStart() {
        super.onStart();

        initVar();
        getData();

        addBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.textInputFirstName.getEditText().getText().toString().trim();
                String lastName = binding.textInputLastName.getEditText().getText().toString().trim();
                String companyName = binding.textInputCompanyName.getEditText().getText().toString().trim();
                String address = binding.textInputAddress.getEditText().getText().toString().trim();
                String city = binding.textInputCity.getEditText().getText().toString().trim();
                String phoneNumber = binding.textInputPhoneNumber.getEditText().getText().toString().trim();
                String postCode = binding.textInputPostCode.getEditText().getText().toString().trim();

                if (!validateFirstName(firstName) | !validateLastName(lastName) | !validateCompanyName(companyName) | !validateAddress(address) | !validateCity(city) | !validatePhoneNumber(phoneNumber)) {
                    return;
                }

                BillingCommon shipping = new BillingCommon(
                        firstName,
                        lastName,
                        companyName,
                        address,
                        "",
                        city,
                        postCode,
                        getString(R.string.current_country),
                        city,
                        sessionManager.getUser().getEmail(),
                        phoneNumber);

                String json = new Gson().toJson(shipping);
                saveAddress(shipping);
                listener.onReceiveAddress(json);

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EditAddressDialogListener) context;
        } catch (ClassCastException e) {
            try {
                listener = (EditAddressDialogListener) getTargetFragment();
            } catch (ClassCastException exception) {
                throw new ClassCastException(context.toString() + " EditAddressDialogListener is not implemented in context activity or target fragment");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private boolean validateFirstName(String firstName) {
        if (firstName.isEmpty()) {
            binding.textInputFirstName.setError("من فضلك ادخل الاسم الاول");
            binding.textInputFirstName.requestFocus();
            return false;
        } else if (firstName.length() > 15) {
            binding.textInputFirstName.setError("اسم طويل");
            return false;
        } else {
            binding.textInputFirstName.setError(null);
            return true;
        }
    }

    private boolean validateLastName(String lastName) {
        if (lastName.isEmpty()) {
            binding.textInputLastName.setError("من فضلك ادخل الاسم الثاتي");
            binding.textInputLastName.requestFocus();
            return false;
        } else if (lastName.length() > 15) {
            binding.textInputLastName.setError("اسم طويل");
            return false;
        } else {
            binding.textInputLastName.setError(null);
            return true;
        }
    }

    private boolean validateCompanyName(String companyName) {
        if (companyName.isEmpty()) {
            binding.textInputCompanyName.setError("من فضلك ادخل اسم الشركة");
            binding.textInputCompanyName.requestFocus();
            return false;
        } else if (companyName.length() > 40) {
            binding.textInputCompanyName.setError("اسم طويل");
            return false;
        } else {
            binding.textInputCompanyName.setError(null);
            return true;
        }
    }

    private boolean validateAddress(String address) {
        if (address.isEmpty()) {
            binding.textInputAddress.setError("الرجاء ادخال العنوان");
            binding.textInputAddress.requestFocus();
            return false;
        } else {
            binding.textInputAddress.setError(null);
            return true;
        }
    }

    private boolean validateCity(String cityName) {
        if (cityName.isEmpty()) {
            binding.textInputCity.setError("الرجاء ادخال المدينة او المحافظة");
            binding.textInputCity.requestFocus();
            return false;
        } else if (cityName.length() > 20) {
            binding.textInputCity.setError("اسم طويل");
            return false;
        } else {
            binding.textInputCity.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            binding.textInputPhoneNumber.setError("من فضلك ادخل رقم الهاتف");
            binding.textInputPhoneNumber.requestFocus();
            return false;
        } else if (!TextUtils.isDigitsOnly(phoneNumber)) {
            binding.textInputPhoneNumber.setError("من المفترض ان يكون ارقام فقط");
            return false;
        } /*else if (phoneNumber.length() != 11) {
            binding.etPhoneNumber.setError("يجب ان يكون 11 رقم");
            return false;
        }*//* else if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            return false;
        } */ else {
            binding.textInputPhoneNumber.setError(null);
            return true;
        }
    }

    public interface EditAddressDialogListener {
        void onReceiveAddress(String jsonAddress);
    }

}
