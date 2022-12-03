package com.hrappv.test

import com.hrappv.data.di.module.MyModule
import it.cosenonjaviste.daggermock.DaggerMockRule

class MyDaggerMockRule : DaggerMockRule<TestComponent>(
    TestComponent::class.java,
    MyModule()
    // TODO : Add your modules here
) {
    init {
        customizeBuilder<DaggerTestComponent.Builder> {
            it
        }
    }
}