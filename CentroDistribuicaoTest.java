import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.Test;

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

    /*@Test
    public void limiteGasolinaOnPoint50(int quant){
        CentroDistribuicao cd = new CentroDistribuicao(300, 4000, 1000, 1000);
        cd.recebeGasolina(1000);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.NORMAL);
    }

    @Test
    public void limiteGasolinaOffPoint50(){
        CentroDistribuicao cd = new CentroDistribuicao(300, 4000, 1000, 1000);
        cd.recebeGasolina(999);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.SOBRAVISO);
    }
    @Test
    public void limiteGasolinaOnPoint25(){
        CentroDistribuicao cd = new CentroDistribuicao(300, 2000, 1000, 1000);
        cd.recebeGasolina(500);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.SOBRAVISO);
    }

    @Test
    public void limiteGasolinaOffPoint25(){
        CentroDistribuicao cd = new CentroDistribuicao(300, 2000, 1000, 1000);
        cd.recebeGasolina(499);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.EMERGENCIA);
    }

    @Test
    public void limiteAditivoOnPoint50(){
        CentroDistribuicao cd = new CentroDistribuicao(250, 10000, 1000, 1000);
        cd.recebeAditivo(50);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.NORMAL);
    }

    @Test
    public void limiteAditivoOffPoint50(){
        CentroDistribuicao cd = new CentroDistribuicao(250, 10000, 1000, 1000);
        cd.recebeAditivo(49);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.SOBRAVISO);
    }

    @Test
    public void limiteAditivoOnPoint25(){
        CentroDistribuicao cd = new CentroDistribuicao(125, 10000, 1000, 1000);
        cd.recebeAditivo(25);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.SOBRAVISO);
    }

    @Test
    public void limiteAditivoOffPoint25(){
        CentroDistribuicao cd = new CentroDistribuicao(125, 10000, 1000, 1000);
        cd.recebeAditivo(24);
        assertEquals(cd.getSituacao(), CentroDistribuicao.SITUACAO.NORMAL);
    }*/
}