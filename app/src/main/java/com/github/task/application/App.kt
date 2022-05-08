package com.github.task.application

import android.app.Application
import com.github.task.auxiliary.SchedulersProvider
import com.github.task.net.repo.GitHubRepo
import com.github.task.net.service.GitHubService
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.rx.RealmObservableFactory
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var kodeinStored: DI

class App : Application() {

    companion object {
        val di: DI
            get() = kodeinStored
    }

    private val baseModule = DI.Module("baseModule") {
        import(databaseModule)
        import(auxiliaryModule)
        import(netModule)
    }

    private val databaseModule = DI.Module("databaseModule") {

        bind<Realm>() with singleton {
            Realm.init(this@App)

            Realm.getInstance(
                RealmConfiguration.Builder()
                    .rxFactory(RealmObservableFactory(false))
                    .deleteRealmIfMigrationNeeded()
                    .build()
            )
        }
    }

    private val netModule = DI.Module("netModule") {
        constant("apiUrl") with "https://api.github.com"

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl(instance<String>("apiUrl"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }

        bind<GitHubService>() with singleton {
            instance<Retrofit>().create(GitHubService::class.java)
        }

        bind<GitHubRepo>() with singleton {
            GitHubRepo(instance())
        }
    }

    private val auxiliaryModule = DI.Module("auxiliaryModule") {

        bind<CompositeDisposable>() with provider { CompositeDisposable() }

        bind<SchedulersProvider>() with singleton { SchedulersProvider() }
    }

    override fun onCreate() {
        if (::kodeinStored.isInitialized.not()) {
            kodeinStored = DI {
                import(baseModule)
            }
        }

        super.onCreate()
    }

}