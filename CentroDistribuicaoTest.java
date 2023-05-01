import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.Test;

import static org.junit.Assert.*;

public class CentroDistribuicaoTest{
    @Test
    public void alcoolValido(){
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(5,70,12,12);
        assertEquals(centroDistribuicao.gettAlcool1(), centroDistribuicao.gettAlcool2());
    }

    @Test
    public void alcoolInvalido(){
        assertThrows(IllegalArgumentException.class, () -> new CentroDistribuicao<>(5,70,11,10));
    }
    @Test
    public void recebeAditivoInvalido(){
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(5,70,12,12);
        assertEquals(centroDistribuicao.recebeAditivo(-1),-1);
    }

    @Test
    public void recebeGasolinaInvalido(){
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(5,70,12,12);
        assertEquals(centroDistribuicao.recebeGasolina(-1),-1);
    }

    @Test
    public void recebeAlcoolInvalido(){
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(5,70,12,12);
        assertEquals(centroDistribuicao.recebeAlcool(-1),-1);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 300, 4000, 1000, 1000, 1",
            "999, 300, 4000, 1000, 1000, 2",
            "500, 300, 2000, 1000, 1000, 2",
            "499, 300, 2000, 1000, 1000, 3"
    })
    public void limitesGasolina(int input, int ad, int gas, int al1, int al2, int situacao){
        CentroDistribuicao cd = new CentroDistribuicao(ad, gas, al1, al2);
        cd.recebeGasolina(input);
        int res = 0;
        switch(cd.getSituacao()){
            case NORMAL-> res = 1;
            case SOBRAVISO-> res = 2;
            case EMERGENCIA-> res = 3;
        }
        assertEquals(situacao, res);
    }

    @ParameterizedTest
    @CsvSource({
            "50, 200, 10000, 1000, 1000, 1",
            "49, 200, 10000, 1000, 1000, 2",
            "25, 100, 10000, 1000, 1000, 2",
            "24, 100, 10000, 1000, 1000, 3"
    })
    public void limitesAditivo(int input, int ad, int gas, int al1, int al2, int situacao){
        CentroDistribuicao cd = new CentroDistribuicao(ad, gas, al1, al2);
        cd.recebeAditivo(input);
        int res = 0;
        switch(cd.getSituacao()){
            case NORMAL-> res = 1;
            case SOBRAVISO-> res = 2;
            case EMERGENCIA-> res = 3;
        }
        assertEquals(situacao, res);
    }

    @ParameterizedTest
    @CsvSource({
            "50, 300, 10000, 600, 600, 1",
            "49, 300, 10000, 600, 600, 2",
            "26, 300, 10000, 300, 300, 2",
            "25, 300, 10000, 300, 300, 3"
    })
    public void limitesAlcool(int input, int ad, int gas, int al1, int al2, int situacao){
        CentroDistribuicao cd = new CentroDistribuicao(ad, gas, al1, al2);
        cd.recebeAlcool(input);
        int res = 0;
        switch(cd.getSituacao()){
            case NORMAL-> res = 1;
            case SOBRAVISO-> res = 2;
            case EMERGENCIA-> res = 3;
        }
        assertEquals(situacao, res);
    }

    @ParameterizedTest
    @CsvSource({
            "52, 100, 100, 100, 1, -14, 0, 0",
            "52, 100, 100, 100, 2, 98, 81, 96",
            "100, 300, 10000, 1000, 1, 295, 9930, 987",
            "100, 300, 10000, 1000, 2, 295, 9930, 987",
            "100, 200, 4000, 500, 2, 195, 3930, 487",
            "100, 200, 4000, 500, 1, 197, 3965, 493",
            "10000, 500, 6000, 1250, 2, -21, 0, 0",
            "10000, 500, 10000, 1000, 1, -21, 0, 0",
            "10000, 400, 7000, 1250, 1, -21, 0, 0",
            "-1, 300, 100, 100, 2, -7, 0, 0",
    })
    public void encomendas(int input, int ad, int gas, int al1, int tipoposto, int adres, int gasres, int alres){
        CentroDistribuicao cd = new CentroDistribuicao(ad, gas, al1, al1);
        int[] aux;
        if(tipoposto==1) aux = cd.encomendaCombustivel(input, CentroDistribuicao.TIPOPOSTO.COMUM);
        else aux = cd.encomendaCombustivel(input, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO);
        if(adres<0){
            assertEquals(adres, aux[0]);
            return;
        }
        int[] res = new int[4];
        res[0] = adres; res[1] = gasres; res[2] = alres; res[3] = alres;
        assertArrayEquals(res, aux);
    }

    @Test
    public void tanquecheioGas(){
        CentroDistribuicao cd = new CentroDistribuicao(1, 5000, 1, 1);
        int res = cd.recebeGasolina(50000000);
        assertEquals(5000, res);
    }
    @Test
    public void tanquecheioAlcool(){
        CentroDistribuicao cd = new CentroDistribuicao(1, 1, 1000, 1000);
        int res = cd.recebeAlcool(750);
        assertEquals(500, res);
    }

    @Test
    public void tanquecheioAditivo(){
        CentroDistribuicao cd = new CentroDistribuicao(400, 1, 1, 1);
        int res = cd.recebeAditivo(4000);
        assertEquals(100, res);
    }

    @Test
    public void nse(){
        CentroDistribuicao cd = new CentroDistribuicao(500, 10000, 1250, 1250);
        cd.encomendaCombustivel(6000, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO);
        assertEquals(CentroDistribuicao.SITUACAO.SOBRAVISO, cd.getSituacao());
        cd.encomendaCombustivel(2500, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO);
        assertEquals(CentroDistribuicao.SITUACAO.EMERGENCIA, cd.getSituacao());
    }

    @Test
    public void nsn(){
        CentroDistribuicao cd = new CentroDistribuicao(500, 10000, 1250, 1250);
        cd.encomendaCombustivel(6000, CentroDistribuicao.TIPOPOSTO.COMUM);
        assertEquals(CentroDistribuicao.SITUACAO.SOBRAVISO, cd.getSituacao());
        cd.recebeAditivo(300);
        cd.recebeGasolina(200);
        cd.recebeAlcool(2000);
        assertEquals(CentroDistribuicao.SITUACAO.NORMAL, cd.getSituacao());
    }

    @Test
    public void nes(){
        CentroDistribuicao cd = new CentroDistribuicao(500, 10000, 1250, 1250);
        cd.encomendaCombustivel(10000, CentroDistribuicao.TIPOPOSTO.COMUM);
        assertEquals(CentroDistribuicao.SITUACAO.EMERGENCIA, cd.getSituacao());
        cd.recebeAditivo(200);
        cd.recebeGasolina(1000);
        cd.recebeAlcool(1000);
        assertEquals(CentroDistribuicao.SITUACAO.SOBRAVISO, cd.getSituacao());
    }

    @Test
    public void nen(){
        CentroDistribuicao cd = new CentroDistribuicao(500, 10000, 1250, 1250);
        cd.encomendaCombustivel(10000, CentroDistribuicao.TIPOPOSTO.COMUM);
        assertEquals(CentroDistribuicao.SITUACAO.EMERGENCIA, cd.getSituacao());
        cd.recebeAditivo(500);
        cd.recebeGasolina(4000);
        cd.recebeAlcool(2000);
        assertEquals(CentroDistribuicao.SITUACAO.NORMAL, cd.getSituacao());
    }
}