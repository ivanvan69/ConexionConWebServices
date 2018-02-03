 package es.ileon.conexionconwebservices;
 import android.app.Activity;
 import android.app.ProgressDialog;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.widget.ImageView;
 import android.widget.TextView;

 import java.util.ArrayList;


 public class MainActivity extends Activity {
     private TextView nome;
     private TextView sobrenome;
     private TextView email;
     private TextView endereco;
     private TextView cidade;
     private TextView estado;
     private TextView username;
     private TextView senha;
     private TextView registro;
     private TextView telefone;
     private ImageView foto;
     private ProgressDialog load;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         GetJson download = new GetJson();

         nome = (TextView)findViewById(R.id.textView5);
         sobrenome = (TextView)findViewById(R.id.textView11);
         email = (TextView)findViewById(R.id.textView8);
         endereco = (TextView)findViewById(R.id.textView7);
         cidade = (TextView)findViewById(R.id.textView4);
         estado = (TextView)findViewById(R.id.textView3);
         username = (TextView)findViewById(R.id.textView2);
         senha = (TextView)findViewById(R.id.textView10);
         registro = (TextView)findViewById(R.id.textView9);
         telefone = (TextView)findViewById(R.id.textView12);
         foto = (ImageView)findViewById(R.id.imageView);

         //Chama Async Task
         download.execute();

     }

     private class GetJson extends AsyncTask<Void, Void, ArrayList<PersonObj>> {

         @Override
         protected void onPreExecute(){
             load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
         }

         @Override
         protected ArrayList<PersonObj>  doInBackground(Void... params) {
             Utils util = new Utils();

             return util.getInformacao("https://randomuser.me/api/1.1?results=2&gender=male&nat=ES");
         }

         @Override
         protected void onPostExecute(ArrayList<PersonObj>  lista){
             PersonObj pessoa = lista.get(0);
             nome.setText(pessoa.getNome().substring(0,1).toUpperCase()+pessoa.getNome().substring(1));
             sobrenome.setText(pessoa.getSobrenome().substring(0,1).toUpperCase()+pessoa.getSobrenome().substring(1));
             email.setText(pessoa.getEmail());
             endereco.setText(pessoa.getEndereco());
             cidade.setText(pessoa.getCidade().substring(0,1).toUpperCase()+pessoa.getCidade().substring(1));
             estado.setText(pessoa.getEstado());
             username.setText(pessoa.getUsername());
             senha.setText(pessoa.getSenha());
             registro.setText(pessoa.getRegistro());
             telefone.setText(pessoa.getTelefone());
             foto.setImageBitmap(pessoa.getFoto());
             load.dismiss();
         }
     }
 }