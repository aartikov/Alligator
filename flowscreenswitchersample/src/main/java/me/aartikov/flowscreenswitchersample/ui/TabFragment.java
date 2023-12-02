package me.aartikov.flowscreenswitchersample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.flowscreenswitchersample.R;
import me.aartikov.flowscreenswitchersample.SampleApplication;
import me.aartikov.flowscreenswitchersample.screens.InnerScreen;
import me.aartikov.flowscreenswitchersample.screens.TabScreen;

@RegisterScreen(TabScreen.class)
public class TabFragment extends Fragment implements ContainerIdProvider {

    TextView mNameTextView;

    private final Navigator mNavigator = SampleApplication.getNavigator();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mNavigator.reset(new InnerScreen(1));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNameTextView = view.findViewById(R.id.name_text_view);

        TabScreen screen = SampleApplication.getScreenResolver().getScreen(this);
        mNameTextView.setText(screen.getName());
    }

    @Override
    public int getContainerId() {
        return R.id.inner_container;
    }
}
