
public class NumerosExtensos {

    private final static String[] unidades = {
        "um", "dois", "tres", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"
    };
    private final static String[] dezenas = {
        "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa", "cem"
    };
    private final static String[] centenas = {
        "cento", "duzentos", "trezentos", "quatrocentos", "cincocentos", "seiscentos", "setecentos", "oitocentos", "novecentos"
    };
    private final static String[] cardinais = {
        "mil", "milhão", "bilhão", "trilhão", "quatrilhão", "quintilhão", "sextilhão", "septilhão", "octilhão", "nonilhão", "decilhão", "unidecilhão", "duodecilhão", "tredecilhão"
    };
    private final static String[] cardinais_plural = {
        "mil", "milhões", "bilhões", "trilhões", "quatrilhões", "quintilhões", "sextilhões", "septilhões", "octilhões", "nonilhões", "decilhões", "unidecilhões", "duodecilhões", "tredecilhões"
    };

    public static String paraExtenso(long numero) {
        String extenso = "";
        if (numero == 0) {
            return "zero";
        }
        if (numero < 0) {
            numero = -numero;
            extenso += "menos ";
        }
        if (numero < 1000) {
            if (numero <= 100) {
                if (numero < 20) {
                    return unidades[(int) numero - 1];
                } else {
                    int dezena = (int) numero / 10 - 1;
                    int resto = (int) numero % 10;
                    extenso += dezenas[dezena - 1];
                    if (resto > 0) {
                        extenso += " e " + unidades[resto - 1];
                    }
                    return extenso;
                }
            } else {
                int centena = (int) numero / 100;
                int resto = (int) numero % 100;
                extenso += centenas[centena - 1];
                if (resto > 0) {
                    extenso += " e " + paraExtenso(resto);
                }
                return extenso;
            }
        } else {
            int c = (String.valueOf(numero).length() - 1) / 3;
            long maxc = (long) Math.pow(10, c * 3);
            int qtd_cardinais = (int) (numero / maxc);
            int resto = (int) (numero % maxc);
            if (qtd_cardinais > 1 || c > 1) {
                extenso += paraExtenso(qtd_cardinais) + " ";
            }
            extenso += qtd_cardinais > 1 ? cardinais_plural[c - 1] : cardinais[c - 1];
            if (resto > 0) {
                extenso += " e " + paraExtenso(resto);
            }
            return extenso;
        }
    }

}
