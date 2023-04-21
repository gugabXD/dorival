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
        if(tAditivo > MAX_ADITIVO || tGasolina > MAX_GASOLINA || tAlcool1 > MAX_ALCOOL) throw new IllegalArgumentException("O sistema falhou em algum lugar!");
        else if(tAditivo >= MAX_ADITIVO*0.5 || tGasolina >= MAX_GASOLINA*0.5 || tAlcool1 >= MAX_ALCOOL*0.25) situacao = SITUACAO.NORMAL;
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
        tAditivo *= 100;
        tAditivo += qtdade*100;
        tAditivo /= 100;
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
        tGasolina*=100;
        tGasolina += qtdade*100;
        tGasolina /= 100;
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
        tAlcool1 *=100;
        tAlcool2 *=100;
        tAlcool1 += qtdade/2*100;
        tAlcool2 += qtdade/2*100;
        tAlcool1 /=100;
        tAlcool2 /=100;
        defineSituacao();
        return qtdade;
     } 

    //5% aditivo, 25% alcool, 75% gasolina
    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) 
    { 
        int[] aux = new int[4];
        if(qtdade<=0) {
            aux[0] = -7;
            return aux;
        }
        double qtAlcool, qtGasolina, qtAditivo;
        qtAlcool = qtdade*0.125*100;
        qtGasolina = qtdade*0.7*100;
        qtAditivo = qtdade*0.05*100;
        tAditivo *=100;
        tGasolina *=100;
        tAlcool1 *=100;
        tAlcool2 *=100;
        switch(situacao){
            case NORMAL->{
                if(combustivelInsuficiente(qtAditivo, qtGasolina, qtAlcool)){
                    aux[0] = -21;
                    divide();
                    return aux;
                }
                tAlcool1 -= qtAlcool;
                tAlcool2 -= qtAlcool;
                tGasolina -= qtGasolina;
                tAditivo -= qtAditivo;
            }
            case SOBRAVISO->{
                if(tipoPosto==TIPOPOSTO.COMUM){
                    qtAlcool /=2;
                    qtGasolina/=2;
                    qtAditivo /=2;
                }
                if(combustivelInsuficiente(qtAditivo, qtGasolina, qtAlcool)){
                    aux[0] = -21;
                    divide();
                    return aux;
                }
                tAlcool1 -= qtAlcool;
                tAlcool2 -= qtAlcool;
                tGasolina -= qtGasolina;
                tAditivo -= qtAditivo;
            }
            case EMERGENCIA->{
                if(tipoPosto==TIPOPOSTO.COMUM){
                    aux[0] = -14;
                    divide();
                    return aux;
                }
                else{
                    qtAlcool /=2;
                    qtGasolina/=2;
                    qtAditivo /=2;
                    if(combustivelInsuficiente(qtAditivo, qtGasolina, qtAlcool)){
                        aux[0] = -21;
                        divide();
                        return aux;
                    }
                    tAlcool1 -= qtAlcool;
                    tAlcool2 -= qtAlcool;
                    tGasolina -= qtGasolina;
                    tAditivo -= qtAditivo;
                }
            }
        }
        divide();
        aux[0] = tAditivo;
        aux[1] = tGasolina;
        aux[2] = tAlcool1;
        aux[3] = tAlcool2;
        return aux;
     } 

     public void divide(){
        tAlcool1 /=100;
        tAlcool2 /=100;
        tGasolina /=100;
        tAditivo /=100;
     }

     public boolean combustivelInsuficiente(double qtAditivo, double qtGasolina, double qtAlcool){
        if(tAlcool1<qtAlcool || tAditivo<qtAditivo || tGasolina<qtGasolina) return true;
        return false;
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