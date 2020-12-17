package prudential.pobmobilecustomerappandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import prudential.pobmobilecustomerappandroid.extensions.isNo3Repeat
import prudential.pobmobilecustomerappandroid.extensions.isValidationCpfCnpj
import prudential.pobmobilecustomerappandroid.extensions.isValidationPasswordPrudential
import prudential.pobmobilecustomerappandroid.model.User
import prudential.pobmobilecustomerappandroid.network.RetrofitService
import prudential.pobmobilecustomerappandroid.network.api.UserService
import retrofit2.Call
import retrofit2.Response


@RunWith(JUnit4::class)
class AutenticationUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule();


    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)


    }


    @Test
    @Throws(Exception::class)
    fun testLoginRequest(){


        instantTaskExecutorRule.run {

            val userService: UserService = RetrofitService.createService()
            val call: Call<User> = userService.login("078.612.534-90","aaaaaaaa1#");
            val response: Response<User> = call.execute()
            Assert.assertEquals(response.isSuccessful,true);

        }
    }

    @Test
    fun testIsCpfOk(){

        Assert.assertEquals("078.612.534-90".isValidationCpfCnpj(),true);

    }

    @Test
    fun testIsCpfErroNumber(){

        Assert.assertEquals("078.612.534-93".isValidationCpfCnpj(),true);

    }

    @Test
    fun testIsCpfErroIncomplete(){

        Assert.assertEquals("078.612.534-".isValidationCpfCnpj(),true);

    }

    @Test
    fun testIsCNPJOk(){

        Assert.assertEquals("18.303.933/0001-13".isValidationCpfCnpj(),true);

    }

    @Test
    fun testIsCNPJErro(){

        Assert.assertEquals("18.303.933/1231-13".isValidationCpfCnpj(),true);

    }

    @Test
    fun testIsCNPJIncomplete(){

        Assert.assertEquals("18.303.933/12".isValidationCpfCnpj(),true);

    }

    @Test
    fun testIsPasswordPrudentialOK(){

        Assert.assertEquals("aaaaaaaa1#".isValidationPasswordPrudential(),true);

    }

    @Test
    fun testIsPasswordPrudentialErro(){

        Assert.assertEquals("aaaaaaaaB#".isValidationPasswordPrudential(),true);

    }

    @Test
    fun testIsStringNoRepeat3PrudentialERRO(){

        Assert.assertEquals("1s3".isNo3Repeat(),false);

    }


    @Test
    fun testIsStringNoRepeat3PrudentialOk(){

        Assert.assertEquals("asdas111dfsdfb123".isNo3Repeat(),true);

    }

}