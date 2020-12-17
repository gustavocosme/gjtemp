package prudential.pobmobilecustomerappandroid.ui.viewCustom.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.DataConfirmationUpdate
import prudential.pobmobilecustomerappandroid.model.RemovebleTextWatch
import prudential.pobmobilecustomerappandroid.model.SimpleCheckItem
import prudential.pobmobilecustomerappandroid.model.User
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.SimpleSelectAdapter
import prudential.pobmobilecustomerappandroid.ui.adapter.SimpleSelectCheckAdapter
import prudential.pobmobilecustomerappandroid.ui.fragment.ProfileUpdateConfirmationFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.TokenFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.UserConfirmationUpdateFragment
import prudential.pobmobilecustomerappandroid.ui.viewCustom.CustomUploadFile
import prudential.pobmobilecustomerappandroid.utils.form.Mask


/**
 * Class build to Extract Profile.
 */
class ProfileCustomView(context: Context) : RelativeLayout(context),
    OnRecyclerClickListener<String>, OnRecyclerClickListener.ChckeckListener<SimpleCheckItem> {

    lateinit var cachedView: View;

    private val matrialStatusAdapter by lazy { SimpleSelectAdapter(this) }
    private val nationalityAdapter by lazy { SimpleSelectCheckAdapter(this) }
    private val taxAddressAdapter by lazy { SimpleSelectAdapter(this) }

    private lateinit var recyclerViewMatrialStatus: RecyclerView
    private lateinit var recyclerViewNationality: RecyclerView
    private lateinit var recyclerViewTaxAddress: RecyclerView

    private lateinit var matrialStatus: TextInputEditText
    private lateinit var nationalityInput: TextInputEditText
    private lateinit var taxAddressInput: TextInputEditText
    private lateinit var nameInput: TextInputEditText
    private lateinit var rgInput: TextInputEditText
    private lateinit var rgDateInput: TextInputEditText
    private lateinit var rgOrgInput: TextInputEditText

    private lateinit var matrialStatusInclude: View
    private lateinit var nationalityInclude: View
    private lateinit var taxAddressInclude: View

    private lateinit var textInputClicked: TextInputEditText

    private lateinit var uploadFileNameButton: CustomUploadFile
    private lateinit var confirmButton: Button

    private val userData = User(
        name = "Maura Ferreira da Costa",
        matrialStatus = "Casado(a)",
        nationality = "Brasileiro(a)",
        rg = "288377479",
        rgData = "28112011",
        rgOrg = "Detran - Departamento de Trânsito do Estado",
        cpf = "111.111.111.11",
        taxAddress = "Estados Unidos"
    )

    private val dataConfirmationUpdateArray = arrayListOf<DataConfirmationUpdate>()
    private val removebleTextWatch = mutableListOf<RemovebleTextWatch>()

    /**
     * Default init method.
     */
    init {
        inflate();
    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.profile_update_profile, this, true)

        inicialize()
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {
        initInputs()
        initRecyclerViews()
        initButtons()
        initInputClick()
    }

    /**
     * Init all recycler views.
     */
    private fun initRecyclerViews() {
        initMatrialStatusRecycler()
        initNationalityRecycler()
        initTaxAddressRecycler()
    }

    /**
     * Init click listeners.
     */
    private fun initInputClick() {
        matrialStatusClick()
        nationalityClick()
        taxAddressClick()
        confirmButtonClick()
    }

    /**
     * Init input edit texts.
     */
    private fun initInputs() {
        matrialStatusInit()
        nationalityInit()
        taxAddressInit()
        nameInit()
        rgInit()
        rgDateInit()
        rgOrgInit()
    }

    private fun initButtons() {
        uploadFileNameInit()
        confirmButtonInit()
    }


    /**
     * Init nationality input text.
     */
    private fun nationalityInit() {
        nationalityInclude =
            cachedView.findViewById<View>(R.id.profile_update_profile_nationality_include)
        recyclerViewNationality =
            nationalityInclude.findViewById(R.id.dropdown_list_select_recycler)

        nationalityInput = cachedView.findViewById(R.id.profile_update_profile_nationality_input)
        nationalityInput.setText(userData.nationality)
    }

    /**
     * Init matrial status input text.
     */
    private fun matrialStatusInit() {
        matrialStatusInclude =
            cachedView.findViewById<View>(R.id.profile_update_profile_matrial_status_include)
        recyclerViewMatrialStatus =
            matrialStatusInclude.findViewById(R.id.dropdown_list_select_recycler)
        matrialStatus = cachedView.findViewById(R.id.profile_update_profile_marital_status_input)
        matrialStatus.setText(userData.matrialStatus)
    }

    /**
     * Init tax address input text.
     */
    private fun taxAddressInit() {
        taxAddressInclude =
            cachedView.findViewById<View>(R.id.profile_update_profile_tax_address_include)
        recyclerViewTaxAddress =
            taxAddressInclude.findViewById(R.id.dropdown_list_select_recycler)
        taxAddressInput = cachedView.findViewById(R.id.profile_update_profile_tax_address_input)
        taxAddressInput.setText(userData.taxAddress)
    }

    /**
     * Init name input text.
     */
    private fun nameInit() {
        nameInput = cachedView.findViewById(R.id.profile_update_profile_name_input)
        nameInput.setText(userData.name)
        val watch = nameInput.addTextChangedListener {
            val hasChanged = userData.name != it.toString()
            uploadFileNameButton.showOrGone(hasChanged)
            val dataConfirmationUpdate =
                DataConfirmationUpdate(
                    label1 = "Nome",
                    hold = userData.name,
                    current = it.toString()
                )
            addFieldToModifiedList(dataConfirmationUpdate, hasChanged)
        }
        removebleTextWatch.add(RemovebleTextWatch(nameInput, watch))
    }

    /**
     * Init rg input text.
     */
    private fun rgInit() {
        rgInput = cachedView.findViewById(R.id.profile_update_profile_rg_input)
        rgMaskInit()
        rgInput.setText(userData.rg)
        val watch = rgInput.addTextChangedListener {
            val hasChanged = userData.rg != it.toString()
            showOrHideRgUploadFile(hasChanged)
            val dataConfirmationUpdate =
                DataConfirmationUpdate(label1 = "RG", hold = userData.rg, current = it.toString())
            addFieldToModifiedList(dataConfirmationUpdate, hasChanged)
        }
        removebleTextWatch.add(RemovebleTextWatch(rgInput, watch))
    }


    /**
     * Init rg date input text.
     */
    private fun rgDateInit() {
        rgDateInput = cachedView.findViewById(R.id.profile_update_profile_rg_date_input)
        rgDateMaskInit()
        rgDateInput.setText(userData.rgData)
        val watch = rgDateInput.addTextChangedListener {
            val hasChanged = userData.rgData != it.toString()
            showOrHideRgUploadFile(hasChanged)
            val dataConfirmationUpdate =
                DataConfirmationUpdate(
                    label1 = "Data de expedição",
                    hold = userData.rgData,
                    current = it.toString()
                )
            addFieldToModifiedList(dataConfirmationUpdate, hasChanged)
        }
        removebleTextWatch.add(RemovebleTextWatch(rgDateInput, watch))
    }

    private fun rgMaskInit() {
        val watch = Mask.insert("##.###.###-#", rgInput)
        rgInput.addTextChangedListener(watch)
        removebleTextWatch.add(RemovebleTextWatch(rgInput, watch))
    }

    private fun rgDateMaskInit() {
        val watch = Mask.insert("##/##/####", rgDateInput)
        rgDateInput.addTextChangedListener(watch)
        removebleTextWatch.add(RemovebleTextWatch(rgDateInput, watch))
    }

    /**
     * Init rg organization input text.
     */
    private fun rgOrgInit() {
        rgOrgInput = cachedView.findViewById(R.id.profile_update_profile_rg_org_input)
        rgOrgInput.setText(userData.rgOrg)
        val watch = rgOrgInput.addTextChangedListener {
            val hasChanged = userData.rgOrg != it.toString()
            showOrHideRgUploadFile(hasChanged)
            val dataConfirmationUpdate =
                DataConfirmationUpdate(
                    label1 = "Orgão expedidor",
                    hold = userData.rgOrg,
                    current = it.toString()
                )
            addFieldToModifiedList(dataConfirmationUpdate, hasChanged)
        }
        removebleTextWatch.add(RemovebleTextWatch(rgOrgInput, watch))
    }


    /**
     * Receive modified data and add to a modified list.
     * @param dataConfirmationUpdate Model to handle changes.
     * @param hasChanged True if field chanegd or not.
     * @param position Position of field on flow.
     */
    private fun addFieldToModifiedList(
        dataConfirmationUpdate: DataConfirmationUpdate,
        hasChanged: Boolean
    ) {
        showOrHideConfirmButton(hasChanged)
        try {
            val index =
                dataConfirmationUpdateArray.indexOfFirst { it.label1 == dataConfirmationUpdate.label1 }
            when {
                index != -1 -> when {
                    hasChanged -> dataConfirmationUpdateArray.set(index, dataConfirmationUpdate)
                    else -> dataConfirmationUpdateArray.removeAt(index)
                }
                index == -1 && hasChanged -> dataConfirmationUpdateArray.add(dataConfirmationUpdate)
            }

        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    /**
     * Handle file upload of qhen name field is modified.
     */
    private fun uploadFileNameInit() {
        uploadFileNameButton =
            cachedView.findViewById(R.id.profile_update_profile_name_upload)
    }

    /**
     * Init confirmation button view.
     */
    private fun confirmButtonInit() {
        confirmButton = cachedView.findViewById(R.id.profile_update_profile_confirm_button)
        showOrHideConfirmButton()
    }

    /**
     * Handle confirmation button visibility.
     */
    private fun showOrHideConfirmButton(show: Boolean? = null) {
        confirmButton.showOrGone(show ?: dataConfirmationUpdateArray.isNotEmpty())
    }

    /**
     * Handle rg upload component visibility.
     */
    private fun showOrHideRgUploadFile(show: Boolean) {
        val rgUploadFile =
            cachedView.findViewById<CustomUploadFile>(R.id.profile_update_profile_rg_orgupload)
        rgUploadFile.showOrGone(show)
    }


    private fun hideAllCustomUpload() {
        showOrHideRgUploadFile(false)
        uploadFileNameButton.showOrGone(false)
    }

    /**
     * Handle matrial status input field click.
     */
    private fun matrialStatusClick() {
        matrialStatus.setOnClickListener {
            showOnlyCurrentDropdown(matrialStatusInclude)
            textInputClicked = matrialStatus
        }
    }

    /**
     * Handle nationality input field click.
     */
    private fun nationalityClick() {
        nationalityInput.setOnClickListener {
            showOnlyCurrentDropdown(nationalityInclude)
            textInputClicked = nationalityInput
        }
    }

    /**
     * Handle tax address input field click.
     */
    private fun taxAddressClick() {
        taxAddressInput.setOnClickListener {
            showOnlyCurrentDropdown(taxAddressInclude)
            textInputClicked = taxAddressInput
        }
    }

    /**
     * Handle confirmation button click.
     */
    private fun confirmButtonClick() {
        confirmButton.setOnClickListener {
            hideAllCustomUpload()
            changeModelUserValues()

            if (dataConfirmationUpdateArray.any { it.label1 == "Residência fiscal" || it.label1 == "TIN" }) {
                Tab.INSTANCE.nav(
                    TokenFragment.newInstance(
                        instance = 0,
                        array = dataConfirmationUpdateArray,
                        isCheck1 = true,
                        isCheck2 = true,
                        isToken = true
                    )
                )
            } else {
                Tab.INSTANCE.nav(
                    UserConfirmationUpdateFragment.newInstance(
                        instance = 0,
                        array = dataConfirmationUpdateArray,
                        isCheck1 = true,
                        isCheck2 = false,
                        isToken = false
                    )
                )
            }
            ProfileUpdateConfirmationFragment.finishChange = {

            }
        }
    }

    private fun changeModelUserValues() {
        userData.apply {
            name = nameInput.text.toString()
            rg = rgInput.text.toString()
            rgData = rgDateInput.text.toString()
            rgOrg = rgOrgInput.text.toString()
        }
    }

    /**
     * Add mask to rg input field.
     */
    private fun addMaskInputsWatch() {
        rgMaskInit()
        rgDateMaskInit()
    }

    /**
     * Remove textwatchers from input fields.
     */
    private fun removeTextWatchs() {
        removebleTextWatch.forEach {
            it.inputEditText.removeTextChangedListener(it.textWatcher)
        }
    }

    /**
     * Init matrial status recycler view and mock data.
     */
    private fun initMatrialStatusRecycler() {
        recyclerViewMatrialStatus.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = matrialStatusAdapter
        }
        matrialStatusAdapter.submitList(
            mutableListOf(
                "Solteiro(a)",
                "Casado(a)",
                "Divorciado(a)",
                "Viúvo(a)"
            )
        )
    }

    /**
     * Init nationality recycler view and mock data.
     */
    private fun initNationalityRecycler() {
        recyclerViewNationality.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = nationalityAdapter
        }
        nationalityAdapter.submitList(
            mutableListOf(
                SimpleCheckItem(title = "Argentino(a)"),
                SimpleCheckItem(title = "Armênico(a)"),
                SimpleCheckItem(title = "Australiano(a)"),
                SimpleCheckItem(title = "Austríaco(a)"),
                SimpleCheckItem(title = "Azerbaijano(a)"),
                SimpleCheckItem(title = "Bengalês(a)"),
                SimpleCheckItem(title = "Barbadiano(a)"),
                SimpleCheckItem(title = "Bielo-russo(a)"),
                SimpleCheckItem(title = "Brasileiro(a)"),
                SimpleCheckItem(title = "Chline(a)"),
                SimpleCheckItem(title = "Norte-americano(a)")
            )
        )
    }

    /**
     * Init tax address recycler view and mock data.
     */
    private fun initTaxAddressRecycler() {
        recyclerViewTaxAddress.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taxAddressAdapter
        }
        taxAddressAdapter.submitList(
            mutableListOf(
                "Argentina",
                "Armênia",
                "Austrália",
                "Áustria",
                "Azerbaidjão",
                "Bangladesh",
                "Barbados",
                "Belarus",
                "Brasil",
                "Estados Unidos"
            )
        )
    }

    /**
     * Show only current dropdown and hide others.
     * @param dropdown Current dropdown to handle visibilty.
     * @param show If is not null set visibility.
     */
    private fun showOnlyCurrentDropdown(dropdown: View? = null, show: Boolean? = null) {
        matrialStatusInclude.showOrGone(show ?: matrialStatusInclude.showOrGoneDropdown(dropdown))
        nationalityInclude.showOrGone(show ?: nationalityInclude.showOrGoneDropdown(dropdown))
        taxAddressInclude.showOrGone(show ?: taxAddressInclude.showOrGoneDropdown(dropdown))

        showOrHideAmericanText()
    }


    /**
     * Show or hide text if nationality is american.
     */
    private fun showOrHideAmericanText() {
        val americanText =
            cachedView.findViewById<Group>(R.id.profile_update_profile_nationality_upload_group)
        americanText.showOrGone(nationalityAdapter.hasAmericanText() and !nationalityInclude.isVisible)
    }

    /**
     * Handle visibility of drop down.
     * @param dropdown Current dropdown.
     */
    private fun View.showOrGoneDropdown(dropdown: View?) = dropdown == this && !isVisible

    override fun onRecyclerItemClick(model: String, position: Int) {
        showOnlyCurrentDropdown(show = false)
        textInputClicked.setText(model)
        showOrGoneTin()
    }

    /**
     * Handle visibility of TIN field.
     */
    private fun showOrGoneTin() {
        val tinGroup = cachedView.findViewById<Group>(R.id.profile_update_profile_tin_group)
        tinGroup.showOrGone(!taxAddressInput.text.isNullOrEmpty())
    }

    /**
     * Handle itens checked on nationality.
     * @param item Model of checked item.
     * @param position Position of checked item.
     */
    override fun onChckedItem(item: SimpleCheckItem, position: Int) {
        nationalityAdapter.checkAtPosition(position)
        nationalityInput.setText("${userData.nationality};${nationalityAdapter.getCheckedItensTitle()}")
    }
}
