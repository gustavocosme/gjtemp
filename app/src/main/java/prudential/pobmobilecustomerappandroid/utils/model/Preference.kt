package prudential.pobmobilecustomerappandroid.utils.model

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import java.util.concurrent.ExecutionException

class Preference(var context: Context) {
    var TASK: AsyncTaskCustom
    var sharedPreferences: SharedPreferences
    val sharedPreference: SharedPreferences
        get() = context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )

    /**
     * @param value
     * VER SER EXISTE A REFERENCIA NO SHARED PREFERENCE
     * @return
     */
    fun isValidationPreferences(value: String?): Boolean = try {
        val load = sharedPreferences.getString(
            value,
            "defaultStringIfNothingFound"
        )
        if (load == "defaultStringIfNothingFound") false else true
    } catch (e: Exception) {
        false
    }

    /**
     * @param list
     * @param id
     * @return
     *
     * VER SE EXISTE UM ITEM NA LISTA _ USADO EM FAVORITOS
     */
    fun isValidationSimpleList(list: LinkedList<String?>, id: String?): Boolean =
        if (list.contains(id)) true else false


    /**
     * @param idName
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     *
     * RETORNA UMA A STRING EM SHPR SE TIVER - SE NÃO "";
     */
    @Throws(InterruptedException::class, ExecutionException::class)
    fun getStringPreferences(idName: String?): String {
        TASK = AsyncTaskCustom()
        TASK.call = object : AsyncTaskCustom.Call {
            override fun onRun(): Any = try {
                val load = sharedPreferences.getString(
                    idName,
                    "defaultStringIfNothingFound"
                )
                if (load == "defaultStringIfNothingFound") "" else load!!
            } catch (e: Exception) {
                ""
            }

            override fun onComplete(result: Any?) {}
        }
        return TASK.execute().get().toString()
    }

    /**
     * @param idName
     * @param value
     * @return
     *
     * ADICIONA UMA STRING EM SHPR - TRUE DE CADASTRAR - FALSE SE NÃO
     */
    fun addCommitPreferences(idName: String?, value: String?): Boolean = try {
        sharedPreferences.edit().putString(idName, value).commit()
        true
    } catch (e: Exception) {
        false
    }

    fun deleteCommitPreferences(idName: String?): Boolean = try {
        sharedPreferences.edit().remove(idName).commit()
        true
    } catch (e: Exception) {
        false
    }

    /**
     * @param value
     * @return
     *
     * RETORNA UMA LISTA SIMPLES - USADO EM FAVORITOS
     */
    fun getSimpleListPreferences(value: String?): LinkedList<String>? = try {
        val bookmarks_string = sharedPreferences.getString(value, "")
        LinkedList(
            Arrays.asList(
                *bookmarks_string!!.split(",".toRegex()).toTypedArray()
            )
        )
    } catch (e: Exception) {
        null
    }

    /**
     * @param idName
     * @param list
     * @param value
     *
     * ADICIONA UM ITEM EM UMA LISTA SIMPLES USADO EM FAVORITOS
     */
    fun addStringSimpleList(
        idName: String?, list: LinkedList<String?>,
        value: String?
    ) {
        list.add(value)
        val builder = StringBuilder()
        var i = 0
        for (bookmark in list) {
            if (i != 0) builder.append(",")
            builder.append(bookmark)
            i++
        }
        val result = builder.toString()
        sharedPreferences.edit().putString(idName, result).commit()
    }

    /**
     * @param idName
     * @param list
     * @param value
     *
     * REMOVE UM ITEM NA LISTA SIMPLES - USADO EM FAVORITOS
     */
    fun removeStringSimpleList(
        idName: String?, list: LinkedList<String?>,
        value: String?
    ) {
        list.add(value)
        val builder = StringBuilder()
        var i = 0
        for (bookmark in list) {
            if (i != 0) builder.append(",")
            builder.append(bookmark)
            i++
        }
        val result = builder.toString()
        sharedPreferences.edit().putString(idName, result).commit()
    }
    // #############################################################################//
    // JSON UTILS
    // #############################################################################//
    /**
     * @param jsonArrayList
     * @return
     *
     * Converte uma JsonArray.toString em um ArrayList<ModelData>
     *
     * try {
     *
     *
     *
     * Type listType = new TypeToken<List></List><MainData>>() { }.getType();
     * List<MainData> dados = (List<MainData>) model
     * .parseJsonArrayToClass(JSON.getJSONArray("events") .toString(),
     * listType); Log.e("Dados: ", dados.get(0).getTitle());
     *
     * } catch (JSONException e) {
     *
     * Log.e("ERRO", e.getMessage());
     *
     * }
    </MainData></MainData></MainData></ModelData> */
    /**
     * @param value
     * @param call
     *
     * SPEED LOOP
     *
     * JSON = (JSONObject) value;
     *
     * try{
     *
     * Type listType = new TypeToken<List></List><MainData>>() {}.getType();
     * String ArrayListString =
     * JSON.getJSONArray("events").toString(); List<MainData> dados =
     * model.parseJsonArrayToClass(ArrayListString, listType);
     * Log.e("Dados Size: ", String.valueOf(dados.size()));
     *
     * model.SearchList(dados,new Model.CallLoop() {
     * @Override public void run(Object value) {
     *
     * MainData data = (MainData) value;
     * Log.e("Run Title: ",data.getTitle());
     *
     * }
     * @Override public void onComplete() {
     *
     *
     * Log.e("onCompleteRUN: ","on Complete Run");
     *
     *
     * } });
     *
     * } catch (JSONException e) {
     *
     * Log.e("ERRO", e.getMessage());
     *
     * }
    </MainData></MainData> */
    fun <T> SearchList(value: List<T>, call: CallLoop) {
        val i: Iterator<*> = value.iterator()
        while (i.hasNext()) {
            call.run(i.next())
        }
        call.onComplete()
    }
    // #############################################################################//
    // #IMAGES
    // #############################################################################//
    // #############################################################################//
    // GOOGLE ANALYTICS
    // #############################################################################//
    // #############################################################################//
    // #CALL
    // #############################################################################//
    /**
     * @author gustavocosme
     */
    interface CallModel {
        /**
         * @param value
         * SE CARREGAR CORRETAMENTE
         */
        fun onCompleteOnline(value: Any?)

        /**
         * @param value
         * SE CARREGOU CORRETAMENTE E TIVER OFFLINE
         */
        fun onCompleteOffline(value: Any?)

        /**
         * ERRO NO CARREGAMENTO
         */
        fun onErro(value: String?)
    }

    interface CallLoop {
        fun run(value: Any?)
        fun onComplete()
    }

    companion object {
        var TAG = "Model"
    }

    init {
        TASK = AsyncTaskCustom()
        sharedPreferences = sharedPreference
    }
}
