public class CentroDistribuicao<rn> { 

    public enum SITUACAO { NORMAL, SOBRAVISO, EMERGENCIA }

    public enum TIPOPOSTO { COMUM, ESTRATEGICO } 

    private int tAditivo, tGasolina, tAlcool1, tAlcool2;
    private SITUACAO situacao;
    private TIPOPOSTO tipoposto;

    public static final int MAX_ADITIVO = 500; 

    public static final int MAX_ALCOOL = 2500; 

    public static final int MAX_GASOLINA = 10000;

    public CentroDistribuicao (int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) 
    { 
        this.tAditivo = tAditivo;
        this.tGasolina = tGasolina;
        this.tAlcool1 = tAlcool1;
        this.tAlcool2 = tAlcool2;
        defineSituacao();
        verificaAditivo(tAditivo);
        verificaGasolina(tGasolina);
        verificaAlcool(tAlcool1, tAlcool2);;
     } 

    public void defineSituacao(){ 
        if(tAditivo >= MAX_ADITIVO*0.5 || tGasolina >= MAX_GASOLINA*0.5 || tAlcool1 >= MAX_ALCOOL*0.25) situacao = SITUACAO.NORMAL;
        else if(tAditivo >= MAX_ADITIVO*0.25 || tGasolina >= MAX_GASOLINA*0.25 || tAlcool1 >= MAX_ALCOOL*0.125) situacao = SITUACAO.SOBRAVISO; 
        else situacao = SITUACAO.EMERGENCIA;
    } 

    public SITUACAO getSituacao(){ 
        return situacao;
     } 

    public int gettGasolina(){ 
        return tGasolina;
     } 

    public int gettAditivo(){ 
        return tAditivo;
     } 

    public int gettAlcool1(){ 
        return tAlcool1;
     } 

    public int gettAlcool2(){ 
        return tAlcool2;
     } 

     //retornam o que puderam armazenar

    public int recebeAditivo(int qtdade) { 
        if(qtdade<=0) return -1;
        int qtDisponivel = MAX_ADITIVO-tAditivo;
        if(qtdade>qtDisponivel) {
            tAditivo = MAX_ADITIVO;
            defineSituacao();
            return qtDisponivel;
        }
        tAditivo += qtdade;
        defineSituacao();
        return qtdade;
     } 

    public int recebeGasolina(int qtdade) { 
        if(qtdade<=0) return -1;
        int qtDisponivel = MAX_GASOLINA-tGasolina;
        if(qtdade>qtDisponivel){
            tGasolina = MAX_GASOLINA;
            defineSituacao();
            return qtDisponivel;
        }
        tGasolina += qtdade;
        defineSituacao();
        return qtdade;
     } 

    public int recebeAlcool(int qtdade) { 
        if(qtdade<=0) return -1;
        int qtDisponivel = MAX_ALCOOL - 2*tAlcool1;
        if(qtdade>qtDisponivel){
            tAlcool1 = MAX_ALCOOL/2;
            tAlcool2 = MAX_ALCOOL/2;
            defineSituacao();
            return qtDisponivel;
        }
        tAlcool1 += qtdade/2;
        tAlcool2 += qtdade/2;
        defineSituacao();
        return qtdade;
     } 

    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) 
    { 
        int[] a = new int[0];
        return a;
     } 

    public void verificaAditivo(int valor){
        if(valor>MAX_ADITIVO || valor <=0) 
        throw new IllegalArgumentException("Valor inválido para o aditivo!", null);
    }

    public void verificaGasolina(int valor){
        if(valor>MAX_GASOLINA || valor<=0){
            throw new IllegalArgumentException("Valor inválido para a gasolina!", null);
        }
    }

    public void verificaAlcool(int a1, int a2){
        if(a1!=a2 || a1<=0 || 2*a1>MAX_ALCOOL){
            throw new IllegalArgumentException("Valor inválido para o álcool!", null);
        }
    }


}