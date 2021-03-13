package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.RecyclerViewMatchers.checkRecyclerViewItem
import com.picpay.desafio.android.di.retrofitModuleTest
import com.picpay.desafio.android.view.users.UsersActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import java.lang.Thread.sleep


class UsersActivityTest {

    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        server.start()
        loadKoinModules(retrofitModuleTest(server.url("").toString()))
    }

    @Test
    fun shouldDisplayLoading() {
        launchActivity<UsersActivity>().apply {
            onView(withId(R.id.user_list_progress_bar)).check(matches(isDisplayed()))
        }
    }


    @Test
    fun shouldDisplayTitle() {
        launchActivity<UsersActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItemOnSuccess() {
        server.enqueue(successResponse)

        launchActivity<UsersActivity>().apply {
            sleep(3000)
            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
            checkRecyclerViewItem(R.id.recyclerView, 0, withText("@eduardo.santos"))
            checkRecyclerViewItem(R.id.recyclerView, 0, withId(R.id.username))
        }
    }

    @Test
    fun shouldDisplayToastOnError() {
        server.enqueue(errorResponse)

        val errorMessage = context.getString(R.string.error)

        launchActivity<UsersActivity>().apply {
            onView(withText(errorMessage)).inRoot(ToastMatchers())
                .check(matches(isDisplayed()))
        }
    }



    companion object {
        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}