
import java.math.BigInteger;
import java.text.Normalizer;
import java.util.Scanner;

public class NumerosExtensos {

    private static BigInteger MAX_VALUE = BigInteger.valueOf(10).pow(45).subtract(BigInteger.ONE);

    private final static String[] unidades = {
        "zero", "um", "dois", "tres", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"
    };
    private final static String[] dezenas = {
        "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa", "cem"
    };
    private final static String[] centenas = {
        "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"
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
            return unidades[0];
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
                    return extenso + unidades[numero.intValue()];
                } else {
                    int dezena = numero.divide(BigInteger.valueOf(10)).subtract(BigInteger.ONE).intValue();
                    int resto = numero.mod(BigInteger.valueOf(10)).intValue();
                    extenso += dezenas[dezena - 1];
                    if (resto > 0) {
                        extenso += " e " + unidades[resto];
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

    public static BigInteger paraCardinal(String extenso) {
        boolean cardinal = false;
        BigInteger multiplicador = BigInteger.ONE;
        BigInteger numero = BigInteger.ZERO;
        String[] extensos = extenso.toLowerCase().replace(" e ", " ").split(" ");
        for (int i = extensos.length - 1; i >= 0; i--) {
            String unidade = extensos[i];
            int indice;
            if ((indice = obterIndice(unidades, unidade)) != -1) {
                numero = numero.add(BigInteger.valueOf(indice).multiply(multiplicador));
                cardinal = false;
            } else if ((indice = obterIndice(dezenas, unidade)) != -1) {
                numero = numero.add(BigInteger.valueOf(10 * (indice + 2)).multiply(multiplicador));
                cardinal = false;
            } else if ((indice = obterIndice(centenas, unidade)) != -1) {
                numero = numero.add(BigInteger.valueOf(100 * (indice + 1)).multiply(multiplicador));
                cardinal = false;
            } else if (!((indice = obterIndice(cardinais, unidade)) == -1 && (indice = obterIndice(cardinais_plural, unidade)) == -1)) {
                if (cardinal) {
                    numero = numero.add(multiplicador);
                }
                multiplicador = BigInteger.valueOf(10).pow((indice + 1) * 3);
                cardinal = true;
            } else if (unidade.equals("menos")) {
                numero = numero.negate();
            } else {
                System.err.println("Erro! palavra não esperada: '" + unidade + "'.");
                return null;
            }
        }
        if (cardinal) {
            numero = numero.add(multiplicador);
        }
        if (compararBaixoNivel(extenso, paraExtenso(numero))) {
            return numero;
        } else {
            System.err.println("Sintaxe incorreta!");
            return null;
        }
    }

    private static int obterIndice(String[] vetor, String valor) {
        for (int i = 0; i < vetor.length; i++) {
            if (compararBaixoNivel(vetor[i], valor)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean compararBaixoNivel(String s1, String s2) {
        s1 = Normalizer.normalize(s1, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
        s2 = Normalizer.normalize(s2, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
        return s1.equals(s2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Deseja converter de cardinal para extenso(1)\nou de extenso para cardinal(2)?");
        int op = scanner.nextInt();
        scanner.nextLine();
        if (op == 1) {
            System.out.println(paraExtenso(scanner.nextInt()));
        } else if (op == 2) {
            System.out.println(paraCardinal(scanner.nextLine()));
        }
    }

}
