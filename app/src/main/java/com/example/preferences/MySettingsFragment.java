package com.example.preferences;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final SwitchPreferenceCompat notificationsRef = findPreference("notifications");
        if (notificationsRef != null) {
            notificationsRef.setSummaryProvider((Preference.SummaryProvider<SwitchPreferenceCompat>) preference -> {
                if (notificationsRef.isChecked()) {
                    return "Активирани";
                } else {
                    return "Деактивирани";
                }
            });
        }


        /* Preferences */

        final CheckBoxPreference checkBoxPreference = findPreference("scientific_literature");
        if (checkBoxPreference != null) {
            checkBoxPreference.setSummaryProvider((Preference.SummaryProvider<CheckBoxPreference>) preference -> {
                if (checkBoxPreference.isChecked()) {
                    return "Прочетете интересните книги от света на науката";
                } else {
                    return "Интересни книги от света на науката";
                }
            });
        }


        Preference feedbackPref = findPreference("feedback");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://nbu.bg/bg/contacts"));
        if (feedbackPref != null) {
            feedbackPref.setIntent(intent);
        }


        EditTextPreference signaturePreference = findPreference("signature");
        if (signaturePreference != null) {
            signaturePreference.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> {
                String text = preference.getText();
                if (TextUtils.isEmpty(text)) {
                    return "Не е зададен подпис";
                }
                return "Дължина на запазената стойност: " + text.length();
            });
        }


        /* Shared Preferences */

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        prefs.edit().putString("key_name", "Стойност от SharedPreferences").apply();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String name = sharedPreferences.getString("key_name", null);
        Toast.makeText(getActivity().getApplicationContext(), name, Toast.LENGTH_LONG).show();

        super.onViewCreated(view, savedInstanceState);
    }
}