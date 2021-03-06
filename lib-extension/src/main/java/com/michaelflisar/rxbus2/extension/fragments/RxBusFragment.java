package com.michaelflisar.rxbus2.extension.fragments;

import com.michaelflisar.rxbus2.interfaces.IRxBusQueue;
import com.michaelflisar.rxbus2.rx.RxDisposableManager;

import org.reactivestreams.Publisher;

import androidx.fragment.app.Fragment;
import io.reactivex.processors.BehaviorProcessor;

public class RxBusFragment extends Fragment implements IRxBusQueue {

    private final BehaviorProcessor<Boolean> resumeStateProcessor = BehaviorProcessor.createDefault(false);

    @Override
    public void onResume() {
        super.onResume();
        resumeStateProcessor.onNext(true);
    }

    @Override
    public void onPause() {
        resumeStateProcessor.onNext(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        RxDisposableManager.unsubscribe(this);
        super.onDestroy();
    }

    // --------------
    // RXBus
    // --------------

    @Override
    public boolean isBusResumed() {
        return resumeStateProcessor.getValue();
    }

    @Override
    public Publisher<Boolean> getResumeObservable() {
        return resumeStateProcessor;
    }
}
