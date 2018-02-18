package ru.doktorov.testapp.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import ru.doktorov.testapp.main.searchbook.SearchBookFragment;
import ru.doktorov.testapp.R;
import ru.doktorov.testapp.main.favoritebook.FavoriteBookFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout, SearchBookFragment.newInstance())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_search:
                selectedFragment = SearchBookFragment.newInstance();
                break;
            case R.id.navigation_favorites:
                selectedFragment = FavoriteBookFragment.newInstance();
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();

        return true;
    };
}
