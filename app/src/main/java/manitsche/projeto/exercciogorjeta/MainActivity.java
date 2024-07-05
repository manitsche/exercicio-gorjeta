package manitsche.projeto.exercciogorjeta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button bCalcular;
    EditText eValorTotalConta, eNumeroPessoas,ePorcentagemGorjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eValorTotalConta = findViewById(R.id.editText_valor_da_conta);
        eNumeroPessoas = findViewById(R.id.editText_numero_de_pessoas);
        ePorcentagemGorjeta = findViewById(R.id.editTex_porcentagem_da_gorjeta);
        bCalcular = findViewById(R.id.button_calcular);

        bCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularValor();
            }
        });
    }

    private void calcularValor() {
        String valorTotalContaString = eValorTotalConta.getText().toString();
        String numeroPessoasString = eNumeroPessoas.getText().toString();
        String porcentagemGorgetaString = ePorcentagemGorjeta.getText().toString();

        if (valorTotalContaString.isEmpty() || numeroPessoasString.isEmpty() || porcentagemGorgetaString.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valorConta = Double.parseDouble(valorTotalContaString);
            int numeroPessoas = Integer.parseInt(numeroPessoasString);
            double porcentagemGorgeta = Double.parseDouble(porcentagemGorgetaString);

            double valorTotalComGorgeta = valorConta + (valorConta * (porcentagemGorgeta / 100));
            double valorPorPessoaComGorgeta = valorTotalComGorgeta / numeroPessoas;
            double valorPorPessoaSemGorgeta = valorConta / numeroPessoas;

            abrirDialog("Você gostaria de saber o valor por pessoa com ou sem gorjeta?", valorPorPessoaComGorgeta, valorPorPessoaSemGorgeta);

        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Erro ao converter valores. Verifique o formato.", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirDialog(String mensagem, double valorComGorjeta, double valorSemGorjeta) {

        // Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        // Configurar título e mensagem
        dialog.setTitle("Atenção");
        dialog.setMessage(mensagem);

        // Configurar cancelamento do AlertDialog
        dialog.setCancelable(false);

        // Configurar ícone
        dialog.setIcon(android.R.drawable.stat_sys_warning);

        // Configurar ações para sim e não
        dialog.setPositiveButton("Com Gorjeta", (dialogInterface, which) -> {
            Toast.makeText(getApplicationContext(),
                    String.format("Valor por pessoa com gorjeta: R$ %.2f", valorComGorjeta),
                    Toast.LENGTH_LONG).show();
        });

        dialog.setNegativeButton("Sem Gorjeta", (dialogInterface, which) -> {
            Toast.makeText(getApplicationContext(),
                    String.format("Valor por pessoa sem gorjeta: R$ %.2f", valorSemGorjeta),
                    Toast.LENGTH_LONG).show();
        });

        dialog.create();
        dialog.show();
    }
}