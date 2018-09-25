package com.royvanrijn.graal;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { MainModule.class })
public interface MainComponent {

    void inject(Main main);
}
