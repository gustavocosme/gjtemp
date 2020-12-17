package prudential.pobmobilecustomerappandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import prudential.pobmobilecustomerappandroid.extensions.dateValidate
import prudential.pobmobilecustomerappandroid.extensions.isNo3Repeat
import prudential.pobmobilecustomerappandroid.extensions.isValidationCpfCnpj
import prudential.pobmobilecustomerappandroid.extensions.isValidationPasswordPrudential
import prudential.pobmobilecustomerappandroid.model.User
import prudential.pobmobilecustomerappandroid.network.RetrofitService
import prudential.pobmobilecustomerappandroid.network.api.UserService
import retrofit2.Call
import retrofit2.Response


@RunWith(JUnit4::class)
class ExtractUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule();


    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)


    }

    @Test
    fun `validate date`() {
        Assert.assertTrue("12/01/2012".dateValidate())
        Assert.assertTrue("01/12/2012".dateValidate())
        Assert.assertFalse("13/12/2012".dateValidate())
        Assert.assertFalse("13/01/2012".dateValidate())
        Assert.assertFalse("12/32/2012".dateValidate())
        Assert.assertFalse("14/01/2012".dateValidate())
        Assert.assertFalse("15/43/2012".dateValidate())
        Assert.assertTrue("02/28/2012".dateValidate())
        Assert.assertTrue("02/29/2012".dateValidate())
        Assert.assertFalse("02/29/2013".dateValidate())
        Assert.assertFalse("02/29/2015".dateValidate())
        Assert.assertTrue("02/29/2016".dateValidate())
        Assert.assertFalse("02/30/2012".dateValidate())
        Assert.assertTrue("02/29/2004".dateValidate())
        Assert.assertFalse("02/31/2004".dateValidate())
        Assert.assertFalse("14/30/2012".dateValidate())
        Assert.assertFalse("01/32/2012".dateValidate())
        Assert.assertFalse("13/03/2012".dateValidate())
        Assert.assertTrue("10/12/2000".dateValidate())
    }




}