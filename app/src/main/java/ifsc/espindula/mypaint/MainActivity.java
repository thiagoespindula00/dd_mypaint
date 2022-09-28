package ifsc.espindula.mypaint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class MainActivity extends AppCompatActivity {

    private ImageView m_ivDesfazerUltimaCamada;
    private ImageView m_ivColorPicker;
    private SimplePaint m_SimplePaint;
    private RadioGroup m_rgFormatosTraco;
    private SeekBar m_sbEspessuraTraco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_ivDesfazerUltimaCamada = findViewById(R.id.ivDesfazerUltimaCamada);
        m_ivColorPicker          = findViewById(R.id.ivColorPicker);
        m_SimplePaint            = findViewById(R.id.simplePaint);
        m_rgFormatosTraco        = findViewById(R.id.rg_FormatosTraco);
        m_sbEspessuraTraco       = findViewById(R.id.sbEspessuraTraco);

        m_ivDesfazerUltimaCamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_SimplePaint.removeUltimaCamada();
            }
        });

        m_ivColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ColorPickerDialog.Builder(MainActivity.this)
                        .setTitle(R.string.seletor_de_cores)
                        .setPositiveButton(getString(R.string.selecionar),
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        m_ivColorPicker.setColorFilter(envelope.getColor());
                                        m_SimplePaint.getCamadaCorrente().getEstiloTraco().setColor(envelope.getColor());
                                    }
                                })
                        .setNegativeButton(getString(R.string.cancelar),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                        .attachAlphaSlideBar(true) // the default value is true.
                        .attachBrightnessSlideBar(true)  // the default value is true.
                        .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                        .show();
            }
        });
        m_ivColorPicker.setColorFilter(m_SimplePaint.getCamadaCorrente().getEstiloTraco().getColor());

        m_rgFormatosTraco.check(R.id.rb_FormatoTracoLinha);
        m_rgFormatosTraco.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FormatoTraco formatoTraco = FormatoTraco.LINHA;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rb_FormatoTracoLinha   : formatoTraco = FormatoTraco.LINHA   ; break;
                    case R.id.rb_FormatoTracoQuadrado: formatoTraco = FormatoTraco.QUADRADO; break;
                    case R.id.rb_FormatoTracoCirculo : formatoTraco = FormatoTraco.CIRCULO ; break;
                }
                m_SimplePaint.getCamadaCorrente().setFormatoTraco(formatoTraco);
            }
        });

        m_sbEspessuraTraco.setMin(Espessura.MINIMO);
        m_sbEspessuraTraco.setMax(Espessura.MAXIMO);
        m_sbEspessuraTraco.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                m_SimplePaint.getCamadaCorrente().setEspessuraTraco(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}