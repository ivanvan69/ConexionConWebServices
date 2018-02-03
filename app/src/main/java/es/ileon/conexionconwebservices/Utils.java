package es.ileon.conexionconwebservices;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



/**
 * Created by ivanvan69 on 3/2/18.
 */

public class Utils {
    public ArrayList<PersonObj>  getInformacao(String end){
        String json;
        ArrayList<PersonObj>  retorno;
        json = NetworkUtil.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJson(json);

        return retorno;
    }

    private ArrayList<PersonObj>  parseJson(String json){
        try {
            ArrayList<PersonObj> listado =new ArrayList<PersonObj>();
            PersonObj pessoa = new PersonObj();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data;

            JSONObject objArray = array.getJSONObject(0);
            for(int i=0;i<array.length();i++) {
                Log.i("Resultado", "longArray:" + objArray.length());

                JSONObject obj = objArray.getJSONObject("user");
                //Atribui os objetos que estão nas camadas mais altas
                pessoa.setEmail(obj.getString("email"));
                pessoa.setUsername(obj.getString("username"));
                pessoa.setSenha(obj.getString("password"));
                pessoa.setTelefone(obj.getString("phone"));
                data = new Date(obj.getLong("dob") * 1000);
                pessoa.setNascimento(sdf.format(data));
                data = new Date(obj.getLong("registered") * 1000);
                pessoa.setRegistro(sdf.format(data));
                //Nome da pessoa é um objeto, instancia um novo JSONObject
                JSONObject nome = obj.getJSONObject("name");
                pessoa.setNome(nome.getString("first"));
                pessoa.setSobrenome(nome.getString("last"));

                //Endereco tambem é um Objeto
                JSONObject endereco = obj.getJSONObject("location");
                pessoa.setEndereco(endereco.getString("street"));
                pessoa.setEstado(endereco.getString("state"));
                pessoa.setCidade(endereco.getString("city"));

                //Imagem eh um objeto
                JSONObject foto = obj.getJSONObject("picture");
                pessoa.setFoto(baixarImagem(foto.getString("large")));
                listado.add(pessoa);
            }
            return listado;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap baixarImagem(String url) {
        try{
            URL endereco;
            InputStream inputStream;
            Bitmap imagem; endereco = new URL(url);
            inputStream = endereco.openStream();
            imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
