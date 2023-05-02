public class CentroDistribuicao {

    public enum SITUACAO {
        NORMAL,
        SOBRAVISO,
        EMERGENCIA
    }

    public enum TIPOPOSTO {
        COMUM,
        ESTRATEGICO
    }

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;

    private int tAditivo;
    private int tGasolina;
    private int tAlcool1;
    private int tAlcool2;
    private SITUACAO situacao;

    public CentroDistribuicao (int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
        try{
            if(tAditivo <= MAX_ADITIVO && tAditivo >= 0) this.tAditivo = tAditivo;
        } catch (Exception e) {
            System.out.println("O valor do ADITIVO está incorreto, ele deve estar entre 0 e 500!");
        }
        try {
            if(tGasolina <= MAX_GASOLINA && tGasolina >= 0) this.tGasolina = tGasolina;
        } catch (Exception e) {
            System.out.println("O valor da GASOLINA está incorreto, ele deve estar entre 0 e 10000!");
        }
        try {
            if(tAlcool1 != tAlcool2);
        } catch (Exception e) {
            System.out.println("O valor de ALCOOL 1 e ALCOOL 2 deve ser exatamente o mesmo!");
        }
        try {
            if(tAlcool1 <= (MAX_ALCOOL/2) && tAlcool1 >= 0) this.tAlcool1 = tAlcool1;
        } catch (Exception e) {
            System.out.println("O valor do ALCOOL 1 está incorreto, ele deve estar entre 0 e 1250!");
        }
        try {
            if(tAlcool2 < (MAX_ALCOOL/2) && tAlcool2 >= 0) this.tAlcool2 = tAlcool2;
        } catch (Exception e) {
            System.out.println("O valor do ALCOOL 2 está incorreto, ele deve estar entre 0 e 1250!");
        }
    }

    public void defineSituacao() {
        if ( tAditivo >= (MAX_ADITIVO / 2) &&
                tGasolina >= (MAX_GASOLINA / 2) &&
                tAlcool1 >= ((MAX_ALCOOL / 2) / 2) &&
                tAlcool2 >= ((MAX_ALCOOL / 2) / 2) )
        { situacao = SITUACAO.NORMAL; }

        if ( tAditivo < (MAX_ADITIVO * 0.25) ||
                tGasolina < (MAX_GASOLINA * 0.25) ||
                tAlcool1 < ((MAX_ALCOOL/2) * 0.25) ||
                tAlcool2 < ((MAX_ALCOOL/2) * 0.25) )
        { situacao = SITUACAO.EMERGENCIA; }

        if (situacao != SITUACAO.NORMAL && situacao != SITUACAO.EMERGENCIA) {
            situacao = SITUACAO.SOBRAVISO;
        }
    }

    public int recebeAditivo(int qtdade) {
        try {
            if ((tAditivo = tAditivo + qtdade) <= MAX_ADITIVO) {
                tAditivo = tAditivo + qtdade;
                defineSituacao();
                return tAditivo;
            }
        } catch (Exception e) {
            return -1;
        }
        tAditivo *= 100;
        tAditivo += qtdade*100;
        tAditivo /= 100;
        defineSituacao();
        return qtdade;
    }

    public int recebeGasolina(int qtdade) {
        try {
            if ((tGasolina = tGasolina + qtdade) <= MAX_GASOLINA){
                tGasolina = tGasolina + qtdade;
                defineSituacao();
                return tGasolina;
            }
        } catch (Exception e) {
            return -1;
        }
        tGasolina*=100;
        tGasolina += qtdade*100;
        tGasolina /= 100;
        defineSituacao();
        return qtdade;
    }

    public int recebeAlcool(int qtdade) {
        int metadeQtdade = qtdade / 2;
        try {
            if (((tAlcool1 + tAlcool2) + qtdade) <= MAX_ALCOOL)
                tAlcool1 = tAlcool1 + metadeQtdade;
            tAlcool2 = tAlcool2 + metadeQtdade;
            defineSituacao();
        } catch (Exception e) {
            return -1;
        }
        int totalAlcool = tAlcool1 + tAlcool2;
        return totalAlcool;
    }

    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) {
        int[] lista = new int[4];

        if (qtdade <= 0) {
            lista[0] = -7;
            return lista;
        }
        double qtdadeAlcool, qtdadeGasolina, qtdadeAditivo;
        qtdadeAlcool = qtdade * 0.125 * 100;
        qtdadeGasolina = qtdade * 0.7 * 100;
        qtdadeAditivo = qtdade * 0.05 * 100;
        tAditivo *= 100;
        tGasolina *= 100;
        tAlcool1 *= 100;
        tAlcool2 *= 100;
        if (situacao == SITUACAO.NORMAL){
            if(tAlcool1<qtdadeAlcool || tAditivo<qtdadeAditivo || tGasolina<qtdadeGasolina){
                lista[0] = -21;
                tAlcool1 /= 100;
                tAlcool2 /= 100;
                tGasolina /= 100;
                tAditivo /= 100;
                return lista;
            }
            if (situacao == SITUACAO.SOBRAVISO){
                if(tipoPosto == TIPOPOSTO.COMUM){
                    qtdadeAlcool /= 2;
                    qtdadeGasolina/= 2;
                    qtdadeAditivo /= 2;
                }
                if(tAlcool1<qtdadeAlcool || tAditivo<qtdadeAditivo || tGasolina<qtdadeGasolina){
                    lista[0] = -21;
                    tAlcool1 /= 100;
                    tAlcool2 /= 100;
                    tGasolina /= 100;
                    tAditivo /= 100;
                    return lista;
                }
                tAlcool1 -= qtdadeAlcool;
                tAlcool2 -= qtdadeAlcool;
                tGasolina -= qtdadeGasolina;
                tAditivo -= qtdadeAditivo;
            }
            if (situacao == SITUACAO.EMERGENCIA){
                if(tipoPosto == TIPOPOSTO.COMUM){
                    lista[0] = -14;
                    tAlcool1 /= 100;
                    tAlcool2 /= 100;
                    tGasolina /= 100;
                    tAditivo /= 100;
                    return lista;
                }
                else {
                    qtdadeAlcool /=2;
                    qtdadeGasolina/=2;
                    qtdadeAditivo /=2;
                    if(tAlcool1<qtdadeAlcool || tAditivo<qtdadeAditivo || tGasolina<qtdadeGasolina){
                        lista[0] = -21;
                        tAlcool1 /= 100;
                        tAlcool2 /= 100;
                        tGasolina /= 100;
                        tAditivo /= 100;
                        return lista;
                    }
                    tAlcool1 -= qtdadeAlcool;
                    tAlcool2 -= qtdadeAlcool;
                    tGasolina -= qtdadeGasolina;
                    tAditivo -= qtdadeAditivo;
                }
            }
        }
        tAlcool1 /= 100;
        tAlcool2 /= 100;
        tGasolina /= 100;
        tAditivo /= 100;
        lista[0] = tAditivo;
        lista[1] = tGasolina;
        lista[2] = tAlcool1;
        lista[3] = tAlcool2;
        return lista;
    }

    public int gettAditivo() {
        return tAditivo;
    }

    public int gettGasolina() {
        return tGasolina;
    }

    public int gettAlcool1() {
        return tAlcool1;
    }

    public int gettAlcool2() {
        return tAlcool2;
    }

    public SITUACAO getSituacao(){
        return situacao;
    }
}