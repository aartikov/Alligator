package me.aartikov.flowscreenswitchersample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.flowscreenswitchersample.R;
import me.aartikov.flowscreenswitchersample.SampleApplication;
import me.aartikov.flowscreenswitchersample.screens.InnerScreen;
import me.aartikov.flowscreenswitchersample.screens.TestFlow2Screen;

@RegisterScreen(InnerScreen.class)
public class InnerFragment extends Fragment {
    @BindView(R.id.counter_text_view)
    TextView mCounterTextView;

    @BindView(R.id.forward_button)
    Button mForwardButton;

    @BindView(R.id.forward_flow_button)
    Button mForwardFlowButton;

    private Unbinder mButterKnifeUnbinder;

    private Navigator mNavigator = SampleApplication.getNavigator();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButterKnifeUnbinder = ButterKnife.bind(this, view);

        InnerScreen screen = SampleApplication.getScreenResolver().getScreen(this);
        int counter = screen.getCounter();
        mCounterTextView.setText(getString(R.string.counter_template, counter));
        mForwardButton.setOnClickListener(v -> mNavigator.goForward(new InnerScreen(counter + 1)));

        mForwardFlowButton.setOnClickListener(view1 -> mNavigator.goForward(new TestFlow2Screen()));
    }

    @Override
    public void onDestroyView() {
        mButterKnifeUnbinder.unbind();
        super.onDestroyView();
    }
}
