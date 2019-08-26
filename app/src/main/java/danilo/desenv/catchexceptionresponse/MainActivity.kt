package danilo.desenv.catchexceptionresponse

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import danilo.desenv.catchexceptionresponse.api.APIClient
import danilo.desenv.catchexceptionresponse.api.MyApi
import danilo.desenv.catchresponseexception.exceptions.*
import danilo.desenv.catchresponseexception.managerresponse.ManagerResponseCustom
import danilo.desenv.catchresponseexception.managerresponse.ManagerResponseCustomImp
import danilo.desenv.catchresponseexception.managerresponse.byCustomException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val myApi = APIClient.getAPIService().create(MyApi::class.java)
    val managerResponseCustom: ManagerResponseCustom =
        ManagerResponseCustomImp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonExecute.setOnClickListener {
            myApi.requestBack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .byCustomException()
                .subscribe({
                    Log.d("Retorno Api", "Sucesso $it")
                }, {
                    when (it) {
                        is NetworkException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is UnauthorizedErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is UnprocessableEntityErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is ForbiddenErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is FailedDependencyErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        is GenericErrorException -> {
                            Log.d("Retorno Api", it.toString())
                        }
                        else -> Log.d("Retorno Api", it.toString())

                    }
                })
        }
    }

}

