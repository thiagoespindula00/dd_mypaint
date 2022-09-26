package ifsc.espindula.mypaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
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
                desenhaTraco(event.getX(), event.getY());
                m_listaCamadasRenderizando.add(new Camada(m_CamadaCorrente));
                break;

            case MotionEvent.ACTION_MOVE:
                //desenhaTraco(event.getX(), event.getY());
                m_listaCamadasRenderizando.add(new Camada(m_CamadaCorrente));
                break;

            case MotionEvent.ACTION_UP:
                desenhaTraco(event.getX(), event.getY());
                m_listaCamadasPrincipal.add(new Camada(m_CamadaCorrente));
                m_listaCamadasRenderizando.clear();
                m_CamadaCorrente.setTraco(new Path());
                break;
        }
        invalidate();
        return true;
    }

    private void desenhaTraco(float x, float y) {
        switch (m_CamadaCorrente.getFormatoTraco()) {
            case LINHA:
                m_CamadaCorrente.getTraco().lineTo(x, y);
                break;

            case QUADRADO: {
                m_CamadaCorrente.getTraco().quadTo(10, 10, 30, 30);
            }
                break;

        }
    }
}
