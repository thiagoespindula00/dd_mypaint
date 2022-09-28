package ifsc.espindula.mypaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SimplePaint extends View {

    private Camada m_CamadaCorrente;
    private ArrayList<Camada> m_listaCamadasPrincipal;
    private ArrayList<Camada> m_listaCamadasRenderizando;

    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        m_CamadaCorrente           = new Camada();
        m_listaCamadasPrincipal    = new ArrayList<Camada>();
        m_listaCamadasRenderizando = new ArrayList<Camada>();
    }

    public Camada getCamadaCorrente() {
        return this.m_CamadaCorrente;
    }

    public void removeUltimaCamada() {
        if (this.m_listaCamadasPrincipal.size() == 0)
            return;

        this.m_listaCamadasPrincipal.remove(this.m_listaCamadasPrincipal.size() - 1);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Camada camada : m_listaCamadasPrincipal)
            canvas.drawPath(camada.getTraco(), camada.getEstiloTraco());


        for (Camada camada : m_listaCamadasRenderizando)
            canvas.drawPath(camada.getTraco(), camada.getEstiloTraco());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                m_CamadaCorrente.getTraco().moveTo(event.getX(), event.getY());
                m_CamadaCorrente.getPontoInicial().set(event.getX(), event.getY());
                m_CamadaCorrente.getPontoFinal().set(event.getX(), event.getY());
                desenhaTraco();
                m_listaCamadasRenderizando.add(new Camada(m_CamadaCorrente));
                break;

            case MotionEvent.ACTION_MOVE:
                m_CamadaCorrente.getPontoFinal().set(event.getX(), event.getY());
                desenhaTraco();
                m_listaCamadasRenderizando.add(new Camada(m_CamadaCorrente));
                break;

            case MotionEvent.ACTION_UP:
                m_CamadaCorrente.getPontoFinal().set(event.getX(), event.getY());
                desenhaTraco();

                if (!m_CamadaCorrente.getTraco().isEmpty())
                    m_listaCamadasPrincipal.add(new Camada(m_CamadaCorrente));

                m_listaCamadasRenderizando.clear();
                m_CamadaCorrente.setTraco       (new Path());
                m_CamadaCorrente.setPontoInicial(new PointF());
                m_CamadaCorrente.setPontoFinal  (new PointF());
                break;
        }
        invalidate();
        return true;
    }

    private void desenhaTraco() {
        switch (m_CamadaCorrente.getFormatoTraco()) {
            case LINHA:
                m_CamadaCorrente.getTraco().lineTo(m_CamadaCorrente.getPontoFinal().x, m_CamadaCorrente.getPontoFinal().y);
                break;

            case QUADRADO:
                m_listaCamadasRenderizando.clear();
                m_CamadaCorrente.setTraco(new Path());
                if (m_CamadaCorrente.getPontoFinal().x != 0 && m_CamadaCorrente.getPontoFinal().y != 0)
                    m_CamadaCorrente.getTraco().addRect(m_CamadaCorrente.getPontoInicial().x, m_CamadaCorrente.getPontoInicial().y, m_CamadaCorrente.getPontoFinal().x, m_CamadaCorrente.getPontoFinal().y, Path.Direction.CCW);
                break;

            case CIRCULO:
                m_listaCamadasRenderizando.clear();
                m_CamadaCorrente.setTraco(new Path());
                if (m_CamadaCorrente.getPontoFinal().x != 0 && m_CamadaCorrente.getPontoFinal().y != 0)
                    m_CamadaCorrente.getTraco().addCircle(m_CamadaCorrente.getPontoInicial().x, m_CamadaCorrente.getPontoInicial().y, calculaRaio(m_CamadaCorrente.getPontoInicial(), m_CamadaCorrente.getPontoFinal()), Path.Direction.CCW);
                break;
        }
    }

    private float calculaRaio(PointF pontoA, PointF pontoB) {
        return (float) Math.sqrt((Math.pow(pontoB.x - pontoA.x, 2)) + Math.pow(pontoB.y - pontoA.y, 2)) / 2;
    }
}
