package ru.netology.nmedia.dagger

import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ModuleForSingleton {

    @Singleton
    @Provides
    fun getGoogleApiAvailability() = GoogleApiAvailability.getInstance()

    @Singleton
    @Provides
    fun getFirebaseInstallations() = FirebaseInstallations.getInstance()

    @Singleton
    @Provides
    fun getFirebaseMessaging() = FirebaseMessaging.getInstance()
}
