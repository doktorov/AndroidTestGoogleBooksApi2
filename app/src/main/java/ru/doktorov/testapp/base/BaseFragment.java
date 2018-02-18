package ru.doktorov.testapp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment extends DaggerFragment {
    private Unbinder mUnBinder;

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mUnBinder.unbind();
        mCompositeDisposable.dispose();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }
}
