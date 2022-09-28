package ifsc.espindula.mypaint;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class Camada {
    private Paint m_EstiloTraco;
    private Path m_Traco;
    private FormatoTraco m_FormatoTraco;
    private PointF m_PontoInicial;
    private PointF m_PontoFinal;
    private int m_EspessuraTraco;

    public Camada() {
        this.m_EstiloTraco  = new Paint();
        this.m_Traco        = new Path();
        this.m_PontoInicial = new PointF();
        this.m_PontoFinal   = new PointF();
        this.m_FormatoTraco = FormatoTraco.LINHA;
        this.m_EstiloTraco.setStyle(Paint.Style.STROKE);
        this.m_EspessuraTraco = Espessura.MINIMO;
    }

    public Camada(Camada camada) {
        this.m_EstiloTraco  = new Paint(camada.getEstiloTraco());
        this.m_Traco        = new Path(camada.getTraco());
        this.m_PontoInicial = new PointF(camada.getPontoInicial().x, camada.getPontoInicial().y);
        this.m_PontoFinal   = new PointF(camada.getPontoFinal()  .x, camada.getPontoFinal()  .y);
        this.m_FormatoTraco = camada.getFormatoTraco();
        this.m_EstiloTraco.setStyle(Paint.Style.STROKE);
        this.m_EstiloTraco.setStrokeWidth(camada.getEspessuraTraco());
    }

    public Paint getEstiloTraco() {
        return this.m_EstiloTraco;
    }

    public Path getTraco() {
        return this.m_Traco;
    }

    public void setTraco(Path path) {
        this.m_Traco = path;
    }

    public FormatoTraco getFormatoTraco() {
        return this.m_FormatoTraco;
    }

    public void setFormatoTraco(FormatoTraco formatoTraco) {
        this.m_FormatoTraco = formatoTraco;
    }

    public PointF getPontoInicial() {
        return this.m_PontoInicial;
    }

    public void setPontoInicial(PointF pontoInicial) {
        this.m_PontoInicial = pontoInicial;
    }

    public PointF getPontoFinal() {
        return this.m_PontoFinal;
    }

    public void setPontoFinal(PointF pontoFinal) {
        this.m_PontoFinal = pontoFinal;
    }

    public int getEspessuraTraco() {
        return this.m_EspessuraTraco;
    }

    public void setEspessuraTraco(int espessuraTraco) {
        this.m_EspessuraTraco = espessuraTraco;
    }
}
