
import java.math.BigInteger;

public class NumerosExtensos {

    private static BigInteger MAX_VALUE = BigInteger.valueOf(10).pow(45).subtract(BigInteger.ONE);

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

    public static String paraExtenso(int numero) {
        return paraExtenso(BigInteger.valueOf(numero));
    }

    public static String paraExtenso(byte numero) {
        return paraExtenso(BigInteger.valueOf(numero));
    }

    public static String paraExtenso(short numero) {
        return paraExtenso(BigInteger.valueOf(numero));
    }

    public static String paraExtenso(long numero) {
        return paraExtenso(BigInteger.valueOf(numero));
    }

    public static String paraExtenso(BigInteger numero) {
        String extenso = "";
        if (numero.equals(BigInteger.ZERO)) {
            return "zero";
        }
        if (numero.compareTo(BigInteger.ZERO) < 0) {
            numero = numero.abs();
            extenso += "menos ";
        }
        if (numero.compareTo(MAX_VALUE) > 0) {
            return "Número muito grande";
        }
        if (numero.compareTo(BigInteger.valueOf(1000)) < 0) {
            if (numero.compareTo(BigInteger.valueOf(100)) <= 0) {
                if (numero.compareTo(BigInteger.valueOf(20)) < 0) {
                    return unidades[numero.subtract(BigInteger.ONE).intValue()];
                } else {
                    int dezena = numero.divide(BigInteger.valueOf(10)).subtract(BigInteger.ONE).intValue();
                    int resto = numero.mod(BigInteger.valueOf(10)).intValue();
                    extenso += dezenas[dezena - 1];
                    if (resto > 0) {
                        extenso += " e " + unidades[resto - 1];
                    }
                    return extenso;
                }
            } else {
                int centena = numero.divide(BigInteger.valueOf(100)).intValue();
                BigInteger resto = numero.mod(BigInteger.valueOf(100));
                extenso += centenas[centena - 1];
                if (resto.compareTo(BigInteger.ZERO) > 0) {
                    extenso += " e " + paraExtenso(resto);
                }
                return extenso;
            }
        } else {
            int c = (numero.toString().length() - 1) / 3;
            BigInteger maxc = BigInteger.valueOf(10).pow(c * 3);
            BigInteger qtd_cardinais = numero.divide(maxc);
            BigInteger resto = numero.mod(maxc);
            if (qtd_cardinais.compareTo(BigInteger.ONE) > 0 || c > 1) {
                extenso += paraExtenso(qtd_cardinais) + " ";
            }
            extenso += qtd_cardinais.compareTo(BigInteger.ONE) > 0 ? cardinais_plural[c - 1] : cardinais[c - 1];
            if (resto.compareTo(BigInteger.ZERO) > 0) {
                extenso += " e " + paraExtenso(resto);
            }
            return extenso;
        }
    }

    public static void main(String[] args) {
        System.out.println(paraExtenso(MAX_VALUE.negate().subtract(BigInteger.ONE)));
    }
}
