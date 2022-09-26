package ifsc.espindula.mypaint;

import android.graphics.Paint;
import android.graphics.Path;

public class Camada {
    private Paint m_EstiloTraco;
    private Path m_Traco;
    private FormatoTraco m_FormatoTraco;

    public Camada() {
        this.m_EstiloTraco = new Paint();
        this.m_Traco = new Path();
        this.m_FormatoTraco = FormatoTraco.LINHA;
        this.m_EstiloTraco.setStyle(Paint.Style.STROKE);
        this.m_EstiloTraco.setStrokeWidth(30);
    }

    public Camada(Camada camada) {
        this.m_EstiloTraco  = new Paint(camada.getEstiloTraco());
        this.m_Traco        = new Path(camada.getTraco());
        this.m_FormatoTraco = camada.getFormatoTraco();
        this.m_EstiloTraco.setStyle(Paint.Style.STROKE);
        this.m_EstiloTraco.setStrokeWidth(30);
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
}
