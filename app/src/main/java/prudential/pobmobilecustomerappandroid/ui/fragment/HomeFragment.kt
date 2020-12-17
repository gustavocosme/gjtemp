package prudential.pobmobilecustomerappandroid.ui.fragment


import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import gustavocosme.ui.BaseFragment
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.*
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.MainActivity
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.activity.autentication.LoginActivity
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModelFactory
import prudential.pobmobilecustomerappandroid.utils.Dialogs
import prudential.pobmobilecustomerappandroid.utils.TypeExtractDialogSheet

class HomeFragment : BaseFragment() {

//    val db by lazy { AppDBSingleton.inicialize(MainActivity.INSTANCE) }

    private val userViewModel by viewModels<UserViewModel> {
        UserViewModelFactory(
            requireActivity().application
        )
    }

    init {

        setLayout(R.layout.fragment_home);
    }

    override fun init() {

        onLogoutTest();

        cachedView!!.findViewById<Button>(R.id.dialogTextbtn).setOnClickListener {

            Tab.INSTANCE.alertShowText("Cobertura suspensa","Ops, como a cobertura do seu seguro foi suspensa, você não pode emitir boletos. Procure seu Life Planner para resolver logo essa questão!")

        }

        cachedView!!.findViewById<Button>(R.id.dialogTextbtnCopy).setOnClickListener {


            Tab.INSTANCE.alertShowExtractCopy(
                "Reemissão de cobrança",
                "Cobrança débito em conta Santander 1232",
                "Reemissão de cobrança efetuada com sucesso! ",
                "16/11/2020 21:30",
                "13377987",
                "Se você tiver uma ou mais cobranças em aberto todas serão agendadas para pagamento nos próximos seis dias úteis.",
                true)
        }


        cachedView!!.findViewById<Button>(R.id.dialogTextbtnTools).setOnClickListener {

            Tab.INSTANCE.alertCustomTips("Toda cobrança tem prazo mínimo de dois dias úteis para baixar após o efetivo pagamento.",cachedView!!.findViewById<Button>(R.id.dialogTextbtnTools));

        }

        cachedView!!.findViewById<Button>(R.id.dialogTextbtnFilter).setOnClickListener {

            Tab.INSTANCE.onOpenFilterExtract {

                Tab.INSTANCE.alertShowText("Tab","Count circle button ${it.count} - Tudo posso naquele que me fortalece.\n" +
                        "Filipenses 4:13");

            }

        }


        cachedView!!.findViewById<Button>(R.id.sheet1).setOnClickListener {

            Tab.INSTANCE.openExtratSheetDialog(TypeExtractDialogSheet.NORMAL,null);


        }

        cachedView!!.findViewById<Button>(R.id.sheet2).setOnClickListener {

            Tab.INSTANCE.openExtratSheetDialog(TypeExtractDialogSheet.ATENCION,null);

        }

        cachedView!!.findViewById<Button>(R.id.sheet3).setOnClickListener {

            Tab.INSTANCE.openExtratSheetDialog(TypeExtractDialogSheet.ASSISTENT,null);


        }

        cachedView!!.findViewById<Button>(R.id.list1).setOnClickListener {

            Tab.INSTANCE.listMenuPagment(object : Dialogs.CALL {

                override fun call(value: String?, p: Int) {


                }

            });

        }

        cachedView!!.findViewById<Button>(R.id.list2).setOnClickListener {

            Tab.INSTANCE.listMenuPagmentCard(object : Dialogs.CALL {

                override fun call(value: String?, p: Int) {

                    if(p == 2)
                    {
                        Tab.INSTANCE.copyString("12312314")
                    }

                }

            });

        }

        cachedView!!.findViewById<Button>(R.id.list3).setOnClickListener {


            Tab.INSTANCE.listMenuPagmentBilletCopy(object : Dialogs.CALL {

                override fun call(value: String?, p: Int) {

                    if(p == 1)
                    {
                        Tab.INSTANCE.copyString("12312314")
                    }


                }

            });

        }




    }

    private fun onLogoutTest() {

        cachedView!!.findViewById<Button>(R.id.fragment_user_btn_logout).setOnClickListener {

            Dialogs.addYesNo(
                MainActivity.INSTANCE,
                getString(R.string.logout),
                object : Dialogs.CALL {

                    override fun call(value: String?, position: Int) {

                        if (position == 1) {
                            userViewModel.logout.observe(this@HomeFragment, Observer {
                                if (it is Resource.Success) {
                                    activity?.startActivity<LoginActivity>()
                                    activity?.finish();
                                }
                            })
//                            db.onLogout();
                        }
                    }

                });

        }



    }

    companion object {

        fun newInstance(instance: Int): HomeFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                HomeFragment()
            fragment.setArguments(args)
            return fragment
        }

    }


}//END CLASS
