package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.SimplePublisher
import org.reactivestreams.Processor
import org.reactivestreams.Publisher
import org.reactivestreams.Subscription

class SharedProcessor<T>(private val parentPublisher: Publisher<T>) : SimplePublisher<T>(), Processor<T, T> {

    private val cancellableManagerProvider = CancellableManagerProvider()

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        parentPublisher.subscribe(this)
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        cancellableManagerProvider.cancel()
    }

    override fun onSubscribe(s: Subscription) = with(cancellableManagerProvider.cancelPreviousAndCreate()) { add { s.cancel() } }

    override fun onNext(t: T) = let { value = t }

    override fun onError(t: Throwable) = let { error = t }

    override fun onComplete() = complete()
}
