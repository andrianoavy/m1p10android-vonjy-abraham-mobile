package mg.itu.m1p10android;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsFragment extends PreferenceFragmentCompat {

    private MediaPlayer mediaPlayer;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        // Get the SwitchPreferenceCompat for "Enable Notifications"
        SwitchPreferenceCompat enableNotificationsSwitch = findPreference("pref_key_enable_notifications");
        if (enableNotificationsSwitch != null) {
            enableNotificationsSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // Handle the switch state change for "Enable Notifications"
                    boolean enableNotifications = (boolean) newValue;
                    if (enableNotifications) {
                        // Enable notifications, implement your logic here
                        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
                        Log.e("Notification","on");
                    } else {
                        // Disable notifications, implement your logic here
                        FirebaseMessaging.getInstance().setAutoInitEnabled(false);
                        Log.e("Notification","off");
                    }
                    return true;
                }
            });
        }

        // Get the SwitchPreferenceCompat for "Enable Sound"
        SwitchPreferenceCompat enableSoundSwitch = findPreference("pref_key_enable_sound");
        if (enableSoundSwitch != null) {
            enableSoundSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // Handle the switch state change for "Enable Sound"
                    boolean enableSound = (boolean) newValue;
                    if (enableSound) {
                        // Enable sound, implement your logic here
                        playSound();
                    } else {
                        // Disable sound, implement your logic here
                        muteSound();
                    }
                    return true;
                }
            });
        }
    }
    // Example method to play the sound (you can replace this with your actual sound implementation)
    private void playSound() {
        // Your code to play the sound here
        // Check if the MediaPlayer is already initialized
        Log.e("sound","on");
        if (mediaPlayer == null) {
            // Initialize the MediaPlayer with the sound resource
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.maki_song); // Replace "your_sound_file" with your sound file name (located in the res/raw folder)
        }

        // Check if the MediaPlayer is not playing
        if (!mediaPlayer.isPlaying()) {
            // Start playing the sound
            mediaPlayer.start();
        }
    }

    // Example method to mute the sound (you can replace this with your actual sound implementation)
    private void muteSound() {
        Log.e("sound","off");
        // Your code to mute the sound here
        // Check if the MediaPlayer is initialized and playing
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            // Pause and release the MediaPlayer to stop the sound
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources when the fragment is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}