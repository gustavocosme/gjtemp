package prudential.pobmobilecustomerappandroid.utils.form

import prudential.pobmobilecustomerappandroid.utils.TypeDoc


object FormHelper {


    fun getDocType(value: String): TypeDoc {

        if (checkCPF(value)) {
            return TypeDoc.CPF;
        }

        if (checkCNPJ(
                value
            )
        ) {
            return TypeDoc.CNPJ;
        }

        return TypeDoc.NONE;

    }

    fun checkCNPJ(CNPJ: String): Boolean {
        if (CNPJ == "00000000000000" || CNPJ == "11111111111111" || CNPJ == "22222222222222" || CNPJ == "33333333333333" || CNPJ == "44444444444444" || CNPJ == "55555555555555" || CNPJ == "66666666666666" || CNPJ == "77777777777777" || CNPJ == "88888888888888" || CNPJ == "99999999999999") {
            return false
        }
        val dig13: Char
        val dig14: Char
        var sm: Int
        var i: Int
        var r: Int
        var num: Int
        var peso: Int
        return try {
            sm = 0
            peso = 2
            i = 11
            while (i >= 0) {
                num = (CNPJ[i].toInt() - 48)
                sm = sm + num * peso
                peso = peso + 1
                if (peso == 10) peso = 2
                i--
            }
            r = sm % 11
            dig13 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
            sm = 0
            peso = 2
            i = 12
            while (i >= 0) {
                num = (CNPJ[i].toInt() - 48)
                sm = sm + num * peso
                peso = peso + 1
                if (peso == 10) peso = 2
                i--
            }
            r = sm % 11
            dig14 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
            if (dig13 == CNPJ[12] && dig14 == CNPJ[13]) true else false
        } catch (erro: Exception) {
            false
        }
    }

    fun checkCPF(CPF: String): Boolean {
        if (CPF == "00000000000" || CPF == "11111111111" || CPF == "22222222222" || CPF == "33333333333" || CPF == "44444444444" || CPF == "55555555555" || CPF == "66666666666" || CPF == "77777777777" || CPF == "88888888888" || CPF == "99999999999") {
            return false
        }
        val dig10: Char
        val dig11: Char
        var sm: Int
        var i: Int
        var r: Int
        var num: Int
        var peso: Int
        return try {
            sm = 0
            peso = 10
            i = 0
            while (i < 9) {
                num = (CPF[i].toInt() - 48)
                sm = sm + num * peso
                peso = peso - 1
                i++
            }
            r = 11 - sm % 11
            dig10 = if (r == 10 || r == 11) '0' else (r + 48).toChar()
            sm = 0
            peso = 11
            i = 0
            while (i < 10) {
                num = (CPF[i].toInt() - 48)
                sm = sm + num * peso
                peso = peso - 1
                i++
            }
            r = 11 - sm % 11
            dig11 = if (r == 10 || r == 11) '0' else (r + 48).toChar()
            if (dig10 == CPF[9] && dig11 == CPF[10]) true else false
        } catch (erro: Exception) {
            false
        }
    }


}